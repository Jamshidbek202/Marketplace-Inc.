package com.jamshidbek.marketplaceinc.utils.models

class ItemModel {

    var userUID : String = ""
    var itemName : String = ""
    var itemImgUrl : String = ""
    var itemDesc : String = ""
    var itemCategory : String = ""
    var itemPrice : String = ""
    var itemCity : String = ""
    var itemPhone : String = ""

    constructor()

    constructor(
        userUID: String,
        itemName: String,
        itemImgUrl: String,
        itemDesc: String,
        itemCategory: String,
        itemPrice: String,
        itemCity: String,
        itemPhone: String
    ) {
        this.userUID = userUID
        this.itemName = itemName
        this.itemImgUrl = itemImgUrl
        this.itemDesc = itemDesc
        this.itemCategory = itemCategory
        this.itemPrice = itemPrice
        this.itemCity = itemCity
        this.itemPhone = itemPhone
    }
}