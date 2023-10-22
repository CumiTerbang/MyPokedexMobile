package com.cumiterbang.mypokedexmobile.ui.home_menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cumiterbang.mypokedexmobile.data.entity.MyPokemonEntity
import com.cumiterbang.mypokedexmobile.data.helper.ApiUrls
import com.cumiterbang.mypokedexmobile.databinding.MyPokemonCollectionItemBinding
import com.cumiterbang.mypokedexmobile.utils.CustomShimmerPlaceholder

class MyPokemonListAdapter (
    private val context: Context
) : RecyclerView.Adapter<MyPokemonListAdapter.GalleryViewHolder>() {

    private var pokemonsList: ArrayList<MyPokemonEntity> = ArrayList()
    var pokemons: ArrayList<MyPokemonEntity>
        get() = pokemonsList
        set(value) {
            pokemonsList = value
        }

    var onItemClick: ((MyPokemonEntity) -> Unit)? = null

    inner class GalleryViewHolder(val binding: MyPokemonCollectionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding =
            MyPokemonCollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        with(holder) {

            if (pokemons.isNullOrEmpty() || position > (pokemons.size-1)) return

            with(pokemons[position]) {
                binding.textViewPokemonName.text = this.nameByCatch
                val imagePath = ApiUrls.getSpritePath(ApiUrls.getPokemonIdFromUrl(this.url))
                Glide.with(context).load(imagePath)
                    .placeholder(CustomShimmerPlaceholder().getPlaceholder())
                    .centerCrop()
                    .into(binding.imageViewSprite)

                itemView.setOnClickListener {
                    onItemClick?.invoke(this)
                }

            }
        }
    }

}