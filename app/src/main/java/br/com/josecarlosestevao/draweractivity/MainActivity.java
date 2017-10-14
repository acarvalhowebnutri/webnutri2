package br.com.josecarlosestevao.draweractivity;

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

import br.com.josecarlosestevao.draweractivity.fragment.Fragment1;
import br.com.josecarlosestevao.draweractivity.fragment.Fragment2;
import br.com.josecarlosestevao.draweractivity.fragment.Fragment3;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
                FragmentTransaction ft = fm.beginTransaction();

                setTitle("Pesquisar");
                Fragment3 frag3 = (Fragment3)fm.findFragmentByTag("layout_frag3");

                if(frag3 == null) {
                    frag3 = new Fragment3();
                }
                ft.replace(R.id.layout_direito, frag3, "layout_frag3");
                ft.commit();
/*
                setTitle("Refeicao");
                Fragment2 frag2 = (Fragment2)fm.findFragmentByTag("frag2");

                if(frag2 == null) {
                    frag2 = new Fragment2();
                }
                ft.replace(R.id.layout_direito, frag2, "frag2");
                ft.commit();
*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(savedInstanceState == null){
            Fragment1 frag1 = new Fragment1();

            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.layout_direito, frag1, "frag1");
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
            //Intent i = new Intent(this, ListaAlimentosConsumidosActivity.class);
            //startActivity(i);



            // Handle the camera action
        } else if (id == R.id.home) {

            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);

            /*Fragment1 frag1 = (Fragment1)fm.findFragmentByTag("frag1");

            if(frag1 == null) {
                frag1 = new Fragment1();
            }
            ft.replace(R.id.layout_direito, frag1, "frag1");
*/

        }


        else if (id == R.id.metas) {
           setTitle("Refeicao");
            Fragment2 frag2 = (Fragment2)fm.findFragmentByTag("frag2");

            if(frag2 == null) {
                frag2 = new Fragment2();
            }
            ft.replace(R.id.layout_direito, frag2, "frag2");
         //   Intent i = new Intent(this, CadastrarAlimentoActivity.class);
           // startActivity(i);

        } else if (id == R.id.configuracoes) {




        } else if (id == R.id.historico) {
            Intent i = new Intent(this, ListaAlimentosConsumidosActivity.class);
            startActivity(i);



        }  else if (id == R.id.nav_share) {

            setTitle("Refeicao");
            Fragment3 frag3 = (Fragment3)fm.findFragmentByTag("frag3");

            if(frag3 == null) {
                frag3 = new Fragment3();
            }
            ft.replace(R.id.layout_direito, frag3, "frag3");
            //   Intent i = new Intent(this, CadastrarAlimentoActivity.class);
            // startActivity(i);

        } else if (id == R.id.nav_send) {
            Intent i = new Intent(this, CadastrarAlimentoActivity.class);
            startActivity(i);

        }
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
