package br.com.josecarlosestevao.appnutriv1.Receita;

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
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessaoDietaPaciente;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;


public class AdaptadorReceita extends BaseAdapter {

    private static final int MENU_APAGAR = Menu.FIRST;
    Context c;
    ArrayList<Receita> planets;
    LayoutInflater inflater;
    Receita receita = new Receita();

    ConsumoDAO dao = new ConsumoDAO(c);
    PesquisaAlimentoFragment save;
    SessionManager session;
    SQLiteDatabase db;
    SessaoDietaPaciente sessaopaciente;
    private DatabaseHelper dbHelper;
    private int ano, mes, dia;
    private TextView dataGasto;


    public AdaptadorReceita(Context c, ArrayList<Receita> planets) {
        this.c = c;
        this.planets = planets;
    }

    @Override
    public int getCount() {
        return planets.size();
    }

    @Override
    public Receita getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_alimento, parent, false);
        }


        session = new SessionManager(c);
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        String nutricionista = user.get(SessionManager.KEY_NAME);

/*
        NutricionistaDao daouser = new NutricionistaDao(c);
        Nutricionista nutri = daouser.ler(nutricionista);
        String nomenutri = nutri.getCrn();
        */
        TextView nameTxt = (TextView) convertView.findViewById(R.id.txtNome);
        TextView exergiaTxt = (TextView) convertView.findViewById(R.id.txtenergia);


        nameTxt.setText(planets.get(position).getAlimento());
        exergiaTxt.setText("calorias : " + planets.get(position).getEnergia());

        final String a = planets.get(position).getAlimento();
        final String data = planets.get(position).getData();
        final String tipo = planets.get(position).getTipo();

        //final Usuario paciente = planets.get(position).getUsuario();
        final Nutricionista n = new Nutricionista();
        final Usuario paciente = new Usuario();
        paciente.setNome(planets.get(position).getUsuario().getNome());
        paciente.setId(planets.get(position).getUsuario().getId());
        //n.setNome(nutricionista);
        //n.setNome(nutri.getCrn());
        n.setNome(nutricionista);



        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        final String currentDateTimeString = sdf.format(date);

        // final String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        //dataatual.setText(currentDateTimeString);

        final int pos = position;


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                receita.setAlimento(a);
                //  receita.setData(currentDateTimeString);
                receita.setUsuario(paciente);
                receita.setNutricionista(n);
                //receita.setNutricionista(n);
                receita.setData(data);
                receita.setTipo(tipo);


                ReceitaDAO db = new ReceitaDAO(c);
                db.adicionarReceita(receita);
                Toast.makeText(c, "Alimento adicionado a Receita", Toast.LENGTH_LONG).show();


            }

        });


        return convertView;


    }


}