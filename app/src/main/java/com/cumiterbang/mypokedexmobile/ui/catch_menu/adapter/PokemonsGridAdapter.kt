package com.cumiterbang.mypokedexmobile.ui.catch_menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cumiterbang.mypokedexmobile.data.helper.ApiUrls
import com.cumiterbang.mypokedexmobile.data.model.PokemonListItemModel
import com.cumiterbang.mypokedexmobile.databinding.CatchPokemonGridItemBinding
import com.cumiterbang.mypokedexmobile.utils.CustomShimmerPlaceholder

class PokemonsGridAdapter (
    private val context: Context
) : RecyclerView.Adapter<PokemonsGridAdapter.GalleryViewHolder>() {

    private var pokemonsList: ArrayList<PokemonListItemModel> = ArrayList()
    var pokemons: ArrayList<PokemonListItemModel>
        get() = pokemonsList
        set(value) {
            pokemonsList = value
        }

    var onItemClick: ((PokemonListItemModel) -> Unit)? = null

    inner class GalleryViewHolder(val binding: CatchPokemonGridItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding =
            CatchPokemonGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        with(holder) {

            if (pokemons.isNullOrEmpty() || position > (pokemons.size-1)) return

            with(pokemons[position]) {
                binding.textViewPokemonName.text = this.name
                val imagePath = ApiUrls.getImagePath(ApiUrls.getPokemonIdFromUrl(this.url))
                Glide.with(context).load(imagePath)
                    .placeholder(CustomShimmerPlaceholder().getPlaceholder())
                    .centerCrop()
                    .into(binding.imageViewPokemon)

                itemView.setOnClickListener {
                    onItemClick?.invoke(this)
                }

            }
        }
    }

}