package com.cabegaira.lab06.main

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.biometrics.BiometricManager
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.cabegaira.lab06.DatabaseHelper
import com.cabegaira.lab06.R
import kotlinx.android.synthetic.main.fingerprint_login.*
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity(),SensorEventListener {
    var db: DatabaseHelper? = null
    private lateinit var executor : Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    var sensor : Sensor? = null
    var sensorManager : SensorManager? = null
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
    override fun onSensorChanged(event: SensorEvent?) {
        try {

            if (event!!.values[0] < 30 ) {
                Log.d("value : ",event!!.values[0].toString())
                Toast.makeText(this@LoginActivity, "Nivel de luz muy alto", Toast.LENGTH_SHORT ).show()

            } else {
                Toast.makeText(this@LoginActivity, "Nivel de luz muy bajo", Toast.LENGTH_SHORT ).show()
            }

        }
        catch (e : Exception)
        {

        }

    }
    override fun onCreate(savedInstanceState: Bundle?){
       super.onCreate(savedInstanceState)

       setContentView(R.layout.fingerprint_login)
        val intent = Intent(this, ItemsCRUD::class.java)
        db = DatabaseHelper(this)
        val id = db?.Img_MAX()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this@LoginActivity, executor, object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@LoginActivity, "Error de autenticacion", Toast.LENGTH_SHORT ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@LoginActivity, "Auntenticacion exitosa", Toast.LENGTH_SHORT ).show()
                startActivity(intent)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@LoginActivity, "Auntenticacion fallida", Toast.LENGTH_SHORT ).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticacion Biometrica")
            .setSubtitle("Inicio de sesion mediante huella")
            .setNegativeButtonText("Cancelar")
            .build()
        loginBtn.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)

        }
    }
    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}