package br.com.josecarlosestevao.appnutriv1.Listas;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Receita.Post;
import br.com.josecarlosestevao.appnutriv1.Receita.Receita;
import br.com.josecarlosestevao.appnutriv1.Receita.ReceitaDAO;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

import static android.content.ContentValues.TAG;

/**
 * Created by Dell on 25/12/2016.
 */
public class ListaAlimentosDietaNutricionistaDataFragment extends Fragment {

    // private static final int MENU_APAGAR = Menu.FIRST;
    private static final int MENU_ADICIONAR_CONSUMO = Menu.FIRST;
    final List<Receita> ali = new ArrayList<Receita>();
    //final List<Receita> ali = new ArrayList<Receita>();
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
    int cont = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag1, null);

        listaAlimentosDietaNutricionistaDataFragment = (ListView) view.findViewById(R.id.list);

        registerForContextMenu(listaAlimentosDietaNutricionistaDataFragment);

        // return super.onCreateView(inflater, container, savedInstanceState);


        return (view);

    }

    @Override
    public void onStart() {
        super.onStart();

        montaLisViewFirebase();
        //montaListaFirebaseCopia();


    }

    @Override
    public void onResume() {
        super.onResume();

        //montaLisViewNew();

        //  montaListaFirebaseCopia();


    }

    public ListView getListaAlimentosDietaNutricionistaDataFragment() {

        return listaAlimentosDietaNutricionistaDataFragment;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_ADICIONAR_CONSUMO, 0, "Adicionar consumo");
    }

    private void montaLisViewNew() {

        // List<Consumo>   alimentosConsumidosNew=null;
        String link = null;
        Bundle bundle = getArguments();
        if (bundle != null) {
            link = bundle.getString("link");
        }
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
        String dateString = sdf.format(date);

        UsuarioDAO daouser = new UsuarioDAO(getContext());

        Usuario paciente = daouser.ler(name);
        String nomenutri = paciente.getCrn();
        ReceitaDAO daoreceita = new ReceitaDAO(getContext());
        //  final List<Consumo> alimentosConsumidos = dao.listaConsumidos(name);
        // final List<Receita> alimentosReceita = daoreceita.listaReceitaPacicente(name, nomenutri);
        final List<Receita> alimentosReceita = daoreceita.recuperaDietaTodos(name, nomenutri, dateString);


        ArrayAdapter<Receita> adapterNew = new ListaAlimentosReceitaAdapter(getActivity(),
                android.R.layout.simple_list_item_1, alimentosReceita);

        listaAlimentosDietaNutricionistaDataFragment.setAdapter(adapterNew);

        //ArrayList<Consumo> alimentoNew = new ArrayList<>();

        // listaAlimentosDietaNutricionistaDataFragment.setOnItemClickListener(new ListaAlimentosConsumidosListenerData(this));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == MENU_ADICIONAR_CONSUMO) {

            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
            final String currentDateTimeString = sdf.format(date);

            Receita consumo = (Receita) getListaAlimentosDietaNutricionistaDataFragment().getItemAtPosition(info.position);
            //consumo.setData(currentDateTimeString);


            session = new SessionManager(getContext());

            session.checkLogin();

            // get user data from session
            HashMap<String, String> user = session.getUserDetails();

            // name

            // final String link1 = DateFormat.getDateInstance().format(new Date());
            //dataatual.setText(currentDateTimeString);
            String name = user.get(SessionManager.KEY_NAME);

            if (usuario == null) {
                usuario = new Usuario();
            }
            usuario.setNome(name);

            //consumo.setUsuario(usuario);
            adicionarconsumo(consumo);
            Toast.makeText(getContext(), "item adicionado aos consumidos ", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void adicionarconsumo(Receita consumo) {
        ConsumoDAO dao = new ConsumoDAO(getContext());
        dao.consumidoReceita(consumo);
        montaLisViewNew();
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


        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            //mDatabase = database.getReference().child("receita").child("a").child("receita");
            mDatabase = database.getReference().child("receita").child(name);
            //   mDatabase = database.getReference().child("receita").child("-Kz1I4FOl4yYZP8eUpi3").child("alimento");


        }
        cont++;

        // app_title change listener
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ali.clear();
                //   Receita appTitle = dataSnapshot.getValue(Receita.class);
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    Receita post = artistSnapshot.getValue(Receita.class);

                    //   String teste = receita.alimento;
                    //.child("receita")
                    //.child("a")
                    //.child("receita")


                    ali.add(post);


                    // update toolbar title
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //mDatabase.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        //mPostListener = postListener;

        ArrayAdapter<Receita> adapterNew = new ListaAlimentosReceitaAdapter(getActivity(),
                android.R.layout.simple_list_item_1, ali);

        listaAlimentosDietaNutricionistaDataFragment.setAdapter(adapterNew);


    }

    public void montaListaFirebaseCopia() {

        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            //mDatabase = database.getReference().child("receita").child("a").child("receita");
            mDatabase = database.getReference().child("receita").child("-Kz1I4FOl4yYZP8eUpi3").child("alimento");

        }

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Post post = dataSnapshot.getValue(Post.class);
                // [START_EXCLUDE]
                String mAuthorView = (post.author);
                ali.add(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(getContext(), "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mDatabase.addValueEventListener(postListener);

        ArrayAdapter<Receita> adapterNew = new ListaAlimentosReceitaAdapter(getActivity(),
                android.R.layout.simple_list_item_1, ali);

        listaAlimentosDietaNutricionistaDataFragment.setAdapter(adapterNew);


    }
}

