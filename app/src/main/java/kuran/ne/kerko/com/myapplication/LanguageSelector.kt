package kuran.ne.kerko.com.myapplication

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import java.util.*
import android.R.id.edit
import android.content.SharedPreferences
import android.content.DialogInterface
import android.R.string.yes
import android.R.string.no
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.R.attr.key







class LanguageSelector : AppCompatActivity() {

    var firstRun=""
    var lang=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selector)


        val mPrefs = getSharedPreferences("Prefs", 0)
        firstRun = mPrefs.getString("lang", "")

        if(firstRun!=null && firstRun.trim()!="")
            lang=firstRun

        val backButon=findViewById(R.id.btnSaveLanguageSelector) as Button
        backButon.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startIntent(intent)
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
                        lang="al"
                    }
                R.id.rdbAnglishtLanguageSelector ->
                    if (checked) {
                        saveBTN.setText("Save Chagnes")
                        lang="en"
                    }
                R.id.rdbGjermanishtLanguageSelector ->
                    if (checked) {
                        saveBTN.setText("Änderungen speichern")
                        lang="de"
                    }
                R.id.rdbTurqishtLanguageSelector ->
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
                this.resources.updateConfiguration(
                    config,
                    this@LanguageSelector.getResources().getDisplayMetrics()
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


    fun startIntent(intentToGo: Intent?){
            /*val prefs2 = getPreferences(Context.MODE_PRIVATE)
            val editor2 = prefs2.edit()
            editor2.putString()
            editor2.commit()*/


        val mPrefs = getSharedPreferences("Prefs", 0)
        val editor = mPrefs.edit()
        editor.putString("lang", lang)
        editor.commit()
        startActivity(intentToGo)
        finish()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Handle the back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MainMenu::class.java)
            startIntent(intent)
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
