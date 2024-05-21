package com.superman.androidsqllite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseInstance extends SQLiteOpenHelper {
    public static final String MA_LOP = "MA_LOP";
    public static final String TEN_LOP = "TEN_LOP";
    public static final String SI_SO = "SI_SO";
    public static final String TABLE_NAME = "TBL_LOP";

    private static final String DATABASE_NAME = "lop.db";


    public DatabaseInstance(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TBL_LOP (MA_LOP VARCHAR(10) PRIMARY KEY, TEN_LOP NVARCHAR(100), SI_SO int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
