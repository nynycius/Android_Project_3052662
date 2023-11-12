package com.gdc.androidproject3052662

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gdc.androidproject3052662.ui.theme.AndroidProject3052662Theme
import kotlinx.coroutines.launch


// create data class for the nav items
data class NavigationItem( val title: String, val selectIcon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidProject3052662Theme {
                // list for drawer nav items
                val items = listOf(
                    NavigationItem(
                        "Map",
                        Icons.Filled.LocationOn,
                        "map"
                    ),
                    NavigationItem(
                        "Profile",
                        Icons.Filled.AccountCircle,
                        "profile"
                    ),
                    NavigationItem(
                        "Passport",
                        Icons.Filled.FavoriteBorder,
                        "passport"
                    )

                )


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var selectedItemIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }
                    // navigation controller
                    val navController = rememberNavController()



                    ModalNavigationDrawer(

                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                items.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        label = {
                                                Text(text = item.title)
                                        },
                                        // selected used to highlight selected option
                                        selected = index == selectedItemIndex,
                                        onClick = {
                                            navController.navigate(item.route)
                                            selectedItemIndex = index
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        })
                                }
                            }
                        },
                        gesturesEnabled = false,
                        //modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    ) {
                        // Scaffold used to wrap content bellow topBar
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text("") },
                                    navigationIcon = {
                                        IconButton (onClick = {
                                            scope.launch {
                                                drawerState.open()
                                        }
                                    }) {
                                        Icon(Icons.Filled.Menu , contentDescription = "Menu") }
                                    }
                                )
                            }
                        ) {contentPadding ->
                            val scrollState = rememberScrollState()
                            Column (modifier = Modifier.padding(contentPadding),
                                    verticalArrangement = Arrangement.spacedBy(5.dp),
//                                    modifier = Modifier.verticalScroll(scrollState)

                            ){
                                //SetNavhost

                                Button(onClick = { navController.navigate("passport")}) {
                                    
                                }
                                Text(text = "test1")
                                Text(text = "test2")
                                Text(text = "test3")
                                Text(text = "test4")
                                Text(text = "test5")
                                Text(text = "test6")
                                Text(text = "test7")
                                Text(text = "test8")
                                Text(text = "test9")
                            }

                        }
                    }




                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AndroidProject3052662Theme {
        MainActivity()
    }
}
