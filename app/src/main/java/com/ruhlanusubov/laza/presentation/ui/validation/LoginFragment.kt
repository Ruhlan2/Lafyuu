package com.ruhlanusubov.laza.presentation.ui.validation

import android.content.Context
import android.content.SharedPreferences
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
import com.ruhlanusubov.laza.databinding.FragmentLoginBinding
import com.ruhlanusubov.laza.model.user.UserDetailsResponse
import com.ruhlanusubov.laza.model.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding?=null
    private val binding: FragmentLoginBinding get() = _binding!!
    private val service=ApiUtil.getService()
    private lateinit var sp:SharedPreferences
    private val auth=FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginTypes()

    }
    private fun loginTypes(){
        with(binding){
            signInbtn.setOnClickListener {
                validation()
                //login()
            }
        }

    }
    private fun login(){
        with(binding){
            val user=username.text.toString()
            val password=password.text.toString()

            if(user.isEmpty()||password.isEmpty()){
                Toast.makeText(requireContext(),"Please fill the blanks",Toast.LENGTH_SHORT).show()
                return
            }
            auth.signInWithEmailAndPassword(user,password).addOnSuccessListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }.addOnFailureListener {
                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
            }


        }
    }
    private fun validation(){


        val user=binding.username.text.toString()
        val password=binding.password.text.toString()

        if(user.isNotEmpty()){
            if(password.isNotEmpty()){
                binding.userlayout.error=null
                binding.passwordlayout.error=null
                auth(user,password)
            }else{
                binding.passwordlayout.error=getString(R.string.error)
                binding.userlayout.error=null
            }
        }else if (user.isEmpty()){
            if(password.isNotEmpty()){
                binding.userlayout.error=getString(R.string.error)
                binding.passwordlayout.error=null
            }else{
                binding.userlayout.error=getString(R.string.error)
                binding.passwordlayout.error=getString(R.string.error)
            }
        }



    }
    private fun auth(
        username:String,
        password:String
    ){
        service.getUser(username,password).enqueue(object:Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        response.body()?.let {
                            sp=requireContext().getSharedPreferences("UserId",Context.MODE_PRIVATE)
                            it.id?.let { ID->
                                with(binding){
                                    preload.visibility=View.VISIBLE
                                    loginScreen.visibility=View.GONE
                                }
                                sp.edit().putInt("id",ID).apply()
                            }
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        }

                    }
                }else{
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        })

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}