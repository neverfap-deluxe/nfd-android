package com.nfd.nfdmobile.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.adapters.NFDAudioAdapter
import com.nfd.nfdmobile.adapters.NFDTextAdapter
import com.nfd.nfdmobile.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private val model : MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val context = context
        val toolbar = activity_toolbar

//        toolbar.visibility = View.GONE
        model.getLatestArticles()
        model.getLatestPractices()
        model.getLatestMeditations()
        model.getLatestPodcasts()

        context?.let {
            model.articles.observe(this, Observer { articles ->
                NFDTextAdapter.setupAdapterAndOnClickListener(articles.take(4), home_articles_list_view, context, "article")
            })

            model.practices.observe(this, Observer { practices ->
                NFDTextAdapter.setupAdapterAndOnClickListener(practices.take(4), home_practices_list_view, context, "practice")
            })

            model.meditations.observe(this, Observer { meditations ->
                NFDAudioAdapter.setupAdapterAndOnClickListener(meditations.take(4), home_meditations_list_view, context, "meditation")
            })
        }

        return view
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}

