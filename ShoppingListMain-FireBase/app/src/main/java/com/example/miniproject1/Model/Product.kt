package com.example.miniproject1.Model


data class Product(
//    @PrimaryKey(autoGenerate = true)
    var id: String = "new",
    val name: String,
    val price: Double,
    var quantity: Int,
    var isPurchased: Boolean
)