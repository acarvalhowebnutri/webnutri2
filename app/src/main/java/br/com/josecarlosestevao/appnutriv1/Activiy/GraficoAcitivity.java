package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Metas.Metas;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

/**
 * Created by Dell on 15/05/2017.
 */
public class GraficoAcitivity extends Activity {


    ConsumoDAO consumoAdapter;
    UsuarioDAO loginAdapter;
    Usuario usuario;
    SessionManager session;
    TextView n, np;
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    Double res;
    Double protein;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafico_layout);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        ConsumoDAO dao = new ConsumoDAO(getApplicationContext());

        //String teste = dao.pesquisarValor(name);
        // Double resultado = dao.somaValor(name);
        //res = Double.valueOf(dao.somarCategoria(name));
        //protein = Double.valueOf(dao.somarProtein(name));


        n = (TextView) findViewById(R.id.txtn);
        np = (TextView) findViewById(R.id.txtp);

        //buscaNecessidadesDRI(name);
        carregarDadosFirebase();
        // grafitoCaboidrato(res);
        //grafigoProteina(protein);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);


        return true;
    }


    public void grafitoCaboidrato(Double rescarb) {

        ProgressBar myprogressbar = (ProgressBar) findViewById(R.id.myprogressbar);
        if (rescarb == null)
            n.setText("campo vazio");
        else {
            //Double t=consumo.getCarboidrato();
            n.setText(rescarb.toString());


            //  EditText etcomusers = (EditText) findViewById(R.id.comusersnumber);
            // double commiteduser = Double.parseDouble(etcomusers.getText().toString
            double commiteduser = rescarb;

            //EditText ettotcomusers = (EditText) findViewById(R.id.totalcomusersnumber);
            // double totalcommiteduser = Double.parseDouble(ettotcomusers.getText().toString());
            double totalcommiteduser = 100.00;

            int percent = (int) ((commiteduser / totalcommiteduser) * 100);


            Resources res = getResources();
            Rect bounds = myprogressbar.getProgressDrawable().getBounds();

            if (percent >= 50) {
                myprogressbar.setProgressDrawable(res.getDrawable(R.drawable.greenprogressbar));
            } else {
                myprogressbar.setProgressDrawable(res.getDrawable(R.drawable.redprogressbar));
            }
            myprogressbar.getProgressDrawable().setBounds(bounds);
            myprogressbar.setProgress(percent);
        }

    }

    public void grafigoProteina(String vitamin, String vitamax) {
        ProgressBar myprogressbarpro = (ProgressBar) findViewById(R.id.myprogressbar01);


        if (vitamin == null)
            np.setText("campo vazio");
        else {
            //Double t=consumo.getCarboidrato();
            np.setText(vitamin.toString());
            // EditText etcomusers = (EditText) findViewById(R.id.comusersnumber);
            // double commiteduser = Double.parseDouble(etcomusers.getText().toString
            String variavel = vitamin;
            // String soNumeros = variavel.substring(variavel.length() - 4);
            String soNumeros = variavel.replaceAll("[^0-9]", "");
            double commiteduserpro = Double.parseDouble(soNumeros);

            //   EditText ettotcomusers = (EditText) findViewById(R.id.totalcomusersnumber);
            // double totalcommiteduser = Double.parseDouble(ettotcomusers.getText().toString());
            double totalcommiteduserpro = 100.00;

            int percentpro = (int) ((commiteduserpro / totalcommiteduserpro) * 100);


            Resources respro = getResources();
            Rect boundspro = myprogressbarpro.getProgressDrawable().getBounds();

            if (percentpro >= 50) {
                myprogressbarpro.setProgressDrawable(respro.getDrawable(R.drawable.greenprogressbar));
            } else {
                myprogressbarpro.setProgressDrawable(respro.getDrawable(R.drawable.redprogressbar));
            }
            myprogressbarpro.getProgressDrawable().setBounds(boundspro);
            myprogressbarpro.setProgress(percentpro);
        }

    }



    /*
    public void Calculate(View view) {
        if (view.getId() == R.id.btncalc) {


            //Double valor = usuario.getValor();


            EditText etcomusers = (EditText) findViewById(R.id.comusersnumber);
            // double commiteduser = Double.parseDouble(etcomusers.getText().toString
             double commiteduser = res;

            EditText ettotcomusers = (EditText) findViewById(R.id.totalcomusersnumber);
            double totalcommiteduser = Double.parseDouble(ettotcomusers.getText().toString());


            int percent = (int) ((commiteduser / totalcommiteduser) * 100);


            ProgressBar myprogressbar = (ProgressBar) findViewById(R.id.myprogressbar);

            Resources res = getResources();
            Rect bounds = myprogressbar.getProgressDrawable().getBounds();

            if (percent >= 50) {
                myprogressbar.setProgressDrawable(res.getDrawable(R.drawable.greenprogressbar));
            } else {
                myprogressbar.setProgressDrawable(res.getDrawable(R.drawable.redprogressbar));
            }
            myprogressbar.getProgressDrawable().setBounds(bounds);
            myprogressbar.setProgress(percent);
        }
    }*/

    private void carregarDadosFirebase() {
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        final String chave = user.get(SessionManager.KEY_NAME);

        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            //mDatabase = database.getReference().child("receita").child("a").child("receita");
            mDatabase = database.getReference().child("metas");
            //   mDatabase = database.getReference().child("receita").child("-Kz1I4FOl4yYZP8eUpi3").child("alimento");


        }


        // app_title change listener
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //   Receita appTitle = dataSnapshot.getValue(Receita.class);

                //  String receita = dataSnapshot.getValue(String.class).toString();
                Metas metas = dataSnapshot.child(chave).getValue(Metas.class);
                // String nome = nutricionista.getNome().toString();
                //nomePerfil.setText("Nome : " + nome);

                String vitamin = metas.getVitamin();
                String vitamax = metas.getVitamax();


                grafigoProteina(vitamin, vitamax);
                /*ednome.setText(usuario.getNome());
                edpeso.setText(usuario.getPeso());
                edsexo.setText(usuario.getSexo());
                edidade.setText(usuario.getDataNasc());
                //  nomeNUtriTextView.setText(usuario.getCrn());
                // update toolbar title
*/
/*
                if (usuario.getSexo() == null) {
                    radioButtonMasc.isChecked();
                    Toast.makeText(getContext(), "sexo padrao adicionado", Toast.LENGTH_LONG).show();
                }else if (usuario.getSexo() == "masculino"){
                    radioButtonMasc.setChecked(selecionouSexoMasculino);
                } if (usuario.getSexo()=="feminino")
                    radioButtonFem.setChecked(selecionouSexoFem);

*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Metas buscaNecessidadesDRI(String chave, String datanasc, String sexo) {


        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        final String currentDateTimeString = sdf.format(date);
        int corrente = Integer.parseInt(currentDateTimeString);
        final String dtnas = sdf.format(datanasc);
        int ds = Integer.parseInt(dtnas);

        int idade = (corrente - ds);

        UsuarioDAO db = new UsuarioDAO(getApplicationContext());
        db.open();
        Metas p = null;
        Cursor cn = db.recuperaDadosDRI(idade, sexo);
        while (cn.moveToNext()) {
            long id = cn.getInt(0);
            String vitamin = cn.getString(1);
            String vitamax = cn.getString(2);


            p = new Metas();
            p.setVitamin(vitamin);
            p.setVitamax(vitamax);
            p.setIdPacienteFB(chave);
            //p.setIdPacienteFB();

            db.alterarMetasFirebase(p);
            //p.setId(idfb);


        }


        db.close();
        return p;
      /*  ArrayAdapter<Consumo> adapter = new ListaAlimentosConsumidosAdapter(this,
                android.R.layout.simple_list_item_1, alimento);*/
        // lv.setAdapter(adapter);


    }


    private void carregarIdadeFirebase() {
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        final String chave = user.get(SessionManager.KEY_NAME);

        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            //mDatabase = database.getReference().child("receita").child("a").child("receita");
            mDatabase = database.getReference().child("paciente");
            //   mDatabase = database.getReference().child("receita").child("-Kz1I4FOl4yYZP8eUpi3").child("alimento");


        }


        // app_title change listener
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //   Receita appTitle = dataSnapshot.getValue(Receita.class);

                //  String receita = dataSnapshot.getValue(String.class).toString();
                Usuario usuario = dataSnapshot.child(chave).getValue(Usuario.class);
                // String nome = nutricionista.getNome().toString();
                //nomePerfil.setText("Nome : " + nome);
                String datanasc = usuario.getDataNasc();
                String sexo = usuario.getSexo();


                buscaNecessidadesDRI(chave, datanasc, sexo);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
