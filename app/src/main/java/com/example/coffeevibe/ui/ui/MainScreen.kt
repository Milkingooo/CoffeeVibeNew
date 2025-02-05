package com.example.coffeevibe.ui.ui

import android.content.Context
import android.content.Intent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.MaterialTheme.colors
import com.example.coffeevibe.database.CartDatabase
import com.example.coffeevibe.repository.CartRepository
import com.example.coffeevibe.ui.activities.OrderActivity
import com.example.coffeevibe.ui.theme.CoffeeVibeTheme
import com.example.coffeevibe.viewmodel.MenuViewModel
import com.example.coffeevibe.viewmodel.OrderViewModel

@Composable
fun MainScreen(
    inFinishOrder: () -> Unit,
    onLogin: () -> Unit,
) {
    val context = LocalContext.current
    val passwordDb = CartDatabase.getDatabase(context)
    val passwordDao = passwordDb.cartDao()
    val repository = CartRepository(passwordDao)
    val orderViewModel = OrderViewModel(repository)
    val navController = rememberNavController()

    CoffeeVibeTheme(content = {
        Column(
            modifier = Modifier.background(colorScheme.background)
        ) {
            NavHost(
                navController,
                startDestination = Screen.Menu.route,
                modifier = Modifier.weight(1f)
            ) {
                composable(Screen.Menu.route,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                    popEnterTransition = { fadeIn() },
                    popExitTransition = { fadeOut() }) { MenuScreen( orderVm = orderViewModel,
                    menuViewModel = MenuViewModel(context)) }
                composable(Screen.Cart.route,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                    popEnterTransition = { fadeIn() },
                    popExitTransition = { fadeOut() }) {
                    CartScreen(
                        onCreateOrder = {
                            inFinishOrder()
                        },
                        orderVm = orderViewModel
                    )
                }
                composable(Screen.Account.route,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                    popEnterTransition = { fadeIn() },
                    popExitTransition = { fadeOut() }) { AccountScreen({ onLogin() })  }

            }
            BottomNavigationBar(navController = navController)
        }
    })
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    CoffeeVibeTheme(content = {
        NavigationBar(
            containerColor = colorScheme.background,
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            NavBarItems.BarItems.forEach { navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = navItem.image,
                            contentDescription = navItem.title,
                            tint = Color.Black,
                        )
                    },
                    label = {
                        Text(text = navItem.title)
                    },
                    modifier = Modifier.background(colorScheme.background)
                )
            }
        }
    })
}

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Меню",
            image = Icons.Filled.Menu,
            route = "menu"
        ),
        BarItem(
            title = "Корзина",
            image = Icons.Filled.ShoppingCart,
            route = "cart"
        ),
        BarItem(
            title = "Аккаунт",
            image = Icons.Filled.AccountCircle,
            route = "account"
        )
    )
}

data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String
)

sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object Cart : Screen("cart")
    object Account : Screen("account")
}
