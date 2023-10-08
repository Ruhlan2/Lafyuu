package com.ruhlanusubov.laza.presentation.ui.validation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.api.ApiUtil
import com.ruhlanusubov.laza.databinding.FragmentRegisterBinding
import com.ruhlanusubov.laza.utils.USER_COLLECTION
import com.shashank.sony.fancytoastlib.FancyToast

class RegisterFragment : Fragment() {
    private var _binding:FragmentRegisterBinding?=null
    private val binding:FragmentRegisterBinding get() = _binding!!
    private val fireStore=FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInbtn.setOnClickListener {
            setup()
        }
    }

    private fun setup(){
        with(binding) {
            val email = email.text.toString()
            val password = passwordText.text.toString()
            val name=name.text.toString()

            if (email.isEmpty() || password.isEmpty()||name.isEmpty()) {
                Toast.makeText(requireContext(), "Fill the blanks", Toast.LENGTH_SHORT).show()
                return
            }
            val userData= hashMapOf(
                "email" to email,
                "name" to name,
                "password" to password
            )
            fireStore.collection(USER_COLLECTION).add(userData).addOnSuccessListener {

                FancyToast.makeText(requireContext(),"Successfully registered",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
            }.addOnFailureListener {
                FancyToast.makeText(requireContext(),"Failed",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}