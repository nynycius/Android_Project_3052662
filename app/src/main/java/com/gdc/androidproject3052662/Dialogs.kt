package com.gdc.androidproject3052662

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


class Dialogs : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

}
    }


// Dialog for asking permission
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
    )
}

//Dialog for CardBuilds
@Composable
fun CardDialog(onDismiss: () -> Unit) {
    val screenHeight = with(LocalDensity.current) {
        (LocalConfiguration.current.screenHeightDp * density).toInt()
    }
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Column{
                Text("RULES",textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Tilt the phone side to side to avoid the obstacles in the way.\nTry to make it to the end of the finish line without losing\nMay the odds be in your favor!", modifier = Modifier.padding(8.dp), fontSize = 16.sp)


                // Close button
                Button(onClick = { onDismiss() }) {
                    Text("Close")
                }
            }

        }
    }
}