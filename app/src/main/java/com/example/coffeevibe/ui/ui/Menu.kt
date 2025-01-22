package com.example.coffeevibe.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun MenuScreen(
    inAccount: () -> Unit,
    inCart: () -> Unit,
) {

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
                Text(
                    text = "CoffeeVibe",
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_extrabold)),
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),

                    ) {
                    items(100) {
                        ListItem(name = it.toString(), price = it + 100)
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
fun ListItem(name: String, price: Int) {
    val isSelected = remember { mutableStateOf(false) }

    CoffeeVibeTheme(content = {
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(250.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surface,

            ),
            shape = RoundedCornerShape(16.dp),

        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clickable {
                        isSelected.value = !isSelected.value
                    },
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
                    onClick = { /* do something */ },
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