package com.kerko.ne.kuran.enums

import com.kerko.ne.kuran.QuranApplication
import java.util.ArrayList

/**
 * Created by Ibrahim Vasija on 26/04/2020
 **/
enum class QuranLanguagesEnum(val identificator: String, val title: String) {
    Albanian("sq", "Shqip"),
    English("en", "English"),
    Turkish("tr", "Türkçe"),
    Latin("latin", "Latin"),
    LatinTurkish("latin_tr", "Latin Türkçe"),
    Arabic("ar", "Arabic"),
    German("de", "Deutsch");

    fun id(): String {
        return identificator
    }

    fun canonicalForm(): String {
        return title
    }


    companion object {
        fun fromCanonicalForm(identificator: String?) : QuranLanguagesEnum? {
            return values().firstOrNull { it.identificator == identificator }
        }

        fun fromTitle(title: String?) : QuranLanguagesEnum? {
            return values().firstOrNull { it.title == title }
        }

        fun toStringList(): List<String> {
            val returnList: ArrayList<String> = ArrayList<String>()
            val language = QuranApplication.instance.getLanguage()
            language?.let {
                returnList.add(language.canonicalForm())
                if(language.identificator.equals(LanguageEnum.Turkish.identificator))
                    returnList.add(QuranLanguagesEnum.LatinTurkish.canonicalForm())
                else
                    returnList.add(QuranLanguagesEnum.Latin.canonicalForm())
                returnList.add(QuranLanguagesEnum.Arabic.canonicalForm())
            }
            return returnList
        }

        fun getPositionOnSpinner(lang: QuranLanguagesEnum): Int {
            if(lang == QuranLanguagesEnum.Arabic)
                return 2
            else if(lang == QuranLanguagesEnum.LatinTurkish || lang == QuranLanguagesEnum.Latin)
                return 1
            return 0
        }
    }
}