package br.com.josecarlosestevao.appnutriv1.DRI;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.Constantes.Constantes;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;


/**
 * Created by Dell on 09/12/2016.
 */
public class DRIDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Context context;
    ArrayList<DRI> alimento = new ArrayList<>();
    //ConsumoDAO dao = new ConsumoDAO(context);
    ListView lv;
    DRIDAO adapter1;
    //AdaptadorPersonalizado adapter;
    Double ini = 0.0;
    String total;
    String totalpro;

    String data = String.valueOf(System.currentTimeMillis());

    public DRIDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public DRIDAO open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public List<DRI> testeDRI(String idade, String sexo) {
        List<DRI> al = new ArrayList<DRI>();
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String testsdata = "Jul 4";

        String selectQuery = "SELECT  " +
                Constantes.KEY_ID + "," +
                Constantes.KEY_VITAMINAAMIN +
                " FROM " + Constantes.TB_DRI
                + " WHERE " +
                Constantes.KEY_MINIDADE + " >" + "idade" + " AND " +
                Constantes.KEY_MAXIDADE + " <" + "idade" + Constantes.KEY_PESSOAL_STATUS + " >?";
        int iCount = 0;
        Consumo a = new Consumo();

        Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(idade), String.valueOf(sexo)});

        try {
            while (c.moveToNext()) {
                DRI alimentoc = new DRI();
                alimentoc.setId(c.getLong(c.getColumnIndex("_id")));
                alimentoc.setVitaminaAmin(c.getString(c.getColumnIndex("VitaminaAmin")));
                //    alimento.setData(c.getString(c.getColumnIndex("data")));
                al.add(alimentoc);
            }
        } finally {
            c.close();
        }
        db.close();
        return al;
    }


    public Cursor teste2DRI(String idade, String sexo) {
        String[] columns = {Constantes.ROW_ID, Constantes.KEY_VITAMINAAMIN};
        Cursor c = null;
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (idade != null && idade.length() > 0) {
            //String sql = "SELECT _ID , VitaminaAmin FROM " + Constantes.TB_DRI + " WHERE " + Constantes.KEY_MINIDADE + " > " + idade        + " AND " + Constantes.KEY_MAXIDADE + " < " + idade + " AND "+ Constantes.KEY_PESSOALSTATUS + " LIKE '%" + sexo + "%'";;
            //String sql = "SELECT  _ID , VitaminaAmin FROM DRI WHERE min_idade <= 30 and max_idade >= 30";
            //and Pessoal_status = homem;
            String sql = "SELECT " + Constantes.KEY_ID + " , " + Constantes.KEY_VITAMINAAMIN + " FROM " + Constantes.TB_DRI + " WHERE " + Constantes.KEY_MINIDADE + " <= '%" + idade + "%'" + " AND " + Constantes.KEY_MAXIDADE + " >= '%" + idade + "%'" + " AND " + Constantes.KEY_PESSOAL_STATUS + " LIKE '%" + sexo + "%'";

            c = db.rawQuery(sql, null);
            return c;

        }

        c = db.query(Constantes.TB_DRI, columns, null, null, null, null, null, null);
        return c;
    }


    public void openDB() {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //CLOSE
    public void closeDB() {
        try {
            dbHelper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SQLiteDatabase getDb() {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }

    public void close() {
        dbHelper.close();
    }


    public Cursor recuperar(String idade, String sexo) {
        String[] columns = {Constantes.ROW_ID, Constantes.KEY_VITAMINAAMIN};
        Cursor c = null;
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (idade != null && idade.length() > 0) {
            String sql = "SELECT " + Constantes.KEY_ID + " , " + Constantes.KEY_VITAMINAAMIN + " FROM " + Constantes.TB_DRI + " WHERE " + Constantes.KEY_MINIDADE + " <= '%" + idade + "%'" + " AND " + Constantes.KEY_MAXIDADE + " >= '%" + idade + "%'" + " AND " + Constantes.KEY_PESSOAL_STATUS + " LIKE '%" + sexo + "%'";
            c = db.rawQuery(sql, null);
            return c;

        }

        c = db.query(Constantes.TB_DRI, columns, null, null, null, null, null, null);
        return c;
    }


    public String teste4DRI(String idade, String sexo) {
        //String SelectQuery = "select sum(valor) FROM consumido";
        // String SelectQueryNew = "select sum(valor) FROM consumido where pessoa ";
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = null;
        String SelectQueryNew2 = "SELECT " + Constantes.KEY_ID + " , " + Constantes.KEY_VITAMINAAMIN + " , " + Constantes.KEY_VITAMINAAMAX + " FROM " + Constantes.TB_DRI + " WHERE " + Constantes.KEY_MINIDADE + " <= " + idade + " AND " + Constantes.KEY_MAXIDADE + " >= " + idade + " AND " + Constantes.KEY_PESSOAL_STATUS + " LIKE '%" + sexo + "%'";
        //  dbHelper.openDatabase();
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        c = db.rawQuery(SelectQueryNew2, null);


//name = null;
       /* String sql = "SELECT * FROM " + Constants.TB_ALIMENTO + " WHERE " + Constants.NAME + " LIKE '%" + searchTerm + "%'";
        c = db.rawQuery(sql, null);
*/


        if (c.moveToNext()) {
            total = c.getString(1);
        }
        return total;
    }

    public DRI teste5DRI(String idade, String sexo) {
        //String SelectQuery = "select sum(valor) FROM consumido";
        // String SelectQueryNew = "select sum(valor) FROM consumido where pessoa ";

        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = null;
        String SelectQueryNew2 = "SELECT " + Constantes.KEY_ID + " , " + Constantes.KEY_VITAMINAAMIN + " , " + Constantes.KEY_VITAMINAAMAX + " FROM " + Constantes.TB_DRI + " WHERE " + Constantes.KEY_MINIDADE + " <= " + idade + " AND " + Constantes.KEY_MAXIDADE + " >= " + idade + " AND " + Constantes.KEY_PESSOAL_STATUS + " LIKE '%" + sexo + "%'";
        //  dbHelper.openDatabase();
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        c = db.rawQuery(SelectQueryNew2, null);


//name = null;
       /* String sql = "SELECT * FROM " + Constants.TB_ALIMENTO + " WHERE " + Constants.NAME + " LIKE '%" + searchTerm + "%'";
        c = db.rawQuery(sql, null);
*/
        DRI alimento = new DRI();

        if (c.moveToNext()) {
            total = c.getString(1);


            alimento.setId(c.getLong(c.getColumnIndex("_id")));
            alimento.setVitaminaAmin(c.getString(c.getColumnIndex("VitaminaAmin")));
            alimento.setVitaminaAmax(c.getString(c.getColumnIndex("VitaminaAmax")));

        }
        db.close();
        c.close();
        dbHelper.close();
        return alimento;
    }

}

