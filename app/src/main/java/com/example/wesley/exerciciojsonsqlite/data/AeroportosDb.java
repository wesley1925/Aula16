package com.example.wesley.exerciciojsonsqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by asbonato on 9/20/15.
 */
public class AeroportosDb {
    AeroportosDbHelper dbHelper;
    public static final String AEROPORTOS = "aeroporto";

    public AeroportosDb(Context context){
        dbHelper = new AeroportosDbHelper(context);
    }

    public void insereCor(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AeroportosContract.AeroportoEntry.COLUMN_NAME_AEROPORTO_NOME, "Congonhas");
        db.insert(AeroportosContract.AeroportoEntry.TABLE_NAME, null, values);

        values.put(AeroportosContract.AeroportoEntry.COLUMN_NAME_AEROPORTO_NOME, "Guarulhos");
        db.insert(AeroportosContract.AeroportoEntry.TABLE_NAME, null, values);

        values.put(AeroportosContract.AeroportoEntry.COLUMN_NAME_AEROPORTO_NOME, "Campinas");
        db.insert(AeroportosContract.AeroportoEntry.TABLE_NAME, null, values);

        values.put(AeroportosContract.AeroportoEntry.COLUMN_NAME_AEROPORTO_NOME, "Porto Alegre");
        db.insert(AeroportosContract.AeroportoEntry.TABLE_NAME, null, values);
    }

       public ArrayList<String>  selecionaAeroportos(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Escolha um aeroporto");

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] colunas = {AeroportosContract.AeroportoEntry._ID,
                AeroportosContract.AeroportoEntry.COLUMN_NAME_AEROPORTO_NOME};

        String ordem = AeroportosContract.AeroportoEntry.COLUMN_NAME_AEROPORTO_NOME;

        Cursor c = db.query(AeroportosContract.AeroportoEntry.TABLE_NAME, colunas, null, null, ordem, null, null );

        while(c.moveToNext()){
            lista.add(c.getString(c.getColumnIndex(AeroportosContract.AeroportoEntry.COLUMN_NAME_AEROPORTO_NOME)));
        }

        c.close();

        return lista;
    }




}
