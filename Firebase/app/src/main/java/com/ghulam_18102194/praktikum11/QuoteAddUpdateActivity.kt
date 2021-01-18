package com.ghulam_18102194.praktikum11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.ghulam_18102194.praktikum11.data.Quote
import com.ghulam_18102194.praktikum11.databinding.ActivityQuoteAddUpdateBinding
import com.ghulam_18102194.praktikum11.helper.ALERT_DIALOG_CLOSE
import com.ghulam_18102194.praktikum11.helper.ALERT_DIALOG_DELETE
import com.ghulam_18102194.praktikum11.helper.EXTRA_POSITION
import com.ghulam_18102194.praktikum11.helper.EXTRA_QUOTE
import kotlinx.android.synthetic.main.activity_dashboard_quote.*

class QuoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false
    private var categoriesSpinnerArray = ArrayList<String>()
    private var quote: Quote? = null
    private var position: Int = 0
    private var categorySelection: Int = 0
    private var categoryName: String = "0"
    private lateinit var binding: ActivityQuoteAddUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoriesSpinnerArray = getCategories()
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

    private fun getCategories(): ArrayList<String> {
        progressbar.visibility = View.VISIBLE
        return categoriesSpinnerArray
    }

    private fun setCategories(paymentMethodSpinnerAarray: ArrayList<String>) {
        var spinnerAdapter= ArrayAdapter(this, android.R.layout.simple_list_item_1,paymentMethodSpinnerAarray)
        binding.edtCategory.adapter=spinnerAdapter
        binding.edtCategory.setSelection(categorySelection)
        binding.edtCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                categoryName = binding.edtCategory.selectedItem.toString()
                progressbar.visibility = View.INVISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            if (title.isEmpty()) {
                binding.edtTitle.error = "Field can not be blank"
                return
            }
            if (isEdit) {

            } else {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
        return true
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

                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}