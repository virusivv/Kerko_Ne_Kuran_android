package com.kerko.ne.kuran.dialogs

import android.app.Dialog
import android.content.Context
import android.widget.SeekBar
import com.kerko.ne.kuran.QuranApplication
import com.kerko.ne.kuran.R
import kotlinx.android.synthetic.main.dialog_font_size.*

/**
 * Created by Ardian Ahmeti on 04/26/2020
 **/
class FontSizeDialog(context: Context, var fontChangedListener: FontChangedListener): Dialog(context) {
    val TAG = "FontSizeDialog"

    init {
        setContentView(R.layout.dialog_font_size)
        updateFontSize()
        setupListeners()
        show()
    }

    private fun updateFontSize() {
        val fontSize = QuranApplication.instance.getFontSize()
        tvFontSize.text = "Font size $fontSize"
        sbFontSize.progress = when(fontSize) {
            1.0f -> 1
            1.1f -> 2
            1.2f -> 3
            1.3f -> 4
            1.4f -> 5
            1.5f -> 6
            else -> 7
        }
    }

    fun intToF(int: Int): Float {
        return when(int) {
            1 -> 1.0f
            2 -> 1.1f
            3 -> 1.2f
            4 -> 1.3f
            5 -> 1.4f
            6 -> 1.5f
            else -> 1.6f
        }
    }

    private fun setupListeners() {
        sbFontSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvFontSize.text = "Font scale ${intToF(progress)}"
                tvFontSize.textScaleX = intToF(progress)
//                tvFontSize.textSize = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        btnSave.setOnClickListener {
            QuranApplication.instance.setFontScale(intToF(sbFontSize.progress))
            fontChangedListener.onFontChanged()
            dismiss()
        }

        btnCancel.setOnClickListener { dismiss() }
    }
}

interface FontChangedListener {
    fun onFontChanged()
}