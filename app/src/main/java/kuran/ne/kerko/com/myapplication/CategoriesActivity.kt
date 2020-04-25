package kuran.ne.kerko.com.myapplication

import Helpers.CategoriesDS
import Helpers.CategoriesAdapter
import Models.CategoriesModel
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import kotlinx.android.synthetic.main.activity_categories.*


class CategoriesActivity : AppCompatActivity() {

    private lateinit var mDbHelper: CategoriesDS

    var categoriesListObject: List<CategoriesModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        mDbHelper = CategoriesDS(this)

        getList()

        etSearchText.addTextChangedListener(object : TextWatcher {

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
            val intent = Intent(this, MainMenuActivity::class.java)
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

        val searchText : String = (etSearchText).text.toString()
        val mPrefs = getSharedPreferences("Prefs", 0)
        val language:String = "sq"//mPrefs.getString("lang", "")

        categoriesListObject = mDbHelper.getCategoriesBasedOnSearchText(searchText, language)

        mDbHelper.close()

        val adapter = CategoriesAdapter(this, categoriesListObject)
        listCategoryCategories.adapter = adapter

        listCategoryCategories.setOnItemClickListener { _, _, position, _ ->
            val category = categoriesListObject!![position]
            val intent = Intent(this, AyahsForCategoriesActivity::class.java)

            intent.putExtra("category", category)
            startAnActivity(intent)
        }
    }

}
