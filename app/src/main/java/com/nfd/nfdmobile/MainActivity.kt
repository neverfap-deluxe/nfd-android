package com.nfd.nfdmobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_main.*
import com.nfd.nfdmobile.fragments.*
import com.nfd.nfdmobile.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Intent
import android.net.Uri
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.nfd.nfdmobile.utilities.Helpers
import kotlinx.android.synthetic.main.custom_toast.*
import kotlinx.android.synthetic.main.fragment_meditation.*


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
//        Helpers.hideStatusBar(window, actionBar)

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
//            progressBar.visibility = View.VISIBLE
//            progressBar.visibility = View.GONE

            if (Helpers.isNetworkAvailable(this)) {
                model.getLatestArticles()
                model.getLatestPractices()
                model.getLatestMeditations()
                model.getLatestPodcasts()

                val toast = Toast.makeText(applicationContext, "Refreshed!", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                val toast = Toast.makeText(applicationContext, "You must be connected to the internet!", Toast.LENGTH_SHORT)
                toast.show()
            }

//            val inflater = layoutInflater
//            val container: ViewGroup = findViewById(R.id.custom_toast_container)
//            container?.let {
//                val layout: ViewGroup = inflater.inflate(R.layout.custom_toast, container) as ViewGroup
//                val text: TextView = layout.findViewById(R.id.custom_toast_text)
//                text.text = "This is a custom toast"
//                with (Toast(applicationContext)) {
//                    setGravity(Gravity.CENTER_VERTICAL, 0, 0)
//                    duration = Toast.LENGTH_LONG
//                    view = layout
//                    show()
//                }
//            }

            true
        }

        R.id.navigation_about -> {
            openFragment(aboutFragment)
            true
        }

        R.id.navigation_website -> {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://neverfapdeluxe.com/"))
            startActivity(browserIntent)
            true
        }

        R.id.navigation_podcasts -> {
            openFragment(podcastsFragment)
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

    fun onClickWebsiteLink(v: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://neverfapdeluxe.com/"))
        startActivity(browserIntent)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
