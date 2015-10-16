package com.example.wesley.exerciciojsonsqlite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AeroportosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Aeroportos.db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String OPEN_PAR = "(";
    public static final String CLOSE_PAR = ")";
    public static final String SQL_CREATE_AEROPORTO =
            "CREATE TABLE " + AeroportosContract.AeroportoEntry.TABLE_NAME + OPEN_PAR +
                    AeroportosContract.AeroportoEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    AeroportosContract.AeroportoEntry.COLUMN_NAME_AEROPORTO_NOME + TEXT_TYPE + CLOSE_PAR;
    public static final String SQL_DROP_AEROPORTO =
            "DROP TABLE IF EXISTS " + AeroportosContract.AeroportoEntry.TABLE_NAME;

    public AeroportosDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_AEROPORTO);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //tabelas de parâmetros que podem ser recriadas
        db.execSQL(SQL_DROP_AEROPORTO);
        db.execSQL(SQL_CREATE_AEROPORTO);
    }

}
