package com.nfd.nfdmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.data.NFDText
import com.nfd.nfdmobile.nfdtext.NFDTextActivity

class NFDTextAdapter(
    private val context: Context,
    private val retrievedList: List<NFDText>
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

        val nfdTextItem = getItem(position) as NFDText

        holder.titleTextView.text = nfdTextItem.title
        holder.dateTextView.text = nfdTextItem.date

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
            retrievedList: List<NFDText>,
            view: ListView,
            context: Context,
            type: String
        ) {
            // NOTE: Populates Text View
            val adapter = NFDTextAdapter(context, retrievedList)
            view.adapter = adapter

            // NOTE: Populates Text OnClick
            view.setOnItemClickListener { _, _, position, _ ->
                val selectedTextItem = retrievedList[position]
                val textItemIntent = NFDTextActivity.newIntent(context, selectedTextItem, type)

                context.startActivity(textItemIntent)
            }
        }
    }
}