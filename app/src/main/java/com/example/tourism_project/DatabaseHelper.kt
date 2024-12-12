package com.example.tourism_project

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "tourism.db"
        const val DATABASE_VERSION = 1

        // Tabel kategori
        const val TABLE_CATEGORIES = "category"
        const val COLUMN_ID = "id"
        const val COLUMN_CATEGORY_NAME = "name"
        const val COLUMN_IMAGE_RES = "image_res"

        // Tabel tempat wisata
        const val TABLE_TOURIST_SPOTS = "tourist_spot"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_CATEGORY_ID = "category_id"

        // Tabel favorit
        const val TABLE_FAVORITES = "favorites"
        const val COLUMN_TOURIST_SPOT_ID = "tourist_spot_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Buat tabel kategori
        val createCategoriesTable = """
            CREATE TABLE $TABLE_CATEGORIES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY_NAME TEXT NOT NULL,
                $COLUMN_IMAGE_RES INTEGER NOT NULL
            )
        """.trimIndent()

        // Buat tabel tempat wisata
        val createTouristSpotsTable = """
            CREATE TABLE $TABLE_TOURIST_SPOTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT NOT NULL,
                $COLUMN_CATEGORY_ID INTEGER NOT NULL,
                $COLUMN_IMAGE_RES INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_CATEGORY_ID) REFERENCES $TABLE_CATEGORIES($COLUMN_ID) ON DELETE CASCADE
            )
        """.trimIndent()

        // Buat tabel favorit
        val createFavoritesTable = """
            CREATE TABLE $TABLE_FAVORITES (
                $COLUMN_TOURIST_SPOT_ID INTEGER PRIMARY KEY,
                FOREIGN KEY ($COLUMN_TOURIST_SPOT_ID) REFERENCES $TABLE_TOURIST_SPOTS($COLUMN_ID) ON DELETE CASCADE
            )
        """.trimIndent()

        db.execSQL(createCategoriesTable)
        db.execSQL(createTouristSpotsTable)
        db.execSQL(createFavoritesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TOURIST_SPOTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        onCreate(db)
    }
}
