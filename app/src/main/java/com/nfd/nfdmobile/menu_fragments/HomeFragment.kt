package com.nfd.nfdmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nfd.nfdmobile.adapters.NFDTextAdapter
import com.nfd.nfdmobile.data.NFDText
import com.nfd.nfdmobile.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    // val model 
    // private val model by viewModel<MainViewModel>()
    // private val model: MainViewModel by viewModels
    private lateinit var model: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.nfd.nfdmobile.R.layout.fragment_home, container, false)
        val context = context

        model = ViewModelProviders.of(this).get(MainViewModel::class.java)

        context?.let {
            model.getArticles().observe(this, Observer<List<NFDText>>{ articles ->
                //            override fun onChanged(articles: List<NFDText>) {
                NFDTextAdapter.setupAdapterAndOnClickListener(articles, home_articles_list_view, context, "article")
//            }
            })

            model.getPractices().observe(this, Observer<List<NFDText>>{ practices ->
                //            override fun onChanged(practices: List<NFDText>) {
                NFDTextAdapter.setupAdapterAndOnClickListener(practices, home_practices_list_view, context, "practice")
//            }
            })
        }

        return view
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}

