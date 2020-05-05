package com.kerko.ne.kuran.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kerko.ne.kuran.QuranApplication
import com.kerko.ne.kuran.R
import com.kerko.ne.kuran.helpers.DataBaseHelper
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Ardian Ahmeti on 04/25/2020
 **/
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val mDbHelper = DataBaseHelper(this)

        mDbHelper.createDataBaseorigjinal()


        Observable.timer(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }

        val locale = Locale(QuranApplication.instance.getLanguage()?.identificator)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.resources.updateConfiguration(
            config,
            this.resources.displayMetrics
        )
    }
}