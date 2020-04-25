package com.kerko.ne.kuran

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.res.Configuration
import android.view.View
import kotlinx.android.synthetic.main.activity_main_menu.*
import java.util.*


class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mPrefs = getSharedPreferences("Prefs", 0)
        val firstRun = "12"//mPrefs.getString("lang", "")

        if(firstRun==null || firstRun.trim()==""){
            val intent = Intent(this, LanguageSelectorActivity::class.java)
            startAnActivity(intent)
        }
        else {
            try {
                val locale = Locale(firstRun)
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(
                    config,
                    this@MainMenuActivity.getResources().getDisplayMetrics()
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        setContentView(R.layout.activity_main_menu)
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startAnActivity(intent)
        }

    }

    fun butonsClicked(v: View){
        var dest="";
        var toReadQuran = true
        when(v.id){
            R.id.btnLexoKuranin -> {
                dest = "normal"
            }
            R.id.btnLexoKuraninLatin -> {
                dest="latin"
            }
            R.id.btnLexoKuraninArabisht -> {
                dest="arab"
            }
            R.id.btnKategorite -> {
                dest="categories"
                toReadQuran=false
            }
        }
        var intent=Intent()
        if (toReadQuran )
            intent = Intent(this, ReadQuranActivity::class.java)
        else
            intent = Intent(this,CategoriesActivity::class.java)
        intent.putExtra("Reading Type",dest)
        startAnActivity(intent)
    }


    fun startAnActivity(intentToGo: Intent){
        startActivity(intentToGo)
        finish()
    }

}
