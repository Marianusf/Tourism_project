package com.example.tourism_project

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tourism_project.ui.theme.Tourism_ProjectTheme
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.io.File

class MapActivity : ComponentActivity() {

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
                val placeName = intent.getStringExtra("placeName") ?: "Lokasi"
                val latitude = intent.getDoubleExtra("latitude", 0.0)
                val longitude = intent.getDoubleExtra("longitude", 0.0)
                val action = intent.getStringExtra("action") ?: "view"

                if (latitude == 0.0 && longitude == 0.0) {
                    Toast.makeText(this, "Lokasi tidak valid", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val destination = GeoPoint(latitude, longitude)
                    if (action == "route") {
                        MapScreenWithRoute(placeName, destination)
                    } else {
                        MapScreenViewOnly(placeName, destination)
                    }
                }
            }
        }
    }

    @Composable
    fun MapScreenViewOnly(placeName: String, destination: GeoPoint) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = placeName,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF6200EE)
            )
            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(
                factory = { context ->
                    mapView = MapView(context)
                    mapView.setTileSource(TileSourceFactory.MAPNIK)
                    mapView.controller.setZoom(15.0)
                    mapView.controller.setCenter(destination)

                    // Tambahkan marker pada lokasi tujuan
                    addMarker(mapView, destination, placeName)

                    mapView
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }

    @Composable
    fun MapScreenWithRoute(placeName: String, destination: GeoPoint) {
        val startLocation = GeoPoint(-7.754944962569629, 110.42132957116411) // Contoh: Malioboro
        var totalDistance by remember { mutableStateOf(0.0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = placeName,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF6200EE)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rute ke $placeName",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(
                factory = { context ->
                    mapView = MapView(context)
                    mapView.setTileSource(TileSourceFactory.MAPNIK)
                    mapView.controller.setZoom(15.0)
                    mapView.controller.setCenter(startLocation)

                    // Tambahkan marker
                    addMarker(mapView, startLocation, "Lokasi Awal")
                    addMarker(mapView, destination, placeName)


                    val hardcodedRoute = listOf(
                        startLocation,
                        GeoPoint(-7.8000, 110.3800),
                        GeoPoint(-7.8050, 110.4000),
                        destination
                    )
                    drawRoute(mapView, hardcodedRoute)
                    totalDistance = calculateTotalDistance(hardcodedRoute)

                    mapView
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Tampilkan jarak total
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF03A9F4))
            ) {
                Text(
                    text = "Total Jarak: %.2f km".format(totalDistance / 1000), // Konversi ke km
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
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
        mapView.invalidate()
    }

    private fun calculateTotalDistance(route: List<GeoPoint>): Double {
        var totalDistance = 0.0
        for (i in 0 until route.size - 1) {
            totalDistance += route[i].distanceToAsDouble(route[i + 1])
        }
        return totalDistance
    }
}
