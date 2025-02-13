package com.example.coffeevibe.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeevibe.database.CartEntity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import kotlin.random.Random

@SuppressLint("SimpleDateFormat")
class OrderFinishViewModel : ViewModel() {
    private val firestore = Firebase.firestore


    fun createOrder(idUser: String, idAddress: Int, totalPrice: Int, items: List<CartEntity>) {
        val idOrder = Random.nextInt(0, 1001).toString()

        try {
            firestore.collection("Order").document(idOrder).set(
                mapOf(
                    "Date" to Timestamp.now(),
                    "IdClient" to idUser,
                    "IdLocation" to idAddress,
                    "Status" to "Создан",
                    "TotalPrice" to totalPrice
                )
            )
                .addOnSuccessListener {
                    items.forEach {
                        val idOrder2 = UUID.randomUUID().toString()
                        firestore.collection("OrderItem").document(idOrder2).set(
                            mapOf(
                                "IdOrder" to idOrder,
                                "IdGood" to it.idItem,
                                "Quantity" to it.quantity,
                            )
                        )
                    }
                }
        }
        catch (e: Exception) {
            Log.d("ErrorCreateOrder", e.toString())
        }
    }

    private fun isOrderNumUsed(idOrder: Int): Boolean {
        viewModelScope.launch {
            val snapshot = firestore
                .collection("Order")
                .get()
                .await()
            snapshot.documents.mapNotNull { item ->
                try {
                    return@mapNotNull item.id != idOrder.toString() || item.data?.get("Status").toString() != "Создан"
                } catch (e: Exception) {
                    Log.e("MyViewModel", "Error loading data", e)
                    null
                }
            }
        }
        return false
    }
}