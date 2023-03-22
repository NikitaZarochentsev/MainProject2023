package com.example.mainproject.data

import com.example.mainproject.data.models.Product
import kotlinx.coroutines.delay
import java.util.UUID

class MockRepository {

    companion object {
        private val productIds = (0..6).map {
            UUID.randomUUID().toString()
        }
    }

    suspend fun getProducts(): Result<List<Product>> {
        randomDelay()
        return randomResult(
            listOf(
                Product(
                    productIds[0],
                    "Nike Tampa Bay Buccaneers Super Bowl LV #1",
                    "Джерси",
                    9000,
                    "https://fanatics.frgimages.com/mens-nike-anthracite-tampa-bay-buccaneers-super-bowl-lv-champions-locker-room-trophy-collection-t-shirt_pi4201000_altimages_ff_4201948-690b50a0cc1f0e0d174ealt1_full.jpg?_hv=2&w=900"
                ),
                Product(
                    productIds[1],
                    "Nike Tampa Bay Buccaneers Super Bowl LV #2",
                    "Джерси",
                    9000,
                    "https://images.geminipremium.com/2021/10/nike-the-champions-tampa-bay-buccaneers-super-bowl-lv-t-shirt-Shirt.jpg"
                ),
                Product(
                    productIds[2],
                    "Nike Tampa Bay Buccaneers Super Bowl LV #3",
                    "Джерси",
                    9000,
                    "https://fanatics.frgimages.com/mens-nike-anthracite-tampa-bay-buccaneers-super-bowl-lv-champions-locker-room-trophy-collection-t-shirt_pi4201000_altimages_ff_4201948-690b50a0cc1f0e0d174ealt1_full.jpg?_hv=2&w=900"
                ),
                Product(
                    productIds[3],
                    "Nike Tampa Bay Buccaneers Super Bowl LV #4",
                    "Джерси",
                    9000,
                    "https://images.geminipremium.com/2021/10/nike-the-champions-tampa-bay-buccaneers-super-bowl-lv-t-shirt-Shirt.jpg"
                ),
                Product(
                    productIds[4],
                    "Nike Tampa Bay Buccaneers Super Bowl LV #5",
                    "Джерси",
                    9000,
                    "https://fanatics.frgimages.com/mens-nike-anthracite-tampa-bay-buccaneers-super-bowl-lv-champions-locker-room-trophy-collection-t-shirt_pi4201000_altimages_ff_4201948-690b50a0cc1f0e0d174ealt1_full.jpg?_hv=2&w=900"
                ),
                Product(
                    productIds[5],
                    "Nike Tampa Bay Buccaneers Super Bowl LV #6",
                    "Джерси",
                    9000,
                    "https://images.geminipremium.com/2021/10/nike-the-champions-tampa-bay-buccaneers-super-bowl-lv-t-shirt-Shirt.jpg"
                )
            ).drop((0..6).random())
        )
    }

    private suspend fun randomDelay() {
        delay((100L..1000L).random())
    }

    private fun <T> randomResult(data: T): Result<T> =
        if ((0..100).random() < 5) {
            Result.failure(RuntimeException())
        } else {
            Result.success(data)
        }
}