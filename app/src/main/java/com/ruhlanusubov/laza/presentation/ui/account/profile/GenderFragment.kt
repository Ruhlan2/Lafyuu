package com.ruhlanusubov.laza.presentation.ui.account.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.databinding.FragmentGenderBinding

class GenderFragment : Fragment() {
    private var _binding:FragmentGenderBinding?=null
    private val binding:FragmentGenderBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentGenderBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        complete()
    }

    private fun complete(){
        val items= arrayOf("male","female","other")
        (binding.gender.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(items)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}