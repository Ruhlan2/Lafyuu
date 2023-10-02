package com.ruhlanusubov.laza.presentation.ui.account.profile

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.databinding.FragmentBirthdayBinding

class BirthdayFragment : Fragment() {

    private var _binding:FragmentBirthdayBinding?=null
    private val binding:FragmentBirthdayBinding get() = _binding!!
    private lateinit var sp:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentBirthdayBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            birthdayLayout.setEndIconOnClickListener {
                date()
            }
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            sp=requireContext().getSharedPreferences("UserId",Context.MODE_PRIVATE)
            birthDayText.setText(sp.getString("Birthday","Date"))
        }


}

    private fun date(){



        val calendar=Calendar.getInstance()

        val year=calendar.get(Calendar.YEAR)
        val month=calendar.get(Calendar.MONTH)
        val day=calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog=DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth" // Month is 0-based

            binding.birthDayText.setText("$selectedDate")


        },year,month,day)

        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}