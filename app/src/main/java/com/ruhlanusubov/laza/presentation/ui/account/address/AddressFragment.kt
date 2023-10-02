package com.ruhlanusubov.laza.presentation.ui.account.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.databinding.FragmentAddressBinding

class AddressFragment : Fragment() {

    private var _binding:FragmentAddressBinding?=null
    private val binding:FragmentAddressBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding= FragmentAddressBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup(){
        with(binding){
            deleteBtn.setOnClickListener {
                findNavController().navigate(AddressFragmentDirections.actionAddressFragmentToDeleteAddressFragment())
            }
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            editBtn.setOnClickListener {
                findNavController().navigate(AddressFragmentDirections.actionAddressFragmentToEditAddressFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}