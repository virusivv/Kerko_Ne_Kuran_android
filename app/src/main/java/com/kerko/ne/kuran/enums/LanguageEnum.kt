package com.kerko.ne.kuran.enums

/**
 * Created by Ardian Ahmeti on 04/26/2020
 **/
enum class LanguageEnum(val identificator: String, val title: String) {
    Albanian("sq", "Shqip"),
    English("en", "English"),
    Turkish("tr", "Türkçe"),
    German("de", "Deutsch");

    fun id(): String {
        return identificator
    }

    fun canonicalForm(): String {
        return title
    }

    companion object {
        fun fromCanonicalForm(identificator: String?) : LanguageEnum? {
            return values().firstOrNull { it.identificator == identificator }
        }
    }
}