package com.jamshidbek.marketplaceinc.utils.models

class UserModel {

    var city : String = ""
    var email : String = ""
    var name : String = ""
    var password : String = ""
    var phone : String = ""
    var surname : String = ""
    var uid : String = ""

    constructor(
        id: String,
        email: String,
        password: String,
        name: String,
        surname: String,
        phone: String,
        city: String
    ) {
        this.uid = id
        this.email = email
        this.password = password
        this.name = name
        this.surname = surname
        this.phone = phone
        this.city = city
    }

    constructor()

}