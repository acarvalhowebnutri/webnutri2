package br.com.josecarlosestevao.appnutriv1.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Receita.Receita;
import br.com.josecarlosestevao.appnutriv1.Receita.ReceitaDAO;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

/**
 * Created by Dell on 25/12/2016.
 */
public class ListaAlimentosDietaNutricionistaDataFragment extends Fragment {

    // private static final int MENU_APAGAR = Menu.FIRST;
    private static final int MENU_ADICIONAR_CONSUMO = Menu.FIRST;
    SessionManager session;
    Intent intent = new Intent();
    String dburl = intent.getStringExtra("databaseUrl");
    Usuario usuario;
    private ListView listaAlimentosDietaNutricionistaDataFragment;

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
    public void onResume() {
        super.onResume();

        montaLisViewNew();

    }

    public ListView getListaAlimentosDietaNutricionistaDataFragment() {

        return listaAlimentosDietaNutricionistaDataFragment;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_ADICIONAR_CONSUMO, 0, "Adinar consumo");
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
            consumo.setData(currentDateTimeString);


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

            consumo.setUsuario(usuario);
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

}

