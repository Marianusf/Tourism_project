package com.example.tourism_project

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Nama dan versi database
    companion object {
        private const val DATABASE_NAME = "tourism_guide.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_CATEGORIES = "categories"
        const val TABLE_TOURIST_SPOTS = "tourist_spots"

        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_CATEGORY_ID = "category_id" // ID kategori tempat wisata
        const val COLUMN_IMAGE = "image" // Gambar (resource ID)
        const val COLUMN_CATEGORY_NAME = "category_name" // Nama kategori
    }

    // Membuat tabel pada saat database pertama kali dibuat
    override fun onCreate(db: SQLiteDatabase?) {
        val createCategoriesTable = """
            CREATE TABLE $TABLE_CATEGORIES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY_NAME TEXT NOT NULL
            )
        """

        val createTouristSpotsTable = """
            CREATE TABLE $TABLE_TOURIST_SPOTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_CATEGORY_ID INTEGER,
                $COLUMN_IMAGE INTEGER,
                FOREIGN KEY($COLUMN_CATEGORY_ID) REFERENCES $TABLE_CATEGORIES($COLUMN_ID)
            )
        """

        db?.execSQL(createCategoriesTable)
        db?.execSQL(createTouristSpotsTable)
    }

    // Upgrade database (jika diperlukan)
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TOURIST_SPOTS")
        onCreate(db)
    }
}
