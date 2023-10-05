package com.ruhlanusubov.laza.service

import com.ruhlanusubov.laza.model.cart.Cart
import com.ruhlanusubov.laza.model.cart.CartProduct
import com.ruhlanusubov.laza.model.cart.CartResponse
import com.ruhlanusubov.laza.model.category.CategoryList
import com.ruhlanusubov.laza.model.product.DetailResponse
import com.ruhlanusubov.laza.model.product.Product
import com.ruhlanusubov.laza.model.user.UserDetailsResponse
import com.ruhlanusubov.laza.model.user.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("productWithoutLimit")
    fun getMore():Call<DetailResponse>
    @GET("products")
    fun getProduct(
        @Query("limit") limit:Int)
    :Call<DetailResponse>

    @GET("products/categories")
    fun getCategory():Call<CategoryList>

    @GET("products/search")
    fun getSearch(
        @Query("q") q:String,
    ):Call<DetailResponse>

    @POST("auth/login")
    @FormUrlEncoded
    fun getUser(
        @Field("username") username:String,
        @Field("password") password:String
    ):Call<UserResponse>


    @GET("users/{id}")
    fun detail(
        @Path("id") id:Int
    ):Call<UserDetailsResponse>

    @GET("users/{id}/carts")
    fun cartDetail(
        @Path("id") id:Int
    ):Call<CartResponse>

}