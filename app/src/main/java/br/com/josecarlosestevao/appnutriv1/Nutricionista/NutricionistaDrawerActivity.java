package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.Activiy.HistoricoFragment;
import br.com.josecarlosestevao.appnutriv1.Activiy.PerfilActivityOld;
import br.com.josecarlosestevao.appnutriv1.Constantes.ConversorImagem;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;

public class NutricionistaDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager session;
    FragmentManager fm = getSupportFragmentManager();
    Nutricionista nutricionista;
    Activity context = this;
    NutricionistaDao nutricionistaDao;
    TextView nomePerfil;
    TextView emailPerfil;
    private ImageView campoFotoObjeto;
    private String text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutricionista_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (nutricionistaDao == null) {
            nutricionistaDao = new NutricionistaDao(context);
        }
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);



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

        if (name != null) {

            //   String recebe = params.getString("nome", null);
            nutricionistaDao = nutricionistaDao.open();

            nutricionista = nutricionistaDao.ler(name);
            final String nome = nutricionista.getNome();
            // final String peso = nutricionista.getPeso();
            //final String dtNas = nutricionista.getDataNasc();

//byte[] foto = nutricionista.getFoto();

            nomePerfil.setText(nome);

            if (nutricionista.getFoto() != null)
                campoFotoObjeto.setImageBitmap(ConversorImagem.converteByteArrayPraBitmap(nutricionista.getFoto()));


            campoFotoObjeto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //   Intent idados = getIntent();
                    Intent alterar = new Intent(NutricionistaDrawerActivity.this, PerfilActivityOld.class);
                    startActivity(alterar);


                    //String userName=nutricionista.getText().toString()


                }


            });

            if (savedInstanceState == null) {
                ListaPacientesFragment listapaciente = new ListaPacientesFragment();

                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.layout_direito_nutricionista, listapaciente, "frag1");
                ft.commit();
            }
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
        getMenuInflater().inflate(R.menu.nutricionista_drawer, menu);
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

            setTitle("Perfil");
            PerfilNutricionistaFragment frag_perfil_nutri = (PerfilNutricionistaFragment) fm.findFragmentByTag("frag_perfil_nutri");

            if (frag_perfil_nutri == null) {
                frag_perfil_nutri = new PerfilNutricionistaFragment();
            }
            ft.replace(R.id.layout_direito, frag_perfil_nutri, "frag_perfil_nutri");

        } else if (id == R.id.home) {

            Intent i = new Intent(this, NutricionistaDrawerActivity.class);
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
        } else if (id == R.id.sair) {


            session.logoutUser();

        }
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
