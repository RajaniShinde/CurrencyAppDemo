package com.example.currencyapp.data.currency.repository

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.domain.currency.model.CurrencyDataRequest
import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.network.HttpApiImpl
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConvertCurrencyRepositoryImplTest{

    @Mock
    private lateinit var httpApiImpl: HttpApiImpl

    private lateinit var currencyRepositoryImpl: ConvertCurrencyRepositoryImpl

    @Before
    fun setUp(){
        currencyRepositoryImpl = ConvertCurrencyRepositoryImpl(httpApiImpl)
    }

    @Test
    fun getCurrencyData(){
        val currencyStringData = "{\"success\":true,\"rates\":{\"AED\":4.013798,\"AFN\":96.717126,\"ALL\":119.772306,\"AMD\":526.276444,\"ANG\":1.969115,\"AOA\":485.192885,\"ARS\":122.053455,\"AUD\":1.43481,\"AWG\":1.96702,\"AZN\":1.857066,\"BAM\":1.948003,\"BBD\":2.206147,\"BDT\":94.177115,\"BGN\":1.948924,\"BHD\":0.412053,\"BIF\":2201.970067,\"BMD\":1.092789,\"BND\":1.481309,\"BOB\":7.511826,\"BRL\":5.099934,\"BSD\":1.092665,\"BTC\":2.378859e-5,\"BTN\":82.285977,\"BWP\":12.515584,\"BYN\":3.558502,\"BYR\":21418.666658,\"BZD\":2.202381,\"CAD\":1.360681,\"CDF\":2196.506127,\"CHF\":1.015267,\"CLF\":0.031063,\"CLP\":856.921611,\"CNY\":6.954294,\"COP\":4067.437582,\"CRC\":720.123327,\"CUC\":1.092789,\"CUP\":28.958912,\"CVE\":109.224201,\"CZK\":24.363761,\"DJF\":194.511114,\"DKK\":7.438113,\"DOP\":60.157876,\"DZD\":156.137275,\"EGP\":19.934008,\"ERN\":16.391842,\"ETB\":55.743128,\"EUR\":1,\"FJD\":2.268192,\"FKP\":0.838124,\"GBP\":0.832907,\"GEL\":3.392526,\"GGP\":0.838124,\"GHS\":8.223237,\"GIP\":0.838124,\"GMD\":58.737738,\"GNF\":9698.503391,\"GTQ\":8.396677,\"GYD\":228.583631,\"HKD\":8.562166,\"HNL\":26.630708,\"HRK\":7.537627,\"HTG\":115.815238,\"HUF\":375.106443,\"IDR\":15673.054688,\"ILS\":3.502373,\"IMP\":0.838124,\"INR\":82.306473,\"IQD\":1595.472108,\"IRR\":46279.618621,\"ISK\":141.549262,\"JEP\":0.838124,\"JMD\":167.50127,\"JOD\":0.774816,\"JPY\":134.827775,\"KES\":125.834981,\"KGS\":93.690474,\"KHR\":4431.260063,\"KMF\":485.580587,\"KPW\":983.510586,\"KRW\":1329.334274,\"KWD\":0.332711,\"KYD\":0.910554,\"KZT\":510.194679,\"LAK\":12840.27241,\"LBP\":1654.482256,\"LKR\":327.781655,\"LRD\":166.866591,\"LSL\":15.998333,\"LTL\":3.226722,\"LVL\":0.661017,\"LYD\":5.069998,\"MAD\":10.613711,\"MDL\":20.048942,\"MGA\":4354.76467,\"MKD\":61.368978,\"MMK\":1942.81562,\"MNT\":3145.891571,\"MOP\":8.815024,\"MRO\":390.125526,\"MUR\":49.124892,\"MVR\":16.883173,\"MWK\":893.357059,\"MXN\":21.733674,\"MYR\":4.59496,\"MZN\":69.75271,\"NAD\":15.998602,\"NGN\":454.294007,\"NIO\":39.023941,\"NOK\":9.526373,\"NPR\":131.657403,\"NZD\":1.566169,\"OMR\":0.420686,\"PAB\":1.092665,\"PEN\":4.043869,\"PGK\":3.846563,\"PHP\":56.022384,\"PKR\":200.390179,\"PLN\":4.645675,\"PYG\":7555.545128,\"QAR\":3.978897,\"RON\":4.941268,\"RSD\":117.673677,\"RUB\":91.182653,\"RWF\":1111.912925,\"SAR\":4.099458,\"SBD\":8.766895,\"SCR\":15.752869,\"SDG\":489.020788,\"SEK\":10.291369,\"SGD\":1.48339,\"SHP\":1.505209,\"SLL\":12976.870959,\"SOS\":638.188578,\"SRD\":22.66883,\"STD\":22618.528352,\"SVC\":9.560411,\"SYP\":2745.086626,\"SZL\":15.998616,\"THB\":36.593163,\"TJS\":13.657449,\"TMT\":3.83569,\"TND\":3.239572,\"TOP\":2.456048,\"TRY\":16.095664,\"TTD\":7.390771,\"TWD\":31.330595,\"TZS\":2537.456149,\"UAH\":32.122032,\"UGX\":3878.768145,\"USD\":1.092789,\"UYU\":44.944029,\"UZS\":12457.795579,\"VEF\":233671339472.58,\"VND\":24997.55101,\"VUV\":124.705383,\"WST\":2.865343,\"XAF\":653.358675,\"XAG\":0.044616,\"XAU\":0.000566,\"XCD\":2.953317,\"XDR\":0.793544,\"XOF\":644.189202,\"XPF\":118.021448,\"YER\":273.469964,\"ZAR\":15.982642,\"ZMK\":9836.411314,\"ZMW\":19.202407,\"ZWL\":351.877649}}"
        val dataResponse = Gson().fromJson(currencyStringData,
            CurrencyListResponse::class.java)
         val currencyDataRequest = CurrencyDataRequest("USD")
        val outComeResponse = OperationResult.SuccessOperationResult((dataResponse))
        runBlocking {
            Mockito.`when`(httpApiImpl.getCurrencyData(currencyDataRequest))
                .thenReturn(outComeResponse)
          val result=  currencyRepositoryImpl.getCurrencyData(currencyDataRequest)
            Assert.assertTrue(result.isSuccess()?.result?.success == true)
        }
    }
}