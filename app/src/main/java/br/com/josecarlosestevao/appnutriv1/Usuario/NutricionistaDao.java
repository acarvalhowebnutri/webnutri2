package br.com.josecarlosestevao.appnutriv1.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.josecarlosestevao.appnutriv1.Constantes.Constantes;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;

/**
 * Created by Dell on 16/10/2017.
 */

public class NutricionistaDao {

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    private Context context;
    // Database open/upgrade helper


    public NutricionistaDao(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }
    public void adicionarNutricionista(Nutricionista nutricionista) {
        ContentValues values = new ContentValues();

        values.put("nome", nutricionista.getNome());
        values.put("senha", nutricionista.getSenha());
        values.put("email", nutricionista.getEmail());
        values.put("crn", nutricionista.getCrn());



        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //String n = alimentoConsumido.getAlimento();
        long id = db.insert(Constantes.TB_NUTRICIONISTA, null, values);
        nutricionista.setId(id);
        dbHelper.close();
        db.close();

        //Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();

    }

    public String pesquisarNutricionista(String nome) {

        dbHelper.openDatabase();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(Constantes.TB_NUTRICIONISTA, null, " nome=?", new String[]{nome}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String senha = cursor.getString(cursor.getColumnIndex("senha"));
        cursor.close();
        dbHelper.close();
        return senha;
    }

    public Nutricionista ler(String userName) {
        dbHelper.openDatabase();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(Constantes.TB_NUTRICIONISTA, null, " nome=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();

        }

        Nutricionista user = new Nutricionista();
        cursor.moveToFirst();
        user.setNome(cursor.getString(1));
        user.setEmail(cursor.getString(2));

        // user.setSenha(cursor.getString(2));


        cursor.close();
        return user;
    }
    public NutricionistaDao open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
}
