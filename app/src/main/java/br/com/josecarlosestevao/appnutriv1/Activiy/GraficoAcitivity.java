package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
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
        res = Double.valueOf(dao.somarCategoria(name));
        protein = Double.valueOf(dao.somarProtein(name));


        n = (TextView) findViewById(R.id.txtn);
        np = (TextView) findViewById(R.id.txtp);


        grafitoCaboidrato(res);
        grafigoProteina(protein);

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

    public void grafigoProteina(Double proteina) {
        ProgressBar myprogressbarpro = (ProgressBar) findViewById(R.id.myprogressbar01);



        if (proteina == null)
            np.setText("campo vazio");
        else {
            //Double t=consumo.getCarboidrato();
            np.setText(proteina.toString());
            // EditText etcomusers = (EditText) findViewById(R.id.comusersnumber);
            // double commiteduser = Double.parseDouble(etcomusers.getText().toString
            double commiteduserpro = proteina;

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
}
