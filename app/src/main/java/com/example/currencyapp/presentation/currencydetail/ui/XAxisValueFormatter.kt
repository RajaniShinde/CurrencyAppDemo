package com.example.currencyapp.presentation.currencydetail.ui

import com.github.mikephil.charting.formatter.ValueFormatter
import timber.log.Timber

class XAxisValueFormatter (private val values: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        Timber.tag("XAxisValueFormatter").d( "getFormattedValue: Index $value  -> ${values.elementAt(value.toInt())}")
        return values[value.toInt()]
    }
}