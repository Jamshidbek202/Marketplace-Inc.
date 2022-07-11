package com.jamshidbek.marketplaceinc.utils.models

class UserModel {

    var uid : String = ""
    var name : String = ""
    var surname : String = ""
    var phone : String = ""
    var email : String = ""
    var password : String = ""
    var city : String = ""
    var imgUrl : String = ""

    constructor(
        uid: String,
        name: String,
        surname: String,
        phone: String,
        email: String,
        password: String,
        city: String,
        imgUrl: String
    ) {
        this.uid = uid
        this.name = name
        this.surname = surname
        this.phone = phone
        this.email = email
        this.password = password
        this.city = city
        this.imgUrl = imgUrl
    }

    constructor()
}