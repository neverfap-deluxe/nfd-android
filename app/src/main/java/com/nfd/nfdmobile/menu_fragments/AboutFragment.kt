package com.nfd.nfdmobile.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AboutFragment : Fragment() {
    private val model: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        return view
    }

    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }
}

