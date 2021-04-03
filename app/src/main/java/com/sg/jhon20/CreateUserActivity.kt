package com.sg.jhon20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CreateUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun createCreateClicked(view: View) {}
    fun createCancleClicked(view: View) {}
}