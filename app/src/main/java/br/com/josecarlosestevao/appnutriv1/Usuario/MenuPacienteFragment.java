package br.com.josecarlosestevao.appnutriv1.Usuario;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.NutricionistaDao;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Receita.Receita;
import br.com.josecarlosestevao.appnutriv1.Receita.ReceitaDAO;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;

/**
 * Created by Dell on 06/01/2017.
 */
public class MenuPacienteFragment extends Fragment {
    final List<Receita> cafeDaManhalist = new ArrayList<Receita>();
    final List<Receita> almoçolist = new ArrayList<Receita>();
    final List<Receita> jantalist = new ArrayList<Receita>();
    final List<Receita> lanchelist = new ArrayList<Receita>();
    final List<Usuario> listanutricionista = new ArrayList<Usuario>();

    SessionManager session;
    Intent intent = new Intent();
    String dburl = intent.getStringExtra("databaseUrl");
    Usuario usuario;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Context context;
    FirebaseDatabase database;
    ListView listaAlimentosDietaNutricionistaDataFragment;
    DatabaseReference mDatabase;
    ReceitaDAO daoreceitaum = new ReceitaDAO(getContext());
    ExpandableListView elvCompra;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menu_paciente, null);


        elvCompra = (ExpandableListView) view.findViewById(R.id.elvCompra);


        montaLisViewFirebase();
        carregarNutricionistasParaSQLite();

        return (view);
    }

    @Override
    public void onResume() {
        super.onResume();

        //montaLisViewNew();

        //  montaListaFirebaseCopia();


    }

    private void montaLisViewFirebase() {
        session = new SessionManager(getContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name

        // final String link1 = DateFormat.getDateInstance().format(new Date());
        //dataatual.setText(currentDateTimeString);
        String name = user.get(SessionManager.KEY_NAME);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        final String currentDateTimeString = sdf.format(date);


        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            //mDatabase = database.getReference().child("receita").child("a").child("receita");
            mDatabase = database.getReference().child("receita").child(name).child(currentDateTimeString);
            //   mDatabase = database.getReference().child("receita").child("-Kz1I4FOl4yYZP8eUpi3").child("alimento");


        }


        // app_title change listener
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cafeDaManhalist.clear();
                almoçolist.clear();
                jantalist.clear();
                cafeDaManhalist.clear();
                //   Receita appTitle = dataSnapshot.getValue(Receita.class);
                for (DataSnapshot artistSnapshot : dataSnapshot.child("café da manha").getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    Receita cafedamanhareceita = artistSnapshot.getValue(Receita.class);

                    //   String teste = receita.alimento;
                    //.child("receita")
                    //.child("a")
                    //.child("receita")


                    cafeDaManhalist.add(cafedamanhareceita);


                    // update toolbar title
                }

                for (DataSnapshot artistSnapshot : dataSnapshot.child("almoço").getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    Receita almoçoreceita = artistSnapshot.getValue(Receita.class);

                    //   String teste = receita.alimento;
                    //.child("receita")
                    //.child("a")
                    //.child("receita")


                    almoçolist.add(almoçoreceita);


                    // update toolbar title
                }

                for (DataSnapshot artistSnapshot : dataSnapshot.child("jantar").getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    Receita almoçoreceita = artistSnapshot.getValue(Receita.class);

                    //   String teste = receita.alimento;
                    //.child("receita")
                    //.child("a")
                    //.child("receita")


                    jantalist.add(almoçoreceita);


                    // update toolbar title
                }
                for (DataSnapshot artistSnapshot : dataSnapshot.child("lanche").getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    Receita almoçoreceita = artistSnapshot.getValue(Receita.class);

                    //   String teste = receita.alimento;
                    //.child("receita")
                    //.child("a")
                    //.child("receita")


                    lanchelist.add(almoçoreceita);


                    // update toolbar title
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //mDatabase.addValueEventListener(postListener);  // cria os grupos
        List<String> lstGrupos = new ArrayList<>();
        lstGrupos.add("Café da Manha");
        lstGrupos.add("Almoço");
        lstGrupos.add("Jantar");
        lstGrupos.add("Lanche");
///////////////////////////////////////////////////////////////////
        // cria os itens de cada grupo
        /*List<Receita> lstDoces = new ArrayList<>();
        lstDoces.add(new Receita((long) 0,"Pacote de bala"));
        lstDoces.add(new Receita((long) 1,"Pacote de chiclete"));
        //  lstDoces.add(new Receita("Bolo de chocolate", 50.0));

        List<Receita> lstLegumes = new ArrayList<>();
        lstLegumes.add(new Receita((long) 0,"Alface"));
        lstLegumes.add(new Receita((long) 1,"Tomate"));

        List<Receita> lstProdutos = new ArrayList<>();
        lstProdutos.add(new Receita((long) 0,"Chave de Fenda"));*/
//////////////////////////////////////////////////////////////////////////////////
        // cria o "relacionamento" dos grupos com seus itens
        HashMap<String, List<Receita>> lstItensGrupo = new HashMap<>();
        lstItensGrupo.put(lstGrupos.get(0), cafeDaManhalist);
        lstItensGrupo.put(lstGrupos.get(1), almoçolist);
        lstItensGrupo.put(lstGrupos.get(2), jantalist);
        lstItensGrupo.put(lstGrupos.get(3), lanchelist);


        // cria um adaptador (BaseExpandableListAdapter) com os dados acima
        AdaptadorListaReceitaRecebidas adaptadorum = new AdaptadorListaReceitaRecebidas(getActivity(), lstGrupos, lstItensGrupo);
        // define o apadtador do ExpandableListView
        elvCompra.setAdapter(adaptadorum);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        //mPostListener = postListener;


    }


    private void carregarNutricionistasParaSQLite() {
        mDatabase = null;

        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
           mDatabase = database.getReference().child("nutricionista");


        }

        final NutricionistaDao dao = new NutricionistaDao(getContext());


        // app_title change listener
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                listanutricionista.clear();

                //   Receita appTitle = dataSnapshot.getValue(Receita.class);
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    Nutricionista nutricionista = artistSnapshot.getValue(Nutricionista.class);
                    dao.adicionarNutricionista(nutricionista);
                    //   String teste = receita.alimento;
                    //.child("receita")
                    //.child("a")
                    //.child("receita")


                    listanutricionista.add(usuario);


                    // update toolbar title
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}


