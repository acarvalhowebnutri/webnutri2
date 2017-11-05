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

import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessaoDietaPaciente;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 25/12/2016.
 */
public class ListaPacientesFragment extends Fragment {


    private static final int MENU_APAGAR = Menu.FIRST;
    private static final int MENU_RECEITAR = Menu.NONE;
    SessionManager session;
    SessaoDietaPaciente sessaoDietaPaciente;
    private ListView listapacientes;


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
        menu.add(1, MENU_RECEITAR, 1, "RECEITAR");
    }

    public ListView getListapacientes() {
        return listapacientes;
    }


    private void montaLisView() {
        session = new SessionManager(getContext());

        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        NutricionistaDao dao = new NutricionistaDao(getContext());
        String nome = user.get(SessionManager.KEY_NAME);

        String crn = dao.pesquisarCRN(nome);


        final List<Usuario> pacientes = dao.listaPacientesCRN(crn);



        ListaPacientesAdapter adapter = new ListaPacientesAdapter(getActivity(), android.R.layout.simple_list_item_1, pacientes);

        listapacientes.setAdapter(adapter);

        ArrayList<Usuario> alimento = new ArrayList<>();

        listapacientes.setOnItemClickListener(new ListaPacientesListener(this));

    }

    private void remove(Usuario usuario) {
        NutricionistaDAOold dao = new NutricionistaDAOold(getContext());
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
        if (item.getItemId() == MENU_RECEITAR) {
            Usuario usuariodois = (Usuario) getListapacientes().getItemAtPosition(info.position);
            String nome = usuariodois.getNome();
            //    sessaoDietaPaciente = new SessaoDietaPaciente(getContext());

            //  sessaoDietaPaciente.createLoginSession(nome);
           /* remove(usuario);
            Toast.makeText(getContext(), "registro removido com sucesso ", Toast.LENGTH_LONG).show();*/


            PesquisaAlimentoReceitaPacienteFragment fragment = new PesquisaAlimentoReceitaPacienteFragment();
            // fragment.setArguments(arguments);
            Bundle bundle = new Bundle();
            bundle.putString("username", nome);
            // bundle.putString("senha", user_pass);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_direito_nutricionista, fragment).commit();


            return true;
        }
        return super.onContextItemSelected(item);
    }
}

