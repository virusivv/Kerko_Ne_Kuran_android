package kuran.ne.kerko.com.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.TextView

class LexoKuranin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lexo_kuranin)


        var readType:String = intent.getStringExtra("Reading Type")


        var txt = findViewById(R.id.textView) as TextView
        txt.setText(readType)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Handle the back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MainMenu::class.java)
            startAnActivity(intent)
            return true
        } else {
            return super.onKeyDown(keyCode, event)
        }

    }


    fun startAnActivity(intentToGo: Intent){
        startActivity(intentToGo)
        finish()
    }
}
