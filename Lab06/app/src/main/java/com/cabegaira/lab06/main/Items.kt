package com.cabegaira.lab06.main

import android.graphics.Bitmap
import java.io.Serializable

class Items: Serializable {
    var id:Int=0
    var desc:String=""
    var Img_item:Bitmap? = null
    var price:Int = 0

    internal constructor(id: Int, desc: String, Img_item: Bitmap?, price:Int) {
        this.id = id
        this.desc = desc
        this.Img_item = Img_item
        this.price = price
    }
}