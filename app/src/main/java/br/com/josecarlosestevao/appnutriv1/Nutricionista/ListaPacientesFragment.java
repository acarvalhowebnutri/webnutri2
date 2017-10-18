package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 25/12/2016.
 */
public class ListaPacientesFragment extends Fragment {


    private static final int MENU_APAGAR = Menu.FIRST;
    private ListView listapacientes;

    SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_lista_pacientes, null);

        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText("Fragment 1");

        listapacientes = (ListView) view.findViewById(R.id.list);

        registerForContextMenu(listapacientes);



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

    public ListView getListapacientes() {
        return listapacientes;
    }


    private void montaLisView() {
        session = new SessionManager(getContext());

        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();


        NutricionistaDAO dao = new NutricionistaDAO(getContext());
        final List<Usuario> pacientes = dao.listaTodosPacientes();



        ListaPacientesAdapter adapter = new ListaPacientesAdapter(getActivity(), android.R.layout.simple_list_item_1, pacientes);

        listapacientes.setAdapter(adapter);

        ArrayList<Consumo> alimento = new ArrayList<>();

        listapacientes.setOnItemClickListener(new ListaPacientesListener(this));

    }

    private void remove(Usuario usuario) {
        NutricionistaDAO dao = new NutricionistaDAO(getContext());
        dao.remove(usuario);
        montaLisView();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == MENU_APAGAR) {
            Usuario usuario = (Usuario) getListapacientes().getItemAtPosition(info.position);
            remove(usuario);
            Toast.makeText(getContext(), "registro removido com sucesso ", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}

