package com.ruhlanusubov.laza.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ruhlanusubov.laza.R
import com.ruhlanusubov.laza.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }
    private fun setup(){
        val navhost=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navcontroller=navhost.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navcontroller)

        navcontroller.addOnDestinationChangedListener{_,destination,_->
            when(destination.id){
                R.id.splashFragment,
                R.id.loginFragment,
                R.id.detailsFragment,
                R.id.registerFragment,
                R.id.profileDetailsFragment,
                R.id.birthdayFragment,
                R.id.changePasswordFragment,
                R.id.genderFragment,
                R.id.nameFragment,
                R.id.emailFragment,
                R.id.googleSignFragment,
                R.id.phoneFragment->{
                        binding.bottomNavigationView.visibility= View.GONE
                    }else->{
                       if ( binding.bottomNavigationView.visibility==View.GONE){
                           binding.bottomNavigationView.visibility=View.VISIBLE
                       }
                    }
            }
        }

    }
}