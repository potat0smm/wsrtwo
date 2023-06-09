package com.example.wsrtwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wsrtwo.R
import com.example.wsrtwo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}