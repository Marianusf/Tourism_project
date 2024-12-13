package com.example.tourism_project

import BottomNavigationBar
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
import com.example.tourism_project.models.TouristSpot
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class CategoryDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoryId = intent.getIntExtra("categoryId", -1)
        val categoryName = intent.getStringExtra("categoryName") ?: "Detail"

        if (categoryId == -1) {
            finish()
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
            TopAppBar(title = { Text(categoryName) })
        },
        bottomBar = {
            BottomNavigationBar(currentScreen = "category")
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            item {
                HeroSection(categoryName = categoryName)
            }
            items(spots) { spot ->
                TouristSpotCard(spot = spot) {
                    // Intent ke PlaceDetailActivity
                    val intent = Intent(context, PlaceDetailActivity::class.java).apply {
                        putExtra("placeName", spot.name)
                        putExtra("description", spot.description)
                        putExtra("latitude", spot.latitude)
                        putExtra("longitude", spot.longitude)
                        putExtra("imageRes", spot.imageRes)
                    }
                    context.startActivity(intent)
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
fun TouristSpotCard(spot: TouristSpot, onClick: (TouristSpot) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(spot) },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = spot.imageRes),
                contentDescription = spot.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
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
            TouristSpot(
                1,
                "Gunung Merapi",
                "Gunung aktif di Yogyakarta",
                -7.5407,
                110.4428,
                R.drawable.merapi,
            ),
            TouristSpot(
                2,
                "Bukit Bintang",
                "Pemandangan malam indah nikmati keasrian malam ",
                -7.8482,
                110.4773,
                R.drawable.bukitbintang,
            ),
            TouristSpot(
                3,
                "Bunker Kaliadem",
                "Bunker dengan pemandangan Gunung Merapi dan nikmati jeep disini",
                -7.5942,
                110.4461,
                R.drawable.bunkerkaliadem,
            ),
            TouristSpot(
                4,
                "Kaliurang",
                "Kawasan wisata pegunungan yang sejuk dan asri!",
                -7.6000,
                110.4244,
                R.drawable.kaliurang,
            )
        )
        2 -> listOf(
            TouristSpot(
                5,
                "Pantai Parangtritis",
                "Pantai terkenal di Yogyakarta dengan pasir hitamnya ",
                -8.0211,
                110.2872,
                R.drawable.parangtrities,
            ),
            TouristSpot(
                6,
                "Pantai Indrayanti",
                "Pantai pasir putih yang menawan, dapat dinikmati bersama teman, pacar atau kerabat anda!",
                -8.1501,
                110.6133,
                R.drawable.pantai_indrayanti,
            ),
            TouristSpot(
                7,
                "Pantai Drini",
                "Pantai dengan pulau karang dan keindahan karangnya",
                -8.1376,
                110.5719,
                R.drawable.pantai_drini,
            ),
            TouristSpot(
                8,
                "Pantai Ngobaran nikmati pasir putih indah di Jogja",
                "Pantai dengan nuansa budaya",
                -8.1289,
                110.5480,
                R.drawable.pantai_ngobaran,
            )
        )
        3 -> listOf(
            TouristSpot(
                9,
                "Candi Borobudur",
                "Candi Buddha terbesar di Indonesia, dan masuk sebagai keajaiban dunia",
                -7.6079,
                110.2038,
                R.drawable.borobudur,
            ),
            TouristSpot(
                10,
                "Candi Prambanan",
                "Candi Hindu yang megah memiliki ribuan sejarah unik",
                -7.7518,
                110.4918,
                R.drawable.prambanan,
            ),
            TouristSpot(
                11,
                "Candi Ratu Boko",
                "Situs istana kuno tentang perjalanan panjang",
                -7.7707,
                110.4891,
                R.drawable.ratu_boko,
            ),
            TouristSpot(
                12,
                "Candi Plaosan",
                "Candi kembar dengan arsitektur unik yang dapat dinikmati bersama saudara anda",
                -7.7428,
                110.5021,
                R.drawable.candi_plaosan,
            )
        )
        else -> emptyList()
    }
}

