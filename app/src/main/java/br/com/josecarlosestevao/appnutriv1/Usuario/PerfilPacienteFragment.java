package br.com.josecarlosestevao.appnutriv1.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.Constantes.ConversorImagem;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.PesquisaNutricionistaFragment;
import br.com.josecarlosestevao.appnutriv1.R;

/**
 * Created by Dell on 06/01/2017.
 */
public class PerfilPacienteFragment extends Fragment {

    UsuarioDAO loginAdapter;
    TextView ednome, edsenha, edpeso, edidade, edsexo, nomeNUtriTextView;
    Button alterardadospacientebtn, deletar, alterarNutri;
    SessionManager session;
    Usuario usuario;
    private ImageView campoFotoObjeto;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_perfil_paciente, null);


        loginAdapter = new UsuarioDAO(getContext());
        //    loginAdapter = loginAdapter.open();


        session = new SessionManager(getActivity());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);


        if (usuario == null) {
            usuario = new Usuario();
        }
        Intent intent = new Intent();
        intent = getActivity().getIntent();
        Bundle params = intent.getExtras();

        ednome = (TextView) view.findViewById(R.id.textViewExibeNome);
        edpeso = (TextView) view.findViewById(R.id.textViewExibePeso);
        edidade = (TextView) view.findViewById(R.id.textViewExibeIdade);
        nomeNUtriTextView = (TextView) view.findViewById(R.id.nomeNUtriTextView);
        edsexo = (TextView) view.findViewById(R.id.sexoEditText);
        alterardadospacientebtn = (Button) view.findViewById(R.id.btnAlterar);
        alterarNutri = (Button) view.findViewById(R.id.btnAlterarNutri);
        campoFotoObjeto = (ImageView) view.findViewById(R.id.foto_objeto);

        if (name != null) {
            //String userName = params.getString("nome");

            usuario = loginAdapter.ler(name);
            ednome.setText(usuario.getNome());
            edpeso.setText(usuario.getPeso());
            edsexo.setText(usuario.getSexo());
            edidade.setText(usuario.getDataNasc());
            nomeNUtriTextView.setText(usuario.getCrn());

            if (usuario.getFoto() != null)

                campoFotoObjeto.setImageBitmap(ConversorImagem.converteByteArrayPraBitmap(usuario.getFoto()));


            //setContentView(textView);
            // setContentView(textView2);

        }

        alterardadospacientebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlterarPerfilPacienteFragment alteraperfilPaciente = new AlterarPerfilPacienteFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito, alteraperfilPaciente, "alteraperfilPaciente")
                        .addToBackStack("layout_frag4").commit();

                /*
                FragmentTransaction ft = fm.beginTransaction();
                AlterarPerfilPacienteFragment alteraperfilPaciente = new AlterarPerfilPacienteFragment();
                ft.replace(R.id.layout_direito, alteraperfilPaciente, "alteraperfilPaciente");*/


            }
        });

        alterarNutri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PesquisaNutricionistaFragment pesquisaNutricionistaCadastrado = new PesquisaNutricionistaFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito, pesquisaNutricionistaCadastrado, "alteraperfilPaciente")
                        .addToBackStack("layout_frag4").commit();

/*
                Intent in = new Intent(getContext(), PesquisaNutricionistaCadastrados.class);
                startActivity(in);*/
            }
        });


        return (view);
    }


}


