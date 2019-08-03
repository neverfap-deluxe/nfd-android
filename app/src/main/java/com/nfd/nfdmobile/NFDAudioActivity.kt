package com.nfd.nfdmobile

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ui.PlayerView
import com.nfd.nfdmobile.data.NFDAudio
import com.nfd.nfdmobile.utilities.MediaPlayerImpl
import kotlinx.android.synthetic.main.activity_audio.*
import kotlinx.android.synthetic.main.activity_text.*

class NFDAudioActivity : AppCompatActivity() {
    private lateinit var videoView: PlayerView

    private val mediaPlayer = MediaPlayerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keeps Screen on (I assume so, not tested)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // NOTE so here it needs to check the type, it it exists then set a different view.
        setContentView(R.layout.activity_audio)
        setSupportActionBar(findViewById(R.id.activity_toolbar))
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.extras?.getString(EXTRA_TITLE)
        val date = intent.extras?.getString(EXTRA_DATE)
        val content = intent.extras?.getString(EXTRA_CONTENT)
        val mp3Url = intent.extras?.getString(EXTRA_MP3_URL)
//        val nfdType = intent.extras?.getString(EXTRA_NFD_TYPE)

        if (content !== null && mp3Url !== null) {
            activity_audio_view_title.text = title
            activity_audio_view_date.text = date
//            activity_audio_view_content.text = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            videoView = nfd_audio_mp3_view

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

        fun newIntent(context: Context, text: NFDAudio): Intent { // textType: String
            val detailIntent = Intent(context, NFDAudioActivity::class.java)

            detailIntent.putExtra(EXTRA_TITLE, text.title)
            detailIntent.putExtra(EXTRA_DATE, text.date)
            detailIntent.putExtra(EXTRA_CONTENT, text.content)
            detailIntent.putExtra(EXTRA_MP3_URL, text.mp3Url)
//            detailIntent.putExtra(EXTRA_NFD_TYPE, textType)

            return detailIntent
        }
    }
}
