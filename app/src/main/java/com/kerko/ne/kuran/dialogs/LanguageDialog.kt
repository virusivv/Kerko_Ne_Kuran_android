package com.kerko.ne.kuran.dialogs

import android.app.Dialog
import android.content.Context
import com.kerko.ne.kuran.QuranApplication
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.enums.LanguageEnum
import kotlinx.android.synthetic.main.dialog_language.btnCancel
import kotlinx.android.synthetic.main.dialog_language.btnSave
import kotlinx.android.synthetic.main.dialog_language.*

/**
 * Created by Ardian Ahmeti on 04/26/2020
 **/
class LanguageDialog(context: Context, var languageChangeListener: LanguageChangeListener) : Dialog(context) {
    val TAG = "LanguageDialog"

    init {
        setContentView(R.layout.dialog_language)
        updateSettingViews()
        setupListeners()
        show()
    }

    private fun updateSettingViews() {
        val language = QuranApplication.instance.getLanguage()
        language?.let {
            when (language) {
                LanguageEnum.Albanian -> radioAlbanian.isChecked = true
                LanguageEnum.English -> radioEnglish.isChecked = true
                LanguageEnum.German -> radioGerman.isChecked = true
                LanguageEnum.Turkish -> radioTurkish.isChecked = true
            }
        }
    }

    private fun setupListeners() {
        btnSave.setOnClickListener {
            val language =
                if (radioAlbanian.isChecked) LanguageEnum.Albanian
                else if (radioEnglish.isChecked) LanguageEnum.English
                else if (radioTurkish.isChecked) LanguageEnum.Turkish
                else LanguageEnum.German
            QuranApplication.instance.setLanguage(language)
            languageChangeListener.onLanguageChanged()
            dismiss()
        }

        btnCancel.setOnClickListener { dismiss() }
    }
}

interface LanguageChangeListener {
    fun onLanguageChanged()
}