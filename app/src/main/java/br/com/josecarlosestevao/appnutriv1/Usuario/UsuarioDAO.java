package br.com.josecarlosestevao.appnutriv1.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
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
    FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Context context;
    // Database open/upgrade helper


    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public static boolean validarEmail(String email) {
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

    public void adicionausuario(Usuario usuario) {
        ContentValues values = new ContentValues();


        values.put("nome", usuario.getNome());
        values.put("senha", usuario.getSenha());
        values.put("email", usuario.getEmail());
        values.put("foto", usuario.getFoto());
        values.put("data", usuario.getDataNasc());
        values.put("peso", usuario.getPeso());
        values.put("sexo", usuario.getSexo());
        values.put("idFirebase", usuario.getImc());
        values.put("_id", usuario.getId());
        //values.put("data", alimento.getData());


        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //String n = alimentoConsumido.getAlimento();
        long id = db.insert(Constantes.TB_USUARIO, null, values);
        //  usuario.setId(id);
        dbHelper.close();
        db.close();

        //this.cadastrarUsuarioNoFirebase(usuario);
        //Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();

    }

    public void cadastrarUsuarioNoFirebase(Usuario usuario) {
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();


        String chave = usuario.getId();
        mDatabase
                .child("paciente")
                .child(usuario.getCrn())
                .child(chave)
                //  .child(id)
                .setValue(usuario);

    }

    public void cadastrarUsuarioNoFirebaseOld(Usuario usuario) {
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();


        // Generate a reference to a new location and add some data using push()
        DatabaseReference pushedPostRef = mDatabase.push();

// Get the unique ID generated by a push()
        String id = mDatabase.push().getKey();
        usuario.setImc(id);

        mDatabase
                .child("paciente")
                .child(id)
                //  .child(id)
                .setValue(usuario);
        // .setValue(usuario);
        //.push().setValue(usuario);
        String postId = pushedPostRef.getKey();
        // usuario.getId();
        // .setValue(usuario);
        usuario.setImc(postId);

        adicionausuario(usuario);
    }

    public void alterarCadastrarUsuarioNoFirebase(Usuario usuario) {
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("paciente");

        String chave = usuario.getId();

        Map<String, Object> hopperUpdates = new HashMap<String, Object>();
        mDatabase = database.getReference().child("paciente").child(chave);

        //   hopperUpdates.put(chave, usuario);
        hopperUpdates.put("nome", usuario.getNome());
        hopperUpdates.put("peso", usuario.getPeso());
        hopperUpdates.put("sexo", usuario.getSexo());
        hopperUpdates.put("dataNasc", usuario.getDataNasc());
        // hopperUpdates.put("imc", usuario.getImc());
        hopperUpdates.put("crn", usuario.getCrn());
        mDatabase.updateChildren(hopperUpdates);
        // Generate a reference to a new location and add some data using push()
//        DatabaseReference pushedPostRef = mDatabase.push();

// Get the unique ID generated by a push()

/////////////////////////////////////////////////////////////////////
        /*mDatabase
                .child("paciente")
               .updateChildren(usuario.getImc()).updateChildren(usuario);
                .setValue(usuario);*/
        ///////////////////////////////
        // .setValue(usuario);
        //.push().setValue(usuario);

        // usuario.getId();
        // .setValue(usuario);


        atualizaDadosPaciente(usuario);
    }



    public void alterarNutricionistaPacienteNoFirebase(Usuario usuario) {
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("paciente");

        String chave = usuario.getId();
        String chavecrn = usuario.getCrn();
        Map<String, Object> hopperUpdates = new HashMap<String, Object>();
        mDatabase = database.getReference().child("paciente").child(chavecrn).child(chave);

        //   hopperUpdates.put(chave, usuario);
        hopperUpdates.put("crn", usuario.getCrn());

        mDatabase.updateChildren(hopperUpdates);

    }

    public void atualizaDadosPaciente(Usuario usuario) {
        // Define the updated row content.

        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        //updatedValues.put("nome", userName);

        updatedValues.put("nome", usuario.getNome());
        updatedValues.put("senha", usuario.getSenha());
        updatedValues.put("email", usuario.getEmail());
        updatedValues.put("foto", usuario.getFoto());
        updatedValues.put("data", usuario.getDataNasc());
        updatedValues.put("peso", usuario.getPeso());
        updatedValues.put("sexo", usuario.getSexo());
        updatedValues.put("idFirebase", usuario.getImc());
        // updatedValues.put("_id", usuario.getId());

        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // String where = "nome = ?";
        String where = "email = ?";
        String userEmail = usuario.getEmail();

        db.update(Constantes.TB_USUARIO, updatedValues, where, new String[]{userEmail});
        dbHelper.close();
        db.close();
    }


    public void alterarNutricionistaNoFirebase(Usuario usuario) {
        database = FirebaseDatabase.getInstance();


        String chave = usuario.getImc();
        mDatabase = database.getReference().child("paciente").child(chave);
        Map<String, Object> hopperUpdates = new HashMap<String, Object>();
        hopperUpdates.put("crn", usuario.getCrn());
        mDatabase.updateChildren(hopperUpdates);
        // Generate a reference to a new location and add some data using push()
//


        atualizaNutricionista(usuario);
    }


    public void atualizaNutricionista(Usuario usuario) {
        // Define the updated row content.

        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        //updatedValues.put("nome", userName);

        updatedValues.put("crn", usuario.getCrn());

        // updatedValues.put("_id", usuario.getId());

        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // String where = "nome = ?";
        String where = "email = ?";
        String userEmail = usuario.getEmail();

        db.update(Constantes.TB_USUARIO, updatedValues, where, new String[]{userEmail});
        dbHelper.close();
        db.close();
    }

    public void atualizaNutricionistaold(String userName, String crn) {
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

    public void pesquisarUsuarioFirebase(Usuario usuario) {
        database = FirebaseDatabase.getInstance();


        String chave = usuario.getImc();
        mDatabase = database.getReference().child("paciente").child(chave);
        Map<String, Object> hopperUpdates = new HashMap<String, Object>();
        hopperUpdates.put("crn", usuario.getCrn());
        mDatabase.updateChildren(hopperUpdates);
        // Generate a reference to a new location and add some data using push()
//


        atualizaNutricionista(usuario);
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

    public String lerIDFB(String userName) {
        dbHelper.openDatabase();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("USUARIO", null, " nome=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();

        }
        cursor.moveToFirst();
        //String teste = cursor.getString(6);
        String teste = cursor.getString(cursor.getColumnIndex("idFirebase"));

        /*Usuario user = new Usuario();
        cursor.moveToFirst();
        user.setImc(cursor.getString(6));
       */
        cursor.close();
        dbHelper.close();

        return teste;
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

    public UsuarioDAO open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
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

    public void close() {


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();
    }
}