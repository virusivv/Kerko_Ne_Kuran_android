package kuran.ne.kerko.com.myapplication


import Helpers.DataBaseHelper
import java.util.Locale


import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
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

        setContentView(R.layout.activity_start)


        var language = ""
        val mSharedPreference = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val restoredText = "sq"//mSharedPreference.getString("lang", null)
        val mDbHelper = DataBaseHelper(this@StartActivity)
        if (restoredText == null || restoredText === "") {
            language = "sq"
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = prefs.edit()
            editor.putString("lang", language)
            editor.commit()
        } else {
            language = restoredText
        }
        mDbHelper.createDataBaseorigjinal()

        val locale = Locale(language)
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
            startActivity(Intent(this@StartActivity, MainMenuActivity::class.java))
            this.finish()

        } catch (cce: ClassCastException) {

        }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}
