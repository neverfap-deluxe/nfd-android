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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(com.nfd.nfdmobile.R.layout.fragment_home, container, false)
        context = getActivity()
        nfdTextDao = NFDTextDao()
        repository = NFDTextRepository()
        NFDText.getItemsFromContentAPI("articles", home_articles_list_view, this)
        NFDText.getItemsFromContentAPI("practices", home_practices_list_view, this)

        return view
    }

    companion object {
        fungetItemsFromContentAPI newInstance(): HomeFragment = HomeFragment()
    }
}

