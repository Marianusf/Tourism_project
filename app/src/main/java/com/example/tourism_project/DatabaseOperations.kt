package com.example.tourism_project

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tourism_project.models.Category
import com.example.tourism_project.models.TouristSpot

class DatabaseOperations(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    // Tambah kategori
    fun addCategory(categoryName: String, imageRes: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_CATEGORY_NAME, categoryName)
            put(DatabaseHelper.COLUMN_IMAGE_RES, imageRes)
        }
        db.insert(DatabaseHelper.TABLE_CATEGORIES, null, values)
    }

    // Tambah tempat wisata
    fun addTouristSpot(name: String, description: String, categoryId: Int, imageRes: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, name)
            put(DatabaseHelper.COLUMN_DESCRIPTION, description)
            put(DatabaseHelper.COLUMN_CATEGORY_ID, categoryId)
            put(DatabaseHelper.COLUMN_IMAGE_RES, imageRes)
        }
        db.insert(DatabaseHelper.TABLE_TOURIST_SPOTS, null, values)
    }

    // Ambil semua kategori
    @SuppressLint("Range")
    fun getCategories(): List<Category> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_CATEGORIES}", null)

        val categories = mutableListOf<Category>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME))
                val imageRes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_RES))
                categories.add(Category(id, name, imageRes))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return categories
    }

    // Ambil tempat wisata berdasarkan kategori
    @SuppressLint("Range")
    fun getTouristSpotsByCategory(categoryId: Int): List<TouristSpot> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_TOURIST_SPOTS} WHERE ${DatabaseHelper.COLUMN_CATEGORY_ID} = ?",
            arrayOf(categoryId.toString())
        )

        val touristSpots = mutableListOf<TouristSpot>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
                val description =
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
                val imageRes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_RES))
                touristSpots.add(TouristSpot(id, name, description, categoryId, imageRes))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return touristSpots
    }



    // Tambah favorit
    fun addFavorite(touristSpotId: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        try {
            val values = ContentValues().apply {
                put(DatabaseHelper.COLUMN_TOURIST_SPOT_ID, touristSpotId)
            }
            db.insert(DatabaseHelper.TABLE_FAVORITES, null, values)
        } finally {
            db.close()
        }
    }

    // Ambil semua favorit
    @SuppressLint("Range")
    fun getFavorites(): List<TouristSpot> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_TOURIST_SPOTS} WHERE ${DatabaseHelper.COLUMN_ID} IN (SELECT ${DatabaseHelper.COLUMN_TOURIST_SPOT_ID} FROM ${DatabaseHelper.TABLE_FAVORITES})",
            null
        )

        return cursor.use {
            val favorites = mutableListOf<TouristSpot>()
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex(DatabaseHelper.COLUMN_ID))
                    val name = it.getString(it.getColumnIndex(DatabaseHelper.COLUMN_NAME))
                    val description =
                        it.getString(it.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
                    val imageRes = it.getInt(it.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_RES))
                    favorites.add(TouristSpot(id, name, description, 0, imageRes))
                } while (it.moveToNext())
            }
            favorites
        }
    }
}