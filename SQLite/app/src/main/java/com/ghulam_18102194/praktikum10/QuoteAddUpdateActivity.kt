package com.ghulam_18102194.praktikum10


import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ghulam_18102194.praktikum10.data.Quote
import com.ghulam_18102194.praktikum10.databinding.ActivityQuoteAddUpdateBinding
import com.ghulam_18102194.praktikum10.db.DatabaseContract
import com.ghulam_18102194.praktikum10.db.DatabaseContract.QuoteColumns.*
import com.ghulam_18102194.praktikum10.db.DatabaseContract.QuoteColumns.Companion.DATE
import com.ghulam_18102194.praktikum10.db.QuoteHelper
import com.ghulam_18102194.praktikum10.helper.ALERT_DIALOG_CLOSE
import com.ghulam_18102194.praktikum10.helper.ALERT_DIALOG_DELETE
import com.ghulam_18102194.praktikum10.helper.EXTRA_POSITION
import com.ghulam_18102194.praktikum10.helper.EXTRA_QUOTE
import com.ghulam_18102194.praktikum10.helper.RESULT_ADD
import com.ghulam_18102194.praktikum10.helper.RESULT_DELETE
import com.ghulam_18102194.praktikum10.helper.RESULT_UPDATE
import com.ghulam_18102194.praktikum10.helper.categoryList
import com.ghulam_18102194.praktikum10.helper.getCurrentDate

class QuoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {

    private var isEdit = false
    private var quote: Quote? = null
    private var position: Int = 0
    private var category: String = "0"
    private lateinit var quoteHelper: QuoteHelper
    private lateinit var binding: ActivityQuoteAddUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var spinnerAdapter= ArrayAdapter(this, android.R.layout.simple_list_item_1,categoryList)
        binding.edtCategory.adapter=spinnerAdapter
        binding.edtCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                category = position.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@QuoteAddUpdateActivity, "array terpilih = " +
                        position.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        quoteHelper = QuoteHelper.getInstance(applicationContext)
        quoteHelper.open()
        quote = intent.getParcelableExtra(EXTRA_QUOTE)
        if (quote != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            quote = Quote()
        }
        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            quote?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
                binding.edtName.setText(it.name)
                binding.edtYear.setText(it.year)
                binding.edtCategory.setSelection(it.category!!.toInt())
            }!!
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val title = binding.edtTitle.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            val year = binding.edtYear.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            if (title.isEmpty()) {
                binding.edtTitle.error = "Field can not be blank"
                return
            }
            quote?.title = title
            quote?.description = description
            quote?.name = name
            quote?.year = year
            quote?.category = category
            val intent = Intent()
            intent.putExtra(EXTRA_QUOTE, quote)
            intent.putExtra(EXTRA_POSITION, position)
            val values = ContentValues()
            values.put(DatabaseContract.QuoteColumns.TITLE, title)
            values.put(DatabaseContract.QuoteColumns.NAME, name)
            values.put(DatabaseContract.QuoteColumns.YEAR, year)
            values.put(DatabaseContract.QuoteColumns.DESCRIPTION, description)
            values.put(DatabaseContract.QuoteColumns.CATEGORY, category)
            if (isEdit) {
                val result = quoteHelper.update(quote?.id.toString(),
                    values).toLong()
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    Toast.makeText(this@QuoteAddUpdateActivity, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                }
            } else {
                quote?.date = getCurrentDate()
                values.put(DATE, getCurrentDate())
                val result = quoteHelper.insert(values)
                if (result > 0) {
                    quote?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    Toast.makeText(this@QuoteAddUpdateActivity, "Gagal menambah data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Quote"
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya") { _, _ ->
                    if (isDialogClose) {
                        finish()
                    } else {
                        val result = quoteHelper.deleteById(quote?.id.toString()).toLong()
                        if (result > 0) {
                            val intent = Intent()
                            intent.putExtra(EXTRA_POSITION, position)
                            setResult(RESULT_DELETE, intent)
                            finish()
                        } else {
                            Toast.makeText(this@QuoteAddUpdateActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}