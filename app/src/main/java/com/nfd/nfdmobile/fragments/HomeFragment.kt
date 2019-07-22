package com.nfd.nfdmobile.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.nfd.nfdmobile.nfdtext.NFDText
import kotlinx.android.synthetic.main.fragment_home.*




class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(com.nfd.nfdmobile.R.layout.fragment_home, container, false)

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        populateTextList("articles", context)
        populateTextList("practices", context)
    }

    private fun populateTextList(type: String, context: Context?) {
        context?.let {
            // NOTE: Get View
            val view = getViewFromType(type)

            // NOTE: Retrieves List Data
            NFDText.getItemsFromContentAPI(type, view, context)
        }
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

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}

