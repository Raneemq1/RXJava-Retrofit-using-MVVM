package com.example.rxjavaapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavaapp.R
import com.example.rxjavaapp.data.model.PostModel

class PostRecyclerAdapter : RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder>() {
    /**
     * Initialize a list of posts
     */
    var postsList: List<PostModel> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTV: TextView = view.findViewById(R.id.titleTV)
        val bodyTV: TextView = view.findViewById(R.id.bodyTV)
        val idTV: TextView = view.findViewById(R.id.idTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Fill widgets of card view with list posts information
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = postsList[position]
        holder.idTV.text = currentItem.userId.toString()
        holder.titleTV.text = currentItem.title
        holder.bodyTV.text = currentItem.body
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    /**
     * Method to update list in adapter
     */
    fun filteredData(filterList: ArrayList<PostModel>) {
        postsList = filterList
        notifyDataSetChanged()
    }
}