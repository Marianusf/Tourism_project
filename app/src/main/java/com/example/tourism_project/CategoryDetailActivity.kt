package com.example.tourism_project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import com.example.tourism_project.models.TouristSpot
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class CategoryDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ambil categoryId dari Intent
        val categoryId = intent.getIntExtra("categoryId", -1)

        // Debugging: Pastikan ID diterima dengan benar
        println("Diterima categoryId: $categoryId")

        if (categoryId == -1) {
            Toast.makeText(this, "Invalid category ID", Toast.LENGTH_SHORT).show()
            finish() // Kembali ke layar sebelumnya
        } else {
            setContent {
                Tourism_ProjectTheme {
                    TouristSpotList(
                        touristSpots = getTouristSpotsByCategory(categoryId),
                        categoryId = categoryId
                    )
                }
            }
        }
    }
}

// Fungsi untuk mendapatkan daftar tempat wisata berdasarkan categoryId
fun getTouristSpotsByCategory(categoryId: Int): List<TouristSpot> {
    return when (categoryId) {
        1 -> listOf(
            TouristSpot(
                1,
                "Gunung Merapi",
                "Gunung berapi aktif yang terkenal",
                1,
                R.drawable.merapi
            ),
            TouristSpot(
                2,
                "Pantai Parangtritis",
                "Pantai terkenal di Yogyakarta",
                1,
                R.drawable.parangtritis
            )
        )

        2 -> listOf(
            TouristSpot(
                3,
                "Candi Borobudur",
                "Candi Budha terbesar di dunia",
                2,
                R.drawable.borobudur
            ),
            TouristSpot(
                4,
                "Keraton Yogyakarta",
                "Pusat kerajaan Yogyakarta",
                2,
                R.drawable.keraton
            )
        )

        3 -> listOf(
            TouristSpot(5, "Gudeg Yu Djum", "Gudeg khas Yogyakarta", 3, R.drawable.gudeg),
            TouristSpot(
                6,
                "Bakpia Pathuk",
                "Bakpia legendaris di Yogyakarta",
                3,
                R.drawable.bakpia
            )
        )

        else -> emptyList() // Kembalikan daftar kosong jika kategori tidak ditemukan
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TouristSpotList(touristSpots: List<TouristSpot>, categoryId: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tempat Wisata Kategori $categoryId") }
            )
        },
        content = { padding ->
            if (touristSpots.isEmpty()) {
                // Tampilkan pesan jika tidak ada tempat wisata
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(text = "Tidak ada tempat wisata untuk kategori ini")
                }
            } else {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(touristSpots) { spot ->
                        TouristSpotItem(spot = spot)
                    }
                }
            }
        }
    )
}

@Composable
fun TouristSpotItem(spot: TouristSpot) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Navigasi ke PlaceDetailActivity ketika item diklik
                val intent = Intent(context, PlaceDetailActivity::class.java)
                intent.putExtra("touristSpotId", spot.id)
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = spot.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = spot.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}
