package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme

@Composable
fun AccountScreen(
    onClose: () -> Unit,
    logOut: () -> Unit
) {
    CoffeeVibeTheme(content = {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(innerPadding)
            ) {
                Text(
                    text = "Account",
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_extrabold)),
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Milkingooo",
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(26.dp))

                SettingsSubCategory("Account settings") {}
                SettingsSubCategory("Support") {}

                Button(
                    onClick = {
                        logOut()
                    },
                    colors = ButtonDefaults.buttonColors(colorScheme.primary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp)
                ) {
                    Text(
                        "Log out",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_black))
                        )
                    )
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun AccountPreview() {
    AccountScreen(
        onClose = {},
        logOut = {}
    )
}

@Composable
fun SettingsSubCategory(name: String, action: () -> Unit) {
    CoffeeVibeTheme(content = {
        val colorScheme = MaterialTheme.colorScheme

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    action()
                }
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
            )

            Icon(
                Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Reset",
                tint = colorScheme.onBackground,
                modifier = Modifier.size(24.dp, 24.dp)
            )

        }

        Spacer(modifier = Modifier.size(10.dp))
    })
}