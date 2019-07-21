package com.nfd.nfdmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nfd.nfdmobile.R


class PracticesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_practice, container, false)

    companion object {
        fun newInstance(): PracticesFragment = PracticesFragment()
    }
}

