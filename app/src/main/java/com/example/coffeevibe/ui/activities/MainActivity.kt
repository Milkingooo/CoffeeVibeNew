package com.example.coffeevibe.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.coffeevibe.ui.ui.MainScreen
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseApp.initializeApp(this)
            MainScreen(
                onLogin = {
                    startActivity(Intent(this, LoginActivity::class.java))
                },
                inFinishOrder = {
                    startActivity(Intent(this, OrderActivity::class.java))
                }
            )
        }
    }
}
