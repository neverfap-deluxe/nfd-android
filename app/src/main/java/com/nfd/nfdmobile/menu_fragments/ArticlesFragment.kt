package com.nfd.nfdmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.adapters.NFDTextAdapter
import com.nfd.nfdmobile.data.NFDText
import com.nfd.nfdmobile.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_article.*


class ArticlesFragment : Fragment() {
    private val model: MainViewModel by viewModels

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        val context = getActivity()

        model.getArticles().observe(this, Observer<List<NFDText>> { articles ->
            override fun onChanged(articles: List<NFDText>) {
              NFDTextAdapter.setupAdapterAndOnClickListener(articles, fragment_articles_list_view, context, "article")
            }
        })

        return view
    }

    companion object {
        fun newInstance(): ArticlesFragment = ArticlesFragment()
    }
}

