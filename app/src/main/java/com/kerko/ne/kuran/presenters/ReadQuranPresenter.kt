package com.kerko.ne.kuran.presenters

import Models.QuranModel
import android.content.Context
import android.service.carrier.CarrierIdentifier
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.kerko.ne.kuran.helpers.QuranDS
import com.kerko.ne.kuran.views.ReadQuranView

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class ReadQuranPresenter : MvpBasePresenter<ReadQuranView>() {
    val TAG = "ReadQuranPresenter"

    private var mDbHelper: QuranDS? = null


    fun init(context: Context?) {
        context?.let {
            mDbHelper = QuranDS(it)
        }
    }

    fun getSurahs(): List<String> {
        lateinit var surahsListObject: List<String>
        mDbHelper?.let {
            it.open()
            surahsListObject = it.getSurahs()
            it.close()
        }
        return surahsListObject
    }

    fun getAyahsNumbers(surahId: Int): List<Int> {
        lateinit var ayahsListObject: List<Int>
        mDbHelper?.let {
            it.open()
            ayahsListObject = it.getAyahList(surahId)
            it.close()
        }
        return ayahsListObject
    }

    fun get10AyahsForSurah(surahId: Int, ayahId:Int, languageIdentifier: String): List<QuranModel> {
        lateinit var ayahsListObject: List<QuranModel>
        mDbHelper?.let {
            it.open()
            ayahsListObject = it.get10AyahsForSurah(surahId,ayahId,languageIdentifier)
            it.close()
        }
        return ayahsListObject
    }
}