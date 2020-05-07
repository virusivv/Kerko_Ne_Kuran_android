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
class ReportBugDialog(context: Context): Dialog(context) {
    val TAG = "ReportBugDialog"

    init {
        setContentView(R.layout.dialog_report_bug)
        setupListeners()
        show()
    }

    private fun setupListeners() {

        btnSave.setOnClickListener {
            //do a post request to web to report a bug
            dismiss()
        }

        btnCancel.setOnClickListener { dismiss() }
    }
}