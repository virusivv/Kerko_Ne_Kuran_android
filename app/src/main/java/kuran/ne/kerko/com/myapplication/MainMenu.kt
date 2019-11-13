package kuran.ne.kerko.com.myapplication

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast


class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        val rdbAnglishtSelector= findViewById(R.id.btnChangeLang) as Button
        rdbAnglishtSelector.setOnClickListener {
            val intent = Intent(this, LanguageSelector::class.java)
            // start your next activity
            startActivity(intent)

        }

    }
}
