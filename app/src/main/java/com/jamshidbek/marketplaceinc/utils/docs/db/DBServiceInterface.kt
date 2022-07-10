package com.jamshidbek.marketplaceinc.utils.docs.db

import com.jamshidbek.marketplaceinc.utils.models.UserModel

interface DBServiceInterface {

    fun addData(model: UserModel)

    fun deleteData(model: UserModel)

    fun updateNote(model: UserModel) : Int

    fun getAllData() : ArrayList<UserModel>
}