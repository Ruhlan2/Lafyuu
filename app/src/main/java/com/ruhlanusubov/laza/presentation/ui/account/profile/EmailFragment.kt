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
import com.ruhlanusubov.laza.databinding.FragmentChangePasswordBinding
import com.ruhlanusubov.laza.databinding.FragmentEmailBinding

class EmailFragment : Fragment() {
    private var _binding: FragmentEmailBinding?=null
    private val binding: FragmentEmailBinding get() = _binding!!
    private lateinit var sp:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentEmailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            sp=requireContext().getSharedPreferences("UserId", Context.MODE_PRIVATE)
            emailEdittext.setText(sp.getString("Email","example@gmail.com"))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}