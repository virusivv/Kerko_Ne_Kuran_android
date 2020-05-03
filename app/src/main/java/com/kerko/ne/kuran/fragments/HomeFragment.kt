package com.kerko.ne.kuran.fragments

import Models.HomeModel
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.activities.MainActivity
import com.kerko.ne.kuran.presenters.HomePresenter
import com.kerko.ne.kuran.views.HomeView
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class HomeFragment : MvpFragment<HomeView, HomePresenter>(), HomeView {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    lateinit var randomCategoryAndCount: HomeModel

    override fun createPresenter() = HomePresenter()

    val TAG = "HomeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init(context)
        writeCategoriesNumber()
        tvLinkToCategory.setOnClickListener {
            (activity as MainActivity?)?.let {
                it.goToCategory(randomCategoryAndCount.id, randomCategoryAndCount.category)
            }
        }
    }

    fun writeCategoriesNumber() {
        randomCategoryAndCount = presenter.getRandomCategoryAndCount()
        tvSample.text = getString(R.string.welcome_text).format(randomCategoryAndCount.count)

        tvLinkToCategory.text = getString(R.string.random_topic_text).format(randomCategoryAndCount.category)
    }

    fun myOnKeyDown(key_code: Int) {
        //do whatever you want here
    }

}