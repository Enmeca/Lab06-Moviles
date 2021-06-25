package com.cabegaira.lab06.main

import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.cabegaira.lab06.R
import kotlinx.android.synthetic.main.fingerprint_login.*
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    private lateinit var executor : Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    override fun onCreate(savedInstanceState: Bundle?){
       super.onCreate(savedInstanceState)
       setContentView(R.layout.fingerprint_login)
        val intent = Intent(this, ItemsCRUD::class.java)

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
}