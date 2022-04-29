package com.example.currencyapp.presentation.currency.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val createFragment = CurrencyFragment.getInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer,createFragment).commitNow()
        }


    }
}