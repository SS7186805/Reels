package com.example.reels.ui.activity

import android.R.attr
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reels.R
import com.example.reels.adapter.MessageAdapter
import com.example.reels.databinding.ActivityChatBinding
import com.example.reels.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var email: String
    private lateinit var image: String
    private lateinit var uid: String
    private lateinit var senderRoom: String
    private lateinit var recieverRoom: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var messageAdapter: MessageAdapter
    var list = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //gvjg
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        email = intent.getStringExtra("email")!!
        image = intent.getStringExtra("image")!!
        uid = intent.getStringExtra("uid")!!
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        senderRoom = auth.currentUser.uid + uid
        recieverRoom = uid + auth.currentUser.uid
        setAdapter()


        val reference: DatabaseReference =
            database.reference.child("Chats").child(senderRoom).child(
                "message"
            )

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                //nln
                for (snap: DataSnapshot in snapshot.children) {
                    val message: Message? = snap.getValue(Message::class.java)
                    list.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        binding.tvEmail.text = email

        addListerner()
    }

    private fun setAdapter() {
        messageAdapter = MessageAdapter(list)
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.adapter = messageAdapter
//        binding.rvChat.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
//            if (attr.bottom < oldBottom) {
//                val lastAdapterItem: Int = messageAdapter.itemCount - 1
//                binding.rvChat.post(Runnable {
//                    var recyclerViewPositionOffset = -1000000
//                    val bottomView: View? = (binding.rvChat.layoutManager as LinearLayoutManager).findViewByPosition(lastAdapterItem)
//                    if (bottomView != null) {
//                        recyclerViewPositionOffset = 0 - bottomView.getHeight()
//                    }
//                    (binding.rvChat.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
//                        lastAdapterItem,
//                        recyclerViewPositionOffset
//                    )
//                })
//            }
//        }
    }

    private fun addListerner() {
        binding.apply {
            cardViewSend.setOnClickListener {
                val message = etMessage.text.toString()
                if (message.trim().isNotEmpty()) {
                    etMessage.setText("")
                    val date = Date()
                    val message = Message(message, auth.uid!!, date.time)
                    database.reference.child("Chats").child(senderRoom).child("message").push()
                        .setValue(
                            message
                        ).addOnCompleteListener {
                        database.reference.child("Chats").child(recieverRoom).child("message")
                            .push().setValue(
                                message
                            ).addOnCompleteListener {

                        }
                    }
                }
            }
        }
    }
}