package com.khadija.myfitapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class FitnessActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var accelerometerSensor: Sensor? = null
    private lateinit var textViewSteps: TextView

    private var steps = 0
    private var lastAcceleration = 0f



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness)

        textViewSteps = findViewById(R.id.textViewSteps)

        val buttonBack = findViewById<Button>(R.id.buttonBack)

        buttonBack.setOnClickListener {
            finish()
        }

        sensorManager =
            getSystemService(SENSOR_SERVICE) as SensorManager

        stepCounterSensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        accelerometerSensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if(stepCounterSensor == null){

            AlertDialog.Builder(this)
                .setTitle("Step Counter unavailable")
                .setMessage("This device does not support Step Counter. Accelerometer will be used instead.")
                .setPositiveButton("ok", null)
                .show()

        }
    }

    override fun onResume(){
        super.onResume()

        if(stepCounterSensor != null){

            sensorManager.registerListener(
                this,
                stepCounterSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }else if(accelerometerSensor != null){

            sensorManager.registerListener(
                this,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause(){
        super.onPause()

        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?){

        if(event == null){
            return
        }

        if (event.sensor.type == Sensor.TYPE_STEP_COUNTER){
            steps = event.values[0].toInt()

            textViewSteps.text = "Steps: $steps "
        }

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER){

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val acceleration =
                sqrt(x * x + y * y + z * z)

            if (acceleration > 10 && lastAcceleration <= 10) {
                steps++

                textViewSteps.text = "Steps: $steps"
            }

            lastAcceleration = acceleration
        }

    }

    override  fun onAccuracyChanged(sensor: Sensor?, accuracy: Int){

    }
}
