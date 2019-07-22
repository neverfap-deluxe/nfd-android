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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_article, container, false)

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        context?.let {
            NFDText.getItemsFromContentAPI("articles", fragment_articles_list_view, context)
        }
    }

    companion object {
        fun newInstance(): ArticlesFragment = ArticlesFragment()
    }
}

