package Helpers

import Models.AyahsForCategoriesModel
import java.util.ArrayList


import Models.CategoriesModel
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log

class CategoriesDS(private val mContext: Context) {
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
    fun open(): CategoriesDS {
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

    fun getCategoriesBasedOnSearchText(searchText: String, language: String):List<CategoriesModel>{
        val returnList:ArrayList<CategoriesModel> = ArrayList<CategoriesModel>()
        val sql =
            ("select ta.id as id,ta.kategoria_" + language + " as kategoria, (select count(*) from kategori_ajet where kategori_id=ta.id) as numriIAjeteve from kategorite"
                    + " ta where ta.kategoria_" + language + " like '"
                    + searchText.replace("\'", "\'\'")
                    + "%' order by ta.kategoria_" + language)
        var cursor: Cursor? = null
        try{
            cursor = mDb?.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            mDb?.execSQL(sql)
            return ArrayList()
        }
        var id: Int
        var category: String
        var ayahsNo: Int
        var i: Int = 1
        if (cursor?.moveToFirst()!!) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                category = cursor.getString(cursor.getColumnIndex("kategoria"))
                ayahsNo = cursor.getInt(cursor.getColumnIndex("numriIAjeteve"))
                val kat= CategoriesModel(id,category,ayahsNo,i)
                returnList.add(kat)
                i++
            } while (cursor.moveToNext())
        }
        return returnList
    }



    fun getAyahsForCategory(category: CategoriesModel, language: String):List<AyahsForCategoriesModel>{
        val returnList:ArrayList<AyahsForCategoriesModel> = ArrayList<AyahsForCategoriesModel>()
        val sql =
            ("select kategoria_" + language + " as kategoria, ksq.ajeti, ksq.surja, ksq.ajeti_id, ksq.surja_id,"
                    + " substr(ksq.ajeti,0,50) as ajeti_shkurt, ka.id as kategori_ajet_id, k.id as kategori_id, ksq.id as kuran_id"
                    + " from kategori_ajet ka"
                    + " left join kategorite k on k.id = ka.kategori_id"
                    + " left join kuran_" + language + " ksq on ka.ajeti_id=ksq.ajeti_id and ka.surja_id = ksq.surja_id"
                    + " where k.id = " + category.id + " order by ksq.surja_id, ksq.ajeti_id")
        var cursor: Cursor? = null
        try{
            cursor = mDb?.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            mDb?.execSQL(sql)
            return ArrayList()
        }
        var id: Int
        var category_text: String
        var ayahId: Int
        var surahId: Int
        var surah: String
        var ayah: String
        var shortAyah: String
        var i: Int = 1
        if (cursor?.moveToFirst()!!) {
            do {
                val existingid = returnList.indexOfFirst { it.surah_id ==  cursor.getInt(cursor.getColumnIndex("surja_id")) &&
                        (it.ayah_id == cursor.getInt(cursor.getColumnIndex("ajeti_id")) -1 ||
                                it.ayah_ids_text.contains((cursor.getInt(cursor.getColumnIndex("ajeti_id")) -1).toString())
                                )}
                if(existingid == null || existingid == -1){
                    id = cursor.getInt(cursor.getColumnIndex("kategori_ajet_id"))
                    category_text = cursor.getString(cursor.getColumnIndex("kategoria"))
                    ayahId = cursor.getInt(cursor.getColumnIndex("ajeti_id"))
                    surahId = cursor.getInt(cursor.getColumnIndex("surja_id"))
                    surah = cursor.getString(cursor.getColumnIndex("surja"))
                    ayah = cursor.getString(cursor.getColumnIndex("ajeti"))
                    shortAyah=cursor.getString(cursor.getColumnIndex("ajeti_shkurt"))
                    val ayah = AyahsForCategoriesModel(id,category_text,surahId,ayahId,surah,ayah,i, shortAyah, ayahId.toString())
                    returnList.add(ayah)
                    i++
                }
                else
                {
                    id = cursor.getInt(cursor.getColumnIndex("kategori_ajet_id"))
                    category_text = cursor.getString(cursor.getColumnIndex("kategoria"))
                    ayahId = cursor.getInt(cursor.getColumnIndex("ajeti_id"))
                    surahId = cursor.getInt(cursor.getColumnIndex("surja_id"))
                    surah = cursor.getString(cursor.getColumnIndex("surja"))
                    ayah = cursor.getString(cursor.getColumnIndex("ajeti"))
                    shortAyah=cursor.getString(cursor.getColumnIndex("ajeti_shkurt"))
                    var ayahIdsText=returnList[existingid].ayah_ids_text
                    if(ayahIdsText.contains(" - "))
                        ayahIdsText = ayahIdsText.substring(0, ayahIdsText.indexOf(" - "))
                    else{
                        returnList[existingid].ayah = "{" + (returnList[existingid].ayah_id) + "} " + returnList[existingid].ayah
                    }

                    returnList[existingid].ayah += "{" + cursor.getString(cursor.getColumnIndex("ajeti_id")) + "} " + cursor.getString(cursor.getColumnIndex("ajeti"))
                    returnList[existingid].ayah_ids_text = ayahIdsText + " - " + cursor.getString(cursor.getColumnIndex("ajeti_id"))
                }
            } while (cursor.moveToNext())
        }
        return returnList
    }
    /* public ArrayList<ArrayList<KategoriteModel>> getallcategories(String gjuha, int pagenumber) {
		try {
			ArrayList<ArrayList<KategoriteModel>> returnobj=new ArrayList<ArrayList<KategoriteModel>>();
			gjuha=gjuha.substring(0,2);
			String gjuhaeardhur = "";
			if (gjuha.equals("zz"))
				gjuhaeardhur = "en";
			else
				gjuhaeardhur = gjuha;
			String sql = "select ta._id as id,ta.tagu as tagu, (select count(*) from crosstagajet where tagid=ta._id) as numri from tbltaguiajetit_"
					+ gjuhaeardhur
					+ " ta order by ta.tagu";

			Cursor mCur = mDb.rawQuery(sql, null);

			mCur.moveToFirst();
			ArrayList<KategoriteModel> category_list = new ArrayList<KategoriteModel>();
			ArrayList<KategoriteModel> category_list2 = new ArrayList<KategoriteModel>();
			//int i=0;
	          while (!mCur.isAfterLast())
	          {
	        	  KategoriteModel categoryTo = cursorToCategory(mCur);

	        	    category_list.add(categoryTo);
	                mCur.moveToNext();
	             //   i++;
	             //   if(i==10)
	             //   {
	                	//returnobj.add(category_list);
	                	//category_list= new ArrayList<KategoriteModel>();
	                	//i=0;
	              //  }
	          }
	          int MaxNum=category_list.size();
	       *//*   for(int i=0;i<MaxNum;i++)
	          {
	        	  category_list2.add(category_list.get(i));
	        	  if(category_list2.size()==10)
	        	  {
	                	returnobj.add(category_list2);
	                	category_list2=new ArrayList<KategoriteModel>();
	        	  }
	          }

	         *//*
	          int pages=MaxNum/10;
	          if(MaxNum%10>0)
	        	  pages++;
	          for(int i=0;i<pages;i++)
	          {
	        	  for(int j=Integer.parseInt(i+""+0);j<Integer.parseInt(i+""+0)+10;j++)
	        	  {
		        	  category_list2.add(category_list.get(j));
		        	  try{
		        		  category_list.get(j+1);
		        	  }
		        	  catch(Exception ex)
		        	  {
		        		  break;
		        	  }
	        	  }
	        	  returnobj.add(category_list2);
	        	  category_list2=new ArrayList<KategoriteModel>();
	          }
	          return returnobj;
	        *//*  ArrayList<KategoriteModel> category_return=new ArrayList<KategoriteModel>(10);
	          int first_number=(pagenumber*10)-10;
	          for(int i=first_number;i<=first_number+9;i++)
	          {
	        	  try{
	        	  if(category_list!=null)
	        		  category_return.add(category_list.get(i));
	        	  }
	        	  catch(IndexOutOfBoundsException iobe)
	        	  {

	        	  }
	          }

			return category_return;
			*//*


		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}*/

/*
    fun getcount(gjuha: String): Int {
        var gjuha = gjuha
        try {
            gjuha = gjuha.substring(0, 2)
            var gjuhaeardhur = ""
            if (gjuha == "zz")
                gjuhaeardhur = "en"
            else
                gjuhaeardhur = gjuha
            val sql = ("select count(ta._id) from tbltaguiajetit_"
                    + gjuhaeardhur
                    + " ta order by ta.tagu")

            val mCur = mDb!!.rawQuery(sql, null)
            mCur.moveToFirst()

            return mCur.getInt(0)


        } catch (mSQLException: SQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString())
            throw mSQLException
        }

    }
*/
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
