package com.example.currencyapp.framework.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
 import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.currencyapp.framework.common.LocalizedMessageManager
import com.example.currencyapp.framework.dto.Error
import com.example.currencyapp.framework.errorhandler.ErrorDetails

abstract class BaseFragment : Fragment() {

    private lateinit var ctx: Context
    lateinit var appCompactActivity : AppCompatActivity
    protected lateinit var screenNavigator: ScreenNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.ctx = context
        screenNavigator = ScreenNavigator(requireActivity(),this.parentFragmentManager)
        if(context is AppCompatActivity){
            this.appCompactActivity = context
        }
    }

    fun <VB: ViewDataBinding> viewBinding(
        layoutId : Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        vb: Class<VB>
    ):VB{
       val binding: VB = DataBindingUtil.inflate(inflater,layoutId,container,false)
        binding.lifecycleOwner = this
        return binding
    }

    fun getErrorMessage(error: Error): String {
       return ErrorDetails(error, LocalizedMessageManager(ctx)).getErrorMessage()
    }


}