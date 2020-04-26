package com.kerko.ne.kuran.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.kerko.ne.kuran.R
import kotlinx.android.synthetic.main.navigation_item_layout.view.*

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class NavigationItem(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    var isClicked = false
    var normalIcon: Int = -1
    var clickedIcon: Int = -1

    init {
        View.inflate(context, R.layout.navigation_item_layout, this)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.NavigationItem,
            0, 0
        ).apply {
            try {
                normalIcon = getResourceId(R.styleable.NavigationItem_normalIcon, -1)
                clickedIcon = getResourceId(R.styleable.NavigationItem_clickedIcon, -1)
                icon.setImageResource(
                    getResourceId(
                        R.styleable.NavigationItem_normalIcon,
                        R.drawable.circle
                    )
                )
                if (getBoolean(R.styleable.NavigationItem_containsTitle, false)) {
                    title.visibility = View.VISIBLE
                    title.text = getText(R.styleable.NavigationItem_title)
                } else {
                    title.visibility = View.GONE
                }
            } finally {
                recycle()
            }
        }

        icon.setOnClickListener {
            this.callOnClick()
        }

        setOnClickListener {
            isClicked = !isClicked

            icon.setColorFilter(ContextCompat.getColor(context, if(isClicked) R.color.orange else R.color.black))
            icon.setImageResource(if (isClicked) clickedIcon else normalIcon)
        }

    }

    fun itemClicked(isClicked: Boolean) {
        this.isClicked = isClicked

        icon.setColorFilter(ContextCompat.getColor(context, if(isClicked) R.color.orange else R.color.black))
        icon.setImageResource(if (isClicked) clickedIcon else normalIcon)
    }
}