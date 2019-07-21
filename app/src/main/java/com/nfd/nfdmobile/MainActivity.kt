package com.nfd.nfdmobile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nfd.nfdmobile.fragments.ArticlesFragment
import com.nfd.nfdmobile.fragments.HomeFragment
import com.nfd.nfdmobile.fragments.PracticesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private lateinit var textMessage: TextView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                textMessage.setText(R.string.title_home)

                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_articles -> {
                textMessage.setText(R.string.title_articles)

                val articlesFragment = ArticlesFragment.newInstance()
                openFragment(articlesFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_practices -> {
                textMessage.setText(R.string.title_practices)

                val practicesFragment = PracticesFragment.newInstance()
                openFragment(practicesFragment)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
