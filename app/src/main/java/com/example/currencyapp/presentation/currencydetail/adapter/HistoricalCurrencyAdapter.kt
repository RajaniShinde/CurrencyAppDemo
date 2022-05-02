package com.example.currencyapp.presentation.currencydetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.databinding.HistoryListItemBinding

class HistoricalCurrencyAdapter : ListAdapter<Pair<String, Double>, RecyclerView.ViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryListItemBinding.inflate(inflater)

        return RatesViewHolder(binding)
    }

//    override fun getItemCount(): Int = currencyAmountList.size

    override fun onBindViewHolder(holder:  RecyclerView.ViewHolder, position: Int) {
        val currentRate: Pair<String, Double> = getItem(position)
        (holder as RatesViewHolder).bind(currentRate)
    }


    class RatesViewHolder(private var binding: HistoryListItemBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(currentRate: Pair<String, Double>) {
            val currencyName = currentRate.first
            val currencyRate = currentRate.second

               binding.txtCurrencyCode.text = "Currency:$currencyName"
                binding.txtCurrencyName.text = currentRate.first
                if (currentRate.second == 1.0) {
                    binding.txtCurrencyRate.text = "1"
                } else {
                    binding.txtCurrencyRate.text = String.format("%.4f", currencyRate)
                }

        }
    }
}

    private class DiffCallback : DiffUtil.ItemCallback<Pair<String, Double>>() {
        override fun areItemsTheSame(oldItem: Pair<String, Double>, newItem: Pair<String, Double>): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(oldItem: Pair<String, Double>, newItem: Pair<String, Double>): Boolean {
            return oldItem == newItem
        }
    }