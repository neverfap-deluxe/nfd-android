package com.nfd.nfdmobile.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.adapters.NFDTextAdapter
import com.nfd.nfdmobile.data.AppDatabase
import com.nfd.nfdmobile.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ArticlesFragment : Fragment() {
    private val model: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        val context = context

//        context?.let {
//            prePopulateViewData(context)
//        }

        model.getLatestArticles(500)

        context?.let {
            model.articles.observe(this, Observer { articles ->
                NFDTextAdapter.setupAdapterAndOnClickListener(articles, fragment_articles_list_view, context, "article")
            })
        }

        return view
    }

//    private fun prePopulateViewData(context: Context) {
//        val database = AppDatabase.getInstance(context)
//        val nfdTextDao = database.nfdTextDao()
//        val articles = nfdTextDao.getTextsByType("article")
//        articles.forEach {
//            NFDTextAdapter.setupAdapterAndOnClickListener(articles, fragment_articles_list_view, context, "article")
//        }
//    }

    companion object {
        fun newInstance(): ArticlesFragment = ArticlesFragment()
    }
}

