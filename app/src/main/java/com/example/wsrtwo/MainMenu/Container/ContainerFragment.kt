package com.example.wsrtwo.MainMenu.Container

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.example.wsrtwo.MainMenu.Menu.MenuFragment
import com.example.wsrtwo.MainMenu.UserProfil.UserFragment
import com.example.wsrtwo.R
import com.example.wsrtwo.databinding.FragmentContainerBinding


class ContainerFragment : Fragment(){

    private var _binding: FragmentContainerBinding?=null
    private val binding get()= _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContainerBinding.inflate(layoutInflater,container,false)
        replaceFragment(MenuFragment())

        binding.bottomNavMenu.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.analyzes -> replaceFragment(MenuFragment())
                R.id.profil ->replaceFragment(UserFragment())
                else -> {}
            }
            true
        }
        return binding.root
    }

    private fun replaceFragment(fragment: Fragment?){
        fragment?.let {
            val fragmentManager = childFragmentManager
            val fragmentTransition = fragmentManager.beginTransaction()
            fragmentTransition.replace(R.id.frameLayout,it)
            fragmentTransition.commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}