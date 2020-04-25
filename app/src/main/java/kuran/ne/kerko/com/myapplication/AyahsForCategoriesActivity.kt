package kuran.ne.kerko.com.myapplication

import Helpers.AyahsForCategoriesAdapter
import Helpers.CategoriesDS
import Models.AyahsForCategoriesModel
import Models.CategoriesModel
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.app.Dialog
import android.view.Window
import kotlinx.android.synthetic.main.activity_ayahs_for_categories.*
import kotlinx.android.synthetic.main.dialog_ayah_category.*


class AyahsForCategoriesActivity : AppCompatActivity() {

    var ayahListForCategory: List<AyahsForCategoriesModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayahs_for_categories)

        val mPrefs = getSharedPreferences("Prefs", 0)
        val language:String = "sq"//mPrefs.getString("lang", "")

        var category:CategoriesModel = intent.extras.get("category") as CategoriesModel
        tvCategoryAyahsForCategories.setText(category.category)

        val mDbHelper = CategoriesDS(this)

        mDbHelper.open()

        ayahListForCategory = mDbHelper.getAyahsForCategory(
            category, language
        )

        mDbHelper.close()

        val adapter = AyahsForCategoriesAdapter(this, ayahListForCategory)
        listAyahsForCategories.adapter = adapter

        listAyahsForCategories.setOnItemClickListener { _, _, position, _ ->
            val selectedAyah = ayahListForCategory!![position]
            val dialog = Dialog(this)

            dialog.setCanceledOnTouchOutside(true)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_ayah_category)

            txtAjetiDheSurjaThot.text = selectedAyah.surah + " " + selectedAyah.ayah_ids_text + " " + getString(R.string.ajetithot)

            txtAjeti.setText(selectedAyah.ayah)

            dialog.show()
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Handle the back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, CategoriesActivity::class.java)
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
