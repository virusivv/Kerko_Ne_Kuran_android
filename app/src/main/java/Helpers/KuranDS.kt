package Helpers

import Models.AjetetPerKategoriModel
import java.util.ArrayList


import Models.KategoriteModel
import Models.KuranModel
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log

class KuranDS(private val mContext: Context) {
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
    fun open(): KuranDS {
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
    fun getSurahs(language: String):List<String>{
        val returnList:ArrayList<String> = ArrayList<String>()
        val sql =
            ("select distinct(surja) from kuran_"+language+" order by surja_id;")
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

    fun getAyahList(surjaId: Int, language: String):List<Int>{
        val returnList:ArrayList<Int> = ArrayList<Int>()
        val sql =
            ("select ajeti_id from kuran_"+language+" where surja_id = "+surjaId)
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

    fun get10AyahsForSurah(surjaId: Int, ajetId: Int, language: String):List<KuranModel>{
        val returnList:ArrayList<KuranModel> = ArrayList<KuranModel>()
        val sql =
            ("select * from kuran_"+ language +" where surja_id = " + surjaId + " and ajeti_id >= " + ajetId + " limit 10;")
        var cursor: Cursor? = null
        try{
            cursor = mDb?.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            mDb?.execSQL(sql)
            return ArrayList()
        }
        var id: Int
        var surja: String
        var surjaId: Int
        var ajeti: String
        var ajetId: Int
        if (cursor?.moveToFirst()!!) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                surja = cursor.getString(cursor.getColumnIndex("surja"))
                surjaId = cursor.getInt(cursor.getColumnIndex("surja_id"))
                ajeti = cursor.getString(cursor.getColumnIndex("ajeti"))
                ajetId = cursor.getInt(cursor.getColumnIndex("ajeti_id"))
                val kat= KuranModel(id,surja,ajeti,surjaId,ajetId)
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
