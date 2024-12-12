package com.example.tourism_project

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tourism_project.RouteHelper.fetchRoute
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme
import kotlinx.coroutines.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.io.File

class PlaceDetailActivity : ComponentActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Konfigurasi OSMDroid
        val config = Configuration.getInstance()
        config.userAgentValue = packageName
        config.osmdroidBasePath = File(cacheDir, "osmdroid")
        config.osmdroidTileCache = File(cacheDir, "osmdroid/tiles")

        setContent {
            Tourism_ProjectTheme {
                PlaceDetailScreen()
            }
        }
    }

    @Composable
    fun PlaceDetailScreen() {
        // State untuk mengontrol apakah peta atau rute ditampilkan
        var showMap by remember { mutableStateOf(false) }
        var showRoute by remember { mutableStateOf(false) }

        // Lokasi awal dan tujuan (statis)
        val startLocation = GeoPoint(-7.7956, 110.3695) // Malioboro
        val destinationLocation = GeoPoint(-7.7583, 110.4257) // Gunung Merapi

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Informasi detail tempat wisata
            Text(
                text = "Gunung Merapi",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF6200EE)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Gunung berapi yang terkenal di Yogyakarta.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol untuk melihat peta
            CustomButton(
                text = "Lihat Maps",
                icon = Icons.Filled.Map,
                backgroundColor = Color(0xFF03A9F4),
                onClick = {
                    showMap = true
                    showRoute = false
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tombol untuk melihat rute
            CustomButton(
                text = "Lihat Rute",
                icon = Icons.Filled.Directions,
                backgroundColor = Color(0xFF4CAF50),
                onClick = {
                    showRoute = true
                    showMap = false
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Menampilkan peta jika tombol "Lihat Maps" diklik
            if (showMap) {
                MapViewWithMarker(destinationLocation)
            }

            // Menampilkan rute jika tombol "Lihat Rute" diklik
            if (showRoute) {
                MapViewWithRoute(startLocation, destinationLocation)
            }
        }
    }

    @Composable
    fun MapViewWithMarker(destination: GeoPoint) {
        AndroidView(
            factory = { context ->
                mapView = MapView(context)
                mapView.setTileSource(TileSourceFactory.MAPNIK)

                // Tambahkan marker untuk tujuan
                addMarker(mapView, destination, "Gunung Merapi")
                mapView.controller.setCenter(destination)
                mapView.controller.setZoom(13.0)

                mapView
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    fun MapViewWithRoute(start: GeoPoint, destination: GeoPoint) {
        AndroidView(
            factory = { context ->
                mapView = MapView(context)
                mapView.setTileSource(TileSourceFactory.MAPNIK)

                // Tambahkan marker untuk lokasi awal
                addMarker(mapView, start, "Lokasi Awal: Malioboro")

                // Tambahkan marker untuk tujuan
                addMarker(mapView, destination, "Tujuan: Gunung Merapi")

                // Fokuskan peta pada lokasi awal
                mapView.controller.setCenter(start)
                mapView.controller.setZoom(13.0)

                // Hitung dan gambar rute
                CoroutineScope(Dispatchers.IO).launch {
                    val route = fetchRoute(start, destination)
                    withContext(Dispatchers.Main) {
                        if (route.isNotEmpty()) {
                            drawRoute(mapView, route)
                            Toast.makeText(context, "Rute berhasil dimuat.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Gagal memuat rute.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                mapView
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    fun CustomButton(
        text: String,
        icon: ImageVector,
        backgroundColor: Color,
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color.White)
        }
    }

    private fun addMarker(mapView: MapView, location: GeoPoint, title: String) {
        val marker = Marker(mapView)
        marker.position = location
        marker.title = title
        mapView.overlays.add(marker)
    }

    private fun drawRoute(mapView: MapView, route: List<GeoPoint>) {
        val polyline = Polyline()
        polyline.setPoints(route)
        polyline.color = android.graphics.Color.BLUE
        polyline.width = 8.0f
        mapView.overlays.add(polyline)
        mapView.invalidate() // Refresh peta
    }
}
