package com.ruhlanusubov.laza.presentation.ui.account.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.databinding.FragmentNameBinding
import com.ruhlanusubov.laza.databinding.FragmentPhoneBinding

class PhoneFragment : Fragment() {
    private var _binding: FragmentPhoneBinding?=null
    private val binding: FragmentPhoneBinding get() = _binding!!
    private lateinit var sp:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPhoneBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            sp=requireContext().getSharedPreferences("UserId",Context.MODE_PRIVATE)
            phone.setText(sp.getString("Phone",null))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}