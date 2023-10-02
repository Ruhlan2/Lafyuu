package com.ruhlanusubov.laza.presentation.ui.explore

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ruhlanusubov.laza.adapter.MainProductAdapter
import com.ruhlanusubov.laza.api.ApiUtil
import com.ruhlanusubov.laza.databinding.FragmentSearchBinding
import com.ruhlanusubov.laza.model.product.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding?=null
    private val binding: FragmentSearchBinding get() = _binding!!
    private val service=ApiUtil.getService()
    private val adapter=MainProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        searchAdapter()
        auto()
    }
    private fun auto(){
        binding.search.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(binding.search, InputMethodManager.SHOW_IMPLICIT)
    }
    private fun searchAdapter(){
        binding.searchRv.layoutManager=GridLayoutManager(requireContext(),2)
        binding.searchRv.adapter=adapter
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        adapter.onClickItemListener={
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it))
        }

    }

    private fun setup(){

        binding.search.addTextChangedListener { it ->

            if (it!=null){
                val search=it.toString()
                service.getSearch(search).enqueue(object:Callback<DetailResponse>{
                    override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                        if (response.isSuccessful){
                            response.body()?.let {

                                with(binding){

                                    result.setText("${it.products?.size} Result")
                                    if(it.products?.size==0){
                                        searchRv.visibility=View.GONE
                                        result.visibility=View.GONE
                                        notFound.visibility=View.VISIBLE
                                    }else{
                                        if(notFound.visibility==View.VISIBLE){
                                            notFound.visibility=View.GONE
                                            searchRv.visibility=View.VISIBLE
                                            result.visibility=View.VISIBLE
                                        }
                                    }
                                    adapter.updateList(it.products?: emptyList())


                                }


                               /* adapter.updateList(it.products?: emptyList())
                                resultSize()
                           */
                            }
                        }else{
                            Toast.makeText(requireContext(), Log.ERROR,Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                        Toast.makeText(requireContext(),t.localizedMessage,Toast.LENGTH_SHORT).show()
                    }

                })
            }else{
                binding.notFound.visibility=View.VISIBLE
                binding.searchRv.visibility=View.GONE
            }
        }



    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}