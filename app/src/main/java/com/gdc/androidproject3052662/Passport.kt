package com.gdc.androidproject3052662

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material.icons.sharp.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.compose.AppTheme

//import com.gdc.androidproject3052662.ui.theme.AndroidProject3052662Theme

class Passport : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}


// create dat class to hold tabs infs
data class TabItem(
    val title: String,
    val unselectIcon: ImageVector,
    val selectIcon: ImageVector
)
// inf to populate building cards
data class BuildingCard(
    val title: String,
    val img: ImageVector,
    val desc: String,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PassportScreen(navController: NavController) {

    // tabs to be used
    val tabItems = listOf(
        TabItem(
            title = "Buildings",
            unselectIcon = Icons.Sharp.DateRange,
            selectIcon = Icons.Filled.AccountBox
        ),
        TabItem(
            title = "Discovered",
            unselectIcon = Icons.Sharp.Place,
            selectIcon = Icons.Filled.LocationOn
        ),
        TabItem(
            title = "Favorite",
            unselectIcon = Icons.Sharp.FavoriteBorder,
            selectIcon = Icons.Filled.Favorite
        )
    )
    // used to keep track on tab index
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    // to remeber state of page
    val pagerState = rememberPagerState {
        tabItems.size
    }

    //used to sync tabs and content change
    LaunchedEffect(selectedTabIndex){
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage){
        if(!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {

            // loop through tab itens
            tabItems.forEachIndexed { index, item ->
                Tab(selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = item.title) },
                    icon = {
                        Icon(
                            // if item is selected, change icon
                            imageVector = if (index == selectedTabIndex) {
                                item.selectIcon
                            } else item.unselectIcon,
                            contentDescription = item.title
                        )

                    }
                )
            }

        }
        // create pager and keep track of its state
        HorizontalPager(state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            Box(modifier = Modifier.fillMaxSize()){
                Text(text = tabItems[index].title)
            }

        }
    }

}

