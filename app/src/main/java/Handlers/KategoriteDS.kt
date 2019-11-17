package Handlers

import java.io.IOException
import java.util.ArrayList
import java.util.Random


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

    fun getTestData2(shtesa: String, gjuhaeardhur: String): ArrayList<KategoriteModel>? {
        try {
            // String sql
            // ="select aj.ajetikuranor , aj.idajetit , ta.tagu  from tblajetetkuranore aj,  crosstagajet cta, tbltaguiajetit ta where aj._id=cta.ajetid and  ta._id = cta.tagid and ta.tagu like '%"+shtesa+"%'";
            val sql =
                ("select ta.id as id,ta.kategoria_" + gjuhaeardhur + " as tagu, (select count(*) from kategori_ajet where kategori_id=ta.id) as numri from kategorite"
                        + " ta where ta.kategoria_" + gjuhaeardhur + " like '"
                        + shtesa.replace("\'", "\'\'")
                        + "%' order by ta.kategoria_" + gjuhaeardhur)
            val mCur = mDb!!.rawQuery(sql, null)
            mCur?.moveToNext()
            mCur!!.moveToFirst()
            val abc=populateCategories(mCur)
            mDbHelper.close()
            return abc
        } catch (mSQLException: SQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString())
            throw mSQLException
        }

    }

    fun viewEmployee(shtesa: String, gjuhaeardhur: String):List<KategoriteModel>{
        val returnList:ArrayList<KategoriteModel> = ArrayList<KategoriteModel>()
        val sql =
            ("select ta.id as id,ta.kategoria_" + gjuhaeardhur + " as tagu, (select count(*) from kategori_ajet where kategori_id=ta.id) as numri from kategorite"
                    + " ta where ta.kategoria_" + gjuhaeardhur + " like '"
                    + shtesa.replace("\'", "\'\'")
                    + "%' order by ta.kategoria_" + gjuhaeardhur)
        var cursor: Cursor? = null
        try{
            cursor = mDb?.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            mDb?.execSQL(sql)
            return ArrayList()
        }
        var id: Int
        var tagu: String
        var numri: Int
        var i: Int = 1
        if (cursor?.moveToFirst()!!) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                tagu = cursor.getString(cursor.getColumnIndex("tagu"))
                numri = cursor.getInt(cursor.getColumnIndex("numri"))
                val emp= KategoriteModel(id,tagu,numri,i)
                returnList.add(emp)
                i++
            } while (cursor.moveToNext())
        }
        return returnList
    }


    internal fun populateCategories(tagateajeteve: Cursor) : ArrayList<KategoriteModel>?
    {
        var i = 1
        val kategoriteListaObject: ArrayList<KategoriteModel>? = null
        tagateajeteve!!.moveToFirst()
        while (!tagateajeteve.isAfterLast()) {
            var idja = ""
            var tagu = ""
            var numri = ""
            var nrRendor = ""
            if (i < 10)
                nrRendor = "0$i"
            else
                nrRendor = i.toString()
            idja = tagateajeteve!!.getString(tagateajeteve!!.getColumnIndex("id"))
            tagu = tagateajeteve!!.getString(tagateajeteve!!.getColumnIndex("tagu"))
            numri = tagateajeteve!!.getString(tagateajeteve!!.getColumnIndex("numri"))
            kategoriteListaObject!!.add(
                KategoriteModel(
                    Integer.parseInt(idja), tagu, Integer.parseInt(numri), Integer.parseInt(nrRendor)
                )
            )
            i++
            tagateajeteve!!.moveToNext()
        }
        return kategoriteListaObject
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
