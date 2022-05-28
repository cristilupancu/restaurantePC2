package com.example.restaurantepc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper {
    public  static final String IMAGE_ID="id";
    private final Context mContext;
    public static final String IMAGE="image";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDB;
    private static final String DATABASE_NAME="PCrestauranteDB";
    private static final int DATABASE_VERSION=1;
    private static final String IMAGES_TABLE="rinfo";
    private static final String CREATE_IMAGE_TABLE=
            "CREATE TABLE rinfo ( MAIL VARCHAR(40),NAME VARCHAR(40) ," +
                    "NUMBER VARCHAR(10),ADRESS VARCHAR(40),image BLOB NOT NULL );";
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_IMAGE_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+IMAGES_TABLE);
            onCreate(db);
        }

    }
    public void Reset(){
        mDbHelper.onUpgrade(this.mDB,1,1);
    }
    public DBHelper(Context context){
        mContext=context;
        mDbHelper=new DatabaseHelper(mContext);
    }
    public DBHelper open() throws SQLException{
        mDB=mDbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        mDbHelper.close();
    }
    public void insertImage(byte[] imageBytes,String a,String b ,String c,String d){
        ContentValues cv=new ContentValues();
        cv.put(IMAGE,imageBytes);
        cv.put("MAIL",a);
        cv.put("NAME",b);
        cv.put("NUMBER",c);
        cv.put("ADRESS",d);

        mDB.insert(IMAGES_TABLE,null,cv);
    }
    @SuppressLint("Range")
    public byte[] retrieveImageFromDB(String mail) {
       /* Cursor cur = mDB.query( true,IMAGES_TABLE, new String[]{IMAGE,},
                null, null, null, null,
                IMAGE_ID + " DESC", "1");*/

        String Query = "Select image from rinfo where mail = '" + mail +"'";
        Log.d("ye3",Query);
        Cursor cur = mDB.rawQuery(Query, null);
        if (cur.moveToFirst()) {
            byte[] blob;
            blob = cur.getBlob(0);
            Log.d("ye3",Query);
            cur.close();
            return blob;
        }
        cur.close();
        return null;
    }


}
