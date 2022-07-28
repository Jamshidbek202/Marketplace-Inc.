package com.jamshidbek.marketplaceinc.utils.models

import java.io.Serializable

class ItemModel : Serializable{

    var userUID : String = ""
    var itemSeller : String = ""
    var itemName : String = ""
    var itemImgUrl : String = ""
    var itemDesc : String = ""
    var itemCategory : String = ""
    var itemPrice : String = ""
    var itemCurrency : String = ""
    var itemCity : String = ""
    var itemPhone : String = ""

    constructor()

    constructor(
        userUID: String,
        itemSeller: String,
        itemName: String,
        itemImgUrl: String,
        itemDesc: String,
        itemCategory: String,
        itemPrice: String,
        itemCurrency: String,
        itemCity: String,
        itemPhone: String
    ) {
        this.userUID = userUID
        this.itemSeller = itemSeller
        this.itemName = itemName
        this.itemImgUrl = itemImgUrl
        this.itemDesc = itemDesc
        this.itemCategory = itemCategory
        this.itemPrice = itemPrice
        this.itemCurrency = itemCurrency
        this.itemCity = itemCity
        this.itemPhone = itemPhone
    }
}