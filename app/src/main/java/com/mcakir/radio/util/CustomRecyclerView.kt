package com.mcakir.radio.util

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class CustomRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        val mult = 0.3//for change speed
        return super.fling((velocityX * mult).toInt(), velocityY)
    }
}