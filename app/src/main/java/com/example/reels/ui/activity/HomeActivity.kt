package com.example.reels.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.reels.R
import com.example.reels.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        navController = Navigation.findNavController(this, R.id.homeActivityNavigation)
        addListener()
    }

    private fun addListener() {
        binding.bttmNavigation.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.Reels ->{
                    navController.navigate(R.id.reelsFragment)
                }

                R.id.Music->{
                    navController.navigate(R.id.musicFragment)
                }

                R.id.Challenge->{
                    navController.navigate(R.id.challengeFragment)
                }

                R.id.Profile ->{
                    navController.navigate(R.id.profileFragment)
                }

            }
             true
        }

        binding.bttmNavigation.setOnNavigationItemReselectedListener {  }
    }
}