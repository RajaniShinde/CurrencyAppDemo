package com.example.currencyapp.presentation.currencydetail.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyDetailActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mBundle = Bundle()

        if(intent != null){
            mBundle.putString("fromCurrency", intent.getStringExtra("fromCurrency"))
            mBundle.putString("toCurrency",intent.getStringExtra("toCurrency"))
        }
        if(savedInstanceState == null){
            val createFragment = CurrencyDetailFragment.getInstance()
            createFragment.arguments = mBundle
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer,createFragment).commitNow()
        }
    }



}