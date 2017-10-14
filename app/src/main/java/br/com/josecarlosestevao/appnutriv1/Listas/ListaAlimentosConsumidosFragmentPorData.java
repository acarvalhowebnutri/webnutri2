package br.com.josecarlosestevao.appnutriv1.Listas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.Alimento.Consumo;
import br.com.josecarlosestevao.appnutriv1.Alimento.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;

/**
 * Created by Dell on 25/12/2016.
 */
public class ListaAlimentosConsumidosFragmentPorData extends Fragment {

    private static final int MENU_APAGAR = Menu.FIRST;
    ListView listaAlimentosConsumidosData;

    SessionManager session;

    Intent intent = new Intent();
    String dburl = intent.getStringExtra("databaseUrl");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag4, null);

        listaAlimentosConsumidosData = (ListView) view.findViewById(R.id.listdata);

        registerForContextMenu(listaAlimentosConsumidosData);

       // return super.onCreateView(inflater, container, savedInstanceState);


        return(view);

    }


    @Override
    public void onResume() {
        super.onResume();

       montaLisViewNew();

    }

    public ListView getListaAlimentosConsumidosData() {

        return listaAlimentosConsumidosData;
    }


    private void montaLisViewNew() {

        // List<Consumo>   alimentosConsumidosNew=null;
        String link=null;
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
        ConsumoDAO daoNew = new ConsumoDAO(getContext());
        //  final List<Consumo> alimentosConsumidos = dao.listaConsumidos(name);
        final List<Consumo> alimentosConsumidosNew = daoNew.listaConsumidosNew(name, link);


        ArrayAdapter<Consumo> adapterNew = new ListaAlimentosConsumidosAdapterData(getActivity(),
                android.R.layout.simple_list_item_1, alimentosConsumidosNew);

        listaAlimentosConsumidosData.setAdapter(adapterNew);

        ArrayList<Consumo> alimentoNew = new ArrayList<>();

        listaAlimentosConsumidosData.setOnItemClickListener(new ListaAlimentosConsumidosListenerData(this));

    }

    }

/*
    private static final int MENU_APAGAR = Menu.FIRST;
    private ListView listaAlimentosConsumidosData;

    SessionManager session;

    Intent intent = new Intent();
    String dburl = intent.getStringExtra("databaseUrl");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag4, null);

        TextView tv = (TextView) view.findViewById(R.id.textViewdata);
        tv.setText("Fragment 1");

        listaAlimentosConsumidosData = (ListView) view.findViewById(R.id.listdata);

        registerForContextMenu(listaAlimentosConsumidosData);


       // Bundle params = intent.getExtras();
        return (view);    }

    @Override
    public void onResume() {
        super.onResume();

        montaLisViewNew();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_APAGAR, 0, "APAGAR");
    }



    public ListView getListaAlimentosConsumidosData() {

        return listaAlimentosConsumidosData;
    }


    private void montaLisViewNew() {

      // List<Consumo>   alimentosConsumidosNew=null;

        Bundle bundle = getArguments();
        if (bundle != null) {
            String link = bundle.getString("link");
        }
        session = new SessionManager(getContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name

        final String link = DateFormat.getDateInstance().format(new Date());
        //dataatual.setText(currentDateTimeString);

        String name = user.get(SessionManager.KEY_NAME);
        ConsumoDAO daoNew = new ConsumoDAO(getContext());
      //  final List<Consumo> alimentosConsumidos = dao.listaConsumidos(name);
        final List<Consumo> alimentosConsumidosNew = daoNew.listaConsumidosNew(name, link);


        ArrayAdapter<Consumo> adapterNew = new ListaAlimentosConsumidosAdapterData(getActivity(),
                android.R.layout.simple_list_item_1, alimentosConsumidosNew);

        listaAlimentosConsumidosData.setAdapter(adapterNew);

        ArrayList<Consumo> alimentoNew = new ArrayList<>();

        listaAlimentosConsumidosData.setOnItemClickListener(new ListaAlimentosConsumidosListenerData(this));


    }

    private void remove(Consumo consumo) {
        ConsumoDAO dao = new ConsumoDAO(getContext());
        dao.remove(consumo);
        montaLisViewNew();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == MENU_APAGAR) {
            Consumo consumo = (Consumo) getListaAlimentosConsumidosData().getItemAtPosition(info.position);
            remove(consumo);
            Toast.makeText(getContext(), "registro removido com sucesso ", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
*/