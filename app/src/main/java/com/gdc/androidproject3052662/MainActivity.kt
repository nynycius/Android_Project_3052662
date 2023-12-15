package com.gdc.androidproject3052662

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.google.android.gms.location.LocationServices
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


// database variables
private var data = mutableSetOf("Empty database")
private lateinit var databaseManager: DatabaseManager
private lateinit var database: SQLiteDatabase

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Initial call to start application, NavDrawer encapsulate the main layout and transition
                    NavDrawer()


                    }


                }
            }
            // init database
            databaseManager = DatabaseManager(this, "build.db", null, 1)
            database = databaseManager.writableDatabase
        }


    override fun onResume() {

        super.onResume()

     }

    override fun onStop() {
        super.onStop()
        addData()
        updateData()
    }

    private fun retrieveData() : String{
        return ""
    }

    private fun addData(){
//        val row
    }

    private fun updateData(){

    }

}




// create data class for the nav items
data class NavigationItem(val title: String, val icon: ImageVector, val route: String)

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
    ) ,

)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavDrawer(){

    //Navabar values
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)

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




@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

}
