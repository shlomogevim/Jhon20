package com.sg.jhon20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ThoghtsAdapter(val thoughts: ArrayList<Thought>) :
    RecyclerView.Adapter<ThoghtsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.thought_list_view, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindThuoght(thoughts[position])
    }

    override fun getItemCount() = thoughts.count()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username = itemView?.findViewById<TextView>(R.id.listViewUsername)
        val timestap = itemView?.findViewById<TextView>(R.id.listViewTimestamp)
        val thuoghtsText = itemView?.findViewById<TextView>(R.id.listViewToughtTxt)
        val numLikes = itemView?.findViewById<TextView>(R.id.listViewNumLikes)
        val likesImage = itemView?.findViewById<ImageView>(R.id.listViewLikesImage)

        fun bindThuoght(thought: Thought) {
            username?.text = thought.userName
            thuoghtsText?.text = thought.thoughtTxt
            numLikes?.text = thought.numLikes.toString()
            timestap?.text = thought.timestamp?.toDate().toString()
            likesImage?.setOnClickListener {
                FirebaseFirestore.getInstance().collection(THOUGHTS_REF).document(thought.documentId)
                    .update(NUM_LIKES,thought.numLikes+1)
            }
        }
    }
}