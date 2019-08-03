package com.nfd.nfdmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.NFDAudioActivity
import com.nfd.nfdmobile.WebViewActivity
import com.nfd.nfdmobile.data.NFDAudio
import com.nfd.nfdmobile.data.NFDWebsite
import com.nfd.nfdmobile.utilities.Helpers
import com.nfd.nfdmobile.viewmodels.MainViewModel

class WebViewAdapter(
    private val context: Context,
    private val retrievedList: List<NFDWebsite>
    ) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // NOTE: view essentially relates to the layout. ViewHolder refers to all the individual views.
        // NOTE: The reason it's confusing is because it's all simply mutating objects. There is no functional flow.
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.website_item, parent, false)

            holder = ViewHolder()
            holder.websiteItemTitle = view.findViewById(R.id.website_item_title) as TextView
            holder.websiteItemUrl = view.findViewById(R.id.website_item_url) as TextView

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val nfdWebsite = getItem(position) as NFDWebsite

        holder.websiteItemTitle.text = nfdWebsite.title
        holder.websiteItemUrl.text = nfdWebsite.url

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
        lateinit var websiteItemTitle: TextView
        lateinit var websiteItemUrl: TextView
    }

    companion object {
            fun setupAdapterAndOnClickListener(
                urlList: List<NFDWebsite>,
                view: ListView,
                context: Context
            ) {

                val adapter = WebViewAdapter(context, urlList)
                view.adapter = adapter

                view.setOnItemClickListener { _, _, position, _ ->
                    val nfdWebsite = urlList[position]
                    val websiteItemIntent = WebViewActivity.newIntent(context, nfdWebsite)

                    context.startActivity(websiteItemIntent)
                }
        }

    }
}