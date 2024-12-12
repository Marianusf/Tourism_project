package com.example.tourism_project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tourism_project.models.TouristSpot
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class CategoryDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengambil categoryId yang dikirimkan dari MainActivity
        val categoryId = intent.getIntExtra("categoryId", -1)  // Ganti default ke -1

        // Cek apakah categoryId valid
        if (categoryId == -1) {
            Toast.makeText(this, "Invalid category ID", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("CategoryDetailActivity", "categoryId: $categoryId")  // Debugging
            val touristSpots = getTouristSpotsByCategory(categoryId)
            setContent {
                Tourism_ProjectTheme {
                    TouristSpotList(touristSpots = touristSpots)
                }
            }
        }
    }

    // Fungsi untuk mendapatkan tempat wisata berdasarkan categoryId
    private fun getTouristSpotsByCategory(categoryId: Int): List<TouristSpot> {
        return when (categoryId) {
            1 -> listOf(
                TouristSpot(1, "Gunung Bromo", "Gunung yang terkenal di Indonesia", 1, R.drawable.bromo),
                TouristSpot(2, "Gunung Rinjani", "Gunung di Lombok", 1, R.drawable.bromo)
            )
            2 -> listOf(
                TouristSpot(3, "Candi Borobudur", "Candi Budha di Jawa Tengah", 2, R.drawable.bromo),
                TouristSpot(4, "Candi Prambanan", "Candi Hindu di Yogyakarta", 2, R.drawable.kuta)
            )
            else -> emptyList()
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
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // Arahkan ke PlaceDetailActivity saat tempat wisata diklik
                val intent = Intent(context, PlaceDetailActivity::class.java)
                intent.putExtra("touristSpotId", spot.id)
                context.startActivity(intent)
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
