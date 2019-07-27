package com.nfd.nfdmobile

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.exoplayer2.ui.PlayerView
import com.nfd.nfdmobile.R
import com.nfd.nfdmobile.data.NFDText
import com.nfd.nfdmobile.utilities.MediaPlayerImpl
import kotlinx.android.synthetic.main.activity_audio.*

import kotlinx.android.synthetic.main.activity_text.*

class NFDAudioActivity : AppCompatActivity() {
    private lateinit var videoView: PlayerView

    private val mediaPlayer = MediaPlayerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // NOTE so here it needs to check the type, it it exists then set a different view.
        setContentView(R.layout.activity_text)
        setSupportActionBar(findViewById(R.id.activity_toolbar))
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.extras?.getString(EXTRA_TITLE)
        val date = intent.extras?.getString(EXTRA_DATE)
        val content = intent.extras?.getString(EXTRA_CONTENT)
        val mp3Url = intent.extras?.getString(EXTRA_MP3_URL)
//        val nfdType = intent.extras?.getString(EXTRA_NFD_TYPE)

        if (content !== null && mp3Url !== null) {
            activity_text_view_title.text = title
            activity_text_view_date.text = date
            activity_text_view_content.text = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            
            videoView.player = mediaPlayer.getPlayerImpl(this)
            mediaPlayer.play(mp3Url)
        }

        setTitle(title)
    }

    
    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mediaPlayer.releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaPlayer.releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // presenter.deactivate()
        mediaPlayer.setMediaSessionState(false)
    }
 
    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_DATE = "date"
        const val EXTRA_CONTENT = "content"
        const val EXTRA_MP3_URL = "mp3Url"
//        const val EXTRA_NFD_TYPE = "nfd_type"

        fun newIntent(context: Context, text: NFDText): Intent { // textType: String
            val detailIntent = Intent(context, NFDAudioActivity::class.java)

            detailIntent.putExtra(EXTRA_TITLE, text.title)
            detailIntent.putExtra(EXTRA_DATE, text.date)
            detailIntent.putExtra(EXTRA_CONTENT, text.content)
            detailIntent.putExtra(EXTRA_MP3_URL, text.content)
//            detailIntent.putExtra(EXTRA_NFD_TYPE, textType)

            return detailIntent
        }
    }
}
