package com.cabegaira.lab06.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cabegaira.lab06.DatabaseHelper
import com.cabegaira.lab06.R
import kotlinx.android.synthetic.main.camera_activity.*
import com.cabegaira.lab06.main.ItemsCRUD
import java.io.ByteArrayOutputStream

class AddItems : AppCompatActivity(){
    private val cameraRequest = 1888
    lateinit var imageView: ImageView
    var dbSq: DatabaseHelper = DatabaseHelper(this);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)
        title = "Añadir item"
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)
        imageView = findViewById(R.id.item_foto)
        val photoButton: Button = findViewById(R.id.photoBtn)
        photoButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

        val Items: Button = findViewById(R.id.insertBtn)
        Items.setOnClickListener {
            InsertItem()
            val ListItems = Intent(this,ItemsCRUD::class.java)
            startActivity(ListItems)
            finish()
        }
        atras.setOnClickListener {
            val ListItems = Intent(this,ItemsCRUD::class.java)
            startActivity(ListItems)
            finish()
        }

    }

    /*
    *
    * FIXME
    *  Se cae si da para atras
    *
    * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest && resultCode == RESULT_OK) {
            val photo = data?.extras!!.get("data") as Bitmap

            val stream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            item_foto.setImageBitmap(photo)

            Log.d("check",dbSq.insertData(byteArray).toString().plus(" "));
        }
    }

    fun InsertItem() {
        try {
            var desc = desctxt.text.toString()
            var price = pricetxt.text.toString().toInt()
            var phone = phonetxt.text.toString()
            dbSq.insertItem(desc,price,phone)
            Toast.makeText(this, "ITEM Agregado", Toast.LENGTH_SHORT).show()
            finish()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        val ListItems = Intent(this,ItemsCRUD::class.java)
        startActivity(ListItems)
        finish()
    }
}