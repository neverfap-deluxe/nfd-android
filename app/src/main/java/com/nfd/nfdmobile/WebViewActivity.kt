package com.nfd.nfdmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.nfd.nfdmobile.data.NFDWebsite
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_webview)

        val title = intent.extras?.getString(EXTRA_TITLE)
        setTitle(title)

        val url = intent.extras?.getString(EXTRA_URL)

        webView = webview_frame
        webView.loadUrl(url)
    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_URL = "url"

        fun newIntent(context: Context, nfdWebsite: NFDWebsite): Intent { // textType: String
            val detailIntent = Intent(context, WebViewActivity::class.java)

            detailIntent.putExtra(EXTRA_TITLE, nfdWebsite.title)
            detailIntent.putExtra(EXTRA_URL, nfdWebsite.url)

            return detailIntent
        }
    }
}
