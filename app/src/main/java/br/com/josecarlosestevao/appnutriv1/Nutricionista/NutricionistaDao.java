package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.Constantes.Constantes;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;
import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 16/10/2017.
 */

public class NutricionistaDao {

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    FirebaseDatabase database;
    private Context context;
    private DatabaseReference mDatabase;
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
        values.put("foto", nutricionista.getFoto());


        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //String n = alimentoConsumido.getAlimento();
        long id = db.insert(Constantes.TB_NUTRICIONISTA, null, values);
        nutricionista.setId(id);
        dbHelper.close();
        db.close();

        //Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
        this.cadastrarNutricionistaNoFirebase(nutricionista);
    }

    private void cadastrarNutricionistaNoFirebase(Nutricionista nutricionista) {
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        mDatabase
                .child("nutricionista")
                .child(nutricionista.getNome())
                .push().setValue(nutricionista);
        //   .setValue(nutricionista);
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
        db.close();

        return senha;
    }

    public String pesquisarCRN(String nome) {

        dbHelper.openDatabase();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(Constantes.TB_NUTRICIONISTA, null, " nome=?", new String[]{nome}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String crn = cursor.getString(cursor.getColumnIndex("crn"));
        cursor.close();
        dbHelper.close();
        db.close();
        return crn;
    }

    public List<Usuario> listaPacientesCRN(String CRN) {
        List<Usuario> listapacientes = new ArrayList<Usuario>();
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String selectQuery = "SELECT  " +
                Constantes.KEY_ID + "," +
                Constantes.KEY_NOME + "," +
                Constantes.KEY_EMAIL +

                " FROM " + Constantes.TB_USUARIO
                + " WHERE " +
                Constantes.KEY_CRN + "=?";// I
        int iCount = 0;
        Consumo a = new Consumo();

        Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(CRN)});

        try {
            while (c.moveToNext()) {
                Usuario paciente = new Usuario();
                paciente.setId(c.getLong(c.getColumnIndex("_id")));
                paciente.setNome(c.getString(c.getColumnIndex("nome")));
                paciente.setEmail(c.getString(c.getColumnIndex("email")));
                //    alimento.setData(c.getString(c.getColumnIndex("data")));
                listapacientes.add(paciente);
            }
        } finally {
            c.close();
        }
        db.close();
        c.close();
        dbHelper.close();
        return listapacientes;
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
        user.setEmail(cursor.getString(3));
        user.setCrn(cursor.getString(4));
        //   user.setFoto(cursor.getBlob(5));


        // user.setSenha(cursor.getString(2));


        cursor.close();
        db.close();
        dbHelper.close();
        return user;
    }


    public NutricionistaDao open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }
}
