package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.MenuPacienteFragment;
import br.com.josecarlosestevao.appnutriv1.Usuario.PerfilPacienteFragment;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Usuario usuario;
    UsuarioDAO usuarioDAO;
    TextView nomePerfil;
    TextView emailPerfil;
    Activity context = this;
    SessionManager session;
    FragmentManager fm = getSupportFragmentManager();
    FirebaseDatabase database;
    DatabaseReference mDatabaseNutri;
    private ImageView campoFotoObjeto;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO(context);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
                FragmentTransaction ft = fm.beginTransaction();

                setTitle("Pesquisar");
                PesquisaAlimentoFragment frag3 = (PesquisaAlimentoFragment) fm.findFragmentByTag("layout_pesquisa_alimento");

                if (frag3 == null) {
                    frag3 = new PesquisaAlimentoFragment();
                }
                ft.replace(R.id.layout_direito, frag3, "layout_pesquisa_alimento");
                ft.commit();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        assert navigationView != null;
        View headerLayout = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        nomePerfil = (TextView) headerLayout.findViewById(R.id.textView);
        emailPerfil = (TextView) headerLayout.findViewById(R.id.textView1);
        //  pv = (ProfilePictureView) headerLayout.findViewById(R.id.fbImgem);
        campoFotoObjeto = (ImageView) headerLayout.findViewById(R.id.imageView);


        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        //  TextView textView = new TextView(this);
        //TextView textView2 = new TextView(this);

        usuarioDAO = usuarioDAO.open();
        carregarDadosPacientesFirebase();

 /*       if (name != null) {

            //   String recebe = params.getString("nome", null);
            usuarioDAO = usuarioDAO.open();

            usuario = usuarioDAO.ler(name);
            final String nome = usuario.getNome();
            final String nutricionista = usuario.getCrn();
            final String peso = usuario.getPeso();
            final String dtNas = usuario.getDataNasc();

//byte[] foto = usuario.getFoto();

            nomePerfil.setText(" Seu nome: " + nome);
         //  nomePerfil.setText(" Seu nutricionista: " + nome);

            if (usuario.getFoto() != null)
                campoFotoObjeto.setImageBitmap(ConversorImagem.converteByteArrayPraBitmap(usuario.getFoto()));


            campoFotoObjeto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //   Intent idados = getIntent();
                    Intent alterar = new Intent(MainActivity.this, PerfilActivityOld.class);
                    startActivity(alterar);


                    //String userName=usuario.getText().toString()


                }


            });
*/

  /*          if (usuario.getFoto() != null) {

                campoFotoObjeto.setImageBitmap(ConversorImagem
                        .converteByteArrayPraBitmap(usuario
                                .getFoto()));
            }
            //    campoFotoObjeto.setImageBitmap(ConversorImagem.converteByteArrayPraBitmap(usuario.getFoto()));


        } else
            Toast.makeText(this, "foto nao encontrada", Toast.LENGTH_SHORT).show();

*/

        if (savedInstanceState == null) {
            //ListaAlimentosConsumidosFragment listafrag = new ListaAlimentosConsumidosFragment();
            //ListaAlimentosDietaNutricionistaDataFragment listafrag = new ListaAlimentosDietaNutricionistaDataFragment();
            MenuPacienteFragment listafrag = new MenuPacienteFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.layout_direito, listafrag, "frag1");
            ft.commit();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = fm.beginTransaction();


        if (id == R.id.perfil) {
 /*               Intent i = new Intent(this, ListaAlimentosConsumidosActivity.class);
                startActivity(i);
*/
            /*Intent alterar = new Intent(MainActivity.this, PerfilActivity.class);
            startActivity(alterar);
            */

            PerfilPacienteFragment perfilPaciente = new PerfilPacienteFragment();
            ft.replace(R.id.layout_direito, perfilPaciente, "perfilPaciente");


        } else if (id == R.id.home) {

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);


        } else if (id == R.id.metas) {
            Intent i = new Intent(this, GraficoAcitivity.class);
            startActivity(i);

        } else if (id == R.id.configuracoes) {


            setTitle("Teste");
            DialogFragment frag4 = (HistoricoFragment) fm.findFragmentByTag("layout_frag4");

            if (frag4 == null) {
                frag4 = new HistoricoFragment();
            }
            ft.replace(R.id.layout_direito, frag4, "layout_frag4");


        } else if (id == R.id.historico) {
            setTitle("Historico");
            DialogFragment newFragment = new HistoricoFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
       /*     DialogFragment dataHistorico = (HistoricoFragment) fm.findFragmentByTag("dataHistorico");


            if (dataHistorico == null) {
                dataHistorico = new HistoricoFragment();
            }
            ft.replace(R.id.layout_direito, dataHistorico, "dataHistorico");

*/
        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {


        } else if (id == R.id.sair) {


            session.logoutUser();

        }
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void carregarDadosPacientesFirebase() {
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> userp = session.getUserDetails();
        final String chavep = userp.get(SessionManager.KEY_NAME);

        if (mDatabaseNutri == null) {
            database = FirebaseDatabase.getInstance();
            //mDatabase = database.getReference().child("receita").child("a").child("receita");
            mDatabaseNutri = database.getReference().child("paciente");
            //   mDatabase = database.getReference().child("receita").child("-Kz1I4FOl4yYZP8eUpi3").child("alimento");


        }


        // app_title change listener
        mDatabaseNutri.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //   Receita appTitle = dataSnapshot.getValue(Receita.class);

                //  String receita = dataSnapshot.getValue(String.class).toString();
                Usuario usuario = dataSnapshot.child(chavep).getValue(Usuario.class);
                String nome = usuario.getNome().toString();
                nomePerfil.setText("Nome : " + nome);
                // update toolbar title


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
