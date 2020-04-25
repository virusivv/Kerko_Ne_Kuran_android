package com.kerko.ne.kuran

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import java.util.*
import android.view.KeyEvent
import android.content.Context
import kotlinx.android.synthetic.main.activity_language_selector.*


class LanguageSelectorActivity : AppCompatActivity() {

    var firstRun=""
    var lang=""

    public lateinit var cntx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selector)



        val mPrefs = cntx.getSharedPreferences("Prefs", 0)
        firstRun = "sq"//mPrefs.getString("lang", "")

        if(firstRun!=null && firstRun.trim()!="")
            lang=firstRun

        btnSaveLanguageSelector.setOnClickListener {
            startIntent()
        }
    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            val saveBTN=findViewById(R.id.btnSaveLanguageSelector) as Button
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rdbShqipLanguageSelector ->
                    if (checked) {
                        saveBTN.setText("Ruaj ndryshimet")
                        lang="sq"
                    }
                R.id.rdbEnglishLanguageSelector ->
                    if (checked) {
                        saveBTN.setText("Save Chagnes")
                        lang="en"
                    }
                R.id.rdbDeutschLanguageSelector ->
                    if (checked) {
                        saveBTN.setText("Änderungen speichern")
                        lang="de"
                    }
                R.id.rdbTurkceLanguageSelector ->
                    if (checked) {
                        saveBTN.setText("Değişiklikleri Kaydet")
                        lang="tr"
                    }
            }
            try {
                val locale = Locale(lang)
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                cntx.resources.updateConfiguration(
                    config,
                    cntx.resources.getDisplayMetrics()
                )
                saveBTN.visibility=View.VISIBLE
                if(firstRun==null || firstRun.trim()=="") {
                    saveBTN.setText(R.string.vazhdo_butoni)
                }else
                {
                    saveBTN.setText(R.string.kthehu_butoni)
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
    }


    fun startIntent(){
        val mPrefs = cntx.getSharedPreferences("Prefs", 0)
        val editor = mPrefs.edit()
        editor.putString("lang", lang)
        editor.commit()
        //dismiss()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Handle the back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //dismiss()
            return true
        } else {
            return super.onKeyDown(keyCode, event)
        }

    }


    open class BaseActivity : AppCompatActivity() {

        companion object {
            public var dLocale: Locale? = null
        }

        init {
            updateConfig(this)
        }

        fun updateConfig(wrapper: BaseActivity) {
            if(dLocale==Locale("") ) // Do nothing if dLocale is null
                return

            Locale.setDefault(dLocale)
            val configuration = Configuration()
            configuration.setLocale(dLocale)
            wrapper.applyOverrideConfiguration(configuration)
        }
    }

}
