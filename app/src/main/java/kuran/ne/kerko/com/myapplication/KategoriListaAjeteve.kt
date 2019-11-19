package kuran.ne.kerko.com.myapplication

import Helpers.AjetetKategoriteListAdapter
import Helpers.KategoriteDS
import Helpers.KategoriteListAdapter
import Models.AjetetPerKategoriModel
import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class KategoriListaAjeteve : AppCompatActivity() {
    private lateinit var listView: ListView

    var tagateajeteve: Cursor? = null
    var categoriesListObject: List<AjetetPerKategoriModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_lista_ajeteve)


        var categoryText:String = intent.getStringExtra("Category Text")
        var categoryID:Int = intent.getIntExtra("Category ID",-1)

        val txtKategoria = findViewById<TextView>(R.id.txtKategoria)
        txtKategoria.setText(categoryText)


        listView = findViewById<ListView>(R.id.listKategoriteAjetet)


        val mDbHelper = KategoriteDS(this)
//		mDbHelper.createDatabase();
        mDbHelper.open()
        if (tagateajeteve != null)
            tagateajeteve!!.close()
        val tekstimarrur = ""

        val mPrefs = getSharedPreferences("Prefs", 0)
        val language:String = mPrefs.getString("lang", "")

        categoriesListObject = mDbHelper.getAyahsForCategory(
            categoryID, language
        )

        // TextView txtti= (TextView) findViewById(R.id.txtrezultati);
        // txtti.setText(rezultati);
        mDbHelper.close()
        //populateCategories()




        val adapter = AjetetKategoriteListAdapter(this, categoriesListObject)
        listView.adapter = adapter

        val context = this
        listView.setOnItemClickListener { _, _, position, _ ->
            val kategoria = categoriesListObject!![position]

            //val detailIntent = RecipeDetailActivity.newIntent(context, selectedRecipe)

            //startActivity(detailIntent)
            Toast.makeText(this,"Tagu eshte: "+kategoria.tagu, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Handle the back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, KategoriteList::class.java)
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
