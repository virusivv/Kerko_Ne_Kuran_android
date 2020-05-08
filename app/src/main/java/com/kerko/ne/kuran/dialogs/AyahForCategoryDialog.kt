package com.kerko.ne.kuran.dialogs

import Models.AyahsForCategoriesModel
import android.app.Dialog
import android.content.Context
import com.kerko.ne.kuran.R
import kotlinx.android.synthetic.main.dialog_ayah_category.*

/**
 * Created by Ardian Ahmeti on 04/26/2020
 **/
class AyahForCategoryDialog(context: Context, ayah: AyahsForCategoriesModel) : Dialog(context) {
    val TAG = "AyahForCategoryDialog"
    lateinit var selectedAyah:AyahsForCategoriesModel
    init {
        setContentView(R.layout.dialog_ayah_category)
        selectedAyah = ayah
        setCanceledOnTouchOutside(true)
        updateAyahTextsViews()
        show()
    }

    private fun updateAyahTextsViews() {
        tvAyahNoSurahSays.text=selectedAyah.surah + " " + selectedAyah.ayah_ids_text + " " + context.getString(R.string.says)
        tvDescription.text=selectedAyah.ayah
    }

}