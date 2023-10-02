package com.ruhlanusubov.laza.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.ruhlanusubov.laza.adapter.ProductAdapter
import com.ruhlanusubov.laza.api.ApiUtil
import com.ruhlanusubov.laza.databinding.FragmentDetailBinding
import com.ruhlanusubov.laza.model.product.DetailResponse
import com.ruhlanusubov.laza.model.product.Product
import com.ruhlanusubov.laza.utils.LIMIT
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailBinding?=null
    private val binding: FragmentDetailBinding get() = _binding!!
    private val adapter=ProductAdapter()
    private val service=ApiUtil.getService()
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detail()
        data()
    }
    private fun detail(){
        val data=args.detail

        with(binding){
            brand.text=data.brand
            name.text=data.title
            desc.text=data.description
            productprice.text="$ ${data.price}"
            rating.rating=data.rating!!.toFloat()

            wishBox.setOnCheckedChangeListener{ checkbox,ischecked->
                if(checkbox.isChecked){
                    FancyToast.makeText(requireContext(),"Added to Wishlist!!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,false).show()
                }else{
                    FancyToast.makeText(requireContext(),"Removed from Wishlist!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING,false).show()
                }
            }

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        val imagelist=ArrayList<SlideModel>()
        data.images?.forEach {
            imagelist.add(SlideModel(it))
        }
        binding.imgslider.setImageList(imagelist)
    }
    private fun data(){
        service.getProduct(LIMIT).enqueue(object:Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful){

                    response.body()?.let {
                        adapter.updateList(it.products?: emptyList())
                    }
                }else{
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        })
        binding.alsoRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.alsoRv.adapter=adapter
        adapter.onClickItemListener={
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentSelf(it))
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}