package com.sg.jhon20.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sg.jhon20.*
import com.sg.jhon20.databinding.ActivityCommentsBinding
import com.sg.jhon20.databinding.ActivityMainBinding

class CommentsActivity : AppCompatActivity() {
    lateinit var thoughtDocmentId: String
   private lateinit var binding: ActivityCommentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        thoughtDocmentId = intent.getStringExtra(DOCUMENT_KEY)
        //Log.i("abc","thought: ${thoughtDocmentId}")
    }

    fun addCommentClick(view: View) {

        val commentText = binding.enterCommentText.text.toString()
        Log.i("abc","enterCommnndTxt=${binding.enterCommentText.text.toString()}")


        val thoughtRef =
            FirebaseFirestore.getInstance().collection(THOUGHTS_REF).document(thoughtDocmentId)

        FirebaseFirestore.getInstance().runTransaction { transaction ->
            val thought = transaction.get(thoughtRef)
            val numCommentTxt = thought.getLong(NUM_COMMENTS)?.plus(1)
            transaction.update(thoughtRef, NUM_COMMENTS, numCommentTxt)

            val newCommentRef = FirebaseFirestore.getInstance().collection(THOUGHTS_REF)
                .document(thoughtDocmentId).collection(COMMENTS_REF).document()
            val data = HashMap<String, Any>()
            data.put(COMMENTS_TXT, commentText)
            data.put(TIMESTAMP, FieldValue.serverTimestamp())
            data.put(USERNAME,FirebaseAuth.getInstance().currentUser.displayName.toString())
            Log.i("abc","enterCommnndTxt=${binding.enterCommentText.text.toString()}")

            transaction.set(newCommentRef,data)

        }.addOnSuccessListener {
            Log.i("abc","enterCommnndTxt=${binding.enterCommentText.text.toString()}")
            binding.enterCommentText.setText("")
        }
            .addOnFailureListener { exception ->
                Log.i("abc", "could not add comment:${exception.localizedMessage}")
            }
    }

}