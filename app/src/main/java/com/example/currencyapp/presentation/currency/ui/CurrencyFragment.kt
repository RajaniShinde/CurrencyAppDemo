package com.example.currencyapp.presentation.currency.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.currencyapp.R
import com.example.currencyapp.data.currency.model.CurrencyData
import com.example.currencyapp.databinding.FragmentCurrencyLayoutBinding
import com.example.currencyapp.framework.ui.BaseFragment
import com.example.currencyapp.presentation.currency.viewmodel.CurrencyViewModel
import com.example.currencyapp.presentation.currencydetail.ui.CurrencyDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_currency_layout.*
import java.lang.Exception
import kotlin.math.round


@AndroidEntryPoint
class CurrencyFragment : BaseFragment() {
    internal val currencyViewModel: CurrencyViewModel by viewModels()
    private var currencyList = ArrayList<String>()
    private var currencyAmountList = ArrayList<CurrencyData>()
    private var progressDialog : ProgressDialog? = null
    private lateinit var fromCurrencyArrayAdapter: ArrayAdapter<String>
    private lateinit var toCurrencyArrayAdapter: ArrayAdapter<String>
    private var selectedToCurrencyPosition = 1
    private var selectedFromCurrencyPosition = 0

    companion object{
        fun getInstance(): CurrencyFragment{
            return CurrencyFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Here will do databinding
        return viewBinding(
            R.layout.fragment_currency_layout, inflater,container,
            FragmentCurrencyLayoutBinding::class.java).apply {
            viewModel = this@CurrencyFragment.currencyViewModel }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDialog()
        setupUI()
        getResponse()
    }

    private fun setupUI() {

        currencyViewModel.getCurrencyData("USD")
        etFromCurrency.setText("1")

        ArrayAdapter(this@CurrencyFragment.requireContext(), android.R.layout.simple_spinner_item, currencyList).also { fromCurrencyArrayAdapter = it }
        fromCurrencyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spFromCurrency.adapter = fromCurrencyArrayAdapter
        spFromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedFromCurrencyPosition = parent.selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
         //Adding values to 2nd Spinner
        toCurrencyArrayAdapter =
            ArrayAdapter<String>(this@CurrencyFragment.requireContext(), android.R.layout.simple_spinner_item, currencyList)
        toCurrencyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spToCurrency.setSelection(1)
        spToCurrency.adapter = toCurrencyArrayAdapter

        spToCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedToCurrencyPosition = parent.selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        etFromCurrency.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(count == 0) {
                    etToCurrency.setText("")
                }else {
                    currencyViewModel.getCurrencyData(spFromCurrency.selectedItem.toString())
                }
            }
        })

        btnExchange.setOnClickListener {
            try {
                val toPosition= spToCurrency.selectedItemPosition
                val fromPosition= spFromCurrency.selectedItemPosition
                spFromCurrency.setSelection(toPosition)
                spToCurrency.setSelection(fromPosition)
                selectedToCurrencyPosition = fromPosition
                selectedFromCurrencyPosition = toPosition

                if(!etFromCurrency.text.isNullOrEmpty()){
                     currencyViewModel.getCurrencyData(spFromCurrency.selectedItem.toString())
                 }
            }catch (e: Exception){
                e.localizedMessage
            }
        }

        btnDetails.setOnClickListener {
            screenNavigator.navigateToDetailScreen(spFromCurrency.selectedItem.toString(),spToCurrency.selectedItem.toString())

        }
    }

    private fun showDialog() {
        currencyViewModel.onLoadingLD.observe(viewLifecycleOwner){
            if(it){
                progressDialog = ProgressDialog.show(appCompactActivity,"Processing","Please Wait",true)
            }else{
                progressDialog?.dismiss()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getResponse() {

        currencyViewModel.onErrorLD.observe(viewLifecycleOwner){
            Toast.makeText(this@CurrencyFragment.requireContext(),getErrorMessage(it),Toast.LENGTH_SHORT).show()
        }

        currencyViewModel.onGetCurrencySuccessLD.observe(viewLifecycleOwner){ it ->

            it?.rates?.entries?.map {
                currencyAmountList.add(CurrencyData(it.key, it.value))
                currencyList.add((it.key))
            }
            fromCurrencyArrayAdapter.notifyDataSetChanged()
            with(toCurrencyArrayAdapter) { notifyDataSetChanged() }
            spFromCurrency.setSelection(selectedFromCurrencyPosition)
            spToCurrency.setSelection(selectedToCurrencyPosition)
            getRatesforSelected()
        }
    }

    @SuppressLint("LogNotTimber", "LogConditional")
    private fun getRatesforSelected() {
        try {
            if(etFromCurrency.text != null || !etFromCurrency.text.equals( "0")) {
                etFromCurrency.error = null
                val fromValue: Int = Integer.parseInt(etFromCurrency.text.toString())
                var convertedRate = 0.0
                when (currencyAmountList[selectedToCurrencyPosition].currencyCode) {
                    spToCurrency.selectedItem.toString() -> {
                        convertedRate = currencyAmountList[selectedToCurrencyPosition].amt
                    }
                }
                val convertedCurrency = round((fromValue * 100) * convertedRate) / 100
                etToCurrency.setText("$convertedCurrency")
            }else{
                etFromCurrency.error = "Please enter valid data"
            }
        }catch (e: Exception){
            Log.d("Calculation",e.toString())
        }

    }

}