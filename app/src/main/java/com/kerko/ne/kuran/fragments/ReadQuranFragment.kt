package com.kerko.ne.kuran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.presenters.HomePresenter
import com.kerko.ne.kuran.presenters.ReadQuranPresenter
import com.kerko.ne.kuran.views.ReadQuranView

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class ReadQuranFragment: MvpFragment<ReadQuranView, ReadQuranPresenter>(), ReadQuranView  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_read_quran, container, false)
    }


    override fun createPresenter() = ReadQuranPresenter()


    val TAG = "ReadQuranFragment"


    fun myOnKeyDown(key_code: Int) {
        //do whatever you want here
    }
}