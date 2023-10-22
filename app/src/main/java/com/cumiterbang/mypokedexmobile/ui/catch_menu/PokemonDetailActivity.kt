package com.cumiterbang.mypokedexmobile.ui.catch_menu

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.cumiterbang.mypokedexmobile.R
import com.cumiterbang.mypokedexmobile.data.helper.ApiUrls
import com.cumiterbang.mypokedexmobile.data.helper.Resource
import com.cumiterbang.mypokedexmobile.data.model.PokemonListItemModel
import com.cumiterbang.mypokedexmobile.data.model.pokemon_detail.PokemonDetailModel
import com.cumiterbang.mypokedexmobile.databinding.ActivityPokemonDetailPageBinding
import com.cumiterbang.mypokedexmobile.utils.CustomShimmerPlaceholder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailPageBinding
    private lateinit var pokemonItemModel: PokemonListItemModel

    private val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseIntentExtras()
        setPage()
        setCatchPokemon()

        viewModel.setPokemonName(pokemonItemModel.name)
        observePokemonDetail()
    }

    private fun parseIntentExtras() {
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("pokemonItemModel")
            pokemonItemModel = Gson().fromJson(value, PokemonListItemModel::class.java)
        }
    }

    private fun setPage() = with(binding) {
        imageViewCloseButton.setOnClickListener {
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

    private fun setCatchPokemon() = with(binding) {
        buttonCatchPokemon.setOnClickListener {
            var random = Random.nextInt(2, 6)
            if (random % 2 == 0) {
                val snackbar = Snackbar.make(
                    binding.root, "Congrats!\n" +
                            "You Catch -${pokemonItemModel.name.uppercase()}-",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                snackbar.show()

                viewModel.catchSuccess(pokemonItemModel)

            } else {
                val snackbar = Snackbar.make(
                    binding.root, "Catching pokemon failed",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                snackbar.show()
            }
        }
    }

    private fun observePokemonDetail() {
        viewModel.getPokemonDetail.observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                Resource.Status.SUCCESS -> {
                    val newData = it.data
                    if (newData != null) {
                        generatePokemonDetail(newData)
                    }

                    binding.progressBar.visibility = View.GONE
                }

                Resource.Status.ERROR -> {
                    val snackbar = Snackbar.make(
                        binding.root, "${it.message}",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null)
                    snackbar.show()

                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun generatePokemonDetail(data: PokemonDetailModel) {
        val dataTemplateHeight =
            layoutInflater.inflate(R.layout.pokemaon_detail_data_item, null, false)
        dataTemplateHeight.findViewById<TextView>(R.id.valueData).text = data.height.toString()
        dataTemplateHeight.findViewById<TextView>(R.id.titleData).text = "Height"
        dataTemplateHeight.id = View.generateViewId()
        binding.pokemonDetailData.addView(dataTemplateHeight)

        val dataTemplateWeight =
            layoutInflater.inflate(R.layout.pokemaon_detail_data_item, null, false)
        dataTemplateWeight.findViewById<TextView>(R.id.valueData).text = data.weight.toString()
        dataTemplateWeight.findViewById<TextView>(R.id.titleData).text = "Weight"
        dataTemplateWeight.id = View.generateViewId()
        binding.pokemonDetailData.addView(dataTemplateWeight)

        var typeValue = ""
        for (item in data.types) {
            typeValue += "${item.type?.name}, "
        }
        val dataTemplateType =
            layoutInflater.inflate(R.layout.pokemaon_detail_data_item, null, false)
        dataTemplateType.findViewById<TextView>(R.id.valueData).text = typeValue.dropLast(2)
        dataTemplateType.findViewById<TextView>(R.id.titleData).text = "Type"
        dataTemplateType.id = View.generateViewId()
        binding.pokemonDetailData.addView(dataTemplateType)

        var abilitiesValue = ""
        for (item in data.abilities) {
            abilitiesValue += "${item.ability?.name}, "
        }
        val dataTemplateAbilities =
            layoutInflater.inflate(R.layout.pokemaon_detail_data_item, null, false)
        dataTemplateAbilities.findViewById<TextView>(R.id.valueData).text = abilitiesValue.dropLast(2)
        dataTemplateAbilities.findViewById<TextView>(R.id.titleData).text = "Abilities"
        dataTemplateAbilities.id = View.generateViewId()
        binding.pokemonDetailData.addView(dataTemplateAbilities)

        var movesValue = ""
        for (item in data.moves) {
            movesValue += "${item.move?.name}, "
        }
        val dataTemplateMoves =
            layoutInflater.inflate(R.layout.pokemaon_detail_data_item, null, false)
        dataTemplateMoves.findViewById<TextView>(R.id.valueData).text = movesValue.dropLast(2)
        dataTemplateMoves.findViewById<TextView>(R.id.titleData).text = "Moves"
        dataTemplateMoves.id = View.generateViewId()
        binding.pokemonDetailData.addView(dataTemplateMoves)

    }
}