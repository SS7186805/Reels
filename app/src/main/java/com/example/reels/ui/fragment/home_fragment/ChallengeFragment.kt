package com.example.reels.ui.fragment.home_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reels.R
import com.example.reels.adapter.UserAdapter
import com.example.reels.databinding.FragmentChallengeBinding
import com.example.reels.model.RegisterUser
import com.example.reels.ui.activity.ChatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class ChallengeFragment : Fragment() {

    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var binding: FragmentChallengeBinding
    private lateinit var userAdapter:UserAdapter
    private var list = ArrayList<RegisterUser>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_challenge, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val reference:DatabaseReference = database.reference.child("user")
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap:DataSnapshot in snapshot.children){
                    val user:RegisterUser? = snap.getValue(RegisterUser::class.java)
                    if (user != null) {
                        if (user.uid.equals(auth.currentUser.uid)){
                            
                        }else{
                            list.add(user)
                        }
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return binding.root
    }

    private fun setAdapter() {
        userAdapter = UserAdapter(requireContext())
        userAdapter.setData(list)
        binding.rvUserList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserList.adapter = userAdapter

        userAdapter.setInstance(object :UserAdapter.OnUserSelectListerner{
            override fun onUser(position: Int, user: RegisterUser) {
                startActivity(Intent(requireContext(),ChatActivity::class.java).putExtra("email",user.email).putExtra("image",user.imageUri).putExtra("uid",user.uid))
            }

        })
    }

}