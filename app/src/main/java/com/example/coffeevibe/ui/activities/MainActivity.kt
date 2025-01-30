package com.example.coffeevibe.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.coffeevibe.ui.activities.ui.theme.AccountActivity
import com.example.coffeevibe.ui.ui.AccountPreview
import com.example.coffeevibe.ui.ui.MenuScreen
import com.example.coffeevibe.viewmodel.OrderViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    private lateinit var orderVm: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseApp.initializeApp(this)

            MenuScreen(
                inCart = {
                    startActivity(Intent(this, CartActivity::class.java))
                },
                inAccount = {
                    startActivity(Intent(this, AccountActivity::class.java))
                }
            )
        }
    }
}
