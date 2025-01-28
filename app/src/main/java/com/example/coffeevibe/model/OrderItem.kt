package com.example.coffeevibe.model

data class OrderItem(
    val id: Int,
    val idClient: Int,
    val orderNum: Int,
    val quantity: Int,
    val date: String
)
