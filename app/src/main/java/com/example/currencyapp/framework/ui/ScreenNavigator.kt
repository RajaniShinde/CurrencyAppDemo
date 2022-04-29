package com.example.currencyapp.framework.ui

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.example.currencyapp.presentation.currencydetail.ui.CurrencyDetailActivity

class ScreenNavigator (
    private val activity: Activity,
    private val fragmentManager: FragmentManager
    ){
    fun navigateToDetailScreen(fromCurremcy: String, toCurrency: String) {
        val intent = Intent(activity, CurrencyDetailActivity::class.java)
        intent.putExtra("fromCurrency","USD")
        intent.putExtra("toCurrency","EUR")
        activity.startActivity(intent)
    }

}