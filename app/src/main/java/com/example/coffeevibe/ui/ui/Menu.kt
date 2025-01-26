package com.example.coffeevibe.ui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeevibe.R
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme

@Composable
fun MenuScreen(
    inAccount: () -> Unit,
    inCart: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }

    CoffeeVibeTheme(content = {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = { /* do something */ }) {
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
                    if(!isSearching) {
                        Text(
                            text = "Menu",
                            color = colorScheme.onBackground,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_bold)),
                            fontSize = 28.sp,
                            textAlign = TextAlign.Left
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
                            placeholder = { Text(text = "Search passwords", color = colorScheme.onSurface) },
                            shape = MaterialTheme.shapes.large,
                            singleLine = true,
                        )
                    }
                    IconButton(onClick = {
                        isSearching = !isSearching
                        searchQuery = ""
                    }){
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

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                    items(100) {
                        ListItem(name = it.toString(),
                            price = it + 100,
                            onInfo = {
                                showInfo = true
                            }
                        )
                    }
                }

                if(showInfo){
                    MinimalDialog(onDismissRequest = {
                        showInfo = false
                    })
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
             onInfo: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    CoffeeVibeTheme(content = {
        OutlinedCard(
            modifier = Modifier
                .width(150.dp)
                .height(250.dp)
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
                Icon(
                    Icons.Filled.Fastfood,
                    contentDescription = "Localized description",
                    tint = colorScheme.onBackground,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = name, fontWeight = FontWeight.Bold)
                Text(text = "$price руб.", color = Color.Gray)

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        expanded = !expanded
                    },
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(text = "Add",
                        color = colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.roboto_condensed_bold))
                        )
                }
            }
        }
    })
}