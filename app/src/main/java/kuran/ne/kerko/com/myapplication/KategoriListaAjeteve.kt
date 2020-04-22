package kuran.ne.kerko.com.myapplication

import Helpers.AjetetKategoriteListAdapter
import Helpers.KategoriteDS
import Models.AjetetPerKategoriModel
import Models.KategoriteModel
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ListView
import android.widget.TextView
import android.app.Dialog
import android.view.Window


class KategoriListaAjeteve : AppCompatActivity() {
    private lateinit var listView: ListView

    var ayahListForCategory: List<AjetetPerKategoriModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_lista_ajeteve)


        var category:KategoriteModel = intent.extras.get("category") as KategoriteModel

        val txtKategoria = findViewById<TextView>(R.id.txtKategoria)
        txtKategoria.setText(category.kategoria)


        listView = findViewById<ListView>(R.id.listKategoriteAjetet)

        val mDbHelper = KategoriteDS(this)
        mDbHelper.open()
        val mPrefs = getSharedPreferences("Prefs", 0)
        val language:String = mPrefs.getString("lang", "")

        ayahListForCategory = mDbHelper.getAyahsForCategory(
            category, language
        )

        mDbHelper.close()

        val adapter = AjetetKategoriteListAdapter(this, ayahListForCategory)
        listView.adapter = adapter

        val context = this
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedAyah = ayahListForCategory!![position]


            val dialog = Dialog(this)
            dialog .setCanceledOnTouchOutside(true)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog .setContentView(R.layout.dialog_ayah_category)
            val ajeti = dialog .findViewById(R.id.txtAjeti) as TextView
            val surjadheajeti = dialog .findViewById(R.id.txtAjetiDheSurjaThot) as TextView

            ajeti.setText(selectedAyah.ajeti)
            surjadheajeti.setText(selectedAyah.surja + " " + selectedAyah.ajetet_id_text + " " + getString(R.string.ajetithot))

            dialog .show()




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
