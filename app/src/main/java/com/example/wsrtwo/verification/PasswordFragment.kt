package com.example.wsrtwo.verification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.wsrtwo.R
import com.example.wsrtwo.databinding.FragmentPasswordBinding


class PasswordFragment : Fragment() {

    private lateinit var pinCircles: List<View>
    private lateinit var del:ImageView
    private var pinCode: String = ""

    private var _binding:FragmentPasswordBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordBinding.inflate(layoutInflater,container,false)

        binding.skipped.setOnClickListener {
        }

        pinCircles = listOf( binding.circle, binding.circleTwo, binding.circleThree, binding.circleFour)

        //удаление
        del = binding.pinDel
        del.setOnClickListener {
            if(pinCode.isNotEmpty()){
                pinCode = pinCode.dropLast(1)
                upDatePinCircles()
            }
        }
        binding.pin0.setOnClickListener { onPinDigitPressed(0) }
        binding.pin1.setOnClickListener { onPinDigitPressed(1) }
        binding.pin2.setOnClickListener { onPinDigitPressed(2) }
        binding.pin3.setOnClickListener { onPinDigitPressed(3) }
        binding.pin4.setOnClickListener { onPinDigitPressed(4) }
        binding.pin5.setOnClickListener { onPinDigitPressed(5) }
        binding.pin6.setOnClickListener { onPinDigitPressed(6) }
        binding.pin7.setOnClickListener { onPinDigitPressed(7) }
        binding.pin8.setOnClickListener { onPinDigitPressed(8) }
        binding.pin9.setOnClickListener { onPinDigitPressed(9) }

        return binding.root
    }
    private fun onPinDigitPressed(digit:Int){
        if(pinCode.length < pinCircles.size){
            pinCode += digit.toString()
            upDatePinCircles()
        }
    }

    private fun upDatePinCircles(){
        pinCircles.forEachIndexed { index, view ->
            if(index < pinCode.length){
                view.setBackgroundResource(R.drawable.circle_blue)
            }else{
                view.setBackgroundResource(R.drawable.circle_circle)
            }
        }
        if(pinCode.length == pinCircles.size){
            navigationToCreateUserFragment()
        }
    }

    private fun navigationToCreateUserFragment() {
        val action = PasswordFragmentDirections.actionPasswordFragmentToCreateUser()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}