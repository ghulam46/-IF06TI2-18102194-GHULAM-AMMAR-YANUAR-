package com.ghulam_18102194.praktikum10.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ghulam_18102194.praktikum10.db.DatabaseContract.QuoteColumns.Companion.TABLE_QUOTE
import com.ghulam_18102194.praktikum10.db.DatabaseContract.QuoteColumns.Companion._ID
import java.sql.SQLException

class QuoteHelper(context: Context) {
    companion object {
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: QuoteHelper? = null
        private lateinit var database: SQLiteDatabase
        fun getInstance(context: Context): QuoteHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: QuoteHelper(context)
                }
    }
    init {
        dataBaseHelper = DatabaseHelper(context)
    }
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }
    fun queryAll(): Cursor {
        return database.query(
                TABLE_QUOTE,
                null,
                null,
                null,
                null,
                null,
                "$_ID ASC")
    }
    fun queryById(id: String): Cursor {
        return database.query(
                TABLE_QUOTE,
                null,
                "$_ID = ?",
                arrayOf(id),
                null,
                null,
                null,
                null)
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(
                TABLE_QUOTE, null, values)
    }
    fun update(id: String, values: ContentValues?): Int {
        return database.update(
                TABLE_QUOTE, values, "$_ID = ?", arrayOf(id))
    }
    fun deleteById(id: String): Int {
        return database.delete(
                TABLE_QUOTE, "$_ID = '$id'", null)
    }
}