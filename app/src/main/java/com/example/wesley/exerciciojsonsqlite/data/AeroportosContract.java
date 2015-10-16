package com.example.wesley.exerciciojsonsqlite.data;

import android.provider.BaseColumns;

public class AeroportosContract {
    public AeroportosContract(){}

    public static abstract class AeroportoEntry implements BaseColumns{
        public static final String TABLE_NAME = "aeroporto";
        public static final String COLUMN_NAME_AEROPORTO_NOME = "nomeAeroporto";
    }

}
