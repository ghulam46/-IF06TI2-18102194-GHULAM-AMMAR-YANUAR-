package com.ghulam_18102194.praktikum10.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ghulam_18102194.praktikum10.db.DatabaseContract.QuoteColumns.Companion.TABLE_QUOTE


internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbquoteapp"
        private const val DATABASE_VERSION = 8
        private const val SQL_CREATE_TABLE_QUOTE = "CREATE TABLE $TABLE_QUOTE"  +
                " (${DatabaseContract.QuoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,"+
                " ${DatabaseContract.QuoteColumns.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.QuoteColumns.DESCRIPTION} TEXT NOT NULL," +
                " ${DatabaseContract.QuoteColumns.CATEGORY} TEXT NOT NULL," +
                " ${DatabaseContract.QuoteColumns.DATE} TEXT NOT NULL, " +
                " ${DatabaseContract.QuoteColumns.NAME} TEXT NOT NULL, " +
                " ${DatabaseContract.QuoteColumns.YEAR} TEXT NOT NULL) "
    }
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(SQL_CREATE_TABLE_QUOTE)
        }
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_QUOTE")
        }
        onCreate(db)
    }
}
