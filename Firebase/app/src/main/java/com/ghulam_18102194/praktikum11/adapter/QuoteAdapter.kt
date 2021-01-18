package com.ghulam_18102194.praktikum11.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ghulam_18102194.praktikum11.R
import com.ghulam_18102194.praktikum11.data.Quote
import com.ghulam_18102194.praktikum11.helper.EXTRA_POSITION
import com.ghulam_18102194.praktikum11.helper.EXTRA_QUOTE
import com.ghulam_18102194.praktikum11.helper.REQUEST_UPDATE
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class QuoteAdapter(private val activity: Activity): RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    var listQuotes = ArrayList<Quote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listQuotes.size
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(listQuotes[position],position)
    }

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemQuoteBinding.bind(itemView)
        fun bind(quote: Quote, position: Int) {
            binding.tvItemTitle.text = quote.title
            binding.tvItemCategory.text = quote.category
            val timestamp = quote.date as com.google.firebase.Timestamp
            val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
            val sdf = SimpleDateFormat("dd/MMM/yyyy, HH:mm")
            val netDate = Date(milliseconds)
            val date = sdf.format(netDate).toString()
            binding.tvItemDate.text = date
            binding.tvItemDescription.text = quote.description

            binding.cvItemQuote.setOnClickListener{
                val intent = Intent(activity, QuoteAddUpdateActivity::class.java)
                intent.putExtra(EXTRA_POSITION, position)
                intent.putExtra(EXTRA_QUOTE, quote)
                activity.startActivityForResult(intent, REQUEST_UPDATE)
            }
        }
    }
}