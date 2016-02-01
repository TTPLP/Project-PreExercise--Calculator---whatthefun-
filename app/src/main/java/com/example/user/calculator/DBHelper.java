package com.example.user.calculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YUAN on 2016/01/23.
 */
public class DBHelper extends SQLiteOpenHelper{
    public static final String DB_Name = "mydata.db";
    public static final int VERSION = 1;
    private static SQLiteDatabase database;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_Name, factory, VERSION);
    }

    public static SQLiteDatabase getDatabase(Context context){
        if(database == null || !database.isOpen()){
            database = new DBHelper(context, DB_Name, null,VERSION).getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Record");
        onCreate(database);
    }
}
