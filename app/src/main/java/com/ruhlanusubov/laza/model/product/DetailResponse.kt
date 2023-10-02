package com.ruhlanusubov.laza.model.product


import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("products")
    val products: List<Product>?,
    @SerializedName("skip")
    val skip: Int?,
    @SerializedName("total")
    val total: Int?
)