package com.example.tourism_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tourism_project.models.TouristSpot
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class FavoriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tourism_ProjectTheme {
                val dbOperations = DatabaseOperations(this)
                val favoriteSpots = dbOperations.getFavorites()
                FavoriteList(favorites = favoriteSpots)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteList(favorites: List<TouristSpot>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") }
            )
        },
        content = { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(favorites) { spot ->
                    TouristSpotItem(spot = spot)
                }
            }
        }
    )
}
