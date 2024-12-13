package com.example.tourism_project

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourism_project.models.TouristSpot
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class CategoryDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoryId = intent.getIntExtra("categoryId", -1)
        val categoryName = intent.getStringExtra("categoryName") ?: "Detail"

        if (categoryId == -1) {
            finish() // Jika ID tidak valid, keluar dari layar
        } else {
            setContent {
                Tourism_ProjectTheme {
                    CategoryDetailScreen(
                        categoryName = categoryName,
                        spots = getTouristSpotsByCategory(categoryId)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(categoryName: String, spots: List<TouristSpot>) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(categoryName) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Hero Section
            HeroSection(categoryName)

            // List tempat wisata
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(spots) { spot ->
                    TouristSpotCard(spot) {
                        // Navigasi ke PlaceDetailActivity
                        val intent = Intent(context, PlaceDetailActivity::class.java)
                        intent.putExtra("spotName", spot.name)
                        intent.putExtra("spotDescription", spot.description)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun HeroSection(categoryName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Explore $categoryName",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}

@Composable
fun TouristSpotCard(spot: TouristSpot, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gambar tempat wisata
            Image(
                painter = painterResource(id = spot.imageRes),
                contentDescription = spot.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            // Detail tempat wisata
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = spot.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = spot.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

fun getTouristSpotsByCategory(categoryId: Int): List<TouristSpot> {
    return when (categoryId) {
        1 -> listOf(
            TouristSpot(1, "Gunung Merapi", "Gunung berapi aktif yang terkenal", 1, R.drawable.merapi),
            TouristSpot(2, "Bukit Bintang", "Pemandangan malam indah", 1, R.drawable.bakpia)
        )
        2 -> listOf(
            TouristSpot(3, "Pantai Parangtritis", "Pantai terkenal di Yogyakarta", 2, R.drawable.parangtritis),
            TouristSpot(4, "Pantai Indrayanti", "Pantai pasir putih yang cantik", 2, R.drawable.parangtritis)
        )
        3 -> listOf(
            TouristSpot(5, "Candi Borobudur", "Candi Budha terbesar di dunia", 3, R.drawable.borobudur),
            TouristSpot(6, "Keraton Yogyakarta", "Pusat kerajaan Yogyakarta", 3, R.drawable.keraton)
        )
        else -> emptyList()
    }
}

