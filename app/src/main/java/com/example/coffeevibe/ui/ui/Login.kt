package com.example.coffeevibe.ui.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    inReg: () -> Unit,
    onLogin: () -> Unit,
) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isInCorrect by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val loginVm = LoginViewModel(context)

    CoffeeVibeTheme(content = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Welcome back",
                color = colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_extrabold)),
                fontSize = 42.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Email",
                textAlign = TextAlign.Left,
                fontSize = 18.sp, // Используем стиль текста из темы
                modifier = Modifier.fillMaxWidth(),
                color = colorScheme.onBackground, // Цвет текста из темы
                fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black))
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorScheme.onBackground,   // Основной цвет для акцентов
                    unfocusedBorderColor = colorScheme.onSurface, // Цвет границ для неактивного состояния
                    unfocusedPlaceholderColor = colorScheme.onBackground,
                    focusedTextColor = colorScheme.onBackground,
                    unfocusedTextColor = colorScheme.onBackground,
                ),
                placeholder = { Text("Enter email", color = colorScheme.onSurface) },
                isError = isInCorrect,
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        Icons.Filled.AccountCircle,
                        contentDescription = "Login",
                        tint = colorScheme.onBackground
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Password",
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                color = colorScheme.onBackground,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black))
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorScheme.onBackground,
                    unfocusedBorderColor = colorScheme.onSurface,
                    unfocusedPlaceholderColor = colorScheme.onBackground,
                    focusedTextColor = colorScheme.onBackground,
                    unfocusedTextColor = colorScheme.onBackground,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = { Text("Enter password", color = colorScheme.onSurface) },
                isError = isInCorrect,
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        Icons.Filled.Password,
                        contentDescription = "Password",
                        tint = colorScheme.onBackground
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Forgot password?",
                textAlign = TextAlign.Left,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{
                        if (email.isNotBlank()) loginVm.sendPasswordResetEmail(email = email)
                        else Toast.makeText(context, "Enter email", Toast.LENGTH_SHORT).show()
                    },
                color = colorScheme.onBackground,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    loginVm.login(
                        login = email,
                        password = password,
                        isLogin = {
                            if(it) {
                                isInCorrect = false
                                onLogin()
                            }
                            else {
                                isInCorrect = true
                            }
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onBackground
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    "Log in",
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "New user? Sign Up",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{
                        inReg()
                    },
                color = colorScheme.onBackground,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium))
            )
        }
    })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginScreen({}, {})
}

