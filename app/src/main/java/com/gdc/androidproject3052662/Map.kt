package com.gdc.androidproject3052662

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.concurrent.TimeUnit

//import com.gdc.androidproject3052662.ui.theme.AndroidProject3052662Theme

class Map : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {

            }
        }
    }
}





@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
// Map API, initially only with generic location
fun MapScreen(navController: NavController) {

    // context to check permission
    val context = LocalContext.current


    val uiSettings by remember { mutableStateOf(MapUiSettings()) }

    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = hasLocationPermission(context)))
    }


    // store lat and long
    var location by remember { mutableStateOf(LatLng(53.350140, -6.266155)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 12f)}

    // use to debug distance
//    val testMarker = LatLng(53.33439041891711, -6.273324243524751)
      val testMarker = LatLng(53.33079569100914, -6.277543034888738)


    // Create a permission launcher
    val requestPermissionLauncher =  rememberPermissionState( permission = Manifest.permission.ACCESS_FINE_LOCATION)

    var openAlertDialog by remember { mutableStateOf(false) }

    // initial permission request, open dialog
    if(!requestPermissionLauncher.status.isGranted){

        PermissionDialog(
            onConfirmation = { requestPermissionLauncher.launchPermissionRequest()},
            dialogTitle = "Permission is necessary" ,
            dialogText = "This application uses your current location to work, you must enable it use this app" ,
            icon = Icons.Filled.LocationOn
        )
    }

    var close by remember { mutableStateOf(false) }

    // if distance smaller than 2 meters pop up Dialog
    if(DistanceTo(latLng = testMarker) < 5 ){

        PermissionDialog( onConfirmation = {   },
            dialogTitle = " Test " ,
            dialogText = "Test " ,
            icon = Icons.Filled.LocationOn)

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
        //.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // location debug
//        Text(text = location.toString())
//
//
//        Text(text = getLocation(context).toString())
//
//        // print distance
//        Text(text = "Distance between points " + DistanceTo(latLng = testMarker).toString())


        Spacer(modifier = Modifier.height(16.dp))


        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            //MapProperties(isMyLocationEnabled = true)
            uiSettings = uiSettings
        ) {
            // set mark to dublin TODO: current location and pointer
            Marker(
                state = MarkerState(position =

                location

                ),
                title = "Dublin",
                snippet = "Marker in Dublin"
            )
            Marker(
                state = MarkerState(position =

                testMarker

                ),
                title = "Test",
                snippet = "Marker test"
            )
        }


    }
}

// determine distance in meters between marker and user to verify if it gets there
@RequiresPermission (Manifest.permission.ACCESS_FINE_LOCATION)
@Composable
fun DistanceTo(latLng: LatLng) : Float {

    val context = LocalContext.current

    // convert current location to Location Object
    val currentLocation : Location = Location ("user")

    // store in mutable of to being able to update values
    var currentLat by remember { mutableStateOf( 0.00 )    }
    currentLat =  getLocation(context = context).latitude

    var currentLng by remember { mutableStateOf( 0.00 )    }
    currentLng =  getLocation(context = context).longitude

    currentLocation.latitude = currentLat
    currentLocation.longitude = currentLng
    


    // maker Location object
    val marker : Location = Location ("Marker")
    marker.latitude = latLng.latitude
    marker.longitude = latLng.longitude

    // calculate distance
    var distance by remember { mutableFloatStateOf( currentLocation.distanceTo(marker)) }

    distance = currentLocation.distanceTo(marker)

    // To debug currentLocation Object
    //Text(text = currentLocation.toString())
    
    return distance
    

}


// confirm if the permission is granted
//private
fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

// used for a simple and fast permission requirement check, if doesn't have permission, function will not work
@RequiresPermission (Manifest.permission.ACCESS_FINE_LOCATION)
// function to find current location, return coordinates
@Composable
fun getLocation(context : Context) : LatLng {

    // The location request that defines the location updates
    var locationRequest by remember {
        mutableStateOf<LocationRequest?>(null)
    }
    // Keeps track of received location updates, start as Dublin city center
    var locationUpdates by remember {
        mutableStateOf<LatLng>(LatLng(53.350140, -6.266155))
    }

    // Only register the location updates effect when we have a request
    if (locationRequest != null) {
        LocationUpdates(locationRequest!!) { result ->
            // For each result update the text
            for (currentLocation in result.locations) {
                locationUpdates = LatLng(currentLocation.latitude, currentLocation.longitude)
            }
        }
    }

    // request location every 2 secs to match walk speedy
     locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(3)).build()

    //Text(text = locationUpdates.toString())
    return locationUpdates


}


// require permission to update current location
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
// used to keep track of location to Dialog when reach given point
@Composable
fun LocationUpdates(
    locationRequest: LocationRequest,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onUpdate: (result: LocationResult) -> Unit,
) {
    val context = LocalContext.current
    val currentOnUpdate by rememberUpdatedState(newValue = onUpdate)

    // Whenever on of these parameters changes, dispose and restart the effect.
    DisposableEffect(locationRequest, lifecycleOwner) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                currentOnUpdate(result)
            }
        }
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                locationClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper(),
                )
            } else if (event == Lifecycle.Event.ON_STOP) {
                locationClient.removeLocationUpdates(locationCallback)
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            locationClient.removeLocationUpdates(locationCallback)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
