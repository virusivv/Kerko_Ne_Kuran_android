package kuran.ne.kerko.com.myapplication

import Helpers.KuranDS
import Models.KuranModel
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.*
import kotlinx.android.synthetic.main.activity_lexo_kuran_new.*


class LexoKuranin : AppCompatActivity() {
    private lateinit var mDbHelper: KuranDS
    private lateinit var readType: String
    private var surahId: Int = 1
    private var ayahId: Int = 1
    private var selectedAyahId: Int = 1
    private var language: String = ""
    private var totalPages: Int = 1
    private var currentPage: Int = 1

    var surahListObject: List<String>? = null
    var ayahListObject: List<Int>? = null
    var kuranListObject: List<KuranModel>? = null

    var medown: MotionEvent? = null
    var meup: MotionEvent? = null
    var getxdown = 0f
    var getxup = 0f
    var getydown = 0f
    var getyup = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lexo_kuran_new)

        // TODO Swipe to change pages
        // TODO Fix the Arabic font size
        // TODO Fix the design
        // TODO Fix bismillah text


        val mPrefs = getSharedPreferences("Prefs", 0)
        language = "sq"//mPrefs.getString("lang", "")


        mDbHelper = KuranDS(this)
        mDbHelper.open()
        readType = intent.getStringExtra("Reading Type")

        if (readType.equals("arab"))
            language = "ar"
        else if (readType.equals("latin") && language == "tr")
            language = "latin_tr"
        else if (readType.equals("latin"))
            language = "latin"

        surahListObject = mDbHelper.getSurahs(language)
        mDbHelper.close()



        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, surahListObject)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cboSurah.setAdapter(aa)

        //var txt = findViewById(R.id.txtAjeti) as TextView
        //txt.setText(readType)
        cboSurah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                surahId = 1

                getAjetet()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                surahId = position + 1

                getAjetet()
            }

        }
    }
    override fun dispatchTouchEvent(me: MotionEvent): Boolean {
        // Call onTouchEvent of SimpleGestureFilter class
        // this.detector.onTouchEvent(me);
        if (me.action == MotionEvent.ACTION_DOWN) {
            getxdown = me.x
            getydown = me.y
        } else if (me.action == MotionEvent.ACTION_UP) {
            getxup = me.x
            getyup = me.y
            // this.detector.onFling(medown, meup, getxdown, getxup);
            if (Math.abs(getydown - getyup) < 250) {
                if (Math.abs(getxdown - getxup) > 150) // nese distanca eshte me
                // e madhe se 50 pixel
                // atehere vazhdo
                {
                    if (getxdown > getxup) // right to left
                    {
                        move("djatht")
                    } else if (getxdown < getxup) {
                        move("majt")
                    }
                }
            }
        }

        // this.detector.onDown(me);
        return super.dispatchTouchEvent(me)
    }

    private fun move(moving: String) {
        if (currentPage > 1 || currentPage < totalPages) {
            try {
                if (moving == "majt" && currentPage > 1) {
                    val firstItemBeforePageChange: Int = kuranListObject!![0].ajetiId
                    cboAyahs.setSelection(firstItemBeforePageChange-11)
                } else if (moving == "djatht" && currentPage < totalPages) {

                    val firstItemBeforePageChange: Int = kuranListObject!![kuranListObject!!.size-1].ajetiId
                    cboAyahs.setSelection(firstItemBeforePageChange)


                } else {
                    if (moving == "djatht") Toast.makeText(
                        this,
                        "resources.getString(R.string.nomorenext)",
                        Toast.LENGTH_SHORT
                    ).show() else if (moving == "majt") Toast.makeText(
                        this,
                        "resources.getString(R.string.nomoreprev)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (ex: java.lang.Exception) {
                cboAyahs.setSelection(0)
            }
        } else {
            if (moving == "djatht") Toast.makeText(
                this,
                "resources.getString(R.string.nomorenext)",
                Toast.LENGTH_SHORT
            ).show() else if (moving == "majt") Toast.makeText(
                this,
                "resources.getString(R.string.nomoreprev)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    fun scrollToAyah(linecount: Int) {
        try {
            val ajeti: Int = (cboAyahs).selectedItem as Int
            for (i in 0..linecount) {
                val numrifillim: Int = txtpershkrimet.getLayout().getLineStart(i)
                val numrifundit: Int = txtpershkrimet.getLayout().getLineEnd(i)
                val ajetiZgjedhun = "{$ajeti}"
                val rowstring: String = txtpershkrimet.getText().toString().substring(numrifillim, numrifundit)
                if(rowstring.contains(ajetiZgjedhun)){
                    val yToScroll = txtpershkrimet.layout.getLineTop(i)
                    scroll.smoothScrollTo(0, yToScroll)
                    break
                }
            }
        }
        catch (ex: Exception){
            ex.printStackTrace()
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

    fun startAnActivity(intentToGo: Intent) {
        startActivity(intentToGo)
        finish()
    }

    fun getAjetet() {

        mDbHelper.open()

        ayahListObject = mDbHelper.getAyahList(surahId, language)

        mDbHelper.close()

        totalPages = (ayahListObject!!.size/10)
        if(ayahListObject!!.size%10>0)
            totalPages++

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, ayahListObject)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)



        cboAyahs.setAdapter(aa)


        cboAyahs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                ayahId = 1
                selectedAyahId = 1
                getAjetetText()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ayahId = position + 1
                selectedAyahId = position + 1
                if (ayahId % 10 == 0)
                    ayahId = ayahId - 9
                else
                    ayahId = ayahId - (ayahId % 10 - 1)
                getAjetetText()
            }

        }
    }

    fun getAjetetText() {

        mDbHelper.open()

        var changedPage = false

        if (kuranListObject == null ||
            kuranListObject!!.isEmpty() ||
            kuranListObject!!.get(0).surjaId != surahId ||
            ayahId != kuranListObject!!.get(0).ajetiId) {
            kuranListObject = mDbHelper.get10AyahsForSurah(surahId, ayahId, language)
            changedPage = true
        }
        mDbHelper.close()

        currentPage=kuranListObject!![0].ajetiId/10
        if(kuranListObject!![0].ajetiId%10>0)
            currentPage++

        var ayahsText = ""
        for (item in kuranListObject!!) {
            var precode = ""
            var postcode = ""
            if (item.ajetiId == selectedAyahId) {
                precode = "<span style=\"color:#FF8000\">"
                postcode = "</span>"

            }

            ayahsText += precode + "{" + item.ajetiId + "}" + " " + item.ajeti + postcode
        }
        if(changedPage) {
            val anim = AlphaAnimation(1.0f, 0.0f)
            anim.duration = 200
            anim.repeatCount = 1
            anim.repeatMode = Animation.REVERSE

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {
                    txtpershkrimet.setText(Html.fromHtml(ayahsText))
                }
            })

            txtpershkrimet.startAnimation(anim)

        }
        else{
            txtpershkrimet.setText(Html.fromHtml(ayahsText))
        }
        txtpershkrimet.post {
            scrollToAyah(txtpershkrimet.getLineCount())
        }
    }

}
