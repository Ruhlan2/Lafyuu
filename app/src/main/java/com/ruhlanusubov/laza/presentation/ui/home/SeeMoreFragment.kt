package com.ruhlanusubov.laza.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.adapter.MainProductAdapter
import com.ruhlanusubov.laza.api.ApiUtil
import com.ruhlanusubov.laza.databinding.FragmentSeeMoreBinding
import com.ruhlanusubov.laza.model.product.DetailResponse
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeeMoreFragment : Fragment() {
    private var _binding:FragmentSeeMoreBinding?=null
    private val binding:FragmentSeeMoreBinding get() = _binding!!
    private val service=ApiUtil.getService()
    private val adapter=MainProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSeeMoreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        more()
    }
    private fun moreAdapter(){
        with(binding){
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            moreRv.layoutManager=GridLayoutManager(requireContext(),2)
            moreRv.adapter=adapter
            adapter.onClickItemListener={
                findNavController().navigate(SeeMoreFragmentDirections.actionSeeMoreFragmentToDetailsFragment(it))
            }
        }
    }
    private fun more(){
        moreAdapter()
        service.getMore().enqueue(object:Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        adapter.updateList(it.products?: emptyList())
                    }
                }else{
                    FancyToast.makeText(requireContext(),"Error",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                FancyToast.makeText(requireContext(),t.localizedMessage,FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}