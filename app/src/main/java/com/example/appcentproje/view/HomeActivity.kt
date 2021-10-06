package com.example.appcentproje.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.appcentproje.R
import com.example.appcentproje.view.fragments.FavoritesFragment
import com.example.appcentproje.view.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeFragment = HomeFragment()
        val favoritesFragment = FavoritesFragment()

        makeCurrentFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_fav -> makeCurrentFragment(favoritesFragment)

            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =

                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fl_wrapper, fragment)
                    commit()
        }
}