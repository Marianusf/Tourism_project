package com.example.tourism_project

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.osmdroid.util.GeoPoint
import java.net.URL
object RouteHelper {
    suspend fun fetchRoute(start: GeoPoint, end: GeoPoint): List<GeoPoint> {
        val url = "https://router.project-osrm.org/route/v1/driving/" +
                "${start.longitude},${start.latitude};${end.longitude},${end.latitude}?overview=full&geometries=geojson"

        println("Mengakses URL: $url")

        return withContext(Dispatchers.IO) {
            try {
                val response = URL(url).readText()
                println("Respons API: $response")

                val jsonObject = JSONObject(response)
                val routes = jsonObject.optJSONArray("routes")
                if (routes == null || routes.length() == 0) {
                    println("Tidak ada rute ditemukan.")
                    return@withContext emptyList()
                }

                val coordinates = routes.getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONArray("coordinates")

                val geoPoints = mutableListOf<GeoPoint>()
                for (i in 0 until coordinates.length()) {
                    val coord = coordinates.getJSONArray(i)
                    val lon = coord.getDouble(0)
                    val lat = coord.getDouble(1)
                    geoPoints.add(GeoPoint(lat, lon))
                }
                geoPoints
            } catch (e: Exception) {
                e.printStackTrace()
                println("Kesalahan saat memuat rute: ${e.message}")
                emptyList()
            }
        }
    }
}
