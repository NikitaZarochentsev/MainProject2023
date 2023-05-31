package com.example.mainproject.data

import com.example.mainproject.data.models.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface CowboysAPI {
    @PUT("user/signin")
    fun signIn(@Body signInBody: SignInBody): Call<SignInResponse>

    @GET("products")
    fun getProducts(
        @Header("Authorization")
        token: String,
        @Query("PageNumber")
        pageNumber: Int,
        @Query("PageSize")
        pageSize: Int,
    ): Call<GetProductsResponse>

    @GET("products/{id}")
    fun getProduct(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: String,
    ): Call<GetProductResponse>

    @GET("user")
    fun getProfile(
        @Header("Authorization")
        token: String,
    ): Call<GetProfileResponse>

    @GET("user/photo/{fileId}")
    fun getAvatar(
        @Header("Authorization")
        token: String,
        @Path("fileId")
        fileId: String,
    ): Call<ResponseBody>

    @GET("orders")
    fun getOrders(
        @Header("Authorization")
        token: String,
        @Query("PageNumber")
        pageNumber: Int,
        @Query("PageSize")
        pageSize: Int,
    ): Call<GetOrdersResponse>

    @PUT("orders/{orderId}")
    fun cancelOrder(
        @Header("Authorization")
        token: String,
        @Path("orderId")
        orderId: String,
    ): Call<CancelOrderResponse>

    @POST("orders/checkout")
    fun createOrder(
        @Header("Authorization")
        token: String,
        @Body
        createOrderBody: CreateOrderBody,
    ): Call<CreateOrderResponse>

    @PATCH("user")
    fun changeProfile(
        @Header("Authorization")
        token: String,
        @Body
        changeProfileBody: List<ChangeProfileBody>,
    ): Call<ChangeProfileResponse>

    @POST("user/photo")
    @Multipart
    fun changePhoto(
        @Header("Authorization")
        token: String,
        @Part
        image: MultipartBody.Part,
    ): Call<ResponseBody>
}