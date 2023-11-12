package com.gdc.androidproject3052662

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.AlertDialogDefaults.containerColor
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
//import com.gdc.androidproject3052662.ui.theme.AndroidProject3052662Theme
import kotlinx.coroutines.launch


// create data class for the nav items
data class NavigationItem(val title: String, val icon: ImageVector, val route: String)


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {

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
                            // loop through items list and populate the navDrawer
                            ModalDrawerSheet {
                                items.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        icon = {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = item.title
                                            )
                                        },
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
                                    title = {  },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }) {
                                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                                        }
                                    }
                                )
                            }
                        ) { contentPadding ->
                            val scrollState = rememberScrollState()
                            Column(
                                modifier = Modifier
                                    .padding(contentPadding)
                                    .verticalScroll(scrollState),
//                                verticalArrangement = Arrangement.spacedBy(5.dp),
//                                    modifier = Modifier.verticalScroll(scrollState)

                            ) {
                                //SetNavhost declared inside Scaffold to fit the given space
                                NavHost(
                                    navController = navController,
                                    startDestination = "map"
                                ) {
                                    composable("map") {
                                        MapScreen(navController)
                                    }
                                    composable("profile") {
                                        ProfileScreen(navController)
                                    }
                                    composable("passport") {
                                        PassportScreen(navController)
                                    }
                                }

                                }

                            }

                        }
                    }


                }
            }
        }
    }


@Composable
// Map API, initially only with generic location
fun MapScreen(navController: NavController) {

//    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
//    val properties by remember {
//        mutableStateOf(MapProperties(mapType = MapType.SATELLITE))

    // as a preliminary test only points to dublin
    val dublin = LatLng(53.350140, -6.266155)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(dublin, 12f)
    }

    // initialize google maps
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .height(727.dp),
        cameraPositionState = cameraPositionState
    ) {
        // set mark to dublin TODO: current location and pointer
        Marker(
            state = MarkerState(position = dublin),
            title = "Dublin",
            snippet = "Marker in Dublin"
        )
    }


}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {

    }
}
