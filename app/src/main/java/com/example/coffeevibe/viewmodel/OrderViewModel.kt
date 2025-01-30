package com.example.coffeevibe.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.coffeevibe.model.OrderItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderViewModel() : ViewModel() {

    private val _itemList = MutableStateFlow(emptyList<OrderItem>())
    val itemList: Flow<List<OrderItem>> get() = _itemList.asStateFlow()

    fun removeItem(id: Int) {
        _itemList.update { currentItems ->
            currentItems.filterNot { it.id == id }
        }
    }

    fun isItemExist(id: Int): Boolean {
        return _itemList.value.any { it.id == id }
    }

    fun addItem(item: OrderItem) {
        _itemList.update { currentItems ->
            if (currentItems.contains(item)) {
                currentItems
            } else {
                currentItems.toMutableList().also { it.add(item) }
            }
        }
        Log.d("Order", _itemList.value.toString())
    }

    fun getTotalPrice(): Int {
        return _itemList.value.sumOf { it.price * it.quantity }
    }

    fun clearCart() {
        _itemList.update { emptyList() }
    }

    fun plusItem(item: OrderItem) {
        _itemList.update { currentItems ->
            currentItems.map { orderItem ->
                if (orderItem.id == item.id && orderItem.quantity < 10) {
                    orderItem.copy(quantity = orderItem.quantity + 1)
                } else {
                    orderItem
                }
            }
        }
    }

    fun minusItem(item: OrderItem) {
        _itemList.update { currentItems ->
            currentItems.map { orderItem ->
                if (orderItem.id == item.id && orderItem.quantity > 1) {
                    orderItem.copy(quantity = orderItem.quantity - 1)
                } else {
                    orderItem
                }
            }.filter { it.quantity > 0 }
        }
    }
}