package com.ceylonsolutions.postmate.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceylonsolutions.postmate.databinding.ItemPostsBinding
import com.ceylonsolutions.postmate.data.model.Post
import com.ceylonsolutions.postmate.util.Helper

class PostAdapter (private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var postList: MutableList<Post?>? = ArrayList<Post?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding :ItemPostsBinding = ItemPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(postList?.get(position))
        holder.itemView.setOnClickListener {
            itemClickListener.onPostClick(postList?.get(position))
        }
    }

    override fun getItemCount(): Int {
        if (postList != null) {
            return postList!!.size
        }
        return 0
    }

    fun addPost(post: Post){
        postList?.toMutableList()?.add(post)
        notifyDataSetChanged()
    }

    fun addPostList(posts: List<Post?>?){
        postList?.clear()
        if (posts != null) {
            postList?.addAll(posts)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(binding : ItemPostsBinding) : RecyclerView.ViewHolder(binding.root){
         private var binding: ItemPostsBinding = binding

        fun bindItems(post: Post?) {
            binding.tvTitle.text = Helper.capitalizeFirstLetter(post?.title)

//            Glide.with(binding.root)
//                .load(AppConstant.BASE_URL_AVATAR+Helper.generateMD5Hash(EMAIL))
//                .centerInside()
//                .into(binding.civAvatar)
        }
    }

    interface ItemClickListener {
        fun onPostClick(post: Post?)
    }
}
