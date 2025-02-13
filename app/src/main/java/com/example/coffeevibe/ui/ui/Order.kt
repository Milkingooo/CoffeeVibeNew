package com.example.coffeevibe.ui.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.repository.CartRepository
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.utils.AuthUtils
import com.example.coffeevibe.viewmodel.OrderViewModel

@Composable
fun CartScreen(
    onCreateOrder: () -> Unit,
    orderVm: OrderViewModel
) {
    val haptic = LocalHapticFeedback.current
    val orderItems by orderVm.itemList.collectAsState()
    val totalPrice by orderVm.total.collectAsState()
    val context = LocalContext.current

    CoffeeVibeTheme(content = {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = { },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                if(!orderVm.isCartIsEmpty() && AuthUtils.isUserAuth()) {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    onCreateOrder()
                                }
                                else if(!AuthUtils.isUserAuth()) {
                                    Toast.makeText(context, "Пожалуйста авторизируйтесь", Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    Toast.makeText(context, "Корзина пуста", Toast.LENGTH_SHORT).show()
                                }
                            },
                            containerColor = if(!orderVm.isCartIsEmpty() && AuthUtils.isUserAuth()) colorScheme.primary else colorScheme.surface,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(10.dp)),

                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Payments,
                                    "Localized description",
                                    tint = colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "К оформлению",
                                    color = colorScheme.onBackground,
                                    fontFamily = FontFamily(Font(R.font.roboto_condensed_extrabold)),
                                    fontSize = 16.sp,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "$totalPrice₽",
                                    color = colorScheme.onBackground,
                                    fontFamily = FontFamily(Font(R.font.roboto_condensed_extrabold)),
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .background(color = colorScheme.background)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .height(85.dp)
                        .padding(start = 12.dp),
                    containerColor = colorScheme.background,
                )
            },
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Cart",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left
                    )

                    IconButton(onClick = { orderVm.deleteAllItems() }) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Localized description",
                            tint = colorScheme.onBackground,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(orderItems, key = { it.id }) {
                        CartItem(
                            name = it.name,
                            price = it.price,
                            image = it.image,
                            quantity = it.quantity,
                            onDelete = {
                                orderVm.deleteItem(it)
                            },
                            onPlus = {
                                if (it.quantity <= 9) {
                                    orderVm.updateItem(it, it.quantity + 1)
                                }
                                else haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            onMinus = {
                                if (it.price > 1) {
                                    orderVm.updateItem(it, it.quantity - 1)
                                } else {
                                    orderVm.deleteItem(it)
                                }
                            }
                        )
                    }
                }
            }
        }
    })
}

@Preview
@Composable
fun CartPreview() {
    val context = LocalContext.current
    val passwordDb = CartDatabase.getDatabase(context)
    val passwordDao = passwordDb.cartDao()
    val repository = CartRepository(passwordDao)
    val orderViewModel = OrderViewModel(repository, context)
    CartScreen(
        onCreateOrder = {},
        orderVm = orderViewModel
    )
}

@Composable
fun CartItem(
    name: String,
    price: Int,
    image: String,
    quantity: Int,
    onDelete: () -> Unit,
    onPlus: () -> Unit,
    onMinus: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(116.dp)
            .background(colorScheme.surface, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model =
                ImageRequest.Builder(LocalContext.current).data(data = image)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true) // Плавный переход при загрузке нового изображения
                    }).build(),
                contentDescription = null, // Описание для доступности
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(shape = RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = name,
                    color = colorScheme.onBackground,
                    modifier = Modifier.width(150.dp),
                    )
                Text(text = "$price руб.", color = colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onMinus()
                }) {
                    Icon(
                        Icons.Filled.Remove,
                        contentDescription = "Localized description",
                        tint = colorScheme.onBackground,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = quantity.toString(),
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
                )

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(onClick = {
                    onPlus()
                }) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Localized description",
                        tint = colorScheme.onBackground,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    )
                }
            }
        }
    }
}