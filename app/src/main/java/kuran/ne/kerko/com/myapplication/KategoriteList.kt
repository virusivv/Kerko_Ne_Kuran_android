package kuran.ne.kerko.com.myapplication

import Helpers.KategoriteDS
import Helpers.KategoriteListAdapter
import Models.KategoriteModel
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_kategorite_list.*


class KategoriteList : AppCompatActivity() {

    private lateinit var mDbHelper: KategoriteDS

    var categoriesListObject: List<KategoriteModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategorite_list)



        mDbHelper = KategoriteDS(this)

        getList()

        edtxtKerkoTexti.addTextChangedListener(object : TextWatcher {

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

        val tekstimarrur : String = (edtxtKerkoTexti).text.toString()
        val mPrefs = getSharedPreferences("Prefs", 0)
        val language:String = "sq"//mPrefs.getString("lang", "")

        categoriesListObject = mDbHelper.getCategoriesBasedOnSearchText(tekstimarrur, language)

        mDbHelper.close()

        val adapter = KategoriteListAdapter(this, categoriesListObject)
        listKategorite.adapter = adapter

        val context = this
        listKategorite.setOnItemClickListener { _, _, position, _ ->
            val kategoria = categoriesListObject!![position]

            val intent = Intent(this, KategoriListaAjeteve::class.java)
            intent.putExtra("category", kategoria)
            startAnActivity(intent)
        }
    }

}
