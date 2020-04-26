package com.kerko.ne.kuran.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.kerko.ne.kuran.R
import kotlinx.android.synthetic.main.settings_item_layout.view.*

/**
 * Created by Ardian Ahmeti on 03/08/2020
 **/
class SettingsItem(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.settings_item_layout, this)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SettingsItem,
            0, 0
        ).apply {
            try {
                ivSettingIcon.setImageResource(
                    getResourceId(
                        R.styleable.SettingsItem_settingIcon,
                        R.drawable.circle
                    )
                )
                tvSettingTitle.text = getText(R.styleable.SettingsItem_settingTitle)
                tvSettingInfo.text = getText(R.styleable.SettingsItem_settingInfo)
            } finally {
                recycle()
            }
        }
    }

    fun setInfoText(text: String?) {
        text?.let { tvSettingInfo.text = it }
    }
}