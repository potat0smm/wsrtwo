package com.example.wsrtwo.start

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.wsrtwo.R


@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashScreen_to_viewPagerFragment)
        },1000)
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}