package com.ghulam_18102194.praktikum10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ghulam_18102194.praktikum10.adapter.QuoteAdapter
import com.ghulam_18102194.praktikum10.data.Quote
import com.ghulam_18102194.praktikum10.databinding.ActivityMainBinding
import com.ghulam_18102194.praktikum10.db.QuoteHelper
import com.ghulam_18102194.praktikum10.helper.mapCursorToArrayList
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var quoteHelper: QuoteHelper
    private lateinit var adapter: QuoteAdapter
    private val EXTRA_STATE = "EXTRA_STATE"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Quotes"
        binding.rvQuotes.layoutManager = LinearLayoutManager(this)
        binding.rvQuotes.setHasFixedSize(true)
        adapter = QuoteAdapter(this)
        binding.rvQuotes.adapter = adapter
        quoteHelper = QuoteHelper.getInstance(applicationContext)
        quoteHelper.open()
        if (savedInstanceState == null) {
            loadQuotes()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Quote>(EXTRA_STATE)
            if (list != null) {
                adapter.listQuotes = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listQuotes)
    }

    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val cursor = quoteHelper.queryAll()
            var quotes = mapCursorToArrayList(cursor)
            progressbar.visibility = View.INVISIBLE
            if (quotes.size > 0) {
                adapter.listQuotes = quotes
            } else {
                adapter.listQuotes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvQuotes, message, Snackbar.LENGTH_SHORT).show()
    }


}

