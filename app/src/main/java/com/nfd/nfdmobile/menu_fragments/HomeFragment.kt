package com.nfd.nfdmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.adapters.NFDAudioAdapter
import com.nfd.nfdmobile.adapters.NFDTextAdapter
import com.nfd.nfdmobile.adapters.WebViewAdapter
import com.nfd.nfdmobile.utilities.Helpers
import com.nfd.nfdmobile.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.progressBar
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private val model : MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val context = context
        val urlList = model.urlList
        if (Helpers.isNetworkAvailable(context!!)) {
            progressBar?.visibility = View.VISIBLE
        }

        model.getLatestArticles()
        model.getLatestPractices()
        model.getLatestMeditations()
        model.populateUrlList()

        context?.let {

            model.urlList.observe(this, Observer { urlList ->
                WebViewAdapter.setupAdapterAndOnClickListener(urlList, home_urls_list_view, context)
                model.urlListLoadedState.postValue(true)
            })

            model.articles.observe(this, Observer { articles ->
                NFDTextAdapter.setupAdapterAndOnClickListener(articles.take(4), home_articles_list_view, context, "article")
                model.articlesLoadedState.postValue(true)
            })

            model.practices.observe(this, Observer { practices ->
                NFDTextAdapter.setupAdapterAndOnClickListener(practices.take(4), home_practices_list_view, context, "practice")
                model.practicesLoadedState.postValue(true)
            })

            model.meditations.observe(this, Observer { meditations ->
                NFDAudioAdapter.setupAdapterAndOnClickListener(meditations.take(4), home_meditations_list_view, context, "meditation")
                model.meditationsLoadedState.postValue(true)
            })

            // loaded state
            model.meditationsLoadedState.observe(this, Observer { stateBool ->
                progressBar?.visibility = View.GONE
            })
        }

        return view
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}

