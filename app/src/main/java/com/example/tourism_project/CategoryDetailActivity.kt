package com.example.tourism_project

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.tourism_project.models.TouristSpot
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class CategoryDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tourism_ProjectTheme {
                // Mengambil ID kategori yang dikirimkan dari MainActivity
                val categoryId = intent.getIntExtra("categoryId", 0)
                val dbOperations = DatabaseOperations(this)
                val touristSpots = dbOperations.getTouristSpotsByCategory(categoryId)
                TouristSpotList(touristSpots = touristSpots)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TouristSpotList(touristSpots: List<TouristSpot>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tempat Wisata") }
            )
        },
        content = { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(touristSpots) { spot ->
                    TouristSpotItem(spot = spot)
                }
            }
        }
    )
}

@Composable
fun TouristSpotItem(spot: TouristSpot) {
    val context = LocalContext.current  // Mendapatkan context yang benar

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // Arahkan ke PlaceDetailActivity saat tempat wisata diklik
                val intent = Intent(context, PlaceDetailActivity::class.java)
                intent.putExtra("touristSpotId", spot.id)
                context.startActivity(intent)  // Menggunakan startActivity dengan context yang benar
            }
    ) {
        Text(
            text = spot.name,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryDetailPreview() {
    Tourism_ProjectTheme {
        TouristSpotList(touristSpots = listOf(
            TouristSpot(1, "Gunung Bromo", "Gunung yang terkenal di Indonesia", 1, R.drawable.bromo),
            TouristSpot(2, "Pantai Kuta", "Pantai terkenal di Bali", 2, R.drawable.kuta)
        ))
    }
}
