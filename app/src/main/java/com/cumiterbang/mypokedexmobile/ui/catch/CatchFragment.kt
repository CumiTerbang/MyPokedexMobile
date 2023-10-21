package com.cumiterbang.mypokedexmobile.ui.catch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cumiterbang.mypokedexmobile.databinding.FragmentCatchBinding

class CatchFragment : Fragment() {

    private var _binding: FragmentCatchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(CatchViewModel::class.java)

        _binding = FragmentCatchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCatchMenu
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}