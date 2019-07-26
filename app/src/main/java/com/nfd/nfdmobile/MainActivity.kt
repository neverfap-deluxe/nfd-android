package com.nfd.nfdmobile

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nfd.nfdmobile.data.AppDatabase

import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.NavUtils
import com.nfd.nfdmobile.fragments.*


class MainActivity : AppCompatActivity() {
//    lateinit var textMessage: TextView

    private val homeFragment = HomeFragment.newInstance()
    private val articlesFragment = ArticlesFragment.newInstance()
    private val practicesFragment = PracticesFragment.newInstance()
    private val meditationsFragment = MeditationsFragment.newInstance()
    private val podcastsFragment = PodcastsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = nav_view

        openFragment(homeFragment)

        val ab = actionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.title = "NeverFap Deluxe"
        ab?.subtitle = ""

//        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
//                textMessage.setText(R.string.title_home)

                openFragment(homeFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_articles -> {
//                textMessage.setText(R.string.title_articles)

                openFragment(articlesFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_practices -> {
//                textMessage.setText(R.string.title_practices)

                openFragment(practicesFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_meditations -> {
//                textMessage.setText(R.string.title_practices)

                openFragment(meditationsFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_podcasts -> {
//                textMessage.setText(R.string.title_practices)

                openFragment(podcastsFragment)

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
