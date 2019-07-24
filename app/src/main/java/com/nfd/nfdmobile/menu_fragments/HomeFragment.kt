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
    private val nFDTextViewModel: NFDTextViewModel by viewModels

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(com.nfd.nfdmobile.R.layout.fragment_home, container, false)
        context = getActivity()

        val model = ViewModelProviders.of(this)[MainViewModel::class.java]

        model.getArticles().observe(this, Observer<List<NFDText>>{ articles ->
            override fun onChanged(articles: List<NFDText>) {
                NFDTextAdapter.setupAdapterAndOnClickListener(articles, home_articles_list_view, context, "article")
            }
        })

        model.getPractices().observe(this, Observer<List<NFDText>>{ practices ->
            override fun onChanged(practices: List<NFDText>) {
                NFDTextAdapter.setupAdapterAndOnClickListener(practices, home_practices_list_view, context, "practice")
            }
        })

        return view
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}

