package com.gdc.androidproject3052662

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdc.androidproject3052662.ui.theme.AndroidProject3052662Theme

class Profile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidProject3052662Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                    //MaterialTheme.colorScheme.background
                    ) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Circular photo at the top center of the screen
        ProfilePhoto(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)

        )

        // Name below the photo
        Text(
            text = "John Doe",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Column with information
        InformationColumn()
    }
}

@Composable
fun ProfilePhoto(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val density = LocalDensity.current.density

    // Sample image, to be replaced with an array of setup images
    val image = painterResource(id = R.drawable.ic_launcher_foreground)

    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .shadow(4.dp, CircleShape)
            .padding(16.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun InformationColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        InformationItem("Current Location", "City, Country")
        InformationItem("Steps", "10,000 steps")
        InformationItem("Average Speed", "5 km/h")
    }
}

@Composable
fun InformationItem(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // fix icon part
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            modifier = Modifier.size(24.dp)
//        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = value, modifier = Modifier.padding(start = 20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    AndroidProject3052662Theme {
        ProfileScreen()
    }
}