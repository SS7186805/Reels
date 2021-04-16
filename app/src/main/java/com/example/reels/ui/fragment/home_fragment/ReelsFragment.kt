package com.example.reels.ui.fragment.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.reels.R
import com.example.reels.adapter.VideoAdapter
import com.example.reels.databinding.FragmentReelsBinding

class ReelsFragment : Fragment() {
    private lateinit var binding:FragmentReelsBinding
    var list = ArrayList<String>()


    private lateinit var adapter: VideoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reels, container, false)

        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("http://1.6.98.142//brian_m4//public//storage//videos//112520200953275fbe2997b98b8.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")
        list.add("https://liciolentimo.co.ke/img/videos/facebook3.mp4")

        adapter = VideoAdapter(requireContext())
        adapter.setData(list)
        binding.viewPagerVideo.adapter = adapter

        return binding.root
    }

}