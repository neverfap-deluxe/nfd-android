package com.nfd.nfdmobile.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.nfdtext.NFDText
import kotlinx.android.synthetic.main.fragment_practice.*


class PracticesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_practice, container, false)
        context = getActivity()

        val model = ViewModelProviders.of(this)[MainViewModel::class.java]

        model.getPractices().observe(this, Observer<List<NFDText>>{ practices ->
            override fun onChanged(practices: List<NFDText>) {
                NFDTextAdapter.setupAdapterAndOnClickListener(practices, fragment_practices_list_view, context, "practice")
            }
        })

        return view
    }

    companion object {
        fun newInstance(): PracticesFragment = PracticesFragment()
    }
}

