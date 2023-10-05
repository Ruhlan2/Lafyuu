package com.ruhlanusubov.laza.presentation.ui.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.adapter.CartAdapter
import com.ruhlanusubov.laza.api.ApiUtil
import com.ruhlanusubov.laza.databinding.FragmentCartBinding
import com.ruhlanusubov.laza.model.cart.Cart
import com.ruhlanusubov.laza.model.cart.CartProduct
import com.ruhlanusubov.laza.model.cart.CartResponse
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : Fragment() {

    private var _binding:FragmentCartBinding ?=null
    private val binding:FragmentCartBinding get() = _binding!!
    private lateinit var sp:SharedPreferences
    private val service=ApiUtil.getService()
    private val adapter=CartAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        binding.checkBtn.setOnClickListener {
            check()
        }
    }
    private fun check(){
        with(binding){
            detail.visibility=View.VISIBLE
            cupon.visibility=View.VISIBLE
        }
    }
    private fun adapter(){
        binding.cartRv.layoutManager=LinearLayoutManager(requireContext())
        binding.cartRv.adapter=adapter
    }
    private fun setup(){
        adapter()
        sp=requireContext().getSharedPreferences("UserId",Context.MODE_PRIVATE)
        val cartId=sp.getInt("id",1)
        service.cartDetail(cartId).enqueue(
            object:Callback<CartResponse>{
                override fun onResponse(
                    call: Call<CartResponse>,
                    response: Response<CartResponse>
                ) {
                    if (response.isSuccessful){
                       response.body()?.let {
                           adapter.updateList(it.carts?.first()?.cartProducts ?: emptyList())
                       }
                    }else{
                        FancyToast.makeText(requireContext(),"Empty",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show()
                    }
                }

                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    FancyToast.makeText(requireContext(),t.localizedMessage,FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
                }

            }
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}