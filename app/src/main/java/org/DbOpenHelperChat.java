package org;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelperChat {

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";

    private static final int DATABASE_VERSION = 1;

    public static SQLiteDatabase mDBC;                                                                 // 각자 다르게 설정해줘야됨.

    private DatabaseHelper mDBHelper;

    private Context mCtx;



    private class DatabaseHelper extends SQLiteOpenHelper {



        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

            super(context, name, factory, version);

        }



        @Override

        public void onCreate(SQLiteDatabase db){

            db.execSQL(DataBasesChat.CreateDB._CREATE0);

        }



        @Override

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+DataBasesChat.CreateDB._TABLENAME0);

            onCreate(db);

        }

    }



    public DbOpenHelperChat(Context context){

        this.mCtx = context;

    }



    public DbOpenHelperChat open() throws SQLException {

        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);

        mDBC = mDBHelper.getWritableDatabase();

        return this;

    }



    public void create(){

        mDBHelper.onCreate(mDBC);

    }



    public void close(){

        mDBC.close();

    }



    // Insert DB

    public long insertColumn(String loginId, String chatId, String id, String name, String image, String chat){

        ContentValues values = new ContentValues();

        values.put(DataBasesChat.CreateDB.LOGINID, loginId);

        values.put(DataBasesChat.CreateDB.CHATID, chatId);

        values.put(DataBasesChat.CreateDB.ID, id);

        values.put(DataBasesChat.CreateDB.NAME, name);

        values.put(DataBasesChat.CreateDB.IMAGE, image);

        values.put(DataBasesChat.CreateDB.CHAT, chat);

        return mDBC.insert(DataBasesChat.CreateDB._TABLENAME0, null, values);

    }



    // Update DB

    public boolean updateColumn(long id, String loginId, String chatId, String id2, String name, String image, String chat){

        ContentValues values = new ContentValues();

        values.put(DataBasesChat.CreateDB.LOGINID, loginId);

        values.put(DataBasesChat.CreateDB.CHATID, chatId);

        values.put(DataBasesChat.CreateDB.ID, id2);

        values.put(DataBasesChat.CreateDB.NAME, name);

        values.put(DataBasesChat.CreateDB.IMAGE, image);

        values.put(DataBasesChat.CreateDB.CHAT, chat);

        return mDBC.update(DataBasesChat.CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;

    }



    // Delete All

    public void deleteAllColumns() {

        mDBC.delete(DataBasesChat.CreateDB._TABLENAME0, null, null);

    }



    // Delete DB

    public boolean deleteColumn(long id){

        return mDBC.delete(DataBasesChat.CreateDB._TABLENAME0, "_id="+id, null) > 0;

    }

    // Select DB

    public Cursor selectColumns(){

        return mDBC.query(DataBasesChat.CreateDB._TABLENAME0, null, null, null, null, null, null);

    }



    // sort by column

    public Cursor sortColumn(String sort){

        Cursor c = mDBC.rawQuery( "SELECT * FROM chattable ORDER BY " + sort + ";", null);

        return c;

    }

}


