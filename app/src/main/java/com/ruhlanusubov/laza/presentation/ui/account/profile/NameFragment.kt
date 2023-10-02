package com.ruhlanusubov.laza.presentation.ui.account.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.databinding.FragmentEmailBinding
import com.ruhlanusubov.laza.databinding.FragmentNameBinding

class NameFragment : Fragment() {
    private var _binding: FragmentNameBinding?=null
    private val binding: FragmentNameBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentNameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}