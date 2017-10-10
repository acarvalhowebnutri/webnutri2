package br.com.josecarlosestevao.draweractivity.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 17/11/2016.
 */


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "WebNutriApp";

    private static int VERSAO = 9;

    public DatabaseHelper(Context context) {

        super(context, BANCO_DADOS, null, VERSAO);
    }




    public static class AlimentoConsumido {
        	public static final String TABELA = "alimneto";
         		public static final String _ID = "_ID";
         		public static final String ALIMENTO = "alimento";
         		public static final String DATA = "DATA";


         		public static final String[] COLUNAS = new String[]{
         							_ID, ALIMENTO, DATA,
         							 };
        	}

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "CREATE TABLE alimento(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +

                "alimento TEXT NOT NULL," +
                "data BIGINT"+");";


        String sqla = "CREATE TABLE alimento_consumido (" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "alimento TEXT NOT NULL," +
                "data TEXT NOT NULL"+");";

        db.execSQL(sql);

        db.execSQL(sqla);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS alimento";
        String sqla = "DROP TABLE IF EXISTS alimento_consumido";
        db.execSQL(sql);
        db.execSQL(sqla);
        onCreate(db);


    }


}
