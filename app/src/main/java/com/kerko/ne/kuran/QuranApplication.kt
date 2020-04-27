package com.kerko.ne.kuran

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.kerko.ne.kuran.enums.LanguageEnum
import com.kerko.ne.kuran.enums.QuranLanguagesEnum

/**
 * Created by Ardian Ahmeti on 04/26/2020
 **/
class QuranApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        lateinit var instance: QuranApplication

    }

    private fun getSharedPreferences() : SharedPreferences {
        return getSharedPreferences(Constants.QURAN_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setFontSize(size: Int) {
        getSharedPreferences().edit().putInt(Constants.FONT_PREF, size).apply()
    }

    fun getFontSize(): Int {
        return getSharedPreferences().getInt(Constants.FONT_PREF, Constants.DEFAULT_FONT_SIZE)
    }

    fun setLanguage(language: LanguageEnum) {
        getSharedPreferences().edit().putString(Constants.LANGUAGE_PREF, language.id()).apply()
    }

    fun getLanguage(): LanguageEnum? {
        return LanguageEnum.fromCanonicalForm(getSharedPreferences().getString(Constants.LANGUAGE_PREF, Constants.DEFAULT_LANGUAGE))
    }

    fun setQuranLanguage(language: QuranLanguagesEnum) {
        getSharedPreferences().edit().putString(Constants.QURAN_LANGUAGE_PREF, language.id()).apply()
    }

    fun getQuranLanguage(): QuranLanguagesEnum? {
        return QuranLanguagesEnum.fromCanonicalForm(getSharedPreferences().getString(Constants.QURAN_LANGUAGE_PREF, Constants.DEFAULT_LANGUAGE))
    }


}