package kuran.ne.kerko.com.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast

class LanguageSelector : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selector)

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
                    }
                R.id.rdbAnglishtLanguageSelector ->
                    if (checked) {
                        saveBTN.setText("Save Chagnes")
                    }
                R.id.rdbGjermanishtLanguageSelector ->
                    if (checked) {
                        saveBTN.setText("Änderungen speichern")
                    }
                R.id.rdbTurqishtLanguageSelector ->
                    if (checked) {
                        saveBTN.setText("Değişiklikleri Kaydet")
                    }
            }
        }
    }

}
