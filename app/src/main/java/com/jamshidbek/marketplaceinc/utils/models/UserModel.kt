package com.jamshidbek.marketplaceinc.utils.models

class UserModel {

    var id : String = ""
    var email : String = ""
    var password : String = ""
    var name : String = ""
    var surname : String = ""
    var phone : String = ""
    var city : String = ""

    constructor(
        id: String,
        email: String,
        password: String,
        name: String,
        surname: String,
        phone: String,
        city: String
    ) {
        this.id = id
        this.email = email
        this.password = password
        this.name = name
        this.surname = surname
        this.phone = phone
        this.city = city
    }
}