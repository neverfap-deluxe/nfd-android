package com.nfd.nfdmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.nfd.nfdmobile.data.NFDText

import kotlinx.android.synthetic.main.activity_text.*

class NFDTextActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // NOTE so here it needs to check the type, it it exists then set a different view.

        setContentView(R.layout.activity_text)

        val ab = actionBar
        ab?.setDisplayHomeAsUpEnabled(true)
//        ab?.title = ""
//        ab?.subtitle = ""

        val title = intent.extras?.getString(EXTRA_TITLE)
        val date = intent.extras?.getString(EXTRA_DATE)
        val content = intent.extras?.getString(EXTRA_CONTENT)
//        val nfdType = intent.extras?.getString(EXTRA_NFD_TYPE)

        if (content !== null) {
            activity_text_view_title.text = title
            activity_text_view_date.text = date
            activity_text_view_content.text = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        setTitle(title)
    }


    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_DATE = "date"
        const val EXTRA_CONTENT = "content"
//        const val EXTRA_NFD_TYPE = "nfd_type"

        fun newIntent(context: Context, text: NFDText): Intent { // textType: String
            val detailIntent = Intent(context, NFDTextActivity::class.java)

            detailIntent.putExtra(EXTRA_TITLE, text.title)
            detailIntent.putExtra(EXTRA_DATE, text.date)
            detailIntent.putExtra(EXTRA_CONTENT, text.content)
//            detailIntent.putExtra(EXTRA_NFD_TYPE, textType)

            return detailIntent
        }
    }
}
