package com.nfd.nfdmobile

import android.os.Bundle
import android.widget.ListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.nfd.nfdmobile.nfdtext.NFDText

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        populateTextList("articles")
        populateTextList("practices")

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun populateTextList(type: String) {
        // NOTE: Get View
        val view = getViewFromType(type)

        // NOTE: Retrieves List Data
        NFDText.getItemsFromContentAPI(type, view, this)
    }

    private fun getViewFromType(type: String): ListView {
        return when (type) {
            "articles" ->  home_articles_list_view
            "practices" -> home_practices_list_view
            else -> {
                throw Exception("populateTextList $type unknown")
            }
        }
    }

    private lateinit var textMessage: TextView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                textMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                textMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
