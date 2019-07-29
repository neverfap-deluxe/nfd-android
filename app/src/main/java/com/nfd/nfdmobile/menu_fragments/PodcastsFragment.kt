package com.nfd.nfdmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.adapters.NFDAudioAdapter
import com.nfd.nfdmobile.utilities.Helpers
import com.nfd.nfdmobile.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_podcast.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PodcastsFragment : Fragment() {
    private val model : MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_podcast, container, false)
        val context = context

        if (Helpers.isNetworkAvailable(context!!)) {
            progressBar?.visibility = View.VISIBLE
        }

        model.getLatestPodcasts()

        context?.let {
            model.podcasts.observe(this, Observer { podcasts ->
                NFDAudioAdapter.setupAdapterAndOnClickListener(podcasts, fragment_podcasts_list_view, context, "podcast")
                model.podcastsLoadedState.postValue(true)
            })

            model.podcastsLoadedState.observe(this, Observer { stateBool ->
                progressBar?.visibility = View.GONE
            })
        }

        return view
    }

    companion object {
        fun newInstance(): PodcastsFragment = PodcastsFragment()
    }
}

