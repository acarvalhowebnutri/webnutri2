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

import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.Activiy.PesquisaAlimentoFragment;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;


public class AdaptadorPersonalizado extends BaseAdapter {

    private static final int MENU_APAGAR = Menu.FIRST;
    Context c;
    ArrayList<Consumo> planets;
    LayoutInflater inflater;
    Consumo consumo = new Consumo();

    ConsumoDAO dao = new ConsumoDAO(c);
    PesquisaAlimentoFragment save;
    SessionManager session;
    SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private int ano, mes, dia;
    private TextView dataGasto;


    public AdaptadorPersonalizado(Context c, ArrayList<Consumo> planets) {
        this.c = c;
        this.planets = planets;
    }

    @Override
    public int getCount() {
        return planets.size();
    }

    @Override
    public Consumo getItem(int position) {
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

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        final String pessoa = user.get(SessionManager.KEY_NAME);
        final Usuario u = new Usuario();
        u.setNome(pessoa);

        TextView nameTxt = (TextView) convertView.findViewById(R.id.txtNome);



        nameTxt.setText(planets.get(position).getAlimento());
        //dataTxt.setText(planets.get(position).getCarboidrato());
        //dataTxt.setText(planets.get(position).getData());

        final String a = planets.get(position).getAlimento();
        final String teste = planets.get(position).getCarboidrato();
        final String protein = planets.get(position).getProteina();

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
       final String currentDateTimeString = sdf.format(date);

       // final String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        //dataatual.setText(currentDateTimeString);

        final int pos = position;


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                consumo.setAlimento(a);
                consumo.setCarboidrato(teste);
                consumo.setProteina(protein);
                consumo.setData(currentDateTimeString);
                consumo.setUsuario(u);

                Toast.makeText(c, planets.get(position).getAlimento() + " : Adicionado", Toast.LENGTH_SHORT).show();
                Toast.makeText(c, planets.get(position).getProteina() + " : Adicionado", Toast.LENGTH_SHORT).show();
               Toast.makeText(c, consumo.getProteina() + " : valor", Toast.LENGTH_SHORT).show();


                //  Toast.makeText(c, consumo.getProteina() + " : valor", Toast.LENGTH_SHORT).show();

                //   Toast.makeText(c, consumo.getCarboidrato() + " : valor", Toast.LENGTH_SHORT).show();
                // Toast.makeText(c, consumo.getUsuario().getNome() + " : nome", Toast.LENGTH_SHORT).show();
                ConsumoDAO db = new ConsumoDAO(c);
                db.consumido(consumo);
                //db.registrarGasto(consumo);


            }

        });


        return convertView;


    }


}