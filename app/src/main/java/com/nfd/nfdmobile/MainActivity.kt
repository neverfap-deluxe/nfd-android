package com.nfd.nfdmobile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nfd.nfdmobile.fragments.ArticlesFragment
import com.nfd.nfdmobile.fragments.HomeFragment
import com.nfd.nfdmobile.fragments.PracticesFragment
import com.nfd.nfdmobile.nfdtext.NFDText

import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_practice.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = nav_view

        // home
        NFDText.getItemsFromContentAPI("articles", home_articles_list_view, this)
        NFDText.getItemsFromContentAPI("practices", home_practices_list_view, this)

//        // articles
//        NFDText.getItemsFromContentAPI("articles", fragment_articles_list_view, this)
//
//        // practices
//        NFDText.getItemsFromContentAPI("practices", fragment_practices_list_view, this)

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
