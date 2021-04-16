package com.example.reels.ui.fragment.home_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.reels.R
import com.example.reels.databinding.FragmentProfileBinding
import com.example.reels.ui.activity.AuthActivity
import com.example.reels.utils.SharedPrefernces
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
private lateinit var binding:FragmentProfileBinding
private lateinit var auth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        auth = FirebaseAuth.getInstance()

        binding.tvLogout.text = auth.currentUser.email

        addListerner()

        return binding.root
    }

    private fun addListerner() {
        binding.apply {
            tvLogout.setOnClickListener{
                SharedPrefernces.init().clearData()
                auth.signOut()
                startActivity(Intent(requireContext(),AuthActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        }
    }
}