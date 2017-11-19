package br.com.josecarlosestevao.appnutriv1.Listas;

import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;

/**
 * Created by Dell on 25/12/2016.
 */
public class ListaAlimentosConsumidosFragment extends Fragment {


    private static final int MENU_APAGAR = Menu.FIRST;
    SessionManager session;
    private ListView listaAlimentosConsumidos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag1, null);

        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText("Fragment 1");

        listaAlimentosConsumidos = (ListView) view.findViewById(R.id.list);

        registerForContextMenu(listaAlimentosConsumidos);



        return (view);
    }

    @Override
    public void onResume() {
        super.onResume();

        montaLisView();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_APAGAR, 0, "APAGAR");
    }

    public ListView getListaAlimentosConsumidos() {
        return listaAlimentosConsumidos;
    }


    private void montaLisView() {
        session = new SessionManager(getContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name

       // final String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        final String currentDateTimeString = sdf.format(date);
        //dataatual.setText(currentDateTimeString);

        String name = user.get(SessionManager.KEY_NAME);
        ConsumoDAO dao = new ConsumoDAO(getContext());
      //  final List<Consumo> alimentosConsumidos = dao.listaConsumidos(name);
        final List<Consumo> alimentosConsumidos = dao.listaConsumidosNew(name, currentDateTimeString);


        ArrayAdapter<Consumo> adapter = new ListaAlimentosConsumidosAdapter(getActivity(), android.R.layout.simple_list_item_1, alimentosConsumidos);

        listaAlimentosConsumidos.setAdapter(adapter);

        ArrayList<Consumo> alimento = new ArrayList<>();

        listaAlimentosConsumidos.setOnItemClickListener(new ListaAlimentosConsumidosListener(this));
    }

    private void remove(Consumo consumo) {
        ConsumoDAO dao = new ConsumoDAO(getContext());
        dao.remove(consumo);
        montaLisView();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == MENU_APAGAR) {
            Consumo consumo = (Consumo) getListaAlimentosConsumidos().getItemAtPosition(info.position);
            remove(consumo);
            Toast.makeText(getContext(), "registro removido com sucesso ", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}

