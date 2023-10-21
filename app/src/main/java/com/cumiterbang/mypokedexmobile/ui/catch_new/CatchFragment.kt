package com.cumiterbang.mypokedexmobile.ui.catch_new

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cumiterbang.mypokedexmobile.data.helper.ApiUrls
import com.cumiterbang.mypokedexmobile.data.helper.Resource
import com.cumiterbang.mypokedexmobile.data.model.PokemonListItemModel
import com.cumiterbang.mypokedexmobile.data.model.PokemonResultsModel
import com.cumiterbang.mypokedexmobile.databinding.FragmentCatchBinding
import com.cumiterbang.mypokedexmobile.ui.catch_new.adapter.PokemonsGridAdapter
import com.cumiterbang.mypokedexmobile.ui.home.HomeViewModel
import com.cumiterbang.mypokedexmobile.utils.EndlessRecyclerViewScrollListener
import com.cumiterbang.mypokedexmobile.utils.GridSpacingItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatchFragment : Fragment() {

    private var _binding: FragmentCatchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: CatchViewModel by viewModels()
    private var pokemonsResult: ArrayList<PokemonListItemModel> = ArrayList()
    private lateinit var pokemonsAdapter: PokemonsGridAdapter
    private var nextUrl: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCatchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPokemonsGridView()
        viewModel.init()
        observePokemons()

    }

    private fun setPokemonsGridView() {
        val layoutManager = GridLayoutManager(binding.root.context, 2)

        pokemonsAdapter = PokemonsGridAdapter(binding.root.context)
        pokemonsAdapter.onItemClick = {
            val pokemonId = ApiUrls.getPokemonIdFromUrl(it.url)
        }

        with(binding.gridViewPokemons) {
            this.adapter = pokemonsAdapter
            this.layoutManager = layoutManager
            this.addItemDecoration(GridSpacingItemDecoration(2, 12, true))
            this.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(
                    currentPage: Int,
                    currentItemsCount: Int,
                    view: RecyclerView?
                ) {
                    val nextPage = currentPage + 1
                    if(nextUrl == "") return

                    viewModel.setOffset(ApiUrls.LIMIT_VALUE*nextPage)

                }

            })
        }
    }

    private fun observePokemons() {
        viewModel.getPokemons.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                Resource.Status.SUCCESS -> {
                    val newData = it.data?.results ?: ArrayList()
                    nextUrl = it.data?.next ?: ""
                    var prevtUrl = it.data?.previous ?: ""

                    if(nextUrl == "" || prevtUrl == ""){
                        pokemonsResult.clear()
                        pokemonsResult = ArrayList()
                    }

                    pokemonsResult.addAll(newData)
                    pokemonsAdapter.pokemons = pokemonsResult
                    pokemonsAdapter.notifyDataSetChanged()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}