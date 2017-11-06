package br.com.josecarlosestevao.appnutriv1.Consumo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.josecarlosestevao.appnutriv1.Constantes.Constantes;
import br.com.josecarlosestevao.appnutriv1.Receita.Receita;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;


/**
 * Created by Dell on 09/12/2016.
 */
public class ConsumoDAO {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Context context;
    ArrayList<Consumo> alimento = new ArrayList<>();
    //ConsumoDAO dao = new ConsumoDAO(context);
    ListView lv;
    ConsumoDAO adapter1;
    //AdaptadorPersonalizado adapter;
    Double ini = 0.0;
    String total;
    String totalpro;

    String data = String.valueOf(System.currentTimeMillis());

    public ConsumoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public ConsumoDAO open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void adiciona(Consumo alimento) {
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getAlimento());
        values.put("valor", alimento.getCarboidrato());
        values.put("proteina", alimento.getProteina());
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        long id = db.insert(Constantes.TB_CONSUMIDO, null, values);
        alimento.setId(id);

        dbHelper.close();
        db.close();

    }

    public void adicionaReceitaConsumo(Receita alimento) {
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getAlimento());
        // values.put("valor", alimento.getCarboidrato());
        //values.put("proteina", alimento.getProteina());
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        long id = db.insert(Constantes.TB_CONSUMIDO, null, values);
        alimento.setId(id);

        dbHelper.close();
        db.close();

    }

    public void consumido(Consumo alimento) {
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getAlimento());
        values.put("pessoa", alimento.getUsuario().getNome());
        values.put("valor", alimento.getCarboidrato());
        values.put("proteina", alimento.getProteina());
        values.put("data", alimento.getData());

        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(Constantes.TB_CONSUMIDO, null, values);
        alimento.setId(id);
        db.close();

    }

    public void consumidoReceita(Receita alimento) {
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getAlimento());
        values.put("pessoa", alimento.getUsuario().getNome());
        // values.put("valor", alimento.getCarboidrato());
        //values.put("proteina", alimento.getProteina());
        values.put("data", alimento.getData());

        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(Constantes.TB_CONSUMIDO, null, values);
        alimento.setId(id);

        dbHelper.close();
        db.close();

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /* public void novoconsumido(Consumo alimento) {
         ContentValues values = new ContentValues();
         values.put("alimento", alimento.getAlimento());
         values.put("pessoa",alimento.getUsuario().getNome());


         //SQLiteDatabase db = dbHelper.getWritableDatabase();
         String idnome = alimento.getUsuario().getNome();
         String novovalor = alimento.getCarboidrato();
         String teste = dao.pesquisarValor(idnome);
         double valorantigo = Double.parseDouble(teste);
         String total = novovalor + valorantigo;
         values.put("valor", total);
                 db.delete("alimento", "_id = ?", where);


         // values.put("valor_usuario", alimento.getUsuario().getValor());
         SQLiteDatabase db = dbHelper.getWritableDatabase();

         long id = db.insert("consumido", null, values);
         alimento.setId(id);
         db.close();

     }


     public void registrarGasto(Consumo alimento){
         ContentValues values = new ContentValues();
         values.put("pessoa", alimento.getUsuario().getNome());
         values.put("carboidrato", alimento.getCarboidrato());


         SQLiteDatabase db = dbHelper.getWritableDatabase();
         db.insert("NUTRIENTES", null, values);
     }

    */
    public void remove(Consumo alimento) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Constantes.TB_CONSUMIDO, " _id=?", new String[]{alimento.getId().toString()});
        db.close();

    }


    private void removerAlimento(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where[] = new String[]{id};
        db.delete("alimento", "_id = ?", where);
        //   db.delete("viagem", "_id = ?", where);
    }


    public void atualiza(Consumo alimento) {
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getAlimento());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update("alimento_consumido", values, "_id=?", new String[]{alimento.getAlimento()});
        db.close();

    }


    public List<Consumo> listaAlimentos() {
        List<Consumo> alimentos = new ArrayList<Consumo>();
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(Constantes.TB_ALIMENTO, null, null, null, null, null, "alimento ASC");
        try {
            while (c.moveToNext()) {
                Consumo alimento = new Consumo();
                alimento.setId(c.getLong(c.getColumnIndex("_id")));
                alimento.setAlimento(c.getString(c.getColumnIndex("alimento")));
                //  alimento.setData(c.getLong(c.getColumnIndex("data")));
                alimentos.add(alimento);
            }
        } finally {
            c.close();
        }
        db.close();
        return alimentos;
    }


    public List<Consumo> listaTodos() {
        List<Consumo> alimentos = new ArrayList<Consumo>();
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("consumido", null, null, null, null, null, "alimento ASC");
        try {
            while (c.moveToNext()) {
                Consumo alimento = new Consumo();
                alimento.setId(c.getLong(c.getColumnIndex("_id")));
                alimento.setAlimento(c.getString(c.getColumnIndex("alimento")));
                //    alimento.setData(c.getString(c.getColumnIndex("data")));
                alimentos.add(alimento);
            }
        } finally {
            c.close();
        }
        db.close();
        return alimentos;
    }

    public List<Consumo> listaConsumidos(String pessoa) {
        List<Consumo> ali = new ArrayList<Consumo>();
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String selectQuery = "SELECT  " +
                Constantes.KEY_ID + "," +
                Constantes.KEY_ALIMENTO +
                " FROM " + Constantes.TB_CONSUMIDO
                + " WHERE " +
                Constantes.KEY_PESSOA + "=?";// I
        int iCount = 0;
        Consumo a = new Consumo();

        Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(pessoa)});

        try {
            while (c.moveToNext()) {
                Consumo alimento = new Consumo();
                alimento.setId(c.getLong(c.getColumnIndex("_id")));
                alimento.setAlimento(c.getString(c.getColumnIndex("alimento")));
                //    alimento.setData(c.getString(c.getColumnIndex("data")));
                ali.add(alimento);
            }
        } finally {
            c.close();
        }
        db.close();
        return ali;
    }

    public List<Consumo> listaConsumidosNew(String pessoa, String data) {
        List<Consumo> al = new ArrayList<Consumo>();
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String testsdata = "Jul 4";

        String selectQuery = "SELECT  " +
                Constantes.KEY_ID + "," +
                Constantes.KEY_ALIMENTO +
                " FROM " + Constantes.TB_CONSUMIDO
                + " WHERE " +
                Constantes.KEY_PESSOA + "=?" + " AND " +
                Constantes.KEY_DATA + " =? ";
        int iCount = 0;
        Consumo a = new Consumo();

        Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(pessoa), String.valueOf(data)});

        try {
            while (c.moveToNext()) {
                Consumo alimentoc = new Consumo();
                alimentoc.setId(c.getLong(c.getColumnIndex("_id")));
                alimentoc.setAlimento(c.getString(c.getColumnIndex("alimento")));
                //    alimento.setData(c.getString(c.getColumnIndex("data")));
                al.add(alimentoc);
            }
        } finally {
            c.close();
        }
        db.close();
        return al;
    }

    public String somarCategoria(String name) {
        //String SelectQuery = "select sum(valor) FROM consumido";
        // String SelectQueryNew = "select sum(valor) FROM consumido where pessoa ";
        String SelectQueryNew2 = "SELECT sum(" + Constantes.KEY_VALOR + ") FROM " + Constantes.TB_CONSUMIDO + " WHERE " + Constantes.KEY_PESSOA + " LIKE '%" + name + "%'";
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQueryNew2, null);


//name = null;
       /* String sql = "SELECT * FROM " + Constants.TB_ALIMENTO + " WHERE " + Constants.NAME + " LIKE '%" + searchTerm + "%'";
        c = db.rawQuery(sql, null);
*/

        if (cursor.moveToNext()) {
            total = cursor.getString(0);
        }
        return total;
    }

    public String somarProtein(String name) {
        //String SelectQuery = "select sum(valor) FROM consumido";
        // String SelectQueryNew = "select sum(valor) FROM consumido where pessoa ";


        String SelectQueryNew3 = "SELECT sum(" + Constantes.KEY_PROTEIN + ") FROM " + Constantes.TB_CONSUMIDO + " WHERE " + Constantes.KEY_PESSOA + " LIKE '%" + name + "%'";
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(SelectQueryNew3, null);


        if (cursor1.moveToNext()) {
            totalpro = cursor1.getString(0);
        }
        return totalpro;
    }


    /* public double somaValor3(String pessoa) {
        Double resultado = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT sum(valor) FROM consumido", null);
        if (c.moveToNext()) {
            String total = c.getString(2);
            //c.getColumnIndex("valor");
        }
        return total;
    }
*/
  /*  public double somaValor2(String pessoa) {
        Double resultado = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT sum(valor) FROM consumido", null);
        if (c.moveToFirst()) {
            do {
                //Recuperando valores
                int column1 = c.getInt(2);

                //Fazendo algo com os valores


            } while (c.moveToNext());
            String colum = c.getString(c.getColumnIndex("valor"));
            Double conver = Double.valueOf(colum);
            total = (ini + conver);
            resultado = total;
        }
        return resultado;
        //   c.close();
        //  db.close();
    }

*/
    public double somaValor(String pessoa) {
        List<Consumo> ali = new ArrayList<Consumo>();
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Double ini = 0.0;
        Double total = 0.0;
        String selectQuery = "SELECT  " +
                Constantes.KEY_ID + "," +
                Constantes.KEY_VALOR +
                " FROM " + Constantes.TB_CONSUMIDO
                + " WHERE " +
                Constantes.KEY_PESSOA + "=?";// I
        int iCount = 0;
        Consumo a = new Consumo();

        Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(pessoa)});

        try {
            while (c.moveToNext()) {
                Consumo alimento = new Consumo();
                alimento.setId(c.getLong(c.getColumnIndex("_id")));
                alimento.setAlimento(c.getString(c.getColumnIndex("valor")));
                Double valor = Double.valueOf(alimento.getCarboidrato());

                total = ini + valor;
                ini = total;
                //    alimento.setData(c.getString(c.getColumnIndex("data")));
                // ali.add(alimento);
            }
        } finally {
            c.close();
        }
        db.close();

        return total;
    }

    /*
        public List<Gasto> listarGastos(Consumo consumo){
            String selection = DatabaseHelper.Gasto.ALIMENTO_ID + " = ?";
            String[] selectionArgs = new String[]{consumo.getId().toString()};

            Cursor cursor = getDb().query(DatabaseHelper.Gasto.TABELA,
                    DatabaseHelper.Gasto.COLUNAS,
                    selection, selectionArgs,
                    null, null, null);
            List<Gasto> gastos = new ArrayList<Gasto>();
            while(cursor.moveToNext()){
                Gasto gasto = criarGasto(cursor);
                gastos.add(gasto);
            }
            cursor.close();
            return gastos;
        }
    */


    public Cursor recuperar(String searchTerm) {
        String[] columns = {Constantes.ROW_ID, Constantes.NAME};
        Cursor c = null;
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (searchTerm != null && searchTerm.length() > 0) {
            String sql = "SELECT * FROM " + Constantes.TB_ALIMENTO + " WHERE " + Constantes.NAME + " LIKE '%" + searchTerm + "%'";
            c = db.rawQuery(sql, null);
            return c;

        }

        c = db.query(Constantes.TB_ALIMENTO, columns, null, null, null, null, null, null);
        return c;
    }


   /* public double buscaValor(String searchTerm){

        Cursor cursor = db.query(Constants.TB_CONSUMIDO, new String[] { Constants.KEY_VALOR },
                Constants.KEY_PESSOA + "=" + searchTerm, null, null, null, null);

        cursor.moveToFirst();
        double total = cursor.getDouble(0);
        cursor.close();
        return total;
    }
*/

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

    /*
        public double calcularTotalGasto(Gasto viagem){
            Cursor cursor = getDb().rawQuery(
                    "SELECT SUM("+DatabaseHelper.Gasto.VALOR + ") FROM " +
                            DatabaseHelper.Gasto.TABELA + " WHERE " +
                            DatabaseHelper.Gasto.ALIMENTO_ID + " = ?",
                    new String[]{ viagem.getId().toString() });
            cursor.moveToFirst();
            double total = cursor.getDouble(0);
            cursor.close();
            return total;
        }
    */
    public String pesquisarValor(String nome) {
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("consumido", null, " pessoa=?", new String[]{nome}, null, null, null);
        //  Cursor cursor = getDb().query("consumido", null, " pessoa=?", new String[]{pessoa}, null, null, null);

        // Cursor cursor = db.query(Constants.TB_CONSUMIDO, null, Constants.KEY_VALOR + "=?", new String[]{pessoa}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String senha = cursor.getString(cursor.getColumnIndex("valor"));
        cursor.close();
        return senha;
    }


}

