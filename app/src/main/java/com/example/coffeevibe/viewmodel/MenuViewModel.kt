package com.example.coffeevibe.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeevibe.database.FirebaseDao
import com.example.coffeevibe.model.Location
import com.example.coffeevibe.model.MenuItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MenuViewModel(val context: Context) : ViewModel() {
    private val firestore = Firebase.firestore

    private val _dataList = MutableStateFlow<List<MenuItem>>(emptyList())
    val dataList: StateFlow<List<MenuItem>> = _dataList.asStateFlow()


    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = firestore.collection("Good").get().await()
                val items = snapshot.documents.mapNotNull { item ->
                    try {
                        MenuItem(
                            id = item.data?.get("Id").toString().toInt(),
                            name = item.data?.get("Name").toString(),
                            price = item.data?.get("Price").toString().toInt(),
                            category = item.data?.get("Category").toString(),
                            description = item.data?.get("Description").toString(),
                            image = item.data?.get("Image").toString(),
                            status = item.data?.get("Status").toString(),
                        )
                    }
                    catch (e: Exception) {
                        Log.e("MyViewModel", "Error loading data", e)
                        null
                    }
                }
                Log.d("MyViewModel", "Loaded data: $items")
                withContext(Dispatchers.Main) {
                    _dataList.value = items
                }
                Log.d("MyViewModel", "Loaded data: $items")
            } catch (e: Exception) {
                Log.e("MyViewModel", "Error loading data", e)
            }
        }
    }

    fun getLocations(locations: (List<Location>) -> Unit){
        val loc: MutableList<Location> = mutableListOf()

        viewModelScope.launch {
            val snapshot = firestore.collection("Location").get().await()
            snapshot.documents.mapNotNull { item ->
                try {
                    loc.add(Location(
                        id = item.data?.get("Id").toString().toInt(),
                        address = item.data?.get("Address").toString()
                    ))
                }
                catch (e: Exception) {
                    Log.e("MyViewModel", "Error loading data", e)
                    null
                }

            }
        }
        locations(loc)
    }
}