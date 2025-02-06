package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme

@Composable
fun MinimalDialog(onDismissRequest: () -> Unit,
                  description: String,
                  image: String,
                  name: String
) {
    CoffeeVibeTheme(content = {
        Dialog(onDismissRequest = { onDismissRequest() }
        ) {
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
                    Image(
                        painter =
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = image).apply(block = fun ImageRequest.Builder.() {
                                crossfade(true) // Плавный переход при загрузке нового изображения
                            }).build()
                        ),
                        contentDescription = null, // Описание для доступности
                        modifier = Modifier
                        .width(250.dp)
                        .height(250.dp)
                            .clip(shape = RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Crop,
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    Text(text = name,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                        color = colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = description,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                        color = colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState()))
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun MinDialogPreview() {
    MinimalDialog(
        onDismissRequest = {},
        description = "Некоторое описание чего-то там, может быть даже полезное, но это вряд-ли :)",
        image = "",
        name = ""
    )
}