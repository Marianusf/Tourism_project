package com.example.tourism_project

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.tourism_project.models.Category
import com.example.tourism_project.models.TouristSpot

class DatabaseOperations(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    // Menambahkan kategori ke database
    fun addCategory(categoryName: String) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_CATEGORY_NAME, categoryName)
        }
        db.insert(DatabaseHelper.TABLE_CATEGORIES, null, values)
    }

    // Menambahkan tempat wisata ke database
    fun addTouristSpot(name: String, description: String, categoryId: Int, image: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, name)
            put(DatabaseHelper.COLUMN_DESCRIPTION, description)
            put(DatabaseHelper.COLUMN_CATEGORY_ID, categoryId)
            put(DatabaseHelper.COLUMN_IMAGE, image)
        }
        db.insert(DatabaseHelper.TABLE_TOURIST_SPOTS, null, values)
    }

    // Mendapatkan semua kategori
    fun getCategories(): List<Category> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_CATEGORIES}", null)

        val categories = mutableListOf<Category>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME))
                categories.add(Category(id, name))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return categories
    }

    // Mendapatkan tempat wisata berdasarkan kategori
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
                val description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
                val image = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE))
                touristSpots.add(TouristSpot(id, name, description, categoryId, image))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return touristSpots
    }

    // Mendapatkan tempat wisata berdasarkan ID
    fun getTouristSpotById(id: Int): TouristSpot? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_TOURIST_SPOTS} WHERE ${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )

        var touristSpot: TouristSpot? = null
        if (cursor != null && cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
            val description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
            val categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID))
            val image = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE))

            touristSpot = TouristSpot(id, name, description, categoryId, image)
        }

        cursor.close()
        return touristSpot
    }

    // Menambahkan tempat wisata ke dalam favorit
    fun addFavorite(touristSpotId: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("tourist_spot_id", touristSpotId)
        }
        db.insert("favorites", null, values)
    }

    // Mendapatkan semua tempat wisata favorit
    fun getFavorites(): List<TouristSpot> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_TOURIST_SPOTS} WHERE ${DatabaseHelper.COLUMN_ID} IN (SELECT tourist_spot_id FROM favorites)",
            null
        )

        val favorites = mutableListOf<TouristSpot>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
                val description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION))
                val image = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE))
                favorites.add(TouristSpot(id, name, description, 0, image))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return favorites
    }
}
