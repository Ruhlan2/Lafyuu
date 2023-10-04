package com.ruhlanusubov.laza.model.cart


import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("carts")
    val carts: List<Cart>?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("skip")
    val skip: Int?,
    @SerializedName("total")
    val total: Int?
)