package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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

import br.com.josecarlosestevao.appnutriv1.Activiy.PerfilActivityOld;
import br.com.josecarlosestevao.appnutriv1.Activiy.PesquisaAlimentoFragment;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;

public class NutricionistaDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager session;
    FragmentManager fm = getSupportFragmentManager();
    Nutricionista usuario;
    Activity context = this;
    NutricionistaDao usuarioDAO;
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

        if (usuarioDAO == null) {
            usuarioDAO = new NutricionistaDao(context);
        }
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  /*              Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
                FragmentTransaction ft = fm.beginTransaction();

                setTitle("Pesquisar");
                PesquisaAlimentoFragment frag3 = (PesquisaAlimentoFragment) fm.findFragmentByTag("layout_frag3");

                if (frag3 == null) {
                    frag3 = new PesquisaAlimentoFragment();
                }
                ft.replace(R.id.layout_direito, frag3, "layout_frag3");
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

        if (name != null) {

            //   String recebe = params.getString("nome", null);
            usuarioDAO = usuarioDAO.open();

            usuario = usuarioDAO.ler(name);
            final String nome = usuario.getNome();
           // final String peso = usuario.getPeso();
            //final String dtNas = usuario.getDataNasc();

//byte[] foto = usuario.getFoto();

            nomePerfil.setText(nome);

            //if (usuario.getFoto() != null)
              //  campoFotoObjeto.setImageBitmap(ConversorImagem.converteByteArrayPraBitmap(usuario.getFoto()));


            campoFotoObjeto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //   Intent idados = getIntent();
                    Intent alterar = new Intent(NutricionistaDrawerActivity.this, PerfilActivityOld.class);
                    startActivity(alterar);


                    //String userName=usuario.getText().toString()


                }


            });

            if (savedInstanceState == null) {
                ListaPacientesFragment listapaciente = new ListaPacientesFragment();

                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.layout_direito, listapaciente, "frag1");
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
