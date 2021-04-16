package com.example.reels.model

class Message(val message:String, val senderId:String, var timeStamp:Long){
    constructor():this("","",0)
}
