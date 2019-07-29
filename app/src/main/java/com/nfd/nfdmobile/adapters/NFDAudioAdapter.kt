package com.nfd.nfdmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.NFDAudioActivity
import com.nfd.nfdmobile.data.NFDAudio
import com.nfd.nfdmobile.utilities.Helpers
import com.nfd.nfdmobile.viewmodels.MainViewModel

class NFDAudioAdapter(
    private val context: Context,
    private val retrievedList: List<NFDAudio>
    ) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // NOTE: view essentially relates to the layout. ViewHolder refers to all the individual views.
        // NOTE: The reason it's confusing is because it's all simply mutating objects. There is no functional flow.
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.text_item, parent, false)

            holder = ViewHolder()
            holder.titleTextView = view.findViewById(R.id.text_item_title) as TextView
            holder.dateTextView = view.findViewById(R.id.text_item_date) as TextView

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val nfdAudioItem = getItem(position) as NFDAudio

        holder.titleTextView.text = nfdAudioItem.title
        holder.dateTextView.text = nfdAudioItem.date

        return view
    }

    override fun getCount(): Int {
        return retrievedList.size
    }

    override fun getItem(position: Int): Any {
        return retrievedList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ViewHolder {
        lateinit var titleTextView: TextView
        lateinit var dateTextView: TextView
    }

    companion object {
            fun setupAdapterAndOnClickListener(
                retrievedList: List<NFDAudio>,
                view: ListView,
                context: Context,
                type: String
            ) {
                // NOTE: Populates Audio View
                val adapter = NFDAudioAdapter(context, retrievedList)
                view.adapter = adapter

            // NOTE: Populates Audio OnClick
            view.setOnItemClickListener { _, _, position, _ ->
                if (Helpers.isNetworkAvailable(context!!)) {
                    val selectedAudioItem = retrievedList[position]
                    val textItemIntent = NFDAudioActivity.newIntent(context, selectedAudioItem) // type

                    context.startActivity(textItemIntent)
                } else {
                    val toast = Toast.makeText(context, "You must be connected to the internet!", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }

    }
}