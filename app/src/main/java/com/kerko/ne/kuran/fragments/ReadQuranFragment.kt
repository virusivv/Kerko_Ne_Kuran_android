package com.kerko.ne.kuran.fragments

import Models.QuranModel
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.kerko.ne.kuran.QuranApplication
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.enums.QuranLanguagesEnum
import com.kerko.ne.kuran.presenters.ReadQuranPresenter
import com.kerko.ne.kuran.views.ReadQuranView
import kotlinx.android.synthetic.main.fragment_read_quran.*
import java.util.ArrayList


/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class ReadQuranFragment : MvpFragment<ReadQuranView, ReadQuranPresenter>(), ReadQuranView {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_read_quran, container, false)
    }


    override fun createPresenter() = ReadQuranPresenter()

    val TAG = "ReadQuranFragment"
    var languagesList: List<String> = ArrayList<String>()
    private var totalPages: Int = 1
    private var currentPage: Int = 1
    private var ayahId: Int = 1
    private var selectedAyahId: Int = 1
    var quranListObject: List<QuranModel> = ArrayList<QuranModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun initButtonListeners() {
        btnNext.setOnClickListener {
            spinnerAyah.setSelection(quranListObject[quranListObject.size-1].ayahId)
        }
        btnPrevious.setOnClickListener {
            spinnerAyah.setSelection(quranListObject[0].ayahId-11)
        }
    }


    fun initSpinnerListeners() {

        spinnerReadLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerReadLanguage.setSelection(0)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                QuranLanguagesEnum.fromTitle(spinnerReadLanguage.selectedItem.toString())?.let {
                    QuranApplication.instance.setQuranLanguage(it)
                }
                initSpinnerAyahList()
            }
        }


        spinnerSurah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerSurah.setSelection(0)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                initSpinnerAyahList()
            }
        }

        spinnerAyah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedAyahId = 1
                ayahId = 1
                spinnerAyah.setSelection(0)
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            presenter.init(context)
            initSpinnerQuranLanguages()

            initSpinnerListeners()
            initButtonListeners()
        }
    }

    fun initSpinnerQuranLanguages() {
        context?.let {
            languagesList = QuranLanguagesEnum.toStringList();
            val arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_item, languagesList)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerReadLanguage.adapter = arrayAdapter

            val quranLang: QuranLanguagesEnum? = QuranApplication.instance.getQuranLanguage()
            quranLang?.let {
                val langPosition: Int = QuranLanguagesEnum.getPositionOnSpinner(quranLang)
                spinnerReadLanguage.setSelection(langPosition)
            }

            initSpinnerSurahList()
        }
    }

    fun initSpinnerSurahList() {
        context?.let {
            var surahsListObject = presenter.getSurahs()
            val arrayAdapter =
                ArrayAdapter(it, android.R.layout.simple_spinner_item, surahsListObject)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSurah.adapter = arrayAdapter
            initSpinnerAyahList()
        }
    }

    fun initSpinnerAyahList() {
        context?.let {
            var AyahListObject = presenter.getAyahsNumbers(spinnerSurah.selectedItemPosition + 1)
            val arrayAdapter =
                ArrayAdapter(it, android.R.layout.simple_spinner_item, AyahListObject)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerAyah.adapter = arrayAdapter

            totalPages = (AyahListObject!!.size / 10)
            if (AyahListObject!!.size % 10 > 0)
                totalPages++
            getAyahsText()
        }
    }

    fun paginationButtonsVisibility() {
        if (currentPage > 1)
            btnPrevious.visibility = View.VISIBLE
        else
            btnPrevious.visibility = View.INVISIBLE

        if (currentPage < totalPages)
            btnNext.visibility = View.VISIBLE
        else
            btnNext.visibility = View.INVISIBLE
    }

    fun getAyahsText() {
        var changedPage = false

        val selectedLanguage: QuranLanguagesEnum? =
            QuranLanguagesEnum.fromTitle(spinnerReadLanguage.selectedItem.toString())
        selectedLanguage?.let {
            val selectedLanguageId = selectedLanguage.identificator
            if (quranListObject == null ||
                quranListObject!!.isEmpty() ||
                quranListObject!![0].surahId != spinnerSurah.selectedItemPosition + 1 ||
                ayahId != quranListObject!![0].ayahId ||
                quranListObject!![0].language != selectedLanguageId
            ) {
                quranListObject = presenter.get10AyahsForSurah(
                    spinnerSurah.selectedItemPosition + 1,
                    ayahId,
                    selectedLanguageId
                )
                changedPage = true
            }
            currentPage = quranListObject!![0].ayahId / 10
            if (quranListObject!![0].ayahId % 10 > 0)
                currentPage++

            var ayahsText = ""
            for (item in quranListObject!!) {
                var precode = ""
                var postcode = ""
                if (item.ayahId == spinnerAyah.selectedItemPosition + 1) {
                    precode = "<span style=\"color:#FF8000\">"
                    postcode = "</span>"
                }

                ayahsText += precode + "{" + item.ayahId + "}" + " " + item.ayah + postcode
            }

            if (changedPage) {
                val anim = AlphaAnimation(1.0f, 0.0f)
                anim.duration = 200
                anim.repeatCount = 1
                anim.repeatMode = Animation.REVERSE

                anim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationEnd(animation: Animation?) {}
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationRepeat(animation: Animation?) {
                        tvAyahTextReadQuran.text = Html.fromHtml(ayahsText)
                        tvAyahTextReadQuran.post {
                            scrollToAyah(tvAyahTextReadQuran.lineCount)
                        }
                    }
                })

                tvAyahTextReadQuran.startAnimation(anim)
            } else {
                tvAyahTextReadQuran.text = Html.fromHtml(ayahsText)
                tvAyahTextReadQuran.post {
                    scrollToAyah(tvAyahTextReadQuran.lineCount)
                }
            }
        }
    }

    fun scrollToAyah(lineCount: Int) {
        try {
            val ayah: Int = (spinnerAyah).selectedItem as Int
            val selectedAyah = "{$ayah}"
            for (i in 0..lineCount) {
                val lineStartCharId: Int = tvAyahTextReadQuran.layout.getLineStart(i)
                val lineEndCharId: Int = tvAyahTextReadQuran.layout.getLineEnd(i)
                val rowString: String =
                    tvAyahTextReadQuran.text.toString().substring(lineStartCharId, lineEndCharId)
                if (rowString.contains(selectedAyah)) {
                    val yToScroll = tvAyahTextReadQuran.layout.getLineTop(i)
                    scroll.smoothScrollTo(0, yToScroll)
                    break
                }
            }
        } catch (ex: Exception) {
        }
        paginationButtonsVisibility()
    }
}