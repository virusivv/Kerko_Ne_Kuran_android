package com.kerko.ne.kuran.presenters

import android.content.Context
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.kerko.ne.kuran.helpers.CategoriesDS
import com.kerko.ne.kuran.views.HomeView

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class HomePresenter : MvpBasePresenter<HomeView>() {
    val TAG = "HomePresenter"

    private var mDbHelper: CategoriesDS? = null


    fun init(context: Context?) {
        context?.let {
            mDbHelper = CategoriesDS(it)
        }
    }

    fun getCategoriesNumber(): Int {
        var categoriesCount: Int = 0
        mDbHelper?.let {
            it.open()
            categoriesCount = it.getCategoriesCount()
            it.close()
        }
        return categoriesCount
    }

}