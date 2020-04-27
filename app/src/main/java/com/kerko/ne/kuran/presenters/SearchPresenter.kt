package com.kerko.ne.kuran.presenters

import Models.AyahsForCategoriesModel
import Models.CategoriesModel
import android.content.Context
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.kerko.ne.kuran.helpers.CategoriesDS
import com.kerko.ne.kuran.views.SearchView

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class SearchPresenter : MvpBasePresenter<SearchView>() {
    val TAG = "SearchPresenter"

    private var mDbHelper: CategoriesDS? = null


    fun init(context: Context?) {
        context?.let {
            mDbHelper = CategoriesDS(it)
        }
    }

    fun getCategoriesList(searchText: String, language: String): List<CategoriesModel> {
        lateinit var categoriesListObject: List<CategoriesModel>
        mDbHelper?.let {
            it.open()
            categoriesListObject = it.getCategoriesBasedOnSearchText(searchText)
            it.close()
        }
        return categoriesListObject
    }

    fun getAyahsForCategory(category: Int): List<AyahsForCategoriesModel> {
        lateinit var ayahsForCategoryListObject: List<AyahsForCategoriesModel>
        mDbHelper?.let {
            it.open()
            ayahsForCategoryListObject = it.getAyahsForCategory(category)
            it.close()
        }
        return ayahsForCategoryListObject
    }
}