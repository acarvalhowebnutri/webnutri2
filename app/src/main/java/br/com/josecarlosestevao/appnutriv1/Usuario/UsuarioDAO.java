package br.com.josecarlosestevao.appnutriv1.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.josecarlosestevao.appnutriv1.Constantes.Constantes;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;

/**
 * Created by Dell on 10/02/2017.
 */
public class UsuarioDAO {
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    private Context context;
    // Database open/upgrade helper


    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }


    public void adicionausuario(Usuario usuario) {
        ContentValues values = new ContentValues();

        values.put("nome", usuario.getNome());
        values.put("senha", usuario.getSenha());
        values.put("foto", usuario.getFoto());
        values.put("data", usuario.getDataNasc());
        values.put("peso", usuario.getPeso());
        values.put("sexo", usuario.getSexo());
        //values.put("data", alimento.getData());


        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //String n = alimentoConsumido.getAlimento();
        long id = db.insert(Constantes.TB_USUARIO, null, values);
        usuario.setId(id);
        dbHelper.close();
        db.close();

        //Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();

    }
    public void atualizaNutricionista(String userName, String crn) {
        // Define the updated row content.

        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        //updatedValues.put("nome", userName);
        updatedValues.put("crn", crn);

        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String where = "nome = ?";
        db.update(Constantes.TB_USUARIO, updatedValues, where, new String[]{userName});
        dbHelper.close();
        db.close();
    }
    public String pesquisarUsuario(String nome) {

        dbHelper.openDatabase();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("USUARIO", null, " nome=?", new String[]{nome}, null, null, null);
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

    public String verificarJaExiste(String nome) {

        dbHelper.openDatabase();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(Constantes.TB_USUARIO, null, " nome=?", new String[]{nome}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String n = cursor.getString(cursor.getColumnIndex("nome"));
        cursor.close();
        dbHelper.close();
        return n;
    }

    public Usuario ler(String userName) {
        dbHelper.openDatabase();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("USUARIO", null, " nome=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();

        }

        Usuario user = new Usuario();
        cursor.moveToFirst();
        user.setNome(cursor.getString(1));
        user.setSexo(cursor.getString(5));
        user.setPeso(cursor.getString(4));
        user.setDataNasc(cursor.getString(3));

        user.setFoto(cursor.getBlob(7));
        user.setCrn(cursor.getString(9));


        cursor.close();
        return user;
    }


    public UsuarioDAO open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public static boolean validarEmail(String email)
    {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

    public Cursor recuperarNutri(String searchTerm) {
        String[] columns = {Constantes.ROW_ID, Constantes.NOME};
        Cursor c = null;
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (searchTerm != null && searchTerm.length() > 0) {
            String sql = "SELECT * FROM " + Constantes.TB_NUTRICIONISTA + " WHERE " + Constantes.NOME + " LIKE '%" + searchTerm + "%'";
            c = db.rawQuery(sql, null);
            return c;

        }

        c = db.query(Constantes.TB_NUTRICIONISTA, columns, null, null, null, null, null, null);
        return c;
    }

    public void close()
    {


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();
    }
}