package com.example.tourism_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.tourism_project.models.TouristSpot
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme

class PlaceDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tourism_ProjectTheme {
                val touristSpotId = intent.getIntExtra("touristSpotId", 0)
                val dbOperations = DatabaseOperations(this)
                val touristSpot = dbOperations.getTouristSpotById(touristSpotId)
                if (touristSpot != null) {
                    PlaceDetail(touristSpot = touristSpot)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(touristSpot: TouristSpot) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(touristSpot.name) }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                // Jika menggunakan URL, ingat untuk memeriksa URL dan Coil version
                Image(
                    painter = rememberImagePainter(touristSpot.image),
                    contentDescription = touristSpot.name,
                    modifier = Modifier.fillMaxWidth().height(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(touristSpot.description, style = MaterialTheme.typography.bodyMedium)

                // Menampilkan rating atau review jika diperlukan
                Text("Rating: ★★★☆☆") // Static example, you can implement a rating system here
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PlaceDetailPreview() {
    Tourism_ProjectTheme {
        PlaceDetail(
            touristSpot = TouristSpot(
                id = 1,
                name = "Gunung Bromo",
                description = "Gunung yang terkenal di Indonesia",
                categoryId = 1,
                image = R.drawable.bromo
            )
        )
    }
}
