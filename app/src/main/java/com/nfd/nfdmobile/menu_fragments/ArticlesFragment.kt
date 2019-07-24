package com.nfd.nfdmobile.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.nfdtext.NFDText
import kotlinx.android.synthetic.main.fragment_article.*


class ArticlesFragment : Fragment() {
    private val nFDTextViewModel: NFDTextViewModel by viewModels

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_article, container, false)
        context = getActivity()
        database = AppDatabase.getInstance(context)
        service = ContentServiceAPI.create()
        repository = NFDTextRepository(database, service, context)
        
        val model = ViewModelProviders.of(this)[MainViewModel::class.java]

        model.getArticles().observe(this, Observer<List<NFDText>>{ articles ->
          NFDTextAdapter.setupAdapterAndOnClickListener(articles, fragment_articles_list_view, context, "article")
        })

        return view
    }

    companion object {
        fun newInstance(): ArticlesFragment = ArticlesFragment()
    }
}

