package com.nfd.nfdmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.adapters.NFDTextAdapter
import com.nfd.nfdmobile.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_practice.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PracticesFragment : Fragment() {
    private val model: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_practice, container, false)
        val context = context

        model.getLatestPractices()

        context?.let {

            model.practices.observe(this, Observer { practices ->
                NFDTextAdapter.setupAdapterAndOnClickListener(practices, fragment_practices_list_view, context, "practice")
            })
        }

        return view
    }

    companion object {
        fun newInstance(): PracticesFragment = PracticesFragment()
    }
}

