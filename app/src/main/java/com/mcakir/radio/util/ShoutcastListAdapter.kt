package com.mcakir.radio.util

import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.mcakir.radio.MainActivity
import com.mcakir.radio.R
import kotlinx.android.synthetic.main.content_main.*


class ShoutcastListAdapter(private val activity: MainActivity, private val shoutcasts: List<Shoutcast>) : RecyclerView.Adapter<ShoutcastListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = activity.layoutInflater

        return CustomViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return if (!shoutcasts.isEmpty()) Int.MAX_VALUE else 0
    }


    inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var picture: ImageView
        private var positionItem: Int = 0
        private var oldTime = 0L
        fun onBind(position: Int) {
            positionItem = position % shoutcasts.size
            picture = view.findViewById(R.id.picture)
            picture.setImageResource(
                    activity.resources.getIdentifier(
                            "radio_$positionItem",
                            "drawable",
                            activity.packageName))
            if (positionItem == 0) {
                picture.setImageResource(
                        activity.resources.getIdentifier(
                                "ic_action_exit",
                                "drawable",
                                activity.packageName))

                view.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                    if (motionEvent.action == MotionEvent.ACTION_DOWN){
                        if (System.currentTimeMillis() - oldTime < 500){
                            onItemClickBack()
                        }else{
                            oldTime = System.currentTimeMillis()
                        }
                    }
                    true
                }
            } else
                view.setOnClickListener { onItemClick() }
        }

        private fun onItemClickBack() {
            activity.onSuperBackPressed()
        }

        private fun onItemClick() {

            val shoutcast = shoutcasts[positionItem]
            activity.name!!.text = shoutcast.name
            activity.name.isSelected = true
            activity.sub_player!!.visibility = View.VISIBLE
            activity.streamURL = shoutcast.url
            activity.radioManager.playOrPause(activity.streamURL)
        }
    }
}
