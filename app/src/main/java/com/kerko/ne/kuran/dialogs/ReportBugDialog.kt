package com.kerko.ne.kuran.dialogs

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.kerko.ne.kuran.AsyncTasks.KNKAsyncTask
import com.kerko.ne.kuran.R
import kotlinx.android.synthetic.main.dialog_font_size.*
import kotlinx.android.synthetic.main.dialog_report_bug.btnCancel
import kotlinx.android.synthetic.main.dialog_report_bug.btnSave
import kotlinx.android.synthetic.main.dialog_report_bug.*


/**
 * Created by Ardian Ahmeti on 04/26/2020
 **/
class ReportBugDialog(context: Context) : Dialog(context) {
    val TAG = "ReportBugDialog"

    init {
        setContentView(R.layout.dialog_report_bug)
        setupListeners()
        show()
    }

    private fun setupListeners() {

        btnSave.setOnClickListener {

            val stringTest =
                KNKAsyncTask().execute("Bug", etReportBug.text.toString()).get()

            if(stringTest == "-1")
                Toast.makeText(context, context.getText(R.string.bug_failed), Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, context.getText(R.string.bug_success), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        btnCancel.setOnClickListener { dismiss() }
    }
}