package com.sg.jhon20.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sg.jhon20.DATE_CREATED
import com.sg.jhon20.USERNAME
import com.sg.jhon20.USERS_REF
import com.sg.jhon20.databinding.ActivityCreateUserBinding

class CreateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateUserBinding

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
    }

    fun createCreateClicked(view: View) {
        val email = binding.createEmailTxt.text.toString()
        val password = binding.cratePasswordText.text.toString()
        val username = binding.createUsernameTxt.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val changeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()
                result.user.updateProfile(changeRequest)
                    .addOnFailureListener { exception ->
                        Log.e(
                            "exception",
                            "could not update display name: ${exception.localizedMessage}"
                        )
                    }
                val data = HashMap<String, Any>()
                data[USERNAME] = username
                data[DATE_CREATED] = FieldValue.serverTimestamp()

                FirebaseFirestore.getInstance().collection(USERS_REF).document(result.user.uid)
                    .set(data)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        "could not add user document: ${exception.localizedMessage}"
                    }
            }
            .addOnFailureListener { exception ->
                Log.e("exception", "could not create user: ${exception.localizedMessage}")
            }
    }

    fun createCancelClicked(view: View) {
        finish()
    }
}