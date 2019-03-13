package com.mcakir.radio.util

import android.support.v7.widget.RecyclerView
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

                view.setOnClickListener {
                    val questionsView = view.findViewById<RelativeLayout>(R.id.exit_view)
                    questionsView.alpha = 0f
                    questionsView.visibility = View.VISIBLE
                    questionsView.animate().alpha(1f)
                    val okBtn = view.findViewById<TextView>(R.id.yes_btn)
                    okBtn.setOnClickListener { onItemClickBack() }
                    val noBtn = view.findViewById<TextView>(R.id.no_btn)
                    okBtn.setOnClickListener { questionsView.animate().alpha(0f).withEndAction { questionsView.visibility = View.GONE } }

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
