package com.nfd.nfdmobile.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.adapters.NFDAudioAdapter
import com.nfd.nfdmobile.adapters.NFDTextAdapter
import com.nfd.nfdmobile.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_meditation.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class MeditationsFragment : Fragment() {
    private val model : MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_meditation, container, false)
        val context = context

        model.getLatestMeditations()

        context?.let {
            model.meditations.observe(this, Observer { meditations ->
                NFDAudioAdapter.setupAdapterAndOnClickListener(meditations, fragment_meditations_list_view, context, "meditation")
            })
        }

        return view
    }

    private fun saveAudioFile(filename: String, audioContents: String) {
        val context = context

        context?.let {
            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(audioContents.toByteArray())
            }

        }
    }


    companion object {
        fun newInstance(): MeditationsFragment = MeditationsFragment()
    }
}

