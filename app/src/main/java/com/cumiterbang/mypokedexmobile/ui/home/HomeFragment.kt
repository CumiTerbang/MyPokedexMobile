package com.cumiterbang.mypokedexmobile.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cumiterbang.mypokedexmobile.R
import com.cumiterbang.mypokedexmobile.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHomeMenu
        val buttonCatch: Button = binding.goCatch
        homeViewModel.myPokemon.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                textView.visibility = View.VISIBLE
                buttonCatch.visibility = View.VISIBLE
                textView.text = resources.getString(R.string.my_pokemon_no_data)
            }else{
                textView.visibility = View.GONE
                buttonCatch.visibility = View.GONE
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}