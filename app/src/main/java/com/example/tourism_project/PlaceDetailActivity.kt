package com.example.tourism_project

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.util.GeoPoint
//import org.osmdroid.views.tileproviders.TileSourceFactory

class PlaceDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val touristSpotId = intent.getIntExtra("touristSpotId", -1)

        if (touristSpotId == -1) {
            Toast.makeText(this, "Invalid tourist spot ID", Toast.LENGTH_SHORT).show()
        } else {
            setContent {
                Tourism_ProjectTheme {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Gambar tempat wisata
                        Image(
                            painter = painterResource(id = R.drawable.merapi),  // Gunakan gambar yang sesuai
                            contentDescription = "Gunung Merapi",
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Detail Wisata")
                        Text(text = "Deskripsi tempat wisata di Yogyakarta")

                        // Peta akan dimuat di bawah ini
                        PlaceDetailMap()
                    }
                }
            }
        }
    }
    @Composable
    fun PlaceDetailScreen(touristSpotId: Int) {
        Text(text = "Detail Tempat Wisata ID: $touristSpotId")
    }
    @Composable
    fun PlaceDetailMap() {
        // Menggunakan AndroidView untuk menampilkan MapView dari OSMDroid
        AndroidView(
            factory = { context ->
                // Membuat dan mengonfigurasi MapView dari OSMDroid
                val mapView = MapView(context)
                mapView.setTileSource(TileSourceFactory.MAPNIK) // Pastikan ini berasal dari OSMDroid
//                mapView.setZoomLevel(15)

                // Set lokasi default (misal: Gunung Merapi) di koordinat (-7.7583, 110.4257)
                val location = GeoPoint(-7.7583, 110.4257)
                mapView.controller.setCenter(location)

                // Menambahkan Marker pada peta
                val startMarker = Marker(mapView)
                startMarker.position = location
                startMarker.title = "Gunung Merapi"
                mapView.overlays.add(startMarker)

                mapView
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    // Mengelola siklus hidup MapView
    override fun onResume() {
        super.onResume()
        // Tidak perlu findViewById, karena kita sudah menggunakan AndroidView
    }

    override fun onPause() {
        super.onPause()
        // Sama seperti onResume(), karena kita menggunakan AndroidView, ini tidak perlu findViewById.
    }

    override fun onDestroy() {
        super.onDestroy()
        // Mengelola siklus hidup MapView jika perlu
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // Mengelola memori jika aplikasi berjalan di perangkat dengan sumber daya terbatas
    }
}
