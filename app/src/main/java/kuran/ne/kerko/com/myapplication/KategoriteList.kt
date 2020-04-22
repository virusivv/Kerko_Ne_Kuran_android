package kuran.ne.kerko.com.myapplication

import Helpers.KategoriteDS
import Helpers.KategoriteListAdapter
import Models.KategoriteModel
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast


class KategoriteList : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var mDbHelper: KategoriteDS

    var categoriesListObject: List<KategoriteModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategorite_list)

        listView = findViewById<ListView>(R.id.listKategorite)


        mDbHelper = KategoriteDS(this)

        getList()

        var editText = findViewById<EditText>(R.id.edtxtKerkoTexti)
        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                getList()
            }
        })
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

    fun getList(){
        mDbHelper.open()

        val tekstimarrur : String = (findViewById(R.id.edtxtKerkoTexti) as EditText).text.toString()
        val mPrefs = getSharedPreferences("Prefs", 0)
        val language:String = mPrefs.getString("lang", "")

        categoriesListObject = mDbHelper.getCategoriesBasedOnSearchText(tekstimarrur, language)

        mDbHelper.close()

        val adapter = KategoriteListAdapter(this, categoriesListObject)
        listView.adapter = adapter

        val context = this
        listView.setOnItemClickListener { _, _, position, _ ->
            val kategoria = categoriesListObject!![position]

            val intent = Intent(this, KategoriListaAjeteve::class.java)
            intent.putExtra("category", kategoria)
            startAnActivity(intent)
        }
    }

}
