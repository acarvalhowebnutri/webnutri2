package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
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
public class ListaPacientesFragment extends DialogFragment {


    private static final int MENU_APAGAR = Menu.FIRST;
    private static final int MENU_RECEITAR = Menu.NONE;
    private static final int MENU_Calendario_Teste = Menu.NONE;
    private static final int MENU_HISTORICO = 3;
    final List<Usuario> lista = new ArrayList<Usuario>();
    SessionManager session;
    SessaoDietaPaciente sessaoDietaPaciente;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    private ListView listapacientes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_lista_pacientes, null);

        TextView tv = (TextView) view.findViewById(R.id.textView1);


        listapacientes = (ListView) view.findViewById(R.id.listViewPaciente);

        registerForContextMenu(listapacientes);


        return (view);

    }

    @Override
    public void onResume() {
        super.onResume();

        //montaLisView();
   // montaLisViewFirebase();


    }

    @Override
    public void onStart() {
        super.onStart();

        montaLisViewFirebase();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_APAGAR, 0, "APAGAR");
        menu.add(1, MENU_RECEITAR, 1, "RECEITAR");
        menu.add(1, MENU_HISTORICO, 1, "VER HISTORICO");

    }

    public ListView getListapacientes() {
        return listapacientes;
    }

    private void montaLisViewFirebase() {
        session = new SessionManager(getContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String chave = user.get(SessionManager.KEY_NAME);
        // name

        // final String link1 = DateFormat.getDateInstance().format(new Date());
        //dataatual.setText(currentDateTimeString);
        //String name = user.get(SessionManager.KEY_NAME);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        final String currentDateTimeString = sdf.format(date);


        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            //mDatabase = database.getReference().child("receita").child("a").child("receita");
            mDatabase = database.getReference();
            //   mDatabase = database.getReference().child("receita").child("-Kz1I4FOl4yYZP8eUpi3").child("alimento");

        }

        Query query = mDatabase.child("paciente").orderByChild("crn").equalTo(chave).limitToFirst(1);

        // app_title change listener
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                lista.clear();

                //   Receita appTitle = dataSnapshot.getValue(Receita.class);
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    Usuario usuario = artistSnapshot.getValue(Usuario.class);

                    //   String teste = receita.alimento;
                    //.child("receita")
                    //.child("a")
                    //.child("receita")


                    lista.add(usuario);


                    // update toolbar title
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ListaPacientesAdapter adapter = new ListaPacientesAdapter(getActivity(), android.R.layout.simple_list_item_1, lista);

        listapacientes.setAdapter(adapter);

        ArrayList<Usuario> alimento = new ArrayList<>();

        listapacientes.setOnItemClickListener(new ListaPacientesListener(this));

    }

/*
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
    */

    private void remove(Usuario usuario) {
        NutricionistaDAOold dao = new NutricionistaDAOold(getContext());
        dao.remove(usuario);
        montaLisViewFirebase();
        //      montaLisView();
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
            String crnpaciente = usuariodois.getCrn();
            String nomePaciente = usuariodois.getNome();
            String idpaciente = usuariodois.getId();


            // EscolherDataReceitaFragment fragment = new EscolherDataReceitaFragment();
            DialogFragment fragment = new EscolherDataReceitaFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", crnpaciente);
            bundle.putString("nomepaciente", nomePaciente);
            bundle.putString("idpaciente", idpaciente);
            fragment.setArguments(bundle);
            fragment.show(getActivity().getSupportFragmentManager(), "datePicker");

            //////////////////////////////////////////////////////////////////////////////
           /* Usuario usuariodois = (Usuario) getListapacientes().getItemAtPosition(info.position);
            String nome = usuariodois.getNome();



            PesquisaAlimentoReceitaPacienteFragment fragment = new PesquisaAlimentoReceitaPacienteFragment();
            // fragment.setArguments(arguments);

            Bundle bundle = new Bundle();
            bundle.putString("username", nome);
            // bundle.putString("senha", user_pass);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_direito_nutricionista, fragment).commit();*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            return true;
        }

        if (item.getItemId() == MENU_HISTORICO) {
            Usuario usuariodois = (Usuario) getListapacientes().getItemAtPosition(info.position);
            String crnpaciente = usuariodois.getCrn();
            String nomePaciente = usuariodois.getNome();
            String idpaciente = usuariodois.getId();


            // EscolherDataReceitaFragment fragment = new EscolherDataReceitaFragment();
            DialogFragment fragment = new HistoricoConsumidosPacienteFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", crnpaciente);
            bundle.putString("nomepaciente", nomePaciente);
            bundle.putString("idpaciente", idpaciente);
            fragment.setArguments(bundle);
            fragment.show(getActivity().getSupportFragmentManager(), "datePicker");

            //////////////////////////////////////////////////////////////////////////////
           /* Usuario usuariodois = (Usuario) getListapacientes().getItemAtPosition(info.position);
            String nome = usuariodois.getNome();



            PesquisaAlimentoReceitaPacienteFragment fragment = new PesquisaAlimentoReceitaPacienteFragment();
            // fragment.setArguments(arguments);

            Bundle bundle = new Bundle();
            bundle.putString("username", nome);
            // bundle.putString("senha", user_pass);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_direito_nutricionista, fragment).commit();*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            return true;
        }


        return super.onContextItemSelected(item);
    }


}

