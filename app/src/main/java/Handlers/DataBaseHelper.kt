package Handlers

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.DeletedContacts
import android.util.Log

class DataBaseHelper(private val mContext: Context) : SQLiteOpenHelper(mContext, DB_NAME, null, 2) {
    private var mDataBase: SQLiteDatabase? = null

    fun setlang(lang: String) {
        DB_NAME = lang
    }

    init {
        DB_PATH = "/data/data/" + mContext.packageName + "/databases/"
    }// 1? its Database Version

    @Throws(IOException::class)
    fun createDataBaseorigjinal() {
        // If database not exists copy it from the assets

        val mDataBaseExist = checkDataBase()
        if (!mDataBaseExist) {
            this.readableDatabase
            this.close()
            try {
                // Copy the database from assests
                copyDataBase()
                Log.e(TAG, "createDatabase database created")
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }

        }
    }

    @Throws(IOException::class)
    fun createDataBase() {
        // If database not exists copy it from the assets

        val mDataBaseExist = checkDataBase()
        if (!mDataBaseExist) {
            this.readableDatabase
            this.close()
            try {
                // Copy the database from assests
                copyDataBase()
                Log.e(TAG, "createDatabase database created")
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }

        }
    }

    // Check that the database exists here: /data/data/your package/databases/Da
    // Name
    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        // Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists()
    }

    // Copy the database from assets
    @Throws(IOException::class)
    private fun copyDataBase() {
        val mInput = mContext.assets.open(DB_NAME)
        val outFileName = DB_PATH + DB_NAME
        val mOutput = FileOutputStream(outFileName)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        mLength = mInput.read(mBuffer)
        while (mLength > 0) {
            mOutput.write(mBuffer, 0, mLength)
            mLength = mInput.read(mBuffer)
        }
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    // Open the database, so we can query it
    @Throws(SQLException::class)
    fun openDataBase(): Boolean {
        val mPath = DB_PATH + DB_NAME
        // Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(
            mPath, null,
            SQLiteDatabase.CREATE_IF_NECESSARY
        )
        // mDataBase = SQLiteDatabase.openDatabase(mPath, null,
        // SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null
    }

    @Synchronized
    override public fun close() {
        if (mDataBase != null)
            mDataBase!!.close()
        super.close()
    }

    override fun onCreate(arg0: SQLiteDatabase) {
        // TODO Auto-generated method stub

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub

    }

    companion object {
        private val TAG = "DataBaseHelper" // Tag just for the LogCat
        // window
        // destination path (location) of our database on device
        private var DB_PATH = ""
        private var DB_NAME = "dbKuran.sqlite"// Database name
    }

}
