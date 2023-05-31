package com.example.mainproject.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mainproject.data.models.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CowboysRepository {

    companion object {
        private const val BASE_URL = "http://45.144.64.179/cowboys/api/"
    }

    private val cowboysAPI: CowboysAPI

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        cowboysAPI = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CowboysAPI::class.java)
    }

    class SignInException : Exception()

    fun signIn(login: String, password: String): Result<String> {
        val signInResult = cowboysAPI.signIn(SignInBody(login, password)).execute()
        return if (signInResult.isSuccessful) {
            val response = signInResult.body()?.data
            if (response != null) {
                Result.success(response.accessToken)
            } else {
                Result.failure(SignInException())
            }
        } else {
            Result.failure(SignInException())
        }
    }

    fun getProducts(
        token: String,
        pageNumber: Int,
        pageSize: Int,
    ): Result<List<GetProductsResponse.Product>> {
        val header = "Bearer $token"
        val productsResponse = cowboysAPI.getProducts(header, pageNumber, pageSize).execute()
        return if (productsResponse.isSuccessful) {
            val response = productsResponse.body()?.data
            if (response != null) {
                Result.success(response)
            } else {
                Result.failure(RuntimeException())
            }
        } else {
            Result.failure(RuntimeException())
        }
    }

    fun getProduct(token: String, id: String): Result<GetProductResponse.Product> {
        val header = "Bearer $token"
        val productResponse = cowboysAPI.getProduct(header, id).execute()
        return if (productResponse.isSuccessful) {
            val response = productResponse.body()?.data
            if (response != null) {
                Result.success(response)
            } else {
                Result.failure(RuntimeException())
            }
        } else {
            Result.failure(RuntimeException())
        }
    }

    fun getProfile(token: String): Result<GetProfileResponse.Data.Profile> {
        val header = "Bearer $token"
        val profileResponse = cowboysAPI.getProfile(header).execute()
        return if (profileResponse.isSuccessful) {
            val response = profileResponse.body()?.data?.profile
            if (response != null) {
                Result.success(response)
            } else {
                Result.failure(RuntimeException())
            }
        } else {
            Result.failure(RuntimeException())
        }
    }

    fun getAvatar(token: String, id: String): Result<Bitmap> {
        val header = "Bearer $token"
        val avatarResponse = cowboysAPI.getAvatar(header, id).execute()
        return if (avatarResponse.isSuccessful) {
            val response = avatarResponse.body()
            if (response != null) {
                val bmp = BitmapFactory.decodeStream(response.byteStream())
                Result.success(bmp)
            } else {
                Result.failure(RuntimeException())
            }
        } else {
            Result.failure(RuntimeException())
        }
    }

    fun getOrders(
        token: String,
        pageNumber: Int,
        pageSize: Int,
    ): Result<List<GetOrdersResponse.Order>> {
        val header = "Bearer $token"
        val ordersResponse = cowboysAPI.getOrders(header, pageNumber, pageSize).execute()
        return if (ordersResponse.isSuccessful) {
            val response = ordersResponse.body()?.data
            if (response != null) {
                Result.success(response)
            } else {
                Result.failure(RuntimeException())
            }
        } else {
            Result.failure(RuntimeException())
        }
    }

    fun cancelOrder(token: String, orderId: String): Result<CancelOrderResponse.Order> {
        val header = "Bearer $token"
        val cancelOrderResponse = cowboysAPI.cancelOrder(header, orderId).execute()
        return if (cancelOrderResponse.isSuccessful) {
            val response = cancelOrderResponse.body()?.data
            if (response != null) {
                Result.success(response)
            } else {
                Result.failure(RuntimeException())
            }
        } else {
            Result.failure(RuntimeException())
        }
    }

    fun createOrder(
        token: String,
        productId: String,
        quantity: Int,
        size: String,
        house: String,
        apartment: String,
        etd: String,
    ): Result<Unit> {
        val header = "Bearer $token"
        val createOrderResponse = cowboysAPI.createOrder(
            header,
            CreateOrderBody(productId, quantity, size, house, apartment, etd)
        ).execute()
        return if (createOrderResponse.isSuccessful) {
            val response = createOrderResponse.body()?.data
            if (response != null) {
                Result.success(Unit)
            } else {
                Result.failure(RuntimeException())
            }
        } else {
            Result.failure(RuntimeException())
        }
    }

    fun changeProfile(
        token: String,
        name: String,
        surname: String,
        occupation: String,
        avatarId: String,
    ): Result<Unit> {
        val header = "Bearer $token"
        val operationName = "replace"
        val changeProfileResponse = cowboysAPI.changeProfile(
            header,
            listOf(
                ChangeProfileBody(operationName, "name", name),
                ChangeProfileBody(operationName, "surname", surname),
                ChangeProfileBody(operationName, "occupation", occupation),
                ChangeProfileBody(operationName, "avatarId", avatarId)
            )
        ).execute()
        return if (changeProfileResponse.isSuccessful) {
            val response = changeProfileResponse.body()?.data
            if (response != null) {
                Result.success(Unit)
            } else {
                Result.failure(RuntimeException())
            }
        } else {
            Result.failure(RuntimeException())
        }
    }

    fun changePhoto(token: String, image: ByteArray): Result<Unit> {
        val header = "Bearer $token"
        val body = MultipartBody.Part.createFormData(
            "uploadedFile",
            "photo",
            image.toRequestBody("image/*".toMediaTypeOrNull())
        )
        val uploadResponse =
            cowboysAPI.changePhoto(header, body)
                .execute()
        return if (uploadResponse.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(RuntimeException())
        }
    }
}