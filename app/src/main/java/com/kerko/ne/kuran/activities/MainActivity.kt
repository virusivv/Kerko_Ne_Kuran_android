package com.kerko.ne.kuran.activities

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.kerko.ne.kuran.GeneralFunctions
import com.kerko.ne.kuran.QuranApplication
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.adapters.MainPagerAdapter
import com.kerko.ne.kuran.fragments.HomeFragment
import com.kerko.ne.kuran.fragments.ReadQuranFragment
import com.kerko.ne.kuran.fragments.SearchFragment
import com.kerko.ne.kuran.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class MainActivity : AppCompatActivity() {

    private val fragmentList = arrayListOf<Fragment>(
        HomeFragment(),
        SearchFragment(),
        ReadQuranFragment(),
        SettingsFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val background = resources.getDrawable(R.drawable.background_gradient_small)
        GeneralFunctions.setBackgroundToStatusBarAndNavigationBar(window, this, background)

        setupClickListeners()
        initFragments()

        if (QuranApplication.instance.isAppRunningForTheFirstTime()) {
            //Show a guid how to use app

            QuranApplication.instance.setAppRunningFirstTime()
        }

    }

    override fun attachBaseContext(ctx: Context) {
        super.attachBaseContext(QuranApplication.instance.baseContext(ctx))
    }

    private fun initFragments() {
        val fragmentManager = this.supportFragmentManager
        fragmentManager.let {
            val pagerAdapter = MainPagerAdapter(it, fragmentList)
            mainViewPager.adapter = pagerAdapter

            mainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) { selectItem(position) }

                override fun onPageScrollStateChanged(state: Int) {}
            })
        }
        navigationItemClicked(0)
    }

    private fun setupClickListeners() {
        niHome.setOnClickListener { navigationItemClicked(0) }
        niAsk.setOnClickListener { navigationItemClicked(1) }
        niRead.setOnClickListener { navigationItemClicked(2) }
        niSettings.setOnClickListener { navigationItemClicked(3) }
    }

    private fun navigationItemClicked(position: Int) {
        mainViewPager.currentItem = position
        selectItem(position)
    }

    private fun selectItem(position: Int) {
        niHome.itemClicked(position == 0)
        niAsk.itemClicked(position == 1)
        niRead.itemClicked(position == 2)
        niSettings.itemClicked(position == 3)
    }

    override fun onBackPressed() {
        if(mainViewPager.currentItem==1) {
           val overrideBack = (fragmentList[1] as SearchFragment).onBackPressed()
            if(!overrideBack)
                return super.onBackPressed()
        }
        else
            return super.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    public fun goToCategory(categoryId:Int, categoryText:String){
        mainViewPager.currentItem = 1
        selectItem(1)
        (fragmentList[1] as SearchFragment).clickedCategory(categoryId, categoryText)
    }
}
