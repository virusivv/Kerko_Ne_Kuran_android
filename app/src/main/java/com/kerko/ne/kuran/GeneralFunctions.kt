package com.kerko.ne.kuran

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Window
import android.view.WindowManager

/**
 * Created by Ardian Ahmeti on 03/08/2020
 **/
object GeneralFunctions {
    fun setBackgroundToStatusBarAndNavigationBar(window: Window, context: Context, background: Drawable){
        window.statusBarColor = context.resources.getColor(android.R.color.transparent)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = context.resources.getColor(android.R.color.transparent)
        window.navigationBarColor = context.resources.getColor(R.color.gray_middle)
        window.setBackgroundDrawable(background)
    }
}