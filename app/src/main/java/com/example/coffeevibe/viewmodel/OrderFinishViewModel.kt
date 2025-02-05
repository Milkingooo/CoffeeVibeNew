package com.example.coffeevibe.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coffeevibe.database.CartEntity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import kotlin.random.Random

@SuppressLint("SimpleDateFormat")
class OrderFinishViewModel : ViewModel() {
    private val firestore = Firebase.firestore


    fun createOrder(idUser: String, idAddress: Int, totalPrice: Int, items: List<CartEntity>) {
        val IdOrder = UUID.randomUUID().toString()

        try {
            firestore.collection("Order").document(IdOrder).set(
                mapOf(
                    "Date" to Timestamp.now(),
                    "IdClient" to idUser,
                    "IdLocation" to idAddress,
                    "Status" to "Создан",
                    "TotalPrice" to totalPrice
                )
            )
            items.forEach {
                val IdOrder2 = UUID.randomUUID().toString()
                firestore.collection("OrderItem").document(IdOrder2).set(
                    mapOf(
                        "IdOrder" to IdOrder,
                        "IdGood" to it.idItem,
                        "Quantity" to it.quantity,
                    )
                )
            }
        }
        catch (e: Exception) {
            Log.d("ErrorCreateOrder", e.toString())
        }
    }

    private fun createOrderItems(items: List<CartEntity>, idOrder: String) {
        items.forEach {
            firestore.collection("OrderItem").document(idOrder).set(
                mapOf(
                    "IdOrder" to idOrder,
                    "IdGood" to it.idItem,
                    "Quantity" to it.quantity,
                )
            )
        }
    }
}