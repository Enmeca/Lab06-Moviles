package com.cabegaira.lab06.main

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cabegaira.lab06.DatabaseHelper
import com.cabegaira.lab06.R
import kotlinx.android.synthetic.main.camera_activity.*
import kotlinx.android.synthetic.main.img_view.*
import java.io.ByteArrayOutputStream

class Img_Item: AppCompatActivity() {
    var db: DatabaseHelper? = null
    var img:Bitmap? = null
    var des:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.img_view)

        img = intent.getParcelableExtra<Bitmap>("img")
        des = intent.getSerializableExtra("desc") as String


/*        val photo = data?.extras!!.get("data") as Bitmap
        val stream = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()*/
        imgItem.setImageBitmap(img)
        desc.setText(des)
    }
}