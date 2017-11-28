package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.ListaNutricionistaAdapter;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 06/01/2017.
 */
public class PesquisaNutricionistaFirebaseFragment extends Fragment {


    final List<Nutricionista> lista = new ArrayList<Nutricionista>();
    //ArrayList<Consumo> alimento = new ArrayList<>();
    final List<Nutricionista> cafeDaManhalist = new ArrayList<Nutricionista>();
    public TextView txtdata;
    EditText sv;
    Button btnpesquisar;
    SessionManager session;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_lista_nutricionista, null);

        lv = (ListView) view.findViewById(R.id.listViewNutriocionista);
        sv = (EditText) view.findViewById(R.id.pesquisaEdit);
        btnpesquisar = (Button) view.findViewById(R.id.btnpesquisar);



        //montaLisViewFirebase();
        // usuarioDAO.deleFromNutricionista();
        //carregarNutricionistasParaSQLite();




        btnpesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                montaLisViewFirebase();
            }
        });

        return (view);
    }

    @Override
    public void onResume() {
        super.onResume();

        //montaLisViewNew();

        //  montaListaFirebaseCopia();


    }

    private void montaLisViewFirebase() {
        session = new SessionManager(getContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String emailnutricionista = sv.getText().toString();


        // name

        // final String link1 = DateFormat.getDateInstance().format(new Date());
        //dataatual.setText(currentDateTimeString);
        String name = user.get(SessionManager.KEY_NAME);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        final String currentDateTimeString = sdf.format(date);


        if (mDatabase == null) {
            database = FirebaseDatabase.getInstance();
            //mDatabase = database.getReference().child("receita").child("a").child("receita");
            mDatabase = database.getReference();
            //   mDatabase = database.getReference().child("receita").child("-Kz1I4FOl4yYZP8eUpi3").child("alimento");

        }

        Query query = mDatabase.child("paciente").orderByChild("email").equalTo(emailnutricionista).limitToFirst(1);

        // app_title change listener
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                lista.clear();

                //   Receita appTitle = dataSnapshot.getValue(Receita.class);
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    //  String receita = dataSnapshot.getValue(String.class).toString();
                    Nutricionista usuario = artistSnapshot.getValue(Nutricionista.class);

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
        ListaNutricionistaAdapter adapter = new ListaNutricionistaAdapter(getActivity(), android.R.layout.simple_list_item_1, lista);

        lv.setAdapter(adapter);

        ArrayList<Usuario> alimento = new ArrayList<>();

       // lv.setOnItemClickListener(new ListaPacientesListener(this));

    }


}


