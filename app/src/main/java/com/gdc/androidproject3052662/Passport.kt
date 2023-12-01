package com.gdc.androidproject3052662

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material.icons.sharp.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
    // to match fling with tab
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
    )

    //used to sync tabs and content change
    LaunchedEffect(selectedTabIndex){
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    //if it is not changed by scroll, updated index from tab
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress){
        if(!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        // tabs for the cards
        TabRow(selectedTabIndex = selectedTabIndex) {

            // loop through tab item
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
            modifier = Modifier.fillMaxWidth(),
            ) { index ->
            val scrollState = rememberScrollState()
            // dummy cards, logic will be implemented with database
            Box(modifier = Modifier.fillMaxSize()
                                    .verticalScroll(scrollState),
            ){
                // use when case to be more reliable
                when (index){
                  0 ->  Buildings()
                  1 ->  Discovered()
                  2 ->  Favorite()
                }

            }

        }
    }

}

// Card will be populated here with database infs
@Composable
fun Buildings(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CardBuild(title = "Griffith College", descrp = "A great college to study in")
        CardBuild(title = "Saint Patrick ", descrp ="Saint Patrick’s has been at the heart of Dublin and Ireland’s history for over 800 years. The Cathedral is a Place Where All are Welcome to Experience the Loving Presence of God. Ireland's Largest Church. Over 800 Years Of History.")
        CardBuild(title = "Christ Church", descrp = "Christ Church Cathedral, more formally The Cathedral of the Holy Trinity, is the cathedral of the United Dioceses of Dublin and Glendalough and the cathedral of the ecclesiastical province of the United Provinces of Dublin and Cashel in the Church of Ireland." )
    }

}
@Composable
fun Discovered(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CardBuild(title = "Saint Patrick ", descrp ="Saint Patrick’s has been at the heart of Dublin and Ireland’s history for over 800 years. The Cathedral is a Place Where All are Welcome to Experience the Loving Presence of God. Ireland's Largest Church. Over 800 Years Of History.")
        CardBuild(title = "Christ Church", descrp = "Christ Church Cathedral, more formally The Cathedral of the Holy Trinity, is the cathedral of the United Dioceses of Dublin and Glendalough and the cathedral of the ecclesiastical province of the United Provinces of Dublin and Cashel in the Church of Ireland." )
    }

}
@Composable
fun Favorite(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CardBuild(title = "Griffith College", descrp = "A great college to study in")

    }

}

// will be used as template to be populate by infs in the database
@Composable
fun CardBuild(title: String, descrp: String ){

    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(8.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
        //set card elevation of the card
        elevation = CardDefaults.cardElevation(
            defaultElevation =  10.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Column(modifier = Modifier.clickable(onClick = {  })) {

            // default current img
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null, // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = descrp,
                    maxLines = 3,
                    //overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

