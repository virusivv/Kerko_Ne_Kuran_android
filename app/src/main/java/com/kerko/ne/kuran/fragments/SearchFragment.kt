package com.kerko.ne.kuran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.presenters.HomePresenter
import com.kerko.ne.kuran.presenters.SearchPresenter
import com.kerko.ne.kuran.views.SearchView

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class SearchFragment: MvpFragment<SearchView, SearchPresenter>(), SearchView  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun createPresenter() = SearchPresenter()

    val TAG = "SearchFragment"
}