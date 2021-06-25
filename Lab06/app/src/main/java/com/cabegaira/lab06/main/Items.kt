package com.cabegaira.lab06.main

import android.graphics.Bitmap
import java.io.Serializable

class Items: Serializable {
    var id:Int=0
    var desc:String=""
    var Img_item:Bitmap? = null

    internal constructor(id: Int, desc: String, Img_item: Bitmap?) {
        this.id = id
        this.desc = desc
        this.Img_item = Img_item
    }
}