package com.cabegaira.lab06


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message

import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.cabegaira.lab06.models.MySensorEvent
import com.cabegaira.lab06.models.SensorType
import com.cabegaira.lab06.sensormanagers.GyroSensorManager
import com.cabegaira.lab06.sensormanagers.LightSensorManager
import com.cabegaira.lab06.sensormanagers.TempSensorManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {


    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun getApplicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Set handlers
        LightSensorManager.setHandler(handler)
        TempSensorManager.setHandler(handler)
        GyroSensorManager.setHandler(handler)

        // Register Button Clicks
        buttonStart.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (!LightSensorManager.sensorExists()){
                    tvLightSensorValue.text = "No Light Sensor"
                }
                else {
                    LightSensorManager.startSensor()
                }

                if (!TempSensorManager.sensorExists()){
                    tvTempSensorValue.text = "No Temperature Sensor"
                }
                else {
                    TempSensorManager.startSensor()
                }

                if (!GyroSensorManager.sensorExists()){
                    tvGyroSensorValue.text = "No Gyroscope Sensor"
                }
                else {
                    GyroSensorManager.startSensor()
                }
            }})

        buttonStop.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                LightSensorManager.stopSensor()
                tvLightSensorValue.text = ""

                TempSensorManager.stopSensor()
                tvTempSensorValue.text = ""

                GyroSensorManager.stopSensor()
                tvGyroSensorValue.text = ""
            }})
    }





    // Handle messages
    val handler: Handler = object : Handler(Looper.getMainLooper()) {
        /*
         * handleMessage() defines the operations to perform when
         * the Handler receives a new Message to process.
         */
        override fun handleMessage(inputMessage: Message) {
            // Gets the image task from the incoming Message object.
            val sensorEvent = inputMessage.obj as MySensorEvent

            // Light Sensor events
            if (sensorEvent.type == SensorType.LIGHT){
                tvLightSensorValue.text = sensorEvent.value
            }
            // Temperature events
            else if (sensorEvent.type == SensorType.TEMPERATURE){
                tvTempSensorValue.text = sensorEvent.value
            }
            // Gyroscope events
            else if (sensorEvent.type == SensorType.GYRO){
                tvGyroSensorValue.text = sensorEvent.value
            }
        }
    }
}