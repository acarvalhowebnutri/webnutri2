package br.com.josecarlosestevao.appnutriv1.Constantes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.Activiy.PesquisaAlimentoFragment;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.NutricionistaDao;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;


public class AdaptadorNutricionista extends BaseAdapter {

    private static final int MENU_APAGAR = Menu.FIRST;
    Context c;
    ArrayList<Nutricionista> planets;
    LayoutInflater inflater;
    Usuario consumo = new Usuario();
    Usuario usuario;

    NutricionistaDao dao = new NutricionistaDao(c);
    PesquisaAlimentoFragment save;
    SessionManager session;
    SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private int ano, mes, dia;
    private TextView dataGasto;


    public AdaptadorNutricionista(Context c, ArrayList<Nutricionista> planets) {
        this.c = c;
        this.planets = planets;
    }

    @Override
    public int getCount() {
        return planets.size();
    }

    @Override
    public Nutricionista getItem(int position) {
        return planets.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_nutricionista, parent, false);
        }


        session = new SessionManager(c);

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        final String pessoa = user.get(SessionManager.KEY_NAME);
        final Usuario u = new Usuario();
        u.setNome(pessoa);

        TextView nameTxt = (TextView) convertView.findViewById(R.id.txtNome);
        TextView emailTxt = (TextView) convertView.findViewById(R.id.txtemail);
        TextView crnTxt = (TextView) convertView.findViewById(R.id.txtcrn);


        final String nomenut = planets.get(position).getNome();
        final String email = planets.get(position).getEmail();
        final String crnnut = planets.get(position).getCrn();


        nameTxt.setText(nomenut);
        emailTxt.setText(email);
        crnTxt.setText(crnnut);
        //dataTxt.setText(planets.get(position).getCarboidrato());
        //dataTxt.setText(planets.get(position).getData());


        //  final String protein = planets.get(position).getProteina();

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        final String currentDateTimeString = sdf.format(date);

        // final String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        //dataatual.setText(currentDateTimeString);

        final int pos = position;


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (usuario == null) {
                    usuario = new Usuario();
                }



                UsuarioDAO db = new UsuarioDAO(c);
                String recebeidfb = db.lerIDFB(pessoa);
                usuario.setCrn(crnnut);
                usuario.setNome(pessoa);
                usuario.setImc(recebeidfb);
                db.alterarNutricionistaNoFirebase(usuario);
                Toast.makeText(c, "Nutricionista adicionado", Toast.LENGTH_LONG).show();


                //db.registrarGasto(consumo);




            }

        });


        return convertView;


    }


}