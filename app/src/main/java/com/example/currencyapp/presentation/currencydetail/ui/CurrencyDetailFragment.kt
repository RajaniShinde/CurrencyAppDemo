package com.example.currencyapp.presentation.currencydetail.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyapp.R
import com.example.currencyapp.data.currency.model.CurrencyData
import com.example.currencyapp.databinding.FragmentHistoryLayoutBinding
import com.example.currencyapp.framework.ui.BaseFragment
import com.example.currencyapp.presentation.currencydetail.adapter.HistoricalCurrencyAdapter
import com.example.currencyapp.presentation.currencydetail.viewmodel.CurrencyDetailViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history_layout.*
import timber.log.Timber

@AndroidEntryPoint
class CurrencyDetailFragment : BaseFragment() {
    private val currencyViewModel: CurrencyDetailViewModel by viewModels()
    private var fromCurrency:String =""
    private var toCurrency : String = ""
    private var currencyAmountList = ArrayList<CurrencyData>()
    private lateinit var adapter: HistoricalCurrencyAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun getInstance(): CurrencyDetailFragment {
            return CurrencyDetailFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Here will do databinding
        return viewBinding(
            R.layout.fragment_history_layout, inflater, container,
            FragmentHistoryLayoutBinding::class.java
        ).apply {
            viewModel = this@CurrencyDetailFragment.currencyViewModel
        }.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDialog()
        setupUI()
        getResponse()
    }


    private fun setupUI() {

        val bundle = arguments
        fromCurrency = bundle?.getString("fromCurrency").toString()
        toCurrency = bundle?.getString("toCurrency").toString()

//      currencyViewModel.getHistoricalCurrencyData("USD",DateUtils.getStartDate(), DateUtils.getEndDate())
        currencyViewModel.getHistoricalCurrencyData("USD","2020-01-01", "2020-01-04")
        currencyViewModel.getOtherCurrencyData(toCurrency,"CAD,INR,EUR,USD,HKD,ISK,PHP,DKK,HUF")

        linearLayoutManager = LinearLayoutManager(this@CurrencyDetailFragment.requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerview.layoutManager = linearLayoutManager
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                this@CurrencyDetailFragment.requireContext(),
                linearLayoutManager.orientation
            )
        )
        recyclerview.setHasFixedSize(true)

    }

    private fun showDialog() {

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getResponse() {
        currencyViewModel.onErrorLD.observe(viewLifecycleOwner){
            Toast.makeText(this@CurrencyDetailFragment.requireContext(),getErrorMessage(it), Toast.LENGTH_SHORT).show()
        }
  
        currencyViewModel.onSuccessLD.observe(viewLifecycleOwner){
            val response = it?.rates?.toSortedMap()
            Timber.tag("XAxisValueFormatter").d( "getHistoricalRates: {${toCurrency}} + ${response?.values} ? ")

            val listOfRates = arrayListOf<Entry>()

            response?.let { it1 ->
                repeat(it1.size){ i ->
                    listOfRates.add(Entry(i.toFloat(), response.values.elementAt(i)?.get(toCurrency)!!.toFloat()))
                }
            }

            val dates = arrayListOf<String>()
            if (response != null) {
                repeat(response.size) { i ->
                    response.keys.let { it1 -> dates.add(it1.elementAt(i)) }
                }
            }

            setLineChart(dates, listOfRates)

        }
        currencyViewModel.getLatestRates().observe(viewLifecycleOwner) {
            adapter = HistoricalCurrencyAdapter()
            recyclerview.adapter = adapter
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        currencyViewModel.onGetCurrencySuccessLD.observe(viewLifecycleOwner){ it ->
            it?.rates?.entries?.map {
                currencyAmountList.add(CurrencyData(it.key, it.value))
             }
        }
    }

    private fun setLineChart(dates: List<String>, listOfRates: ArrayList<Entry>) {
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(false)

        val lineDataSet = LineDataSet(listOfRates, getString(R.string.currency_rates))
        lineDataSet.fillAlpha = 110
        lineDataSet.color = Color.RED
        lineDataSet.valueTextSize = 8f
        lineDataSet.valueTextColor = Color.BLUE
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = ContextCompat.getColor(this@CurrencyDetailFragment.requireContext(), R.color.teal_200)

        val dataSets = arrayListOf<ILineDataSet>()
        dataSets.add(lineDataSet)

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = XAxisValueFormatter(dates.toList())
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelCount = 5
        xAxis.labelRotationAngle = -45f

        val lineData = LineData(dataSets)
        lineData.setDrawValues(true)
        lineChart.data = lineData
        lineChart.invalidate()

        // Hide Left Axis
        lineChart.axisLeft.textColor = ContextCompat.getColor(this@CurrencyDetailFragment.requireContext(), R.color.white)
        // Change Label Text Color
        lineChart.legend.textColor = Color.GREEN
        // Make Right Axis 5 labels
        lineChart.axisRight.labelCount = 5
    }

}