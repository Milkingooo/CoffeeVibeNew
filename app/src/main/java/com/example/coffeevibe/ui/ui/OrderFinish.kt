package com.example.coffeevibe.ui.ui

import android.widget.Toast
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.model.Location
import com.example.coffeevibe.repository.CartRepository
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.utils.AuthUtils
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderFinishViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel


@Composable
fun OrderFinish(
    onBackPressed: () -> Unit,
    orderVm: OrderViewModel,
    menuVm: MenuViewModel,
    orderFinishVm: OrderFinishViewModel
) {
    val items by orderVm.itemList.collectAsState()
    var locations: List<Location> = emptyList()
    var placeSelected by remember { mutableStateOf(0) }
    menuVm.getLocations { locations = it }
    val isUserAuth = AuthUtils.isUserAuth()
    val totalPrice by orderVm.total.collectAsState()
    val context = LocalContext.current

    CoffeeVibeTheme(content = {
        Scaffold(
            modifier = Modifier
                .background(colorScheme.background),
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Оформление",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 26.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()

                    )

                    IconButton(
                        onClick = {
                            onBackPressed()
                        }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Localized description",
                            tint = colorScheme.onBackground,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OrderPlaced(
                    place = locations
                ) {
                    placeSelected = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Товары",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(5.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items, key = { it.id }) {
                        OrderFinishItem(
                            name = it.name,
                            price = it.price,
                            image = it.image,
                            quantity = it.quantity
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Оплата при получении",
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = {
                        if(placeSelected != 0 && placeSelected != 0) {
                            orderFinishVm.createOrder(
                                idUser = AuthUtils.getUserId()!!,
                                idAddress = placeSelected,
                                totalPrice = totalPrice,
                                items = items,
                            )
                            Toast.makeText(context, "Заказ оформлен", Toast.LENGTH_SHORT).show()
                            orderVm.deleteAllItems()
                            onBackPressed()
                        }
                    },
                    colors = if (isUserAuth && placeSelected != 0) {
                        ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary,
                            contentColor = colorScheme.onBackground
                        )
                    } else {
                        ButtonDefaults.buttonColors(
                            containerColor = colorScheme.surface,
                            contentColor = colorScheme.onBackground
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = isUserAuth
                ) {
                    Text(
                        "Заказать",
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                        fontSize = 18.sp
                    )
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun OrderFinishPreview() {
    val context = LocalContext.current
    val passwordDb = CartDatabase.getDatabase(context)
    val passwordDao = passwordDb.cartDao()
    val repository = CartRepository(passwordDao)
    val orderViewModel = OrderViewModel(repository, context)
    val menuVm = MenuViewModel(context)
    val orderFinVm = OrderFinishViewModel()
    OrderFinish({}, orderViewModel, menuVm = menuVm, orderFinishVm = orderFinVm)
}

@Composable
fun OrderPlaced(
    place: List<Location>,
    selectedPlace: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedPlace by remember { mutableStateOf("Выбрать место получения") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                expanded = !expanded
            }
            .animateContentSize()
            .height(if (expanded) 250.dp else 55.dp)
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedPlace,
                    color = colorScheme.onBackground,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_black)),
                    fontSize = 16.sp
                )

                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = "Login",
                    tint = colorScheme.onBackground,
                )
            }
            if (expanded) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(place, key = { it.id }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem()
                                .height(55.dp)
                                .clickable {
                                    selectedPlace(it.id)
                                    expanded = !expanded
                                    selectedPlace = it.address
                                },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = it.address,
                                color = colorScheme.onBackground,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                            )

                            Icon(
                                Icons.Filled.Place,
                                contentDescription = "Login",
                                tint = colorScheme.onBackground,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )

                        }
                    }
                }
            }
        }
    }
}