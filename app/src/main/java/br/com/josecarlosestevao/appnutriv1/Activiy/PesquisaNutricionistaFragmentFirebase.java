package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessaoDietaPaciente;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.ListaPacientesAdapter;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.NutricionistaDAOold;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

/**
 * Created by Dell on 06/01/2017.
 */
public class PesquisaNutricionistaFragmentFirebase extends Fragment {

    private static final int MENU_ESCOLHER = Menu.FIRST;
    private static final int MENU_RECEITAR = Menu.NONE;
    private static final int MENU_Calendario_Teste = Menu.NONE;
    final List<Usuario> lista = new ArrayList<Usuario>();
    UsuarioDAO dao;
    SessionManager session;
    SessaoDietaPaciente sessaoDietaPaciente;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    Button pesuisar;
    EditText pesquisarEditText;
    Usuario paciente;
    String idnutricionista;
    private ListView listapacientes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_lista_nutricionista, null);

        TextView tv = (TextView) view.findViewById(R.id.textView1);


        listapacientes = (ListView) view.findViewById(R.id.listViewNutriocionista);
        pesquisarEditText = (EditText) view.findViewById(R.id.pesquisaEdit);
        pesuisar = (Button) view.findViewById(R.id.btnpesquisar);
        registerForContextMenu(listapacientes);


        pesuisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recebeemail = pesquisarEditText.getText().toString();
                montaLisViewFirebase(recebeemail);

            }
        });

        return (view);

    }

    @Override
    public void onResume() {
        super.onResume();

        //montaLisView();
        // montaLisViewFirebase();


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_ESCOLHER, 0, "Adicionar Nutricionista");
        //menu.add(1, MENU_RECEITAR, 1, "RECEITAR");

    }

    public ListView getListapacientes() {
        return listapacientes;
    }

    private void montaLisViewFirebase(String recebeemail) {
        session = new SessionManager(getContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String chave = user.get(SessionManager.KEY_NAME);
        // name


        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            //mDatabase = database.getReference().child("receita").child("a").child("receita");
            mDatabase = database.getReference();
            //   mDatabase = database.getReference().child("receita").child("-Kz1I4FOl4yYZP8eUpi3").child("alimento");

        }

        Query query = mDatabase.child("nutricionista").orderByChild("email").equalTo(recebeemail).limitToFirst(1);

        // app_title change listener
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                lista.clear();

                //   Receita appTitle = dataSnapshot.getValue(Receita.class);
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    final Usuario usuario = artistSnapshot.getValue(Usuario.class);

                    //   String teste = receita.alimento;
                    //.child("receita")
                    //.child("a")
                    //.child("receita")


                    lista.add(usuario);

                    idnutricionista = usuario.getId();
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

        //listapacientes.setOnItemClickListener(new ListaPacientesListener(this));

    }


    private void remove(Usuario usuario) {
        NutricionistaDAOold dao = new NutricionistaDAOold(getContext());
        dao.remove(usuario);
        //   montaLisViewFirebase();
        //      montaLisView();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        session = new SessionManager(getContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name

        // final String link1 = DateFormat.getDateInstance().format(new Date());
        //dataatual.setText(currentDateTimeString);
        String name = user.get(SessionManager.KEY_NAME);

        if (paciente == null) {
            paciente = new Usuario();
        }

        if (dao == null) {
            dao = new UsuarioDAO(getContext());
        }
        paciente.setNome(name);


        if (item.getItemId() == MENU_ESCOLHER) {
            Usuario paciente = (Usuario) getListapacientes().getItemAtPosition(info.position);
            paciente.setId(name);
            paciente.setCrn(idnutricionista);
            dao.alterarNutricionistaNoFirebase(paciente);
            Toast.makeText(getContext(), "registro removido com sucesso ", Toast.LENGTH_LONG).show();
            return true;
        }


        return super.onContextItemSelected(item);
    }


}


