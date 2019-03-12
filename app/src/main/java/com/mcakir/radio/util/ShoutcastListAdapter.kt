package com.mcakir.radio.util

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        fun onBind(position: Int) {
            positionItem = position % shoutcasts.size
            picture = view.findViewById(R.id.picture)
            picture.setImageResource(
                    activity.resources.getIdentifier(
                            "radio_$positionItem",
                            "drawable",
                            activity.packageName))
            view.setOnClickListener { onItemClick() }
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
