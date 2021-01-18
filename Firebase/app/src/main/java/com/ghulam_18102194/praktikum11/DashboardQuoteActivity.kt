package com.ghulam_18102194.praktikum11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ghulam_18102194.praktikum11.adapter.QuoteAdapter
import com.ghulam_18102194.praktikum11.data.Quote
import com.ghulam_18102194.praktikum11.databinding.ActivityDashboardQuoteBinding
import com.ghulam_18102194.praktikum11.helper.REQUEST_ADD
import com.ghulam_18102194.praktikum11.helper.REQUEST_UPDATE
import com.ghulam_18102194.praktikum11.helper.RESULT_ADD
import com.ghulam_18102194.praktikum11.helper.RESULT_DELETE
import com.ghulam_18102194.praktikum11.helper.RESULT_UPDATE
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dashboard_quote.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardQuoteActivity : AppCompatActivity() {
    private lateinit var adapter: QuoteAdapter
    private lateinit var binding: ActivityDashboardQuoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardQuoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Quotes"
        binding.rvQuotes.layoutManager = LinearLayoutManager(this)
        binding.rvQuotes.setHasFixedSize(true)
        adapter = QuoteAdapter(this)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@DashboardQuoteActivity, QuoteAddUpdateActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }
        loadQuotes()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        loadQuotes()
    }

    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val quotesList = ArrayList<Quote>()
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvQuotes, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                REQUEST_ADD -> if (resultCode == RESULT_ADD) {
                    loadQuotes()
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                REQUEST_UPDATE ->
                    when (resultCode) {
                        RESULT_UPDATE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        RESULT_DELETE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
    }
}