package com.kerko.ne.kuran.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.kerko.ne.kuran.R
import kotlinx.android.synthetic.main.dialog_about_us.*
import kotlinx.android.synthetic.main.dialog_font_size.*


/**
 * Created by Ibrahim Vasija on 05/06/2020
 **/
class AboutUsDialog(context: Context) : Dialog(context) {
    val TAG = "AboutUsDialog"

    init {
        setContentView(R.layout.dialog_about_us)
        updateSettingViews()
        setupListeners()
        show()
    }

    private fun updateSettingViews() {

        tvTranslators.text = "%s\n%s\n%s\n%s".format(
            context.resources.getString(R.string.translator_sq),
            context.resources.getString(R.string.translator_en),
            context.resources.getString(R.string.translator_tr),
            context.resources.getString(R.string.translator_de)
        )

    }

    private fun setupListeners() {
        tvAptDevURL.setOnClickListener {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/developer?id=AptDev")
            )
            context.startActivity(webIntent)

        }
    }
}