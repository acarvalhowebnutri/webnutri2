package br.com.josecarlosestevao.appnutriv1.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.Constantes.ConversorImagem;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;

/**
 * Created by Dell on 06/01/2017.
 */
public class AlterarPerfilPacienteFragment extends Fragment {

    UsuarioDAO loginAdapter;
    EditText ednome, edsenha, edpeso, edidade, edsexo, nomeNUtriTextView;
    Button salvar, deletar, alterarNutri;
    SessionManager session;
    Usuario usuario;
    RadioButton radioButtonFem, radioButtonMasc;
    boolean selecionouSexoMasculino;
    boolean selecionouSexoFem;
    private ImageView campoFotoObjeto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_altera_perfil_paciente, null);


        loginAdapter = new UsuarioDAO(getContext());
        //    loginAdapter = loginAdapter.open();


        session = new SessionManager(getActivity());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        final String name = user.get(SessionManager.KEY_NAME);


        if (usuario == null) {
            usuario = new Usuario();
        }
        Intent intent = new Intent();
        intent = getActivity().getIntent();
        Bundle params = intent.getExtras();

        ednome = (EditText) view.findViewById(R.id.editTextExibeNome);
        edpeso = (EditText) view.findViewById(R.id.editTextExibePeso);
        edidade = (EditText) view.findViewById(R.id.editDataNascimento);
        //edsexo = (EditText) view.findViewById(R.id.editTextSexo);
        salvar = (Button) view.findViewById(R.id.btnSalvarAlteracaoPerfil);
        radioButtonMasc = (RadioButton) view.findViewById(R.id.radioButtonMasc);
        radioButtonFem = (RadioButton) view.findViewById(R.id.radioButtonFem);


        campoFotoObjeto = (ImageView) view.findViewById(R.id.foto_objeto);

        if (name != null) {
            //String userName = params.getString("nome");

            usuario = loginAdapter.ler(name);
            ednome.setText(usuario.getNome());
            edpeso.setText(usuario.getPeso());
//            edsexo.setText(usuario.getSexo());
            edidade.setText(usuario.getDataNasc());

            if (usuario.getFoto() != null)

                campoFotoObjeto.setImageBitmap(ConversorImagem.converteByteArrayPraBitmap(usuario.getFoto()));


            //setContentView(textView);
            // setContentView(textView2);

        }

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

  /*              Intent i = new Intent(getContext(), CadastrarMedidasActivity.class);
                startActivity(i);
*/
                UsuarioDAO dao = new UsuarioDAO(getContext());
                String recebeidfb = dao.lerIDFB(name);

                if (usuario == null) {
                    usuario = new Usuario();
                }

                usuario.setNome(ednome.getText().toString());
                // usuario.setSenha(edsenha.getText().toString());
                //  usuario.setEmail(ed.getText().toString());
                usuario.setDataNasc(edidade.getText().toString());
                usuario.setPeso(edpeso.getText().toString());
                usuario.setImc(recebeidfb);
                if (selecionouSexoMasculino = radioButtonMasc.isChecked()) {
                    usuario.setSexo("masculino");
                } else if (selecionouSexoFem = radioButtonFem.isChecked()) {
                    usuario.setSexo("feminino");

                }


                dao.alterarCadastrarUsuarioNoFirebase(usuario);
                //  dao.adicionausuario(userU);
                Toast.makeText(getContext(), "Cadastro alterado", Toast.LENGTH_LONG).show();
                PerfilPacienteFragment voltaperfilPaciente = new PerfilPacienteFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito, voltaperfilPaciente, "voltaperfilPaciente")
                        .addToBackStack("layout_frag4").commit();


            }
        });


        return (view);
    }


}


