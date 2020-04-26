package com.kerko.ne.kuran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.presenters.HomePresenter
import com.kerko.ne.kuran.views.HomeView
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class HomeFragment : MvpFragment<HomeView, HomePresenter>(), HomeView  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun createPresenter() = HomePresenter()

    val TAG = "HomeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init(context)
        writeCategoriesNumber()
    }

    fun writeCategoriesNumber(){
        val categoriesCount=presenter.getCategoriesNumber()
        tvSample.text = "Welcome, we do have $categoriesCount categories present!"
    }

}