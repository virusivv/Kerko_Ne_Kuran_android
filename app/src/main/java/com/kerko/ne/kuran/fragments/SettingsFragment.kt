package com.kerko.ne.kuran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.kerko.ne.kuran.QuranApplication
import com.kerko.ne.kuran.dialogs.LanguageDialog
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.dialogs.FontChangedListener
import com.kerko.ne.kuran.dialogs.FontSizeDialog
import com.kerko.ne.kuran.dialogs.LanguageChangeListener
import com.kerko.ne.kuran.presenters.SettingsPresenter
import com.kerko.ne.kuran.views.SettingsView
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class SettingsFragment : MvpFragment<SettingsView, SettingsPresenter>(), SettingsView, LanguageChangeListener, FontChangedListener {

    val TAG = "SettingsFragment"

    override fun createPresenter() = SettingsPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    override fun onResume() {
        updateLanguage()
        updateFontSize()
        super.onResume()
    }

    private fun setupClickListeners() {
        siLanguage.setOnClickListener {
            context?.let {
                LanguageDialog(it, this)
            }
        }
        siFontSize.setOnClickListener {
            context?.let {
                FontSizeDialog(it, this)
            }
        }
    }

    private fun updateLanguage() {
        siLanguage.setInfoText(QuranApplication.instance.getLanguage()?.canonicalForm())
    }

    private fun updateFontSize() {
        val font = QuranApplication.instance.getFontSize()
        siFontSize.setInfoText(font.toString())

    }


    override fun onLanguageChanged() {
        updateLanguage()
    }

    override fun onFontChanged() {
        updateFontSize()
    }

}