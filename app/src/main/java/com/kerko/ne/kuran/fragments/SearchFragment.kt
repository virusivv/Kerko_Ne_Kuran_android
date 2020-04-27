package com.kerko.ne.kuran.fragments

import Models.CategoriesModel
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.dialogs.AyahForCategoryDialog
import com.kerko.ne.kuran.adapters.AyahsForCategoriesAdapter
import com.kerko.ne.kuran.adapters.CategoriesAdapter
import com.kerko.ne.kuran.presenters.SearchPresenter
import com.kerko.ne.kuran.views.SearchView
import kotlinx.android.synthetic.main.fragment_search.*


/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class SearchFragment : MvpFragment<SearchView, SearchPresenter>(), SearchView {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun createPresenter() = SearchPresenter()

    val TAG = "SearchFragment"
    lateinit var selectedCategory: CategoriesModel
    var isAyahsListVisible: Boolean = false
    var isAyahsDialogVisible: Boolean = false
    var searchText: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etSearchText.visibility = View.VISIBLE
        tvCategoryAyahsForCategories.visibility = View.INVISIBLE
        presenter.init(context)
        etSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { callGetCategories() }
        })
        callGetCategories()
    }

    fun callGetCategories() {
        searchText = (etSearchText).text.toString()
        getCategories()
    }

    fun getCategories() {
        context?.let {
            val mPrefs = context?.getSharedPreferences("Prefs", 0)
            val language: String = "sq"//mPrefs.getString("lang", "")
            val categoriesListObject = presenter.getCategoriesList(searchText, language)
            val adapter = CategoriesAdapter(
                it,
                categoriesListObject
            )
            listCategoryCategories.adapter = adapter
            listCategoryCategories.setOnItemClickListener { _, _, position, _ ->
                selectedCategory = categoriesListObject!![position]
                clickedCategory(selectedCategory.id, selectedCategory.category)
            }
        }

    }

    fun clickedCategory(categoryId: Int, categoryText: String) {
        tvCategoryAyahsForCategories.text = categoryText
        tvCategoryAyahsForCategories.visibility = View.VISIBLE
        etSearchText.visibility = View.INVISIBLE
        isAyahsListVisible = true
        getAyahsForSelectedCategory(categoryId)
    }

    fun getAyahsForSelectedCategory(selectedId: Int) {
        context?.let {
            val mPrefs = context?.getSharedPreferences("Prefs", 0)
            val language: String = "sq"//mPrefs.getString("lang", "")
            var ayahListForCategory = presenter.getAyahsForCategory(selectedId)
            val adapter = AyahsForCategoriesAdapter(it, ayahListForCategory)
            listCategoryCategories.adapter = adapter
            listCategoryCategories.setOnItemClickListener { _, _, position, _ ->
                val selectedAyah = ayahListForCategory!![position]
                isAyahsDialogVisible = true
                AyahForCategoryDialog(it, selectedAyah)
            }
        }
    }

    fun onBackPressed(): Boolean {
        if (isAyahsListVisible) {
            tvCategoryAyahsForCategories.visibility = View.INVISIBLE
            etSearchText.visibility = View.VISIBLE
            isAyahsListVisible = false
            callGetCategories()
            return true
        } else
            return false
    }
}