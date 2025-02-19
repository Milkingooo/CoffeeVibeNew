package com.example.coffeevibe.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(val context: Context) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun login(login: String, password: String, isLogin: (Boolean) -> Unit) {
        if (login.isNotBlank() && password.isNotBlank()) {
            auth.signInWithEmailAndPassword(login, password)
                .addOnSuccessListener {
                    isLogin(true)
                    Toast.makeText(context, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    isLogin(false)
                    Toast.makeText(context, "Авторизация не прошла", Toast.LENGTH_SHORT).show()
                }
        } else {
            isLogin(false)
            Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
        }
    }

    fun signUp(email: String, password: String, name: String, isSignUp: (Boolean) -> Unit) {
        if (email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && password.length >= 6) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    isSignUp(true)
                    addUserInDb(
                        id = auth.currentUser?.uid.toString(),
                        email = email,
                        password = password,
                        name = name
                    )
                    Toast.makeText(context, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    isSignUp(false)
                    Toast.makeText(context, "Регистрация не прошла", Toast.LENGTH_SHORT).show()
                }
        } else isSignUp(false)
    }

    private fun addUserInDb(
        name: String,
        email: String,
        password: String,
        id: String
    ) {
        viewModelScope.launch {
            db.collection("Client")
                .add(
                    hashMapOf(
                        "Name" to name,
                        "Email" to email,
                        "Password" to password,
                        "Id" to id
                    )
                )
                .addOnSuccessListener {
                    Log.d("UserAdd", "User added")
                }
                .addOnFailureListener {
                    Log.d("UserAdd", it.message.toString())
                }
        }
    }

    suspend fun checkEmailInDb(email: String): Boolean {
        return try {
            !(db.collection("Client").whereEqualTo("Email", email).get().await().isEmpty)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(context, "Письмо отправлено", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Письмо не отправлено", Toast.LENGTH_SHORT).show()
            }
    }

    fun logout() {
        if (auth.currentUser != null) Toast.makeText(
            context,
            "Вы вышли из аккаунта",
            Toast.LENGTH_SHORT
        ).show()
        auth.signOut()
    }

    fun giveUserName(getName: (String) -> Unit) {
        db.collection("Client")
            .whereEqualTo("Id", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    getName(document.getString("Name").toString())
                    Log.d("Name", document.getString("Name").toString())
                    break
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error getting user name", Toast.LENGTH_SHORT).show()
                Log.d("Name", "Error getting user name")
                getName("Нет имени :(")
            }
    }

    fun isLogin(): Boolean {
        return auth.currentUser != null
    }
}