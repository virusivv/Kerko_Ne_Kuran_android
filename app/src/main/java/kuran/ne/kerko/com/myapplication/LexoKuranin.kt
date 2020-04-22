package kuran.ne.kerko.com.myapplication

import Helpers.KategoriteDS
import Helpers.KuranDS
import Models.KategoriteModel
import Models.KuranModel
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import android.view.View
import android.widget.*

class LexoKuranin : AppCompatActivity() {
    private lateinit var mDbHelper: KuranDS
    private lateinit var readType: String
    private var surahId: Int = 1
    private var ayahId: Int = 1
    private var selectedAyahId: Int = 1
    private var language: String = ""

    var surahListObject: List<String>? = null
    var ayahListObject: List<Int>? = null
    var kuranListObject: List<KuranModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lexo_kuranin)

        // TODO Swipe to change pages
        // TODO on Ayah Change scroll to ayah
        // TODO Fix the Arabic font size
        // TODO Fix the design
        // TODO Fix bismillah text


        val mPrefs = getSharedPreferences("Prefs", 0)
        language = mPrefs.getString("lang", "")


        mDbHelper = KuranDS(this)
        mDbHelper.open()
        readType = intent.getStringExtra("Reading Type")

        if(readType.equals("arab"))
            language="ar"
        else if(readType.equals("latin") && language == "tr")
            language = "latin_tr"
        else if(readType.equals("latin"))
            language = "latin"

        surahListObject=mDbHelper.getSurahs(language)
        mDbHelper.close()



        var cboSurah=findViewById(R.id.cboSurah) as Spinner;

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, surahListObject)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cboSurah!!.setAdapter(aa)

        //var txt = findViewById(R.id.txtAjeti) as TextView
        //txt.setText(readType)
        cboSurah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                surahId=1

                getAjetet()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                surahId = position + 1

                getAjetet()
            }

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

    fun getAjetet() {

        mDbHelper.open()

        ayahListObject = mDbHelper.getAyahList(surahId, language)

        mDbHelper.close()

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, ayahListObject)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        var cboAyahs=findViewById(R.id.cboAyahs) as Spinner;

        cboAyahs!!.setAdapter(aa)


        cboAyahs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                ayahId = 1
                selectedAyahId = 1
                getAjetetText()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ayahId = position + 1
                selectedAyahId = position + 1
                if(ayahId % 10 == 0)
                    ayahId = ayahId - 9
                else
                    ayahId = ayahId - (ayahId % 10 - 1)
                getAjetetText()
            }

        }
    }

    fun getAjetetText() {

        mDbHelper.open()

        if(kuranListObject==null || kuranListObject!!.isEmpty() || kuranListObject!!.get(0).surjaId != surahId || ayahId!=kuranListObject!!.get(0).ajetiId)
            kuranListObject = mDbHelper.get10AyahsForSurah(surahId, ayahId, language)

        mDbHelper.close()

        var ayahsText:String =""
        for(item in kuranListObject!!) {
            var precode: String = ""
            var postcode: String = ""
            if (item.ajetiId == selectedAyahId) {
                precode="<span style=\"color:#FF8000\">"
                postcode="</span>"

            }

            ayahsText+=precode+"{"+item.ajetiId+"}"+" " + item.ajeti+postcode
        }

        var txt = findViewById(R.id.txtpershkrimet) as TextView
        txt.setText(Html.fromHtml(ayahsText))

    }

}
