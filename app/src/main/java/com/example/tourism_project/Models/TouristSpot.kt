package com.example.tourism_project.models

data class TouristSpot(
    val id: Int,
    val name: String,
    val description: String,
    val latitude: Double, // Koordinat Latitude
    val longitude: Double, // Koordinat Longitude
    val imageRes: Int, // Gambar Tempat
)
