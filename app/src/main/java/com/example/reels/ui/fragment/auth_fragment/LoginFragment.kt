package com.example.reels.ui.fragment.auth_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.reels.R
import com.example.reels.databinding.FragmentLoginBinding
import com.example.reels.ui.activity.HomeActivity
import com.example.reels.utils.SharedPrefernces
import com.google.android.exoplayer2.util.Log
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        auth = FirebaseAuth.getInstance()

        addListerner()


        return binding.root
    }

    private fun addListerner() {
        binding.apply {
            tvSignUp.setOnClickListener {
                findNavController().navigate(R.id.signUpFragment)
            }

            btnSubmit.setOnClickListener{
                if (validation()){
                    progressBar.visibility = View.VISIBLE
                    signIn()
                }
            }

        }
    }

    private fun validation(): Boolean {
        if (binding.etEmail.text.toString().isEmpty()){
            return false
        }else if (binding.etPassword.text.toString().isEmpty()){
            return false
        }
        return true
    }

    private fun signIn() {
        auth.signInWithEmailAndPassword(binding.etEmail.text.toString(),binding.etPassword.text.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                binding.progressBar.visibility = View.GONE
                SharedPrefernces.init().is_Login = true
                startActivity(Intent(requireContext(),HomeActivity::class.java))
                activity?.finish()
            }else{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),it.exception.toString(),Toast.LENGTH_SHORT).show()
                Log.i("failed",it.exception?.message!!)
            }
        }
    }

}