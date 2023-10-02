package com.ruhlanusubov.laza.presentation.ui.validation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.api.ApiUtil
import com.ruhlanusubov.laza.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding:FragmentRegisterBinding?=null
    private val binding:FragmentRegisterBinding get() = _binding!!
    private val auth=FirebaseAuth.getInstance()

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
        binding.signInbtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setup(){
        with(binding){
            val email=email.text.toString()
            val password=passwordText.text.toString()

            if (email.isEmpty()||password.isEmpty()){
                Toast.makeText(requireContext(),"Fill the blanks",Toast.LENGTH_SHORT).show()
                return
            }
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
            }.addOnFailureListener {
                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}