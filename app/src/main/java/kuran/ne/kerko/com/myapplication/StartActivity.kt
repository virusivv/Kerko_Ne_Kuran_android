package kuran.ne.kerko.com.myapplication


import java.util.Locale


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.preference.PreferenceManager

class StartActivity : Activity() {

   /* private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager
                .activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }*/

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)


        var gjuha = ""
        val mSharedPreference = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val restoredText = mSharedPreference.getString("lang", null)
        val mDbHelper = TestAdapter(this@StartActivity)
        if (restoredText == null || restoredText === "") {
            gjuha = "sq"
            val prefs2 = PreferenceManager.getDefaultSharedPreferences(this)
            val editor2 = prefs2.edit()
            editor2.putString("lang", gjuha)
            editor2.commit()
        } else {
            gjuha = restoredText
        }
        mDbHelper.createDatabaseorigjinal()

        val locale = Locale(gjuha)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.resources.updateConfiguration(
            config,
            this.resources.displayMetrics
        )


        // Editor editor = pref.edit();
        try {
            /*if (isNetworkAvailable) {

                val checkDbTask = CheckDbTask(
                    this@StartActivity,
                    "http://thegeekyland.com/KerkoNeKuran/KerkoNeKuranUpdater.php"
                )
                checkDbTask.execute("", "")
                if (checkDbTask.getStatus() === AsyncTask.Status.FINISHED) {
                    startActivity(
                        Intent(
                            this@StartActivity,
                            MainActivity::class.java
                        )
                    )
                    this.finish()
                }
            } else {
                startActivity(Intent(this@StartActivity, MainActivity::class.java))
                this.finish()
            }*/
            startActivity(Intent(this@StartActivity, MainMenu::class.java))
            this.finish()

        } catch (cce: ClassCastException) {

        }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}
