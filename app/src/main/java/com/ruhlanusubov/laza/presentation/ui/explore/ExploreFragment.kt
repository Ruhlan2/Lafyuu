package com.ruhlanusubov.laza.presentation.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ruhlanusubov.laza.databinding.FragmentExploreBinding
import com.ruhlanusubov.laza.databinding.FragmentSplashBinding
import com.ruhlanusubov.laza.presentation.ui.splash.SplashFragmentDirections
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding?=null
    private val binding: FragmentExploreBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentExploreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup(){





    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}