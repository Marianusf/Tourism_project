package com.example.tourism_project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class PlaceDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Mengambil touristSpotId dari Intent
            val touristSpotId = intent.getIntExtra("touristSpotId", -1)

            // Cek apakah touristSpotId valid
            if (touristSpotId != -1) {
                PlaceDetail(touristSpotId = touristSpotId)
            } else {
                Text("Invalid touristSpotId")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(touristSpotId: Int) {
    // Mendapatkan gambar tempat wisata berdasarkan touristSpotId
    val imageRes = getImageForTouristSpot(touristSpotId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Place Detail") }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Image(
                    painter = rememberImagePainter(imageRes),
                    contentDescription = "Tourist Spot Image",
                    modifier = Modifier.fillMaxWidth().height(250.dp)
                )
                // Menampilkan detail lainnya
                Text(text = "Tourist Spot ID: $touristSpotId")
            }
        }
    )
}

fun getImageForTouristSpot(id: Int): Int {
    return when (id) {
        1 -> R.drawable.bromo
        2 -> R.drawable.kuta
        else -> R.drawable.ic_launcher_background
    }
}
