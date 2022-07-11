package com.jamshidbek.marketplaceinc.utils.docs.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.jamshidbek.marketplaceinc.utils.models.UserModel

class DBHelper(context: Context) : SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.DB_VERSION), DBServiceInterface{

    override fun onCreate(p0: SQLiteDatabase?) {
        val query =
            "create table ${Constant.TABLE_NAME} (${Constant.ID} integer not null primary key autoincrement unique, " +
                    "${Constant.UID} text not null, ${Constant.NAME} text not null, ${Constant.SURNAME} text not null, ${Constant.PHONE} text not null, " +
                    "${Constant.EMAIL} text not null, ${Constant.PASSWORD} text not null," +
                    "${Constant.CITY} text not null, ${Constant.IMG_URL} text not null)"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists ${Constant.TABLE_NAME}")
        onCreate(p0)
    }

    override fun addData(model: UserModel) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constant.UID, model.uid)
        contentValues.put(Constant.NAME, model.name)
        contentValues.put(Constant.SURNAME, model.surname)
        contentValues.put(Constant.PHONE, model.phone)
        contentValues.put(Constant.EMAIL, model.email)
        contentValues.put(Constant.PASSWORD, model.password)
        contentValues.put(Constant.CITY, model.city)
        contentValues.put(Constant.IMG_URL, model.imgUrl)

        database.insert(Constant.TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun deleteData(model: UserModel) {
        val database = this.writableDatabase
        database.delete(Constant.TABLE_NAME, "${Constant.ID} = ?", arrayOf("0"))
        database.close()
    }

    override fun updateData(model: UserModel): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constant.UID, model.uid)
        contentValues.put(Constant.NAME, model.name)
        contentValues.put(Constant.SURNAME, model.surname)
        contentValues.put(Constant.PHONE, model.phone)
        contentValues.put(Constant.EMAIL, model.email)
        contentValues.put(Constant.PASSWORD, model.password)
        contentValues.put(Constant.CITY, model.city)
        contentValues.put(Constant.IMG_URL, model.imgUrl)

        return database.update(Constant.TABLE_NAME, contentValues, null, null)
    }

    override fun getAllData(): UserModel {

        var query = "select * from ${Constant.TABLE_NAME}"
        val database = this.readableDatabase
        var cursor = database.rawQuery(query, null)
        var user = UserModel()

        if (cursor.moveToFirst()) {
            do {
                val uid = cursor.getString(1)
                val name = cursor.getString(2)
                val surname = cursor.getString(3)
                val phone = cursor.getString(4)
                val email = cursor.getString(5)
                val password = cursor.getString(6)
                val city = cursor.getString(7)
                val imgUrl = cursor.getString(8)

                user = UserModel(uid, name, surname, phone, email, password, city, imgUrl)

            } while (cursor.moveToNext())
        }

        return user
    }
}