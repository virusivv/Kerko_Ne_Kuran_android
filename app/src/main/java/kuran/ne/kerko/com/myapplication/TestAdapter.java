//error in the sql command part of merremratesureve function nuk po dihet per cfare arsye po del force close
package kuran.ne.kerko.com.myapplication;

import java.io.IOException;

import Helpers.DataBaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestAdapter {
	protected static final String TAG = "DataAdapter";

	private final Context mContext;
	private SQLiteDatabase mDb;
	private DataBaseHelper mDbHelper;

	public TestAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
	}
	public TestAdapter createDatabaseorigjinal() throws SQLException {
		try {
			mDbHelper.createDataBaseorigjinal();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}
	public TestAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public TestAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public Cursor getTestData(String gjuha) {
		try {
			String gjuhaeardhur = "";
			if (gjuha.equals("zz"))
				gjuhaeardhur = "en";
			else
				gjuhaeardhur = gjuha;
			String sql = "select ta._id as id,ta.tagu as tagu, (select count(*) from crosstagajet where tagid=ta._id) as numri from tbltaguiajetit_"
					+ gjuhaeardhur + " ta order by ta.tagu";

			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor getcategoriesinitiallimit10(String gjuha) {
		try {
			String gjuhaeardhur = "";
			if (gjuha.equals("zz"))
				gjuhaeardhur = "en";
			else
				gjuhaeardhur = gjuha;
			String sql = "select ta._id as id,ta.tagu as tagu, (select count(*) from crosstagajet where tagid=ta._id) as numri from tbltaguiajetit_"
					+ gjuhaeardhur + " ta order by ta.tagu limit 10";

			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor getcategoriesnextlimit10(String gjuha, String kushtiifundit) {
		try {
			String gjuhaeardhur = "";
			if (gjuha.equals("zz"))
				gjuhaeardhur = "en";
			else
				gjuhaeardhur = gjuha;
			String sql = "select ta._id as id,ta.tagu as tagu, (select count(*) from crosstagajet where tagid=ta._id) as numri from tbltaguiajetit_"
					+ gjuhaeardhur
					+ " ta where ta.tagu>'"
					+ kushtiifundit.replace("\'", "\'\'")
					+ "' order by ta.tagu limit 10";

			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}


	public Cursor getcategoriesprevlimit10(String gjuha, String kushtiifundit) {
		try {
			String gjuhaeardhur = "";
			if (gjuha.equals("zz"))
				gjuhaeardhur = "en";
			else
				gjuhaeardhur = gjuha;
			String sql = "select ta._id as id,ta.tagu as tagu, (select count(*) from crosstagajet where tagid=ta._id) as numri from tbltaguiajetit_"
					+ gjuhaeardhur
					+ " ta where ta.tagu<'"
					+ kushtiifundit.replace("\'", "\'\'")
					+ "' order by ta.tagu desc limit 10";

			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor getTestData2(String shtesa, String gjuhaeardhur) {
		try {
			String gjuha = "";
			if (gjuhaeardhur.equals("zz"))
				gjuha = "en";
			else
				gjuha = gjuhaeardhur;
			// String sql
			// ="select aj.ajetikuranor , aj.idajetit , ta.tagu  from tblajetetkuranore aj,  crosstagajet cta, tbltaguiajetit ta where aj._id=cta.ajetid and  ta._id = cta.tagid and ta.tagu like '%"+shtesa+"%'";
			String sql = "select ta._id as id,ta.tagu as tagu, (select count(*) from crosstagajet where tagid=ta._id) as numri from tbltaguiajetit_"
					+ gjuha
					+ " ta where ta.tagu like '"
					+ shtesa.replace("\'", "\'\'")
					+ "%' order by ta.tagu";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			mCur.moveToFirst();
			mDbHelper.close();
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor merrajetet(Object idja, String gjuhaeardhur) {
		try {
			String gjuha = "";
			if (gjuhaeardhur.equals("zz"))
				gjuha = "en";
			else
				gjuha = gjuhaeardhur;
			String sql = "select ank._id,ank.surja_emri as ajetikuranor , aj.idajetit , ta.tagu , ank.ajeti as ajeti from tblajetetkuranore aj left join tblajetetnekuran_"
					+ gjuha
					+ " ank on aj.ajetikuranor = ank.surja_id and aj.idajetit = ank.ajeti_id left join crosstagajet cta on aj._id=cta.ajetid left join tbltaguiajetit_"
					+ gjuha
					+ " ta on ta._id = cta.tagid where ta.tagu='"
					+ idja.toString().replace("\'", "\'\'") + "'  order by ank.surja_id, ank.ajeti_id";
			String ajetipermeukthy = "";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			mCur.moveToFirst();
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	// haha
	public Cursor merrajetet10tepare(Object idja, String gjuhaeardhur) {
		try {
			String gjuha = "";
			if (gjuhaeardhur.equals("zz"))
				gjuha = "en";
			else
				gjuha = gjuhaeardhur;
			String sql = "select ank._id,ank.surja_emri as ajetikuranor , aj.idajetit , ta.tagu , ank.ajeti as ajeti from tblajetetkuranore aj left join tblajetetnekuran_"
					+ gjuha
					+ " ank on aj.ajetikuranor = ank.surja_id and aj.idajetit = ank.ajeti_id left join crosstagajet cta on aj._id=cta.ajetid left join tbltaguiajetit_"
					+ gjuha
					+ " ta on ta._id = cta.tagid where ta.tagu='"
					+ idja.toString().replace("\'", "\'\'") + "'  order by ank.surja_id, ank.ajeti_id limit 10";
			String ajetipermeukthy = "";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			mCur.moveToFirst();
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	
	public Cursor merrajetet10next(Object idja, String gjuhaeardhur,
			String last_item) {
		try {
			String gjuha = "";
			if (gjuhaeardhur.equals("zz"))
				gjuha = "en";
			else
				gjuha = gjuhaeardhur;
			String sql = "select ank._id,ank.surja_emri as ajetikuranor , aj.idajetit , ta.tagu , ank.ajeti as ajeti from tblajetetkuranore aj left join tblajetetnekuran_"
					+ gjuha
					+ " ank on aj.ajetikuranor = ank.surja_id and aj.idajetit = ank.ajeti_id left join crosstagajet cta on aj._id=cta.ajetid left join tbltaguiajetit_"
					+ gjuha
					+ " ta on ta._id = cta.tagid where ta.tagu='"
					+ idja.toString().replace("\'", "\'\'")
					+ "' and ank._id>"
					+ last_item
					+ " order by ank.surja_id, ank.ajeti_id limit 10";
			String ajetipermeukthy = "";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToFirst();
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}

	public Cursor merrajetet10prev(Object idja, String gjuhaeardhur,
			String first_item) {
		try {
			String gjuha = "";
			if (gjuhaeardhur.equals("zz"))
				gjuha = "en";
			else
				gjuha = gjuhaeardhur;
			String sql = "select ank._id,ank.surja_emri as ajetikuranor , aj.idajetit , ta.tagu , ank.ajeti as ajeti from tblajetetkuranore aj left join tblajetetnekuran_"
					+ gjuha
					+ " ank on aj.ajetikuranor = ank.surja_id and aj.idajetit = ank.ajeti_id left join crosstagajet cta on aj._id=cta.ajetid left join tbltaguiajetit_"
					+ gjuha
					+ " ta on ta._id = cta.tagid where ta.tagu='"
					+ idja.toString().replace("\'", "\'\'")
					+ "' and ank._id<"
					+ first_item
					+ " order by ank.surja_id desc, ank.ajeti_id desc limit 10";
			String ajetipermeukthy = "";
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			mCur.moveToFirst();
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}



}
