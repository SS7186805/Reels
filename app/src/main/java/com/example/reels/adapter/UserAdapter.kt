package com.example.reels.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reels.R
import com.example.reels.model.RegisterUser

class UserAdapter(val context:Context):RecyclerView.Adapter<UserAdapter.UserHolder>() {
    var list = ArrayList<RegisterUser>()
    var onUserSelectListerner:OnUserSelectListerner ?= null
    fun setData(list: ArrayList<RegisterUser>) {
        this.list.addAll(list)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_user,parent,false)
        return UserHolder(view)
    }



    override fun onBindViewHolder(holder: UserAdapter.UserHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onUserSelectListerner?.onUser(position,list[position])
        }
        return holder.bind(list[position])
    }

    override fun getItemCount(): Int {
      return list.size
    }

    class UserHolder(item: View) :RecyclerView.ViewHolder(item) {
        var userName = item.findViewById<TextView>(R.id.tvUserName)
        fun bind(registerUser: RegisterUser) {
            userName.text = registerUser.email
        }

    }

    interface OnUserSelectListerner{
        fun onUser(position: Int,user:RegisterUser)
    }

    fun setInstance(onUser:OnUserSelectListerner){
        this.onUserSelectListerner = onUser
    }
}