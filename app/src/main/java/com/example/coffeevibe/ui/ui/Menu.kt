package com.example.coffeevibe.ui.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.coffeevibe.R
import com.example.coffeevibe.model.MenuItem
import com.example.coffeevibe.model.OrderItem
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.utils.NetworkUtils
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@Composable
fun MenuScreen(
    inAccount: () -> Unit,
    inCart: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val networkAvailable = remember { NetworkUtils.isNetworkAvailable(context) }
    val menuViewModel = MenuViewModel(context)
    val orderVm = OrderViewModel()
    val goods by menuViewModel.dataList.collectAsState()
    var selectedDescription by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf("") }
    var selectedName by remember { mutableStateOf("") }
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    CoffeeVibeTheme(content = {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        }) {
                            Icon(
                                Icons.Filled.ArrowUpward,
                                contentDescription = "Localized description",
                                tint = colorScheme.onBackground
                            )
                        }
                        IconButton(onClick = {
                            inAccount()
                        }) {
                            Icon(
                                Icons.Filled.AccountCircle,
                                contentDescription = "Localized description",
                                tint = colorScheme.onBackground
                            )
                        }

                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { inCart() },
                            containerColor = colorScheme.primary,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                        ) {
                            Icon(
                                Icons.Filled.ShoppingCart,
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

            if (!networkAvailable) {
                NotInternet(){ }
            } else {

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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isSearching) {
                            Text(
                                text = "Menu",
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
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
                                        Icons.Filled.Search,
                                        contentDescription = "Search",
                                        tint = colorScheme.onBackground
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = "Search passwords",
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

                    //OrderNumber()

                    Spacer(modifier = Modifier.height(4.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        state = listState,
                    ) {
                        val filteredGoods = if (searchQuery.isBlank()) {
                            goods
                        } else {
                            goods.filter {
                                it.name.contains(searchQuery, true) ||
                                        it.name.contains(searchQuery, true)
                            }
                        }
                        if (filteredGoods.isNotEmpty()) {
                            items(filteredGoods.size) {
                                ListItem(
                                    name = filteredGoods[it].name,
                                    price = filteredGoods[it].price,
                                    image = filteredGoods[it].image,
                                    onInfo = {
                                        showInfo = true
                                        selectedDescription = filteredGoods[it].description
                                        selectedImage = filteredGoods[it].image
                                        selectedName = filteredGoods[it].name
                                    },
                                    onAdd = {
                                        orderVm.addItem(filteredGoods[it].let {
                                            OrderItem(
                                               id = it.id,
                                               name = it.name,
                                               price = it.price,
                                               image = it.image,
                                           )
                                        })
                                    }
                                )
                                Log.d(
                                    "ListItem",
                                    goods[it].name + goods[it].price + goods[it].image
                                )
                            }
                        }
                    }

                    if (showInfo) {
                        MinimalDialog(onDismissRequest = {
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
    MenuScreen(
        {},
        {}
    )
}

@Composable
fun ListItem(name: String,
             price: Int,
             image: String,
             onInfo: () -> Unit,
             onAdd: () -> Unit,
)
{
    var expanded by remember { mutableStateOf(false) }

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
            border = BorderStroke(if(expanded) 2.dp else 1.dp, if(expanded) colorScheme.secondary else colorScheme.onSurface)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
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
                    .width(130.dp)
                    .height(130.dp)
                    .clip(shape = RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,

                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = name,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .animateContentSize())
                Text(text = "$price руб.",
                    color = colorScheme.onBackground,
                    textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        expanded = !expanded
                        onAdd()
                    },
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(text = "Add to cart",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),

                        )
                }
            }
        }
    })
}