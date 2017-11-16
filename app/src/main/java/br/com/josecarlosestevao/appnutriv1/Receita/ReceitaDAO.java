package br.com.josecarlosestevao.appnutriv1.Receita;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.Constantes.Constantes;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;
import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 30/10/2017.
 */

public class ReceitaDAO {

    final List<Receita> ali = new ArrayList<Receita>();
    final List<ReceitaTeste> ali2 = new ArrayList<ReceitaTeste>();
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Context context;
    FirebaseDatabase database;
    int cont = 0;
    int variavel = 0;
    private DatabaseReference mDatabase;

    public ReceitaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public static boolean isDataConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public ReceitaDAO open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();
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

    public void adicionarReceita(Receita receita) {
        ContentValues values = new ContentValues();
        values.put("alimento", receita.getAlimento());
        values.put("nutricionista", receita.getNutricionista().getNome());
        values.put("usuario", receita.getUsuario().getNome());
        values.put("data", receita.getData());


        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(Constantes.TB_RECEITA, null, values);
        receita.setId((id));

        db.close();
        dbHelper.close();

        //ADICIONA NO FIREBASE
        this.cadastrarReceitaNoFirebase(receita);

    }

    public void adicionarReceitanoSQL(Receita receita) {
        ContentValues values = new ContentValues();
        values.put("alimento", receita.getAlimento());
        values.put("nutricionista", receita.getNutricionista().getNome());
        values.put("usuario", receita.getUsuario().getNome());
        values.put("data", receita.getData());


        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(Constantes.TB_RECEITA, null, values);
        receita.setId((id));

        db.close();
        dbHelper.close();

        //ADICIONA NO FIREBASE

    }

    public List<Receita> listaReceitaPacicente(String pessoa, String nutricionista) {
        List<Receita> al = new ArrayList<Receita>();
        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String testsdata = "Jul 4";

        String selectQuery = "SELECT  " +
                Constantes.KEY_ID + "," +
                Constantes.KEY_ALIMENTO +
                " FROM " + Constantes.TB_RECEITA
                + " WHERE " +
                Constantes.KEY_USUARIO + " =?" + " AND " +
                Constantes.KEY_DATA + " =? " + " AND " +
                Constantes.KEY_NUTRICIONISTA + " =? ";
        int iCount = 0;
        Receita a = new Receita();

        Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(pessoa), String.valueOf(nutricionista)});

        try {
            while (c.moveToNext()) {
                Receita alimentoc = new Receita();
                alimentoc.setId((c.getLong(c.getColumnIndex("_id"))));
                alimentoc.setAlimento(c.getString(c.getColumnIndex("alimento")));
                //    alimento.setData(c.getString(c.getColumnIndex("data")));
                al.add(alimentoc);
            }
        } finally {
            c.close();
        }
        db.close();
        c.close();
        dbHelper.close();
        return al;
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
        c.close();
        dbHelper.close();
        return al;
    }


    public List<Receita> recuperaDietaTodos(final String pessoa, String nutricionista, String data) {


        // Se não tiver conexão com Internet, busco do SQLite

        dbHelper.openDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                Constantes.KEY_ID + "," +
                Constantes.KEY_ALIMENTO +
                " FROM " + Constantes.TB_RECEITA
                + " WHERE " +
                Constantes.KEY_USUARIO + "=?" + " AND " +
                Constantes.KEY_NUTRICIONISTA + " =?" + " AND " +
                Constantes.KEY_DATA + " =? ";// I
        int iCount = 0;
        Receita a = new Receita();

        Cursor c = db.rawQuery(selectQuery, new String[]{String.valueOf(pessoa), String.valueOf(nutricionista), String.valueOf(data)});

        try {
            while (c.moveToNext()) {
                Receita alimento = new Receita();
                alimento.setId((c.getLong(c.getColumnIndex("_id"))));
                alimento.setAlimento(c.getString(c.getColumnIndex("alimento")));
                //    alimento.setData(c.getString(c.getColumnIndex("data")));
                ali.add(alimento);
            }
        } finally {
            c.close();
        }
        db.close();
        c.close();
        dbHelper.close();

        return ali;

    }


/*    private void cadastrarUsuarioNoFirebase(Receita receita){
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        mDatabase
                .child("users")
                .child(receita.getUsuario().getNome())
                .child("receita")
                .setValue(receita);
    }*/

    //int i=0;
    private void cadastrarReceitaNoFirebase(Receita receita) {
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        String id = mDatabase.push().getKey();
        receita.setIdFb(id);
//r.setAlimento(receita.getAlimento());
        String paciente = receita.getUsuario().getNome();

        mDatabase.child("receita").child(paciente).child(id).setValue(receita);
                //      .child(String.valueOf(i))
        //        .child(receita.getUsuario().getNome())
        //  .child("receita")
        //      .child((receita.getUsuario().getNome()) + cont)
        //.push(cave)
        //.push().setValue(receita);
        // .setValue(receita);


    }

    private void listarDadosFirebase() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(this.getClass().toString(), "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    private List<Receita> listarDadosFirebaseTeste() {

        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            mDatabase = database.getReference();
        }

        if (isDataConnectionAvailable(this.context)) {
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    Receita receita = dataSnapshot
                            .child("receita")
                            .child("a")
                            .child("receita")
                            .getValue(Receita.class);
                    // ...
                    System.out.println(receita.toString());
                    //                  Log.d(TAG, "User name: " + receita.getUsuario() + ", email " + receita.getNutricionista());
                    ali.add(receita);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(this.getClass().toString(), "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };

            mDatabase.addListenerForSingleValueEvent(postListener);
            //return ali;
        }
        return ali;
    }

    private void listarDadosFirebaseTeste2() {

        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            mDatabase = database.getReference();
        }

        if (isDataConnectionAvailable(this.context)) {

            // final ValueEventListener postListener = new ValueEventListener() {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("nutricionista");
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    //Post post = dataSnapshot.getValue(Post.class);
                    // ...

                    //////////////////////////////////////////////////
                    /*Receita receita = dataSnapshot
                            .child("receita")
                            .child("a")
                            .child("receita")
                            .getValue(Receita.class);
                    // ...
                    System.out.println(receita.toString());
                    ali.add(receita);*/
                    //mDatabase.addListenerForSingleValueEvent(dataSnapshot);

                    /////////////////////////////////////
                    Receita post = dataSnapshot.getValue(Receita.class);


                    if (post == null) {
                        // User is null, error out

                        Toast.makeText(context,
                                "Receita nula.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // Write new post
                        Usuario u = new Usuario();
                        u.setNome("joao");
                        Nutricionista n = new Nutricionista();
                        n.setNome("n");
                        Receita teste = new Receita();
                        teste.setAlimento("arroz");
                        //                    teste.setUsuario(u);
                        //                  teste.setNutricionista(n);
                        ali.add(teste);

                    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                   /* final Receita p = new Receita();

                    String nome = null;
                    if (dataSnapshot.getChildrenCount() == 0) {

                    } else {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //  Receita p = postSnapshot.getValue(Receita.class);
                            if (postSnapshot.getKey().equals("a")) {
                                Receita post = postSnapshot.getValue(Receita.class);
                                p.setAlimento(post.getAlimento());

                                nome = postSnapshot.getValue().toString();
                                p.setAlimento(nome);

                            }
                            ali.add(p);

                        }
                    }
                    */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(this.getClass().toString(), "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });
            //mDatabase.addValueEventListener(postListener);
        }
    }

    public String contador() {
        // select count(nome) from Produtos;
        //SELECT COUNT(_id) FROM receita;


        dbHelper.openDatabase();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selectQuery = "SELECT COUNT " +
                Constantes.KEY_ID + "," +
                " FROM " + Constantes.TB_RECEITA;//
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.getCount() < 1) // UserName Not Exist
        {
            c.close();
            return "NOT EXIST";
        }

        String num = c.getString(c.getColumnIndex("COUNT(_id)"));
        c.close();
        // I
        return num;
    }


}