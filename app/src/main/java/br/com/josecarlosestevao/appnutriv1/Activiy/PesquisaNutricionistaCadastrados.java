package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.Constantes.AdaptadorNutricionista;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.NutricionistaDao;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

/**
 * Created by Dell on 06/01/2017.
 */
public class PesquisaNutricionistaCadastrados extends Activity {

    final List<Usuario> listanutricionista = new ArrayList<Usuario>();
    public TextView txtdata;
    ListView lv;
    SearchView sv;
    ArrayList<Nutricionista> alimento = new ArrayList<>();
    //   ConsumoDAO adapter1;
    AdaptadorNutricionista adapter;
    Nutricionista consumo;
    Nutricionista u;
    ConsumoDAO alimentoRepo;
    Cursor cursor;
    Button btnVoltarPerfil;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    //   private ConsumoDAO dao;
    //  List<Consumo> alimentoConsumidos = dao.listaTodos();
    private int ano, mes, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pesq_nutri_cad);

        lv = (ListView) findViewById(R.id.listfrag);
        sv = (SearchView) findViewById(R.id.searchnutri);
        txtdata = (TextView) findViewById(R.id.txtData);
        btnVoltarPerfil = (Button) findViewById(R.id.criarContaBtn);


        adapter = new AdaptadorNutricionista(getApplicationContext(), alimento);
        consumo = new Nutricionista();
        //   lv.setOnItemClickListener(new ItemList());
        //  lv.setAdapter(adapter);


        //   if(cursor==null) insertDummy();
        carregarNutricionistasParaSQLite();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    lv.clearTextFilter();
                } else {
                    //buscaNutricionistaSQLite(newText);

                    // Toast.makeText(getApplicationContext(), "Dados alterados", Toast.LENGTH_LONG).show();

                    //mListView.setFilterText(newText.toString());
                }
                return false;
            }


        });

        /*

        btnVoltarPerfil.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent voltar = new Intent(getApplication(), PerfilActivity.class);
                startActivity(voltar);

            }
        });
*/
        //Intent voltar = new Intent(getApplicationContext(), PerfilActivityOld.class);
        //startActivity(voltar);


        //   return(view);
    }


    public void buscaNutricionistaSQLite(String newText) {

        alimento.clear();

        UsuarioDAO db = new UsuarioDAO(getApplicationContext());
        db.open();
        Nutricionista p = null;
        Cursor cn = db.recuperarNutri(newText);
        while (cn.moveToNext()) {
            long id = cn.getInt(0);
            String name = cn.getString(1);
            String senha = cn.getString(2);
            String email = cn.getString(3);
            String crn = cn.getString(4);
            String idfb = cn.getString(5);

            p = new Nutricionista();
            p.setIdlong(id);
            p.setNome(name);
            p.setSenha(senha);
            p.setEmail(email);
            p.setCrn(crn);
            p.setId(idfb);

            alimento.add(p);
        }

        db.close();
      /*  ArrayAdapter<Consumo> adapter = new ListaAlimentosConsumidosAdapter(this,
                android.R.layout.simple_list_item_1, alimento);*/
        lv.setAdapter(adapter);


    }


    public ListView getListaAlimentoConsumidos() {

        return lv;
    }

    private void carregarNutricionistasParaSQLite() {
        mDatabase = null;

        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            mDatabase = database.getReference().child("nutricionista");


        }

        final NutricionistaDao dao = new NutricionistaDao(getApplicationContext());


        // app_title change listener
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                listanutricionista.clear();

                //   Receita appTitle = dataSnapshot.getValue(Receita.class);
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    Nutricionista nutricionista = artistSnapshot.getValue(Nutricionista.class);

                    if (nutricionista != null)

                        dao.adicionarNutricionista(nutricionista);
                    //   String teste = receita.alimento;
                    //.child("receita")
                    //.child("a")
                    //.child("receita")


                    //listanutricionista.add(usuario);


                    // update toolbar title
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}


