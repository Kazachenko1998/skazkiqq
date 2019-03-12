package com.mcakir.radio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import com.mcakir.radio.player.PlaybackStatus
import com.mcakir.radio.player.RadioManager
import com.mcakir.radio.util.Shoutcast
import com.mcakir.radio.util.ShoutcastHelper
import com.mcakir.radio.util.ShoutcastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    lateinit var radioManager: RadioManager

    lateinit var streamURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        radioManager = RadioManager.with(this)
        listview!!.adapter = ShoutcastListAdapter(this, ShoutcastHelper.retrieveShoutcasts(this))
        listview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        listview.scrollToPosition(Int.MAX_VALUE / 2)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(listview)
        init()
    }

    private fun init() {
        play_trigger.setOnClickListener {
            if (!TextUtils.isEmpty(streamURL)) radioManager.playOrPause(streamURL)
        }
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onDestroy() {
        radioManager.unbind()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        radioManager.bind()
    }

    override fun onBackPressed() {
        finish()
    }

    @Subscribe
    fun onEvent(status: String) {
        when (status) {
            PlaybackStatus.LOADING -> {
            }
            PlaybackStatus.ERROR ->
                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show()
        }// loading

        play_trigger!!.setImageResource(if (status == PlaybackStatus.PLAYING)
            R.drawable.ic_pause_black
        else
            R.drawable.ic_play_arrow_black)
    }


}
