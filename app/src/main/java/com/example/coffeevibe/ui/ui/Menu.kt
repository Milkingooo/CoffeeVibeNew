package com.example.coffeevibe.ui.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.database.CartEntity
import com.example.coffeevibe.repository.CartRepository
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.utils.NetworkUtils
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuScreen(
    orderVm: OrderViewModel,
    menuViewModel: MenuViewModel
) {
    val context = LocalContext.current
    val networkAvailable by NetworkUtils.isNetworkAvailable(context).collectAsState(initial = true)
    val goods by menuViewModel.dataList.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isSearching by rememberSaveable { mutableStateOf(false) }
    var showInfo by rememberSaveable { mutableStateOf(false) }
    var selectedDescription by rememberSaveable { mutableStateOf("") }
    var selectedImage by rememberSaveable { mutableStateOf("") }
    var selectedName by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyGridState()
    val scrollState = rememberScrollState()
    val isOrderHas by menuViewModel.isOrderHas.collectAsState()
    val numAndPrice by menuViewModel.orderNP.collectAsState()

    Log.d("numAndPrice", numAndPrice)
    CoffeeVibeTheme(content = {
        Scaffold(
            modifier = Modifier.background(colorScheme.background),
        ) { innerPadding ->
            if (!networkAvailable) {
                NotInternet() { }
            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorScheme.background)
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isSearching) {
                            Text(
                                text = "Меню",
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                fontSize = 28.sp,
                                textAlign = TextAlign.Left,
                            )
                        }
                        AnimatedVisibility(
                            visible = isSearching,
                            enter = fadeIn() + expandHorizontally(),
                            exit = shrinkHorizontally() + fadeOut()
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = {
                                    searchQuery = it
                                },
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
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Search,
                                        contentDescription = "Search",
                                        tint = colorScheme.onBackground
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = "Поиск",
                                        color = colorScheme.onSurface
                                    )
                                },
                                shape = MaterialTheme.shapes.large,
                                singleLine = true,
                            )
                        }
                        IconButton(onClick = {
                            isSearching = !isSearching
                            searchQuery = ""
                        }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = colorScheme.onBackground,
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        state = listState,
                    ) {
                        val filteredGoods = if (searchQuery.isBlank()) {
                            goods
                        } else {
                            goods.filter {
                                it.name.contains(searchQuery, true)
                            }
                        }
                        if (filteredGoods.isNotEmpty()) {
                            if (isOrderHas) {
                                item(span = { GridItemSpan(2) }) {
                                    val np = numAndPrice.split("#")
                                    OrderNumber(
                                        number = np[0],
                                        price = np[1]
                                    )
                                }
                            }
                            val categories = goods.groupBy { it.category }

                            categories.forEach { (category, goods) ->

                                item(span = { GridItemSpan(2) }) {
                                    Text(
                                        text = category,
                                        color = colorScheme.onBackground,
                                        fontFamily = FontFamily(Font(R.font.roboto_condensed_medium)),
                                        fontSize = 28.sp,
                                        textAlign = TextAlign.Left,
                                    )
                                }

                                items(goods.size, key = { goods[it].id }) {
                                    ListItem(
                                        name = goods[it].name,
                                        price = goods[it].price,
                                        image = goods[it].image,
                                        onInfo = {
                                            showInfo = true
                                            selectedDescription = goods[it].description
                                            selectedImage = goods[it].image
                                            selectedName = goods[it].name
                                        },
                                        onAdd = {
                                            orderVm.addItem(
                                                id = goods[it].id,
                                                name = goods[it].name,
                                                price = goods[it].price,
                                                image = goods[it].image,
                                                quantity = 1
                                            )
                                        },
                                        onDelete = {
                                            orderVm.deleteItemById(goods[it].id)
                                        },
                                        isSelected = orderVm.isItemInCart(goods[it].id),
                                    )
                                    Log.d(
                                        "ListItem",
                                        goods[it].name + goods[it].price + goods[it].image
                                    )
                                }
                            }

                        }

                    }

                    if (showInfo) {
                        MinimalDialog(
                            onDismissRequest = {
                                showInfo = false
                                selectedDescription = ""
                                selectedImage = ""
                                selectedName = ""
                            },
                            description = selectedDescription,
                            image = selectedImage,
                            name = selectedName
                        )
                    }
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    val passwordDb = CartDatabase.getDatabase(context)
    val passwordDao = passwordDb.cartDao()
    val repository = CartRepository(passwordDao)
    val orderViewModel = OrderViewModel(repository, context)
    MenuScreen(orderVm = orderViewModel, menuViewModel = MenuViewModel(context))
}

@Composable
fun ListItem(
    name: String,
    price: Int,
    image: String,
    onInfo: () -> Unit,
    onAdd: () -> Unit,
    isSelected: Boolean = false,
    onDelete: () -> Unit,
) {

    CoffeeVibeTheme(content = {
        OutlinedCard(
            modifier = Modifier
                .width(150.dp)
                .height(300.dp)
                .animateContentSize()
                .clickable {
                    onInfo()
                },
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surface,
            ),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                if (isSelected) 2.dp else 1.dp,
                if (isSelected) colorScheme.secondary else colorScheme.onSurface
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {

                AsyncImage(
                    model =
                    ImageRequest.Builder(LocalContext.current).data(data = image)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true) // Плавный переход при загрузке нового изображения
                        }).build(),
                    contentDescription = null, // Описание для доступности
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .clip(shape = RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .animateContentSize()
                )
                Text(
                    text = "$price руб.",
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (isSelected) {
                            onDelete()
                        } else {
                            onAdd()
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        text = if (isSelected) "Из корзины" else "В корзину",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
                    )
                }
            }
        }
    })
}