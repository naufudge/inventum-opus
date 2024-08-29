package com.example.inventumopus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.inventumopus.ui.Navigation
import com.example.inventumopus.ui.screens.poppins
import com.example.inventumopus.ui.theme.InventumOpusTheme

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventumOpusTheme {
                val navController = rememberNavController()

                val navItems = listOf(
                    BottomNavigationItem(
                        title = "Home",
                        route = "home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home
                    ),
                    BottomNavigationItem(
                        title = "Search",
                        route = "search",
                        selectedIcon = Icons.Filled.Search,
                        unselectedIcon = Icons.Outlined.Search
                    ),
                    BottomNavigationItem(
                        title = "Profile",
                        route = "profile",
                        selectedIcon = Icons.Filled.Person,
                        unselectedIcon = Icons.Outlined.Person
                    )
                )

                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }

                Surface {
                    Scaffold (
                        modifier = Modifier,
                        bottomBar = {
                            NavigationBar (
                                modifier = Modifier
                                    .shadow(20.dp, shape = RoundedCornerShape(0.dp))
                                    .graphicsLayer {
                                        shadowElevation = 20.dp.toPx()
                                        shape = RoundedCornerShape(0.dp)
                                        clip = true
                                    }
                            ) {
                                navItems.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(item.route)
                                        },
                                        label = {
                                            Text(text = item.title, fontFamily = poppins, fontWeight = FontWeight.W500)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if(index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title)
                                        }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        Navigation(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController
                        )
                    }
                }

            }
        }
    }
}

