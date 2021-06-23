package com.cabegaira.lab06.sensormanagers

import android.app.Service
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.annotation.RequiresApi
import com.cabegaira.lab06.MainActivity
import com.cabegaira.lab06.MyApplication
import com.cabegaira.lab06.models.MySensorEvent
import com.cabegaira.lab06.models.SensorType

object LightSensorManager :
    HandlerThread("LightSensorManager"), SensorEventListener {
    private val TAG : String = "LightSensorManager"
    private var handler: Handler? = null
    private var sensorManager : SensorManager?= null
    private var sensor : Sensor?= null
    private var sensorExists = false
    private var sensorThread: HandlerThread? = null
    private var sensorHandler: Handler? = null


    init{
        sensorManager = (MainActivity.getApplicationContext().getSystemService(Service.SENSOR_SERVICE)) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Check sensor exists
        if (sensor != null) {
            sensorExists = true
        } else {
            sensorExists = false
        }
    }

    fun startSensor(){
        sensorThread = HandlerThread(TAG, Thread.NORM_PRIORITY)
        sensorThread!!.start()
        sensorHandler = Handler(sensorThread!!.getLooper()) //Blocks until looper is prepared, which is fairly quick
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL, sensorHandler)
    }


    fun stopSensor(){
        sensorManager!!.unregisterListener(this)
        sensorThread!!.quitSafely()
    }

    fun sensorExists() : Boolean{
        return sensorExists
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d(TAG, ""+accuracy)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.values.isNotEmpty()) {
            // ground!!.updateMe(event.values[1] , event.values[0])
            //Log.d(TAG, ""+event.values[1]+", "+event.values[0])
            //Log.d(TAG, "onSensorChanged: "+event.values[0])

            var msgEvent = MySensorEvent()
            msgEvent.type = SensorType.LIGHT
            msgEvent.value = event.values[0].toString()

            // Send message to MainActivity
            sendMessage(msgEvent)
        }
    }

    fun setHandler(handler: Handler){
        this.handler = handler
    }

    fun sendMessage(sensorEvent: MySensorEvent) {
        if (handler == null) return

        handler?.obtainMessage(sensorEvent.type.ordinal, sensorEvent)?.apply {
            sendToTarget()
        }
    }
}