package kuran.ne.kerko.com.myapplication

import Models.KategoriteModel
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class KategoriteList : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategorite_list)

        var readType:String = intent.getStringExtra("Reading Type")



        listView = findViewById<ListView>(R.id.recipe_list_view)



        val kategorite = ArrayList<KategoriteModel>()
        kategorite.add(KategoriteModel(1,"bekare","pershkrimi",2,1))
        kategorite.add(KategoriteModel(2,"bekare","pershkrimi3",2,2))





        val adapter = KategoriteListAdapter(this, kategorite)
        listView.adapter = adapter

        val context = this
        listView.setOnItemClickListener { _, _, position, _ ->
            val kategoria = kategorite[position]

            //val detailIntent = RecipeDetailActivity.newIntent(context, selectedRecipe)

            //startActivity(detailIntent)
            Toast.makeText(this,"Surja dhe ajeti i zgjedhur: "+kategoria.surja+" - " +kategoria.ajeti,Toast.LENGTH_SHORT)
        }
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
