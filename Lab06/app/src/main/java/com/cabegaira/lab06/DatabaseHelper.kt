package com.cabegaira.lab06

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("Create Table $TABLE_IMG(ID INTEGER PRIMARY KEY AUTOINCREMENT,Item_img BLOB)")

        db.execSQL("Create Table $TABLE_DES(ID INTEGER PRIMARY KEY AUTOINCREMENT,img INTEGER, desc TEXT, price NUMERIC, phone TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMG)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DES)
        onCreate(db)
    }


    fun insertData(Category_img: ByteArray): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(CAL_NO1, Category_img)


        val result = db.insert(TABLE_IMG, null, cv)

        return if (result .equals( -1))
            false
        else
            true


    }

    fun getQuery(query : String?): Cursor {
        val db = this.writableDatabase
        var mCursor : Cursor = db.rawQuery(query, null)
        mCursor?.moveToFirst()
        return mCursor
    }

    fun getdata(): ByteArray {
        val db = writableDatabase
        val res = db.rawQuery("select * from " + TABLE_IMG, null)

        if (res.moveToFirst()) {
            do {
                return res.getBlob(0)
            } while (res.moveToNext())
        }
        return byteArrayOf()
    }

    fun Img_MAX() : Int{
        val db = writableDatabase
        val res = db.rawQuery("select MAX(ID) from "+ TABLE_IMG, null)
        if (res.moveToFirst()) {
            do {
                return res.getInt(0)
            } while (res.moveToNext())
        }

        return -1
    }

    fun insertItem(desc:String,price:Int,phone:String){

        val db = this.writableDatabase
        val contentValues = ContentValues()

        val img = Img_MAX()
        contentValues.put(DESC, desc)
        contentValues.put(IMG, img)
        contentValues.put(PRICE, price)
        contentValues.put(PHONE, phone)
        db.insert(TABLE_DES, null, contentValues)

    }
    companion object {
        private val DATABASE_NAME = "user.db"
        private val TABLE_IMG = "tbl_items"
        private val TABLE_DES = "tbl_desc"
        private val CAL_NO1 = "Item_img"
        private val IMG = "img"

        private val DESC = "desc"
        private val PRICE = "price"
        private val PHONE = "phone"
    }

}