package com.kerko.ne.kuran

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import com.kerko.ne.kuran.Constants.APP_FIRST_TIME
import com.kerko.ne.kuran.enums.LanguageEnum
import com.kerko.ne.kuran.enums.QuranLanguagesEnum
import java.util.*
/**
 * Created by Ardian Ahmeti on 04/26/2020
 **/
class QuranApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        firstSetup()
    }

    companion object {
        lateinit var instance: QuranApplication
    }

    fun firstSetup() {
        if(isAppRunningForTheFirstTime()) {
            setDefaultLanguage()
            setAppRunForTheFirstTime()
        }
    }

    private fun getSharedPreferences() : SharedPreferences {
        return getSharedPreferences(Constants.QURAN_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun isAppRunningForTheFirstTime(): Boolean {
        return getSharedPreferences().getBoolean(APP_FIRST_TIME, true)
    }

    private fun setAppRunForTheFirstTime() {
        getSharedPreferences().edit().putBoolean(APP_FIRST_TIME, false).apply()
    }

    fun setFontSize(size: Int) {
        getSharedPreferences().edit().putInt(Constants.FONT_PREF, size).apply()
    }

    fun getFontSize(): Int {
        return getSharedPreferences().getInt(Constants.FONT_PREF, Constants.DEFAULT_FONT_SIZE)
    }

    private fun setDefaultLanguage() {
        Locale.setDefault(Locale(LanguageEnum.Albanian.identificator))
    }

    fun setLanguage(language: LanguageEnum) {
        val locale = Locale(language.identificator)
        val config =  Configuration(instance.resources.configuration)
        config.setLocale(locale)

        instance.baseContext.resources.updateConfiguration(config, instance.baseContext.resources.displayMetrics);
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

