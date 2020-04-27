package com.kerko.ne.kuran.helpers

import Models.HomeModel
import java.util.ArrayList


import Models.QuranModel
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.kerko.ne.kuran.QuranApplication

/**
 * Created by Ibrhaim Vasija on 26/04/2020
 **/
class QuranDS(private val mContext: Context) {
    private var mDb: SQLiteDatabase? = null
    private val mDbHelper: DataBaseHelper


    //            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
    //            throw mSQLException;
    val dbVersion: Int
        get() {
            try {
                val sql = "SELECT * FROM tbl_db_version"
                val mCur = mDb!!.rawQuery(sql, null)
                if (mCur != null) {
                    mCur.moveToFirst()
                    mDbHelper.close()
                    return mCur.getInt(0)

                }
                return -1
            } catch (mSQLException: SQLException) {
                return -1
            }

        }

    init {
        mDbHelper = DataBaseHelper(mContext)
    }


    @Throws(SQLException::class)
    fun open(): QuranDS {
        try {
            mDbHelper.openDataBase()
            mDbHelper.close()
            mDb = mDbHelper.readableDatabase
        } catch (mSQLException: SQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString())
            throw mSQLException
        }

        return this
    }
    fun getSurahs():List<String>{

        val language = QuranApplication.instance.getLanguage()
        val returnList:ArrayList<String> = ArrayList<String>()
        val sql =
            ("select distinct(surja) from kuran_"+language?.identificator+" order by surja_id;")
        var cursor: Cursor? = null
        try{
            cursor = mDb?.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            mDb?.execSQL(sql)
            return ArrayList()
        }
        if (cursor?.moveToFirst()!!) {
            do {
                returnList.add(cursor.getString(cursor.getColumnIndex("surja")))
            } while (cursor.moveToNext())
        }
        return returnList
    }

    fun getAyahList(surjaId: Int):List<Int>{
        val language = QuranApplication.instance.getLanguage()

        val returnList:ArrayList<Int> = ArrayList<Int>()
        val sql =
            ("select ajeti_id from kuran_"+language?.identificator+" where surja_id = "+surjaId)
        var cursor: Cursor? = null
        try{
            cursor = mDb?.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            mDb?.execSQL(sql)
            return ArrayList()
        }
        if (cursor?.moveToFirst()!!) {
            do {
                returnList.add(cursor.getInt(cursor.getColumnIndex("ajeti_id")))
            } while (cursor.moveToNext())
        }
        return returnList
    }

    fun get10AyahsForSurah(surahId: Int, ayahId: Int, languageIdentifier: String):List<QuranModel>{
        val language = QuranApplication.instance.getLanguage()

        val returnList:ArrayList<QuranModel> = ArrayList<QuranModel>()
        val sql =
            ("select * from kuran_$languageIdentifier where surja_id = $surahId and ajeti_id >= $ayahId limit 10;")
        var cursor: Cursor? = null
        try{
            cursor = mDb?.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            mDb?.execSQL(sql)
            return ArrayList()
        }
        var id: Int
        var surah: String
        var surahId: Int
        var ayah: String
        var ayahId: Int
        if (cursor?.moveToFirst()!!) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                surah = cursor.getString(cursor.getColumnIndex("surja"))
                surahId = cursor.getInt(cursor.getColumnIndex("surja_id"))
                ayah = cursor.getString(cursor.getColumnIndex("ajeti"))
                ayahId = cursor.getInt(cursor.getColumnIndex("ajeti_id"))
                val kat = QuranModel(id,surah,ayah,surahId,ayahId, languageIdentifier)
                returnList.add(kat)
            } while (cursor.moveToNext())
        }
        return returnList
    }



    /* private KategoriteModel cursorToCategory(Cursor cursor)
    {
    	//0int id,1String question,2String answer1,3String answer2,4String answer3, 5String correct_answer,6String picture_name,7int category_id, 8int test_id
    	return new KategoriteModel(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Integer.parseInt(cursor.getString(2)),);
    }*/
    fun close() {
        mDbHelper.close()
    }

    companion object {
        protected val TAG = "DataAdapter"
    }

}
