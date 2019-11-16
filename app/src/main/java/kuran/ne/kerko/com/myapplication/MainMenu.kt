package kuran.ne.kerko.com.myapplication

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import android.content.SharedPreferences
import android.content.res.Configuration
import android.view.View
import java.util.*


class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mPrefs = getSharedPreferences("Prefs", 0)
        val firstRun = mPrefs.getString("lang", "")

        if(firstRun==null || firstRun.trim()==""){
            val intent = Intent(this, LanguageSelector::class.java)
            startAnActivity(intent)
        }else
        {

            try {
                val locale = Locale(firstRun)
                Locale.setDefault(locale)
                val config = Configuration()
                config.setLocale(locale)
                this.resources.updateConfiguration(
                    config,
                    this@MainMenu.getResources().getDisplayMetrics()
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        setContentView(R.layout.activity_main_menu)
        val rdbAnglishtSelector= findViewById(R.id.btnChangeLang) as Button
        rdbAnglishtSelector.setOnClickListener {
            val intent = Intent(this, LanguageSelector::class.java)
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
                dest="kategorite"
                toReadQuran=false
            }
        }
        var intent=Intent()
        if (toReadQuran )
            intent = Intent(this, LexoKuranin::class.java)
        else
            intent = Intent(this,KategoriteList::class.java)
        intent.putExtra("Reading Type",dest)
        startAnActivity(intent)
    }


    fun startAnActivity(intentToGo: Intent){
        startActivity(intentToGo)
        finish()
    }

}
