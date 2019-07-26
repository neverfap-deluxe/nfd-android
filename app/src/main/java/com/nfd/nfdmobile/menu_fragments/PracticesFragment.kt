package com.nfd.nfdmobile.fragments

import android.content.Context
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_practice.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PracticesFragment : Fragment() {
    private val model: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_practice, container, false)
        val context = context

//        context?.let {
//            prePopulateViewData(context)
//        }

        model.getLatestPractices(500)

        context?.let {
            model.practices.observe(this, Observer { practices ->
                NFDTextAdapter.setupAdapterAndOnClickListener(practices, fragment_practices_list_view, context, "practice")
            })
        }

        return view
    }

//    private fun prePopulateViewData(context: Context) {
//        val database = AppDatabase.getInstance(context)
//        val nfdTextDao = database.nfdTextDao()
//        val practices = nfdTextDao.getTextsByType("practice")
//        practices.forEach {
//            NFDTextAdapter.setupAdapterAndOnClickListener(practices, fragment_practices_list_view, context, "practice")
//        }
//    }

    companion object {
        fun newInstance(): PracticesFragment = PracticesFragment()
    }
}

