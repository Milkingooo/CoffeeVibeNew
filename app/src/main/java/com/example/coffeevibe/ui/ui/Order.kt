package com.example.coffeevibe.ui.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.viewmodel.OrderViewModel

@Composable
fun CartScreen(
    onCreateOrder: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val orderVm = OrderViewModel()
    val orderItems by orderVm.itemList.collectAsState(emptyList())
    Log.d("Order", orderItems.toString())

    CoffeeVibeTheme(content = {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Spacer(modifier = Modifier.width(16.dp))

                        Text(text = "Total price: ${orderVm.getTotalPrice()} руб.",
                            color = colorScheme.onBackground,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_extrabold)),
                            fontSize = 22.sp,
                        )
//                        IconButton(onClick = { /* do something */ }) {
//                            Icon(
//                                Icons.Filled.DeleteSweep,
//                                contentDescription = "Localized description",
//                                tint = colorScheme.onBackground,
//                                modifier = Modifier.size(26.dp)
//                            )
//                        }

                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            containerColor = colorScheme.primary,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Icon(
                                Icons.Filled.Payments,
                                "Localized description",
                                tint = colorScheme.onBackground
                            )
                        }
                    },
                    modifier = Modifier
                        .background(color = colorScheme.background)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
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
                Text(
                    text = "Cart",
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
                    fontSize = 28.sp,
                    textAlign = TextAlign.Left
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(100, key = { it }) {
                        CartItem(
                            name = "orderItems[it].name",
                            price = 100,
                            image = "orderItems[it].image",
                            onDelete = {
                                //orderVm.removeItem(orderItems[it].id)
                            },
                            onPlus = {
                                //orderVm.plusItem(orderItems[it])
                            },
                            onMinus = {
                                //orderVm.minusItem(orderItems[it])
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
    CartScreen(
        onCreateOrder = {}
    )
}

@Composable
fun CartItem(name: String,
             price: Int,
             image: String,
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
            Icon(
                Icons.Filled.Fastfood,
                contentDescription = "Localized description",
                tint = colorScheme.onBackground,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                Text(text = "$name", color = colorScheme.onBackground)
                Text(text = "$price руб.", color = colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
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
                    text = "1",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
                )

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(onClick = { }) {
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