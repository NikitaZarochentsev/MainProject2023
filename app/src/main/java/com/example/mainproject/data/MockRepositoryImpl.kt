package com.example.mainproject.data

import com.example.mainproject.domain.models.*
import com.example.mainproject.domain.repositories.MockRepository
import kotlinx.coroutines.delay
import java.util.UUID

class MockRepositoryImpl : MockRepository {

    companion object {
        private val productIds = (0..6).map {
            UUID.randomUUID().toString()
        }
        private val orderIds = (0..6).map {
            UUID.randomUUID().toString()
        }
        private val products = listOf(
            Product(
                id = productIds[0],
                title = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #1",
                department = "Джерси",
                price = 9000,
                badge = Badge(
                    value = "Хит сезона",
                    color = "#3C72BF"
                ),
                preview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                images = listOf(
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900",
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900"
                ),
                sizes = listOf(
                    Size(
                        value = "S",
                        isAvailable = true
                    ),
                    Size(
                        value = "M",
                        isAvailable = true
                    ),
                    Size(
                        value = "L",
                        isAvailable = true
                    ),
                    Size(
                        value = "XL",
                        isAvailable = true
                    )
                ),
                description = "The Tampa Bay Buccaneers are headed to Super Bowl LV! As a major fan, this is no surprise but it's definitely worth celebrating, especially after the unprecedented 2020 NFL season. Add this Tom Brady Game Jersey to your collection to ensure you're Super Bowl ready. This Nike gear features bold commemorative graphics that will let the Tampa Bay Buccaneers know they have the best fans in the league.",
                details = listOf(
                    "Material: 100% Polyester",
                    "Foam tongue helps reduce lace pressure.",
                    "Mesh in the upper provides a breathable and plush sensation that stretches with your foot.",
                    "Midfoot webbing delivers security. The webbing tightens around your foot when you lace up, letting you choose your fit and feel.",
                    "Nike React foam is lightweight, springy and durable. More foam means better cushioning without the bulk. A Zoom Air unit in the forefoot delivers more bounce with every step. It's top-loaded to be closer to your foot for responsiveness.",
                    "The classic fit and feel of the Pegasus is back—with a wider toe box to provide extra room. Seaming on the upper provides a better shape and fit, delivering a fresh take on an icon.",
                    "Officially licensed",
                    "Imported",
                    "Brand: Nike"
                )
            ),
            Product(
                id = productIds[1],
                title = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #2",
                department = "Джерси",
                price = 9000,
                badge = Badge(
                    value = "Хит сезона",
                    color = "#3C72BF"
                ),
                preview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                images = listOf(
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900",
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900"
                ),
                sizes = listOf(
                    Size(
                        value = "S",
                        isAvailable = true
                    ),
                    Size(
                        value = "M",
                        isAvailable = true
                    ),
                    Size(
                        value = "L",
                        isAvailable = true
                    ),
                    Size(
                        value = "XL",
                        isAvailable = true
                    )
                ),
                description = "The Tampa Bay Buccaneers are headed to Super Bowl LV! As a major fan, this is no surprise but it's definitely worth celebrating, especially after the unprecedented 2020 NFL season. Add this Tom Brady Game Jersey to your collection to ensure you're Super Bowl ready. This Nike gear features bold commemorative graphics that will let the Tampa Bay Buccaneers know they have the best fans in the league.",
                details = listOf(
                    "Material: 100% Polyester",
                    "Foam tongue helps reduce lace pressure.",
                    "Mesh in the upper provides a breathable and plush sensation that stretches with your foot.",
                    "Midfoot webbing delivers security. The webbing tightens around your foot when you lace up, letting you choose your fit and feel.",
                    "Nike React foam is lightweight, springy and durable. More foam means better cushioning without the bulk. A Zoom Air unit in the forefoot delivers more bounce with every step. It's top-loaded to be closer to your foot for responsiveness.",
                    "The classic fit and feel of the Pegasus is back—with a wider toe box to provide extra room. Seaming on the upper provides a better shape and fit, delivering a fresh take on an icon.",
                    "Officially licensed",
                    "Imported",
                    "Brand: Nike"
                )
            ),
            Product(
                id = productIds[2],
                title = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #3",
                department = "Джерси",
                price = 9000,
                badge = Badge(
                    value = "Хит сезона",
                    color = "#3C72BF"
                ),
                preview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                images = listOf(
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900",
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900"
                ),
                sizes = listOf(
                    Size(
                        value = "S",
                        isAvailable = true
                    ),
                    Size(
                        value = "M",
                        isAvailable = true
                    ),
                    Size(
                        value = "L",
                        isAvailable = true
                    ),
                    Size(
                        value = "XL",
                        isAvailable = true
                    )
                ),
                description = "The Tampa Bay Buccaneers are headed to Super Bowl LV! As a major fan, this is no surprise but it's definitely worth celebrating, especially after the unprecedented 2020 NFL season. Add this Tom Brady Game Jersey to your collection to ensure you're Super Bowl ready. This Nike gear features bold commemorative graphics that will let the Tampa Bay Buccaneers know they have the best fans in the league.",
                details = listOf(
                    "Material: 100% Polyester",
                    "Foam tongue helps reduce lace pressure.",
                    "Mesh in the upper provides a breathable and plush sensation that stretches with your foot.",
                    "Midfoot webbing delivers security. The webbing tightens around your foot when you lace up, letting you choose your fit and feel.",
                    "Nike React foam is lightweight, springy and durable. More foam means better cushioning without the bulk. A Zoom Air unit in the forefoot delivers more bounce with every step. It's top-loaded to be closer to your foot for responsiveness.",
                    "The classic fit and feel of the Pegasus is back—with a wider toe box to provide extra room. Seaming on the upper provides a better shape and fit, delivering a fresh take on an icon.",
                    "Officially licensed",
                    "Imported",
                    "Brand: Nike"
                )
            ),
            Product(
                id = productIds[3],
                title = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #1",
                department = "Джерси",
                price = 9000,
                badge = Badge(
                    value = "Хит сезона",
                    color = "#3C72BF"
                ),
                preview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                images = listOf(
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900",
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900"
                ),
                sizes = listOf(
                    Size(
                        value = "S",
                        isAvailable = true
                    ),
                    Size(
                        value = "M",
                        isAvailable = true
                    ),
                    Size(
                        value = "L",
                        isAvailable = true
                    ),
                    Size(
                        value = "XL",
                        isAvailable = true
                    )
                ),
                description = "The Tampa Bay Buccaneers are headed to Super Bowl LV! As a major fan, this is no surprise but it's definitely worth celebrating, especially after the unprecedented 2020 NFL season. Add this Tom Brady Game Jersey to your collection to ensure you're Super Bowl ready. This Nike gear features bold commemorative graphics that will let the Tampa Bay Buccaneers know they have the best fans in the league.",
                details = listOf(
                    "Material: 100% Polyester",
                    "Foam tongue helps reduce lace pressure.",
                    "Mesh in the upper provides a breathable and plush sensation that stretches with your foot.",
                    "Midfoot webbing delivers security. The webbing tightens around your foot when you lace up, letting you choose your fit and feel.",
                    "Nike React foam is lightweight, springy and durable. More foam means better cushioning without the bulk. A Zoom Air unit in the forefoot delivers more bounce with every step. It's top-loaded to be closer to your foot for responsiveness.",
                    "The classic fit and feel of the Pegasus is back—with a wider toe box to provide extra room. Seaming on the upper provides a better shape and fit, delivering a fresh take on an icon.",
                    "Officially licensed",
                    "Imported",
                    "Brand: Nike"
                )
            ),
            Product(
                id = productIds[4],
                title = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #1",
                department = "Джерси",
                price = 9000,
                badge = Badge(
                    value = "Хит сезона",
                    color = "#3C72BF"
                ),
                preview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                images = listOf(
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900",
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900"
                ),
                sizes = listOf(
                    Size(
                        value = "S",
                        isAvailable = true
                    ),
                    Size(
                        value = "M",
                        isAvailable = true
                    ),
                    Size(
                        value = "L",
                        isAvailable = true
                    ),
                    Size(
                        value = "XL",
                        isAvailable = true
                    )
                ),
                description = "The Tampa Bay Buccaneers are headed to Super Bowl LV! As a major fan, this is no surprise but it's definitely worth celebrating, especially after the unprecedented 2020 NFL season. Add this Tom Brady Game Jersey to your collection to ensure you're Super Bowl ready. This Nike gear features bold commemorative graphics that will let the Tampa Bay Buccaneers know they have the best fans in the league.",
                details = listOf(
                    "Material: 100% Polyester",
                    "Foam tongue helps reduce lace pressure.",
                    "Mesh in the upper provides a breathable and plush sensation that stretches with your foot.",
                    "Midfoot webbing delivers security. The webbing tightens around your foot when you lace up, letting you choose your fit and feel.",
                    "Nike React foam is lightweight, springy and durable. More foam means better cushioning without the bulk. A Zoom Air unit in the forefoot delivers more bounce with every step. It's top-loaded to be closer to your foot for responsiveness.",
                    "The classic fit and feel of the Pegasus is back—with a wider toe box to provide extra room. Seaming on the upper provides a better shape and fit, delivering a fresh take on an icon.",
                    "Officially licensed",
                    "Imported",
                    "Brand: Nike"
                )
            ),
            Product(
                id = productIds[5],
                title = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #1",
                department = "Джерси",
                price = 9000,
                badge = Badge(
                    value = "Хит сезона",
                    color = "#3C72BF"
                ),
                preview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                images = listOf(
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900",
                    "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3888000/altimages/ff_3888062-f848e302bef4a86eff9aalt3_full.jpg&w=900"
                ),
                sizes = listOf(
                    Size(
                        value = "S",
                        isAvailable = true
                    ),
                    Size(
                        value = "M",
                        isAvailable = true
                    ),
                    Size(
                        value = "L",
                        isAvailable = true
                    ),
                    Size(
                        value = "XL",
                        isAvailable = true
                    )
                ),
                description = "The Tampa Bay Buccaneers are headed to Super Bowl LV! As a major fan, this is no surprise but it's definitely worth celebrating, especially after the unprecedented 2020 NFL season. Add this Tom Brady Game Jersey to your collection to ensure you're Super Bowl ready. This Nike gear features bold commemorative graphics that will let the Tampa Bay Buccaneers know they have the best fans in the league.",
                details = listOf(
                    "Material: 100% Polyester",
                    "Foam tongue helps reduce lace pressure.",
                    "Mesh in the upper provides a breathable and plush sensation that stretches with your foot.",
                    "Midfoot webbing delivers security. The webbing tightens around your foot when you lace up, letting you choose your fit and feel.",
                    "Nike React foam is lightweight, springy and durable. More foam means better cushioning without the bulk. A Zoom Air unit in the forefoot delivers more bounce with every step. It's top-loaded to be closer to your foot for responsiveness.",
                    "The classic fit and feel of the Pegasus is back—with a wider toe box to provide extra room. Seaming on the upper provides a better shape and fit, delivering a fresh take on an icon.",
                    "Officially licensed",
                    "Imported",
                    "Brand: Nike"
                )
            )
        )
        private val orders = listOf(
            Order(
                id = orderIds[0],
                number = 1,
                productId = productIds[0],
                productPreview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                productQuantity = 1,
                productSize = "M",
                createdAt = "2021-08-09T18:31:42+03:00",
                etd = "2021-09-30T16:00:00+03:00",
                deliveryAddress = "г. Саранск, ул. Демократическая, 14",
                status = OrderStatus.in_work,
                productTitle = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #1"
            ),
            Order(
                id = orderIds[0],
                number = 2,
                productId = productIds[0],
                productPreview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                productQuantity = 4,
                productSize = "M",
                createdAt = "2021-08-09T18:31:42+03:00",
                etd = "2021-09-30T16:00:00+03:00",
                deliveryAddress = "г. Саранск, ул. Демократическая, 14",
                status = OrderStatus.cancelled,
                productTitle = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #1"
            ),
            Order(
                id = orderIds[1],
                number = 3,
                productId = productIds[1],
                productPreview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                productQuantity = 2,
                productSize = "L",
                createdAt = "2021-08-09T18:31:42+03:00",
                etd = "2021-09-30T16:00:00+03:00",
                deliveryAddress = "г. Саранск, ул. Демократическая, 14",
                status = OrderStatus.in_work,
                productTitle = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #2"
            ),
            Order(
                id = orderIds[1],
                number = 4,
                productId = productIds[1],
                productPreview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                productQuantity = 6,
                productSize = "L",
                createdAt = "2021-08-09T18:31:42+03:00",
                etd = "2021-09-30T16:00:00+03:00",
                deliveryAddress = "г. Саранск, ул. Демократическая, 14",
                status = OrderStatus.cancelled,
                productTitle = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #2"
            ),
            Order(
                id = orderIds[2],
                number = 5,
                productId = productIds[2],
                productPreview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                productQuantity = 2,
                productSize = "S",
                createdAt = "2021-08-09T18:31:42+03:00",
                etd = "2021-09-30T16:00:00+03:00",
                deliveryAddress = "г. Саранск, ул. Демократическая, 14",
                status = OrderStatus.in_work,
                productTitle = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #3"
            ),
            Order(
                id = orderIds[2],
                number = 6,
                productId = productIds[2],
                productPreview = "https://fanatics.frgimages.com/FFImage/thumb.aspx?i=/productimages/_3533000/ff_3533150-d9254664c08370f8572c_full.jpg&w=340",
                productQuantity = 8,
                productSize = "S",
                createdAt = "2021-08-09T18:31:42+03:00",
                etd = "2021-09-30T16:00:00+03:00",
                deliveryAddress = "г. Саранск, ул. Демократическая, 14",
                status = OrderStatus.cancelled,
                productTitle = "Men's Nike J.J. Watt Black Arizona Cardinals Legend Jersey #3"
            )
        )
    }

    private lateinit var token: String

    override suspend fun signIn(login: String, password: String): Result<Boolean> {
        randomDelay()
        token = "AAAAAAAAAAAAAAAAAAAAAFnz2wAAAAAACOwLSPtVT5gxxxxxxxxxxxx"
        return Result.success(true)
    }

    override suspend fun getProducts(): Result<List<Product>> {
        randomDelay()
        return randomResult(products)
    }

    override suspend fun getProfile(): Result<Profile> {
        randomDelay()
        return Result.success(
            Profile(
                "Олег",
                "Виноградов",
                "Разработчик",
                "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200"
            )
        )
    }

    override fun signOut() {
        token = ""
    }

    override suspend fun getProduct(productId: String): Result<Product> {
        randomDelay()
        return Result.success(products.first { it.id == productId })
    }

    override suspend fun getOrders(): Result<List<Order>> {
        randomDelay()
        return Result.success(orders)
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