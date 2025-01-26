package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme

@Composable
fun MinimalDialog(onDismissRequest: () -> Unit) {
    CoffeeVibeTheme(content = {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface,
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        Icons.Filled.Fastfood,
                        contentDescription = "Localized description",
                        tint = colorScheme.onBackground,
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    Text(text = "Некоторое описание чего-то там, может быть даже полезное, но это вряд-ли :)",
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                        color = colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun MinDialogPreview() {
    MinimalDialog {}
}