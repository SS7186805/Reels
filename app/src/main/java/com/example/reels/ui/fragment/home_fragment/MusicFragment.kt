package com.example.reels.ui.fragment.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.reels.R
import com.example.reels.databinding.FragmentMusicBinding

class MusicFragment : Fragment() {
private lateinit var binding:FragmentMusicBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_music,container,false)
        return binding.root
    }
}