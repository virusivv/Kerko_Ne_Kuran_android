package Helpers

import Models.AjetetPerKategoriModel
import java.util.ArrayList


import Models.KategoriteModel
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log

class KategoriteDS(private val mContext: Context) {
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
    fun open(): KategoriteDS {
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

    fun getCategoriesBasedOnSearchText(searchText: String, language: String):List<KategoriteModel>{
        val returnList:ArrayList<KategoriteModel> = ArrayList<KategoriteModel>()
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
        var kategoria: String
        var numriAjeteve: Int
        var i: Int = 1
        if (cursor?.moveToFirst()!!) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                kategoria = cursor.getString(cursor.getColumnIndex("kategoria"))
                numriAjeteve = cursor.getInt(cursor.getColumnIndex("numriIAjeteve"))
                val kat= KategoriteModel(id,kategoria,numriAjeteve,i)
                returnList.add(kat)
                i++
            } while (cursor.moveToNext())
        }
        return returnList
    }



    fun getAyahsForCategory(kategoria: KategoriteModel, language: String):List<AjetetPerKategoriModel>{
        val returnList:ArrayList<AjetetPerKategoriModel> = ArrayList<AjetetPerKategoriModel>()
        val sql =
            ("select kategoria_" + language + " as kategoria, ksq.ajeti, ksq.surja, ksq.ajeti_id, ksq.surja_id,"
                    + " substr(ksq.ajeti,0,50) as ajeti_shkurt, ka.id as kategori_ajet_id, k.id as kategori_id, ksq.id as kuran_id"
                    + " from kategori_ajet ka"
                    + " left join kategorite k on k.id = ka.kategori_id"
                    + " left join kuran_" + language + " ksq on ka.ajeti_id=ksq.ajeti_id and ka.surja_id = ksq.surja_id"
                    + " where k.id = " + kategoria.id + " order by ksq.surja_id, ksq.ajeti_id")
        var cursor: Cursor? = null
        try{
            cursor = mDb?.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            mDb?.execSQL(sql)
            return ArrayList()
        }
        var id: Int
        var tagu: String
        var ajeti_id: Int
        var surja_id: Int
        var surja: String
        var ajeti: String
        var ajet_shkurt: String
        var i: Int = 1
        if (cursor?.moveToFirst()!!) {
            do {
                //val id: Int, val tagu: String, val surja_id: Int, val ajeti_id: Int, val surja: String, val ajeti: String
                val existingid = returnList.indexOfFirst { it.surja_id ==  cursor.getInt(cursor.getColumnIndex("surja_id")) &&
                        (it.ajeti_id == cursor.getInt(cursor.getColumnIndex("ajeti_id")) -1 ||
                                it.ajetet_id_text.contains((cursor.getInt(cursor.getColumnIndex("ajeti_id")) -1).toString())
                                )}
                if(existingid == null || existingid == -1){
                    id = cursor.getInt(cursor.getColumnIndex("kategori_ajet_id"))
                    tagu = cursor.getString(cursor.getColumnIndex("kategoria"))
                    ajeti_id = cursor.getInt(cursor.getColumnIndex("ajeti_id"))
                    surja_id = cursor.getInt(cursor.getColumnIndex("surja_id"))
                    surja = cursor.getString(cursor.getColumnIndex("surja"))
                    ajeti = cursor.getString(cursor.getColumnIndex("ajeti"))
                    ajet_shkurt=cursor.getString(cursor.getColumnIndex("ajeti_shkurt"))
                    val ajeti= AjetetPerKategoriModel(id,tagu,surja_id,ajeti_id,surja,ajeti,i, ajet_shkurt, ajeti_id.toString())
                    returnList.add(ajeti)
                    i++
                }
                else
                {
                    id = cursor.getInt(cursor.getColumnIndex("kategori_ajet_id"))
                    tagu = cursor.getString(cursor.getColumnIndex("kategoria"))
                    ajeti_id = cursor.getInt(cursor.getColumnIndex("ajeti_id"))
                    surja_id = cursor.getInt(cursor.getColumnIndex("surja_id"))
                    surja = cursor.getString(cursor.getColumnIndex("surja"))
                    ajeti = cursor.getString(cursor.getColumnIndex("ajeti"))
                    ajet_shkurt=cursor.getString(cursor.getColumnIndex("ajeti_shkurt"))
                    var ajetet_id_text=returnList[existingid].ajetet_id_text
                    if(ajetet_id_text.contains(" - "))
                        ajetet_id_text = ajetet_id_text.substring(0, ajetet_id_text.indexOf(" - "))
                    else{
                        returnList[existingid].ajeti = "{" + (returnList[existingid].ajeti_id) + "} " + returnList[existingid].ajeti
                    }

                    returnList[existingid].ajeti += "{" + cursor.getString(cursor.getColumnIndex("ajeti_id")) + "} " + cursor.getString(cursor.getColumnIndex("ajeti"))
                    returnList[existingid].ajetet_id_text = ajetet_id_text + " - " + cursor.getString(cursor.getColumnIndex("ajeti_id"))
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
