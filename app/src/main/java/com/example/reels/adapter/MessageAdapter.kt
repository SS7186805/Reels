package com.example.reels.adapter

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reels.R
import com.example.reels.model.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(var list: ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var item_1 = 1
    var item_2 = 2
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (item_1 == viewType){
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.layout_chats,
                parent,
                false
            )
            MessageHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.layout_reciever_chat,
                parent,
                false
            )
            SenderMessageHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var message:Message = list[position]
        if (holder.javaClass == MessageHolder::class.java){
            var senderHolder = holder as  MessageHolder
            senderHolder.recieverMessage.text = list[position].message
        }else{
           var reciever = holder as SenderMessageHolder
            reciever.senderMessage.text = list[position].message
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        var message:Message = list[position]
        if (FirebaseAuth.getInstance().currentUser.uid.equals(message.senderId)){
            return item_2
        }else{
            return item_1
        }
    }

    class MessageHolder(item: View):RecyclerView.ViewHolder(item){
        var recieverMessage = item.findViewById<TextView>(R.id.tvRecieverMessage)
    }

    class SenderMessageHolder(item: View):RecyclerView.ViewHolder(item){
        var senderMessage = item.findViewById<TextView>(R.id.tvSenderMessage)

    }


}