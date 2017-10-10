package br.com.josecarlosestevao.draweractivity.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.josecarlosestevao.draweractivity.fragment.Constants;


/**
 * Created by Dell on 09/12/2016.
 */
public class AlimentoConsumidoDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Context context;
    ArrayList<AlimentoConsumido> alimento = new ArrayList<>();

    ListView lv;
    AlimentoConsumidoDAO adapter1;
    //CustomAdapter adapter;

    public AlimentoConsumidoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    String data = String.valueOf(System.currentTimeMillis());

    public void adiciona(AlimentoConsumido alimento) {
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getAlimento());
        values.put("data", alimento.getData());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //String n = alimentoConsumido.getAlimento();


        long id = db.insert("alimento", null, values);
        alimento.setId(id);
        db.close();

    }


    public void consumido(AlimentoConsumido alimento) {
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getAlimento());
        //values.put("data","11-05-2015 05:00:00");
        values.put("data", alimento.getData());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert("alimento", null, values);
        alimento.setId(id);
        db.close();

    }

    public void atualiza(AlimentoConsumido alimento) {
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getAlimento());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update("alimento_consumido", values, "_id=?", new String[]{alimento.getAlimento()});
        db.close();

    }

    public List<AlimentoConsumido> listaAlimentos() {
        List<AlimentoConsumido> alimentos = new ArrayList<AlimentoConsumido>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("alimento", null, null, null, null, null, "alimento ASC");
        try {
            while (c.moveToNext()) {
                AlimentoConsumido alimento = new AlimentoConsumido();
                alimento.setId(c.getLong(c.getColumnIndex("_id")));
                alimento.setAlimento(c.getString(c.getColumnIndex("alimento")));
                alimento.setData(c.getString(c.getColumnIndex("data")));
                alimentos.add(alimento);
            }
        } finally {
            c.close();
        }
        db.close();
        return alimentos;
    }


    public List<AlimentoConsumido> listaTodos() {
        List<AlimentoConsumido> alimentos = new ArrayList<AlimentoConsumido>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("alimento_consumido", null, null, null, null, null, "alimento ASC");
        try {
            while (c.moveToNext()) {
                AlimentoConsumido alimento = new AlimentoConsumido();
                alimento.setId(c.getLong(c.getColumnIndex("_id")));
                alimento.setAlimento(c.getString(c.getColumnIndex("alimento")));
                alimento.setData(c.getString(c.getColumnIndex("data")));
                alimentos.add(alimento);
            }
        } finally {
            c.close();
        }
        db.close();
        return alimentos;
    }


    public Cursor recuperar(String searchTerm) {
        String[] columns = {Constants.ROW_ID, Constants.NAME};
        Cursor c = null;

        if (searchTerm != null && searchTerm.length() > 0) {
            String sql = "SELECT * FROM " + Constants.TB_ALIMENTO + " WHERE " + Constants.NAME + " LIKE '%" + searchTerm + "%'";
            c = db.rawQuery(sql, null);
            return c;

        }

        c = db.query(Constants.TB_ALIMENTO, columns, null, null, null, null, null);
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


    public List<AlimentoConsumido> listarViagens() {
        Cursor cursor = getDb().query(DatabaseHelper.AlimentoConsumido.TABELA,
                DatabaseHelper.AlimentoConsumido.COLUNAS,
                null, null, null, null, null);
        List<AlimentoConsumido> alimentos = new ArrayList<AlimentoConsumido>();
        while (cursor.moveToNext()) {
            AlimentoConsumido alimentoConsumido = criarViagem(cursor);
            alimentos.add(alimentoConsumido);
        }
        cursor.close();
        return alimentos;
    }

    private AlimentoConsumido criarViagem(Cursor cursor) {
        AlimentoConsumido alimentoConsumido = new AlimentoConsumido(
                cursor.getInt(cursor.getColumnIndex(
                        DatabaseHelper.AlimentoConsumido._ID)),

                cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.AlimentoConsumido.ALIMENTO)),

                new Date(cursor.getLong(cursor.getColumnIndex(
                        DatabaseHelper.AlimentoConsumido.DATA))
                );


        return alimentoConsumido;
    }
}
/*
    public List<AlimentoConsumido> listaporData(String data) {
        List<AlimentoConsumido> alimentos = new ArrayList<AlimentoConsumido>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + Constants.TB_ALIMENTO + " WHERE " + Constants.DATE +  " LIKE '%" + data + "%'";

       Cursor c = db.query(sql, null, null, null, null, null, "alimento ASC");
        try {
            while (c.moveToNext()) {
                AlimentoConsumido alimento = new AlimentoConsumido();
                alimento.setId(c.getLong(c.getColumnIndex("_id")));
                alimento.setAlimento(c.getString(c.getColumnIndex("alimento")));
                alimento.setData(c.getString(c.getColumnIndex("data")));
                alimentos.add(alimento);
            }
        } finally {
            c.close();
        }
        db.close();
        return alimentos;
    }

*/

/*
    public boolean add(String name) {

//        String data = String.valueOf((System.currentTimeMillis()));
        String data = "data";

        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, name);
            cv.put(Constants.DATE, data);


            db.insert(Constants.TB_CONSUMIDO, Constants.ROW_ID, cv);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
*/
    /*
        public void buscaAlimento(String newText) {

            alimento.clear();

            AlimentoConsumidoDAO db=new AlimentoConsumidoDAO(context);
            db.openDB();
            AlimentoConsumido p=null;
            Cursor c=db.recuperar(newText);
            while (c.moveToNext())
            {
                int id=c.getInt(0);
                String name=c.getString(1);

                p=new AlimentoConsumido();
                //p.setId(id);
                p.setAlimento(name);

                alimento.add(p);
            }

            db.closeDB();
           ArrayAdapter<AlimentoConsumido> adapter = new ListaAlimentosConsumidosAdapter(this,
                    android.R.layout.simple_list_item_1, alimento);
            lv.setAdapter((ListAdapter) adapter1);



        }
    */

   /* public void save(String name) {
        AlimentoConsumidoDAO db = new AlimentoConsumidoDAO(context);
        //  DBAdapter db=new DBAdapter(this);
        db.openDB();
        //db.add(name);
        if (db.add(name)) {
            // Toast.makeText((),name + " adicionado", Toast.LENGTH_SHORT).show();
            db.closeDB();

            //nameEditText.setText("");
        } else {
            // Toast.makeText(this, "Unable To Save", Toast.LENGTH_SHORT).show();
        }


        db.closeDB();

        //  this.buscaAlimento(null);

    }

*/
}

