package kuran.ne.kerko.com.myapplication

import Handlers.KategoriteDS
import Models.KategoriteModel
import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ListView
import android.widget.Toast
import java.util.ArrayList


class KategoriteList : AppCompatActivity() {

    private lateinit var listView: ListView

    var tagateajeteve: Cursor? = null
    var categoriesListObject: List<KategoriteModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategorite_list)

        var readType:String = intent.getStringExtra("Reading Type")



        listView = findViewById<ListView>(R.id.recipe_list_view)


        val mDbHelper = KategoriteDS(this)
//		mDbHelper.createDatabase();
        mDbHelper.open()
        if (tagateajeteve != null)
            tagateajeteve!!.close()
        val tekstimarrur = ""
        categoriesListObject = mDbHelper.viewEmployee(
            tekstimarrur, "sq"
        )

        // TextView txtti= (TextView) findViewById(R.id.txtrezultati);
        // txtti.setText(rezultati);
        mDbHelper.close()
        //populateCategories()




        val adapter = KategoriteListAdapter(this, categoriesListObject)
        listView.adapter = adapter

        val context = this
        listView.setOnItemClickListener { _, _, position, _ ->
            val kategoria = categoriesListObject!![position]

            //val detailIntent = RecipeDetailActivity.newIntent(context, selectedRecipe)

            //startActivity(detailIntent)
            Toast.makeText(this,"Tagu eshte: "+kategoria.tagu,Toast.LENGTH_SHORT).show()
        }
    }

    /*private fun populateCategories() {
        var i = 1
        kategoriteListaObject = ArrayList<KategoriteModel>()
        tagateajeteve!!.moveToFirst()
        while (!tagateajeteve.isAfterLast()) {
            var idja = ""
            var tagu = ""
            var numri = ""
            var nrRendor= ""
            if(i<10)
                nrRendor="0"+i
            else
                nrRendor=i.toString()
            idja = tagateajeteve!!.getString(tagateajeteve!!.getColumnIndex("id"))
            tagu = tagateajeteve!!.getString(tagateajeteve!!.getColumnIndex("tagu"))
            numri = tagateajeteve!!.getString(tagateajeteve!!.getColumnIndex("numri"))
            kategoriteListaObject!!.add(
                KategoriteModel(
                    Integer.parseInt(idja), tagu, Integer.parseInt(numri),Integer.parseInt(nrRendor)
                )
            )
            i++
            tagateajeteve!!.moveToNext()
        }
    }*/
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

    //////////////////////////////////////////////////////
    // Read from DB




}
