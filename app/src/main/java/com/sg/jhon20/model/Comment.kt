package com.sg.jhon20.model

import com.google.firebase.Timestamp

class Comment constructor(
    val username: String,
    val timestamp: Timestamp?,
    val commentTxt: String) {
}