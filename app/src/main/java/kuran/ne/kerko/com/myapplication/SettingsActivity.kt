package kuran.ne.kerko.com.myapplication

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

    }

    fun setOnClickListeners(){
        btnLanguage.setOnClickListener {
            val dialog = Dialog(this)
            dialog .setCanceledOnTouchOutside(true)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog .setContentView(R.layout.activity_language_selector)
            dialog .show()

        }
    }

}
