package com.example.throneapp.characterlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.throneapp.databinding.MovieLayoutBinding
import com.example.throneapp.characterlist.model.Characters

class CharacterListAdapter : RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {
    private var characterList = ArrayList<Characters>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(characterList[position].imageUrl)
            .into(holder.binding.ivImage)
        holder.binding.tvMovieName.text = characterList[position].fullName
        holder.binding.tvTitle.text = characterList[position].title
        holder.binding.tvFamily.text = characterList[position].family
        holder.binding.tvImage.text = characterList[position].image
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun setCharacterList(characters: List<Characters>) {
        this.characterList = characters as ArrayList<Characters>
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: MovieLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}