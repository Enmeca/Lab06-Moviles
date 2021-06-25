package com.cabegaira.lab06

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cabegaira.lab06.main.ItemsCRUD
import java.io.ByteArrayOutputStream

class Camera : AppCompatActivity() {

    private val cameraRequest = 1888
    lateinit var imageView: ImageView
    var dbSq: DatabaseHelper= DatabaseHelper(this);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)
/*        title = "KotlinApp"
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)
        imageView = findViewById(R.id.imageView)
        val photoButton: Button = findViewById(R.id.button)
        photoButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

        val Items: Button = findViewById(R.id.button_list)
        photoButton.setOnClickListener {
            val ListItems = Intent(this,ItemsCRUD::class.java)
            startActivity(ListItems)
        }*/
    }

    /*
    *
    * FIXME
    *  Se cae si da para atras
    *
    * */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest) {
            val photo = data?.extras!!.get("data") as Bitmap
            val stream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            Log.d("check",dbSq.insertData(byteArray).toString().plus(" "));
        }
    }

}