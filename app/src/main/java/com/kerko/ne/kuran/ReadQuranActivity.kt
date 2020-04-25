package com.kerko.ne.kuran

import Helpers.QuranDS
import Models.QuranModel
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
import kotlinx.android.synthetic.main.activity_read_quran.*


class ReadQuranActivity : AppCompatActivity() {
    private lateinit var mDbHelper: QuranDS
    private lateinit var readType: String
    private var surahId: Int = 1
    private var ayahId: Int = 1
    private var selectedAyahId: Int = 1
    private var language: String = ""
    private var totalPages: Int = 1
    private var currentPage: Int = 1

    var surahListObject: List<String>? = null
    var ayahListObject: List<Int>? = null
    var quranListObject: List<QuranModel>? = null

    var getxdown = 0f
    var getxup = 0f
    var getydown = 0f
    var getyup = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_quran)

        // TODO Swipe to change pages
        // TODO Fix the Arabic font size
        // TODO Fix the design
        // TODO Fix bismillah text
        
        val mPrefs = getSharedPreferences("Prefs", 0)
        language = "sq"//mPrefs.getString("lang", "")
        
        mDbHelper = QuranDS(this)
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



        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, surahListObject)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cboSurahReadQuran.setAdapter(arrayAdapter)

        //var txt = findViewById(R.id.txtAjeti) as TextView
        //txt.setText(readType)
        cboSurahReadQuran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                surahId = 1

                getAyahs()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                surahId = position + 1

                getAyahs()
            }

        }
    }
    override fun dispatchTouchEvent(me: MotionEvent): Boolean {
        if (me.action == MotionEvent.ACTION_DOWN) {
            getxdown = me.x
            getydown = me.y
        } else if (me.action == MotionEvent.ACTION_UP) {
            getxup = me.x
            getyup = me.y
            if (Math.abs(getydown - getyup) < 250) {
                if (Math.abs(getxdown - getxup) > 150)
                {
                    if (getxdown > getxup) // right to left
                    {
                        move("right")
                    } else if (getxdown < getxup) {
                        move("left")
                    }
                }
            }
        }

        return super.dispatchTouchEvent(me)
    }

    private fun move(moving: String) {
        if (currentPage > 1 || currentPage < totalPages) {
            try {
                if (moving == "left" && currentPage > 1) {
                    val firstItemBeforePageChange: Int = quranListObject!![0].ayahId
                    cboAyahsReadQuran.setSelection(firstItemBeforePageChange-11)
                } else if (moving == "right" && currentPage < totalPages) {
                    val lastItemBeforePageChange: Int = quranListObject!![quranListObject!!.size-1].ayahId
                    cboAyahsReadQuran.setSelection(lastItemBeforePageChange)
                } else {
                    if (moving == "right") Toast.makeText(
                        this,
                        "resources.getString(R.string.nomorenext)",
                        Toast.LENGTH_SHORT
                    ).show() else if (moving == "left") Toast.makeText(
                        this,
                        "resources.getString(R.string.nomoreprev)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (ex: java.lang.Exception) {
                cboAyahsReadQuran.setSelection(0)
            }
        } else {
            if (moving == "right") Toast.makeText(
                this,
                "resources.getString(R.string.nomorenext)",
                Toast.LENGTH_SHORT
            ).show() else if (moving == "left") Toast.makeText(
                this,
                "resources.getString(R.string.nomoreprev)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun scrollToAyah(lineCount: Int) {
        try {
            val ayah: Int = (cboAyahsReadQuran).selectedItem as Int
            for (i in 0..lineCount) {
                val lineStartCharId: Int = tvAyahTextReadQuran.layout.getLineStart(i)
                val lineEndCharId: Int = tvAyahTextReadQuran.layout.getLineEnd(i)
                val selectedAyah = "{$ayah}"
                val rowString: String = tvAyahTextReadQuran.text.toString().substring(lineStartCharId, lineEndCharId)
                if(rowString.contains(selectedAyah)){
                    val yToScroll = tvAyahTextReadQuran.layout.getLineTop(i)
                    scroll.smoothScrollTo(0, yToScroll)
                    break
                }
            }
        }
        catch (ex: Exception){
        }
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

    fun startAnActivity(intentToGo: Intent) {
        startActivity(intentToGo)
        finish()
    }

    fun getAyahs() {

        mDbHelper.open()

        ayahListObject = mDbHelper.getAyahList(surahId, language)

        mDbHelper.close()

        totalPages = (ayahListObject!!.size/10)
        if(ayahListObject!!.size%10>0)
            totalPages++

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ayahListObject)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        cboAyahsReadQuran.setAdapter(arrayAdapter)

        cboAyahsReadQuran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                ayahId = 1
                selectedAyahId = 1
                getAyahsText()
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
                getAyahsText()
            }

        }
    }

    fun getAyahsText() {

        mDbHelper.open()

        var changedPage = false

        if (quranListObject == null ||
            quranListObject!!.isEmpty() ||
            quranListObject!![0].surahId != surahId ||
            ayahId != quranListObject!![0].ayahId) {
            quranListObject = mDbHelper.get10AyahsForSurah(surahId, ayahId, language)
            changedPage = true
        }
        mDbHelper.close()

        currentPage=quranListObject!![0].ayahId/10
        if(quranListObject!![0].ayahId%10>0)
            currentPage++

        var ayahsText = ""
        for (item in quranListObject!!) {
            var precode = ""
            var postcode = ""
            if (item.ayahId == selectedAyahId) {
                precode = "<span style=\"color:#FF8000\">"
                postcode = "</span>"
            }

            ayahsText += precode + "{" + item.ayahId + "}" + " " + item.ayah + postcode
        }

        if(changedPage) {
            //maybe different animation
        }

        val anim = AlphaAnimation(1.0f, 0.0f)
        anim.duration = 200
        anim.repeatCount = 1
        anim.repeatMode = Animation.REVERSE

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {
                tvAyahTextReadQuran.text = Html.fromHtml(ayahsText)
            }
        })

        tvAyahTextReadQuran.startAnimation(anim)

        tvAyahTextReadQuran.post {
            scrollToAyah(tvAyahTextReadQuran.lineCount)
        }
    }

}
