package com.cumiterbang.mypokedexmobile.ui.home_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cumiterbang.mypokedexmobile.R
import com.cumiterbang.mypokedexmobile.data.helper.Resource
import com.cumiterbang.mypokedexmobile.data.local.MyPokemonDatabase
import com.cumiterbang.mypokedexmobile.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()

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

        setPage()
        observeMyPokemonCollection()
    }

    private fun setPage() = with(binding) {
        buttonGoCatch.setOnClickListener {
            viewModel.setGoCactchPokemon(true)
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