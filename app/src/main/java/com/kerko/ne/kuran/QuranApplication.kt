package com.kerko.ne.kuran

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import android.view.WindowManager
import com.kerko.ne.kuran.Constants.APP_FIRST_TIME
import com.kerko.ne.kuran.enums.LanguageEnum
import com.kerko.ne.kuran.enums.QuranLanguagesEnum
import java.util.*

/**
 * Created by Ardian Ahmeti on 04/26/2020
 **/
class QuranApplication : Application() {

    private val TAG = javaClass.simpleName

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        lateinit var instance: QuranApplication
    }

    /**
     * Does the necessary modifications when the app is run for the first time
     * For now, it only sets a shared pref
     * */
    fun setAppRunningFirstTime() {
        getSharedPreferences().edit().putBoolean(APP_FIRST_TIME, false).apply()
    }

    /**
     * This function is used to implement the i18n (multi-language)
     * */
    fun baseContext(context: Context) : Context {
        val config = Configuration(resources.configuration)
        val metrics = resources.displayMetrics

        getLanguage()?.let {
            val locale = Locale(it.identificator)
            Locale.setDefault(locale)
            config.setLocale(locale)
        }

        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = config.fontScale * metrics.density
        config.fontScale = getFontSize().toFloat()

        resources.updateConfiguration(config, metrics)
        return this
    }

    /**
     * A general function that returns this app's specific shared preference instance
     * */
    private fun getSharedPreferences(): SharedPreferences {
        return getSharedPreferences(Constants.QURAN_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Checks if app is running for the first time on client's device
     * */
    fun isAppRunningForTheFirstTime(): Boolean {
        return getSharedPreferences().getBoolean(APP_FIRST_TIME, true)
    }

    /**
     * Saves the chosen font size to shared preferences
     *
     * @param size -> the chosen font size
     * */
    fun setFontScale(scale: Float) {
        val config = Configuration(resources.configuration)
        val metrics = resources.displayMetrics

        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = config.fontScale * metrics.density
        config.fontScale = scale

        resources.updateConfiguration(config, metrics)

        getSharedPreferences().edit().putFloat(Constants.FONT_PREF, scale).apply()
    }

    /**
     * Returns current font size
     * */
    fun getFontSize(): Float {
        return getSharedPreferences().getFloat(Constants.FONT_PREF, Constants.DEFAULT_FONT_SCALE)
    }

    /**
     * Sets the language of the application
     * This is used to load the strings from string.xml files for different languages we support
     * Returns the instance of this application because we need it when we attach base context to activity
     *
     * @param language -> the chosen language to set
     * */
    fun setLanguage(language: LanguageEnum): Context {
        Log.d(TAG, "Setting language to $language")
        val locale = Locale(language.identificator)
        val config = Configuration(resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        getSharedPreferences().edit().putString(Constants.LANGUAGE_PREF, language.id()).apply()

        return this
    }

    /**
     * Returns the current language of the application
     * */
    fun getLanguage(): LanguageEnum? {
        return LanguageEnum.fromCanonicalForm(
            getSharedPreferences().getString(
                Constants.LANGUAGE_PREF,
                Constants.DEFAULT_LANGUAGE
            )
        )
    }

    fun setQuranLanguage(language: QuranLanguagesEnum) {
        getSharedPreferences().edit().putString(Constants.QURAN_LANGUAGE_PREF, language.id())
            .apply()
    }

    fun setSelectedSurah(surahId: Int) {
        getSharedPreferences().edit().putInt(Constants.SELECTED_SURAH, surahId)
            .apply()
    }

    fun setSelectedAyah(ayahId: Int) {
        getSharedPreferences().edit().putInt(Constants.SELECTED_AYAH, ayahId)
            .apply()
    }

    fun getSelectedSurah(): Int {
        return getSharedPreferences().getInt(
                Constants.SELECTED_SURAH,
                0
            )
    }

    fun getSelectedAyah(): Int {
        return getSharedPreferences().getInt(
            Constants.SELECTED_AYAH,
            0
        )
    }

    fun getQuranLanguage(): QuranLanguagesEnum? {
        return QuranLanguagesEnum.fromCanonicalForm(
            getSharedPreferences().getString(
                Constants.QURAN_LANGUAGE_PREF,
                Constants.DEFAULT_LANGUAGE
            )
        )
    }


}
