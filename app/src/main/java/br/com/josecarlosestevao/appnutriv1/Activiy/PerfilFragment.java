package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Constantes.ConversorImagem;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;
import br.com.josecarlosestevao.appnutriv1.R;


public class PerfilFragment extends AppCompatActivity {

    UsuarioDAO loginAdapter;
    TextView ednome, edsenha, edpeso, edidade, edsexo;
    Button salvar, deletar;
    SessionManager session;
    Usuario usuario;
    private ImageView campoFotoObjeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perfil);

        loginAdapter = new UsuarioDAO(this);
    //    loginAdapter = loginAdapter.open();


        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);


        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        ednome = (TextView) findViewById(R.id.textViewExibeNome);
        edpeso = (TextView) findViewById(R.id.textViewExibePeso);
        edidade = (TextView) findViewById(R.id.textViewExibeIdade);
        edsexo = (TextView) findViewById(R.id.sexoEditText);
        salvar = (Button) findViewById(R.id.btnAlterar);
        campoFotoObjeto = (ImageView) findViewById(R.id.foto_objeto);


        if (name != null) {
            //String userName = params.getString("nome");

            usuario = loginAdapter.ler(name);
            ednome.setText(usuario.getNome());
            edpeso.setText(usuario.getPeso());
            edsexo.setText(usuario.getSexo());
            edidade.setText(usuario.getDataNasc());

            if (usuario.getFoto() != null)

                campoFotoObjeto.setImageBitmap(ConversorImagem.converteByteArrayPraBitmap(usuario.getFoto()));


            //setContentView(textView);
            // setContentView(textView2);

        }

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), CadastrarMedidasActivity.class);
                startActivity(i);


/*
                String userName = ednome.getText().toString();
                // String password = edsenha.getText().toString();
                String idade = edidade.getText().toString();
                String peso = edpeso.getText().toString();
                String sexo = edsexo.getText().toString();


                loginAdapter.atualizar(userName, peso, idade, sexo);
                Intent voltar = new Intent(PerfilFragment.this, MainActivity.class);
                Bundle params = new Bundle();

                params.putString("mensagem", userName);
                voltar.putExtras(params);
                startActivity(voltar);
                Toast.makeText(getApplicationContext(), "Dados alterados", Toast.LENGTH_LONG).show();
*/
            }
        });

    }


}
