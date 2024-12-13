package com.example.tourism_project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class PlaceDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val placeName = intent.getStringExtra("placeName") ?: "Detail Tempat"
        val description = intent.getStringExtra("description") ?: "Deskripsi tidak tersedia"
        val imageRes = intent.getIntExtra("imageRes", R.drawable.bakpia)
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)

        setContent {
            Tourism_ProjectTheme {
                PlaceDetailScreen(
                    placeName = placeName,
                    description = description,
                    imageRes = imageRes,
                    latitude = latitude,
                    longitude = longitude
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    placeName: String,
    description: String,
    imageRes: Int,
    latitude: Double,
    longitude: Double
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(placeName) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF3F51B5))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Gambar Utama
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = placeName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Deskripsi
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Navigasi
            ButtonWithIcon(
                text = "Lihat Maps",
                icon = Icons.Filled.Map,
                backgroundColor = Color(0xFF03A9F4),
                onClick = {
                    if (latitude != 0.0 && longitude != 0.0) {
                        val intent = Intent(context, MapActivity::class.java).apply {
                            putExtra("placeName", placeName)
                            putExtra("latitude", latitude)
                            putExtra("longitude", longitude)
                            putExtra("showRoute", false) // Maps Only
                        }
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Lokasi tidak valid!", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ButtonWithIcon(
                text = "Lihat Rute",
                icon = Icons.Filled.Directions,
                backgroundColor = Color(0xFF4CAF50),
                onClick = {
                    val intent = Intent(context, MapActivity::class.java).apply {
                        putExtra("placeName", placeName)
                        putExtra("latitude", latitude)
                        putExtra("longitude", longitude)
                        putExtra("action", "route") // Aksi "Lihat Rute"
                    }
                    context.startActivity(intent)
                }
            )

        }
    }
}

@Composable
fun ButtonWithIcon(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = Color.White)
    }
}
