package com.example.tourism_project.models

data class TouristSpot(
    val id: Int,
    val name: String,
    val description: String,
    val categoryId: Int,
    val imageRes: Int// Drawable resource ID
)
