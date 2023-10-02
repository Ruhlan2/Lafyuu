package com.ruhlanusubov.laza.presentation.ui.account.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ruhlanusubov.laza.databinding.FragmentProfileDetailsBinding

class ProfileDetailsFragment : Fragment() {
    private var _binding:FragmentProfileDetailsBinding?=null
    private val binding:FragmentProfileDetailsBinding get() = _binding!!
    private lateinit var sp:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentProfileDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
    }

    private fun setup(){
        with(binding){
            backBtn.setOnClickListener { findNavController().popBackStack() }
            gender.setOnClickListener { findNavController().navigate(ProfileDetailsFragmentDirections.actionProfileDetailsFragmentToGenderFragment()) }
            birth.setOnClickListener { findNavController().navigate(ProfileDetailsFragmentDirections.actionProfileDetailsFragmentToBirthdayFragment()) }
            email.setOnClickListener { findNavController().navigate(ProfileDetailsFragmentDirections.actionProfileDetailsFragmentToEmailFragment()) }
            number.setOnClickListener { findNavController().navigate(ProfileDetailsFragmentDirections.actionProfileDetailsFragmentToPhoneFragment()) }
            password.setOnClickListener { findNavController().navigate(ProfileDetailsFragmentDirections.actionProfileDetailsFragmentToChangePasswordFragment()) }

        sp=requireContext().getSharedPreferences("UserId",Context.MODE_PRIVATE)
        Glide.with(requireContext()).load(sp.getString("Image",null)).into(profileimg)
            fullName.text=sp.getString("Full name",null)
            username.text=sp.getString("UserName",null)
            gender.text=sp.getString("Gender",null)
            birth.text=sp.getString("Birthday",null)
            email.text=sp.getString("Email",null)
            number.text=sp.getString("Phone",null)



        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}