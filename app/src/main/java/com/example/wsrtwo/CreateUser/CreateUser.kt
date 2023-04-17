package com.example.wsrtwo.CreateUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.wsrtwo.R
import com.example.wsrtwo.databinding.FragmentCreateUserBinding

class CreateUser : Fragment() {


    private var _binding: FragmentCreateUserBinding?=null
    private val binding get()= _binding!!

    private lateinit var editText: List<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateUserBinding.inflate(layoutInflater,container,false)

        navigationToNextFragment()

        editText = listOf(binding.name,binding.patronymic,binding.surname,binding.birthday)

        return binding.root
    }

    private fun navigationToNextFragment(){
        binding.name.addTextChangedListener {
            binding.patronymic.addTextChangedListener {
                binding.surname.addTextChangedListener {
                    binding.birthday.addTextChangedListener {
                            binding.goMenu.isEnabled = false
                            var status = false
                            if (editText.isNotEmpty()) {
                                status = true
                            }
                            binding.goMenu.isEnabled = status
                            binding.goMenu.setOnClickListener {
                                val action =
                                    CreateUserDirections.actionCreateUserToContainerFragment()
                                findNavController().navigate(action)
                            }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}