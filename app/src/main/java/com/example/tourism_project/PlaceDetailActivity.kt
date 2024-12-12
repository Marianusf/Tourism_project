package com.example.tourism_project


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.io.File

class PlaceDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Konfigurasi OSMDroid
        val config = Configuration.getInstance()
        config.userAgentValue = packageName
        config.osmdroidBasePath = File(cacheDir, "osmdroid")
        config.osmdroidTileCache = File(cacheDir, "osmdroid/tiles")

        val touristSpotId = intent.getIntExtra("touristSpotId", -1)

        if (touristSpotId == -1) {
            Toast.makeText(this, "Invalid tourist spot ID", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            setContent {
                Tourism_ProjectTheme {
                    PlaceDetailScreen(touristSpotId)
                }
            }
        }
    }

    @Composable
    fun PlaceDetailScreen(touristSpotId: Int) {
        // State untuk mengontrol apakah peta ditampilkan
        var showMap by remember { mutableStateOf(false) }

        Column(modifier = Modifier.fillMaxSize()) {
            // Gambar tempat wisata
            Image(
                painter = painterResource(id = R.drawable.merapi), // Gambar default
                contentDescription = "Gunung Merapi",
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Informasi tempat wisata
            Text(
                text = "Gunung Merapi",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Gunung berapi aktif yang terkenal di Yogyakarta.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol untuk menampilkan peta
            Button(
                onClick = { showMap = !showMap },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(if (showMap) "Sembunyikan Peta" else "Tampilkan Peta")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Jika showMap = true, tampilkan peta
            if (showMap) {
                PlaceDetailMap()
            }
        }
    }

    @Composable
    fun PlaceDetailMap() {
        AndroidView(
            factory = { context ->
                val mapView = MapView(context)
                mapView.setTileSource(TileSourceFactory.MAPNIK)

                // Set lokasi default
                val location = GeoPoint(-7.7583, 110.4257) // Contoh lokasi Gunung Merapi
                mapView.controller.setCenter(location)
                mapView.controller.setZoom(15.0)

                // Tambahkan marker
                val marker = Marker(mapView)
                marker.position = location
                marker.title = "Gunung Merapi"
                mapView.overlays.add(marker)

                mapView
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }


    var lastClickTime = 0L

    fun isClickAllowed(): Boolean {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > 1000) { // Interval 1 detik
            lastClickTime = currentTime
            return true
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE))
    }

    override fun onPause() {
        super.onPause()
        Configuration.getInstance().save(this, getPreferences(MODE_PRIVATE))
    }
}
