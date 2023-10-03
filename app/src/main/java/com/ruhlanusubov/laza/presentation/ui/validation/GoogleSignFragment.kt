package com.ruhlanusubov.laza.presentation.ui.validation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.databinding.FragmentGoogleSignBinding
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GoogleSignFragment : Fragment() {

    private var _binding:FragmentGoogleSignBinding?=null
    private val binding:FragmentGoogleSignBinding get() =_binding!!
    private val auth=FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentGoogleSignBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup(){
        binding.signBtn.setOnClickListener {
            validation()
        }
    }
    private fun validation(){
        with(binding){
            val email=email.text.toString()
            val password=password.text.toString()

            if(email.isEmpty()||password.isEmpty()){
                FancyToast.makeText(requireContext(),"Fill the blanks",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    with(binding){
                        main.visibility=View.GONE
                        preload.visibility=View.VISIBLE
                    }
                    lifecycleScope.launch {
                        delay(4000L)
                        findNavController().navigate(GoogleSignFragmentDirections.actionGoogleSignFragmentToHomeFragment())

                    }

                }.addOnFailureListener {
                    FancyToast.makeText(requireContext(), it.localizedMessage,FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}