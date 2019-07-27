package com.nfd.nfdmobile

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_main.*
import com.nfd.nfdmobile.fragments.*
import com.nfd.nfdmobile.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.Menu


class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModel()

    private val homeFragment = HomeFragment.newInstance()
    private val articlesFragment = ArticlesFragment.newInstance()
    private val practicesFragment = PracticesFragment.newInstance()
    private val meditationsFragment = MeditationsFragment.newInstance()
    private val podcastsFragment = PodcastsFragment.newInstance()

    private val aboutFragment = AboutFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.activity_toolbar))

        openFragment(homeFragment)

        val navView: BottomNavigationView = nav_view
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_refresh -> {
            model.getLatestArticles()
            model.getLatestPractices()
            model.getLatestMeditations()
            model.getLatestPodcasts()
            true
        }

        R.id.action_more -> {
            openFragment(aboutFragment)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(homeFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_articles -> {
                openFragment(articlesFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_practices -> {
                openFragment(practicesFragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_meditations -> {
                openFragment(meditationsFragment)

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
