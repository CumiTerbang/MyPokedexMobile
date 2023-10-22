package com.cumiterbang.mypokedexmobile.ui.catch_menu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cumiterbang.mypokedexmobile.data.helper.ApiUrls
import com.cumiterbang.mypokedexmobile.data.model.PokemonListItemModel
import com.cumiterbang.mypokedexmobile.databinding.ActivityPokemonDetailPageBinding
import com.cumiterbang.mypokedexmobile.utils.CustomShimmerPlaceholder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class PokemonDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailPageBinding
    private lateinit var pokemonItemModel: PokemonListItemModel

    private val viewModel:PokemonDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseIntentExtras()
        setPage()
        setCatchPokemon()
    }

    private fun parseIntentExtras() {
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("pokemonItemModel")
            pokemonItemModel = Gson().fromJson(value, PokemonListItemModel::class.java)
        }
    }

    private fun setPage() = with(binding){
        imageViewCloseButton.setOnClickListener{
            finish()
        }

        textViewPokemonName.text = pokemonItemModel.name

        val imagePath = ApiUrls.getImagePath(ApiUrls.getPokemonIdFromUrl(pokemonItemModel.url))
        Glide.with(this@PokemonDetailActivity).load(imagePath)
            .placeholder(CustomShimmerPlaceholder().getPlaceholder())
            .centerInside()
            .into(imageViewPokemon)

        val spritePath = ApiUrls.getSpritePath(ApiUrls.getPokemonIdFromUrl(pokemonItemModel.url))
        Glide.with(this@PokemonDetailActivity).load(spritePath)
            .placeholder(CustomShimmerPlaceholder().getPlaceholder())
            .centerCrop()
            .into(imageViewSprite)
    }

    private fun setCatchPokemon()= with(binding){
        buttonCatchPokemon.setOnClickListener{
            var random = Random.nextInt(2,6)
            if(random%2 == 0){
                val snackbar = Snackbar.make(
                    binding.root, "Congrats!\n" +
                            "You Catch -${pokemonItemModel.name.uppercase()}-",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                snackbar.show()

                viewModel.catchSuccess(pokemonItemModel)

            }else{
                val snackbar = Snackbar.make(
                    binding.root, "Catching pokemon failed",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                snackbar.show()
            }
        }
    }
}