package com.cumiterbang.mypokedexmobile.ui.home_menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cumiterbang.mypokedexmobile.R
import com.cumiterbang.mypokedexmobile.data.entity.MyPokemonEntity
import com.cumiterbang.mypokedexmobile.data.helper.ApiUrls
import com.cumiterbang.mypokedexmobile.data.helper.Resource
import com.cumiterbang.mypokedexmobile.data.local.MyPokemonDatabase
import com.cumiterbang.mypokedexmobile.data.model.PokemonListItemModel
import com.cumiterbang.mypokedexmobile.databinding.FragmentHomeBinding
import com.cumiterbang.mypokedexmobile.ui.catch_menu.PokemonDetailActivity
import com.cumiterbang.mypokedexmobile.ui.catch_menu.adapter.PokemonsGridAdapter
import com.cumiterbang.mypokedexmobile.ui.home_menu.adapter.MyPokemonListAdapter
import com.cumiterbang.mypokedexmobile.utils.EndlessRecyclerViewScrollListener
import com.cumiterbang.mypokedexmobile.utils.GridSpacingItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private var myCollection: ArrayList<MyPokemonEntity> = ArrayList()
    private lateinit var pokemonsAdapter: MyPokemonListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMyPokemonCollectionRecyclerView()
        setPage()
        observeMyPokemonCollection()
    }

    private fun setPage() = with(binding) {
        buttonGoCatch.setOnClickListener {
            viewModel.setGoCactchPokemon(true)
        }
    }

    private fun setMyPokemonCollectionRecyclerView(){
        val layoutManager = LinearLayoutManager(binding.root.context)

        pokemonsAdapter = MyPokemonListAdapter(binding.root.context)
        pokemonsAdapter.onItemClick = {

            val pokemonItem = PokemonListItemModel(
                name = it.name,
                url = it.url
            )

            val intent = Intent(activity, PokemonDetailActivity::class.java)
            intent.putExtra("pokemonItemModel", Gson().toJson(pokemonItem))
            startActivity(intent)
        }

        pokemonsAdapter.onLongItemClick = {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Releasing your pokemon")
            builder.setMessage("Do you want to release ${it.nameByCatch.uppercase()}?")
            builder.setPositiveButton(R.string.yes) { dialog, which ->
                val snackbar = Snackbar.make(
                    binding.root, "${it.nameByCatch.uppercase()} had been released",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null)
                snackbar.show()

                viewModel.deleteSelectedPokemon(it)
            }
            builder.setNegativeButton(R.string.no) { dialog, which ->

            }
            builder.show()

        }

        with(binding.listViewMyPokemonCollection) {
            this.adapter = pokemonsAdapter
            this.layoutManager = layoutManager
        }
    }

    private fun observeMyPokemonCollection() = with(binding) {
        viewModel.myPokemonCollection.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }

                Resource.Status.SUCCESS -> {
                    val myPokemonCollection = it.data ?: emptyList()
                    if (myPokemonCollection.isEmpty()) {
                        textHomeMenu.visibility = View.VISIBLE
                        buttonGoCatch.visibility = View.VISIBLE

                        textHomeMenu.text = resources.getString(R.string.my_pokemon_no_data)
                    } else {
                        myCollection.clear()
                        myCollection = ArrayList()

                        myCollection = ArrayList(myPokemonCollection)
                        pokemonsAdapter.pokemons = myCollection
                        pokemonsAdapter.notifyDataSetChanged()

                        pokemonsAdapter.itemCount

                        textHomeMenu.visibility = View.GONE
                        buttonGoCatch.visibility = View.GONE
                    }

                    progressBar.visibility = View.GONE
                }

                Resource.Status.ERROR -> {
                    progressBar.visibility = View.GONE
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}