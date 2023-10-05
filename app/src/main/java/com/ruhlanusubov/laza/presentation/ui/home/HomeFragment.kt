package com.ruhlanusubov.laza.presentation.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.ruhlanusubov.laza.adapter.CategoryAdapter
import com.ruhlanusubov.laza.adapter.MainProductAdapter
import com.ruhlanusubov.laza.adapter.ProductAdapter
import com.ruhlanusubov.laza.api.ApiUtil
import com.ruhlanusubov.laza.databinding.FragmentHomeBinding
import com.ruhlanusubov.laza.model.category.CategoryList
import com.ruhlanusubov.laza.model.product.DetailResponse
import com.ruhlanusubov.laza.model.product.Product
import com.ruhlanusubov.laza.model.user.UserDetailsResponse
import com.ruhlanusubov.laza.utils.LIMIT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val adapter = ProductAdapter()
    private val category = CategoryAdapter()
    private val main = MainProductAdapter()
    private val megaSale = ProductAdapter()
    private val service = ApiUtil.getService()
    private lateinit var timer: CountDownTimer
    private lateinit var sp: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        more()
        timer()
        userData()
        flashData()
        categoryAdapter()
        flashSaleAdapter()
        megaAdapter()
        mainAdapter()
        binding.searchbtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }
    }

    private fun more(){
        with(binding){
            moreCategory.setOnClickListener {
                categoryResponse()
            }
            seeMore.setOnClickListener {
                seeMore()
            }
        }
    }
    private fun categoryResponse(){

    }
    private fun seeMore(){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSeeMoreFragment())
    }
    private fun timer() {
        val totalMillis :Long= 3600000 // 1 hour in milliseconds

        timer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = millisUntilFinished / (1000 * 60 * 60)
                val minutes = (millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60)
                val seconds = (millisUntilFinished % (1000 * 60)) / 1000

                with(binding){
                    hour.text = String.format("%02d", hours)
                    minute.text = String.format("%02d", minutes)
                    second.text = String.format("%02d", seconds)
                }

            }

            override fun onFinish() {
               Toast.makeText(requireContext(),"Finished",Toast.LENGTH_SHORT).show()
            }
        }

        timer.start()
    }

    private fun categoryAdapter(){
        binding.categoryRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.categoryRv.adapter=category
    }
    private fun flashSaleAdapter() {
        binding.flashSaleRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.flashSaleRv.adapter = adapter
        adapter.onClickItemListener={

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
        }
    }
    private fun mainAdapter(){
        with(binding){
            mainRv.layoutManager=GridLayoutManager(requireContext(),2)
            mainRv.adapter=main

           main.onClickItemListener={
               findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
           }
        }
    }
    private fun megaAdapter(){
        with(binding){
            megaSaleRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            megaSaleRv.adapter=megaSale

            megaSale.onClickItemListener={
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it))
            }
        }
    }
    private fun userData(){
        sp=requireContext().getSharedPreferences("UserId",Context.MODE_PRIVATE)
        val id=sp.getInt("id",1)
        service.detail(id).enqueue(object:Callback<UserDetailsResponse>{
            override fun onResponse(
                call: Call<UserDetailsResponse>,
                response: Response<UserDetailsResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        sp.edit().putString("Gender",it.gender).apply()
                        sp.edit().putString("Birthday",it.birthDate).apply()
                        sp.edit().putString("Email",it.email).apply()
                        sp.edit().putString("Phone",it.phone).apply()
                        sp.edit().putString("Image",it.image).apply()
                        sp.edit().putString("Full name","${it.firstName} ${it.lastName}").apply()
                        sp.edit().putString("UserName",it.username).apply()
                        sp.edit().putString("Password",it.password).apply()
                    }
                }else{
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun categoryData(){
       service.getCategory().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        category.updateList(it)
                    }
                }else{
                    Toast.makeText(requireContext(),Log.ERROR, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun flashData() {
        service.getProduct(LIMIT).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        binding.preload.visibility=View.GONE
                        adapter.updateList(it.products?: emptyList())
                        megaSale.updateList(it.products?: emptyList())
                        main.updateList(it.products?: emptyList())
                        slider(it.products?: emptyList())
                        with(binding){
                            preload.visibility=View.GONE
                            dataView.visibility=View.VISIBLE
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), Log.ERROR, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
        categoryData()
    }
    private fun slider(
        list: List<Product>
    ) {

        val imagelist=ArrayList<SlideModel>()
        list.forEach {
            it.images?.forEach { url->
                imagelist.add(SlideModel(url))
            }
        }
        binding.slider.setImageList(imagelist,ScaleTypes.CENTER_CROP)
        binding.slider.setSlideAnimation(AnimationTypes.BACKGROUND_TO_FOREGROUND)
        binding.slider.startSliding(2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}