package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import br.com.josecarlosestevao.appnutriv1.Constantes.SelectDateFragment;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Login.LoginActivity;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

public class CadastrarMedidasActivity extends AppCompatActivity {

    private static final int ID_RETORNO_TIRA_FOTO_OBJETO = 5678;
    private static final int RESULT_GALERIA = 1234;
    private static final int MENU_FOTO = Menu.FIRST;
    private static final int MENU_GALERIA = 2;

    EditText nomeEditText, senhaEditText, confirmarSenhaEditText, cadastro_peso, emailEditText;
    Button criarContaBtn;
    UsuarioDAO loginAdapter;
    Bundle bundle = new Bundle();
    TextView cadastro_data_nasc;
    CheckBox cadastro_sexo_masc, cadastro_sexo_fem;
    SessionManager session;

    RadioButton radioButtonFem, radioButtonMasc;
    boolean selecionouSexoMasculino;
    boolean selecionouSexoFem;
    Switch simpleSwitch1;
    private DatePicker cadastra_nascimento_data;
    private Bitmap foto;
    private byte[] recebfoto;
    private Usuario usuario;
    private ImageView campoFotoObjeto;
    private Usuario userU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadasta_usuario);


        if (usuario == null) {
            usuario = new Usuario();
        }
        if (userU == null) {
            userU = new Usuario();
        }


        criarContaBtn = (Button) findViewById(R.id.criarContaBtn);
        cadastro_peso = (EditText) findViewById(R.id.cadastro_peso);

        // cadastro_sexo_masc = (CheckBox) findViewById(R.id.cadastro_sexo_masc);
        //cadastro_sexo_fem = (CheckBox) findViewById(R.id.cadastro_sexo_masc);
        cadastro_data_nasc = (TextView) findViewById(R.id.cadastro_data_nasc);
        radioButtonMasc = (RadioButton) findViewById(R.id.radioButtonMasc);
        radioButtonFem = (RadioButton) findViewById(R.id.radioButtonFem);


        cadastro_data_nasc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");


            }
        });
        criarContaBtn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 try {
                                                     criarConta();
                                                 } catch (IOException e) {
                                                     e.printStackTrace();
                                                 }

                                             }
                                         }

        );


    }


    private void validaCampos() throws IOException {


        // Valida campo peso
        if (cadastro_peso.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha o campo peso", Toast.LENGTH_LONG).show();
            cadastro_peso.requestFocus();
            return;
        } else
            criarConta();


    }


    public void criarConta() throws IOException {

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();


        if (userU == null) {
            userU = new Usuario();
        }
        userU.setNome(bundle.getString("nome"));
        userU.setSenha(bundle.getString("senha"));
        userU.setEmail(bundle.getString("email"));
        userU.setDataNasc(cadastro_data_nasc.getText().toString());
        userU.setPeso(cadastro_peso.getText().toString());
        if (selecionouSexoMasculino = radioButtonMasc.isChecked()) {
            userU.setSexo("m");
        } else if (selecionouSexoFem = radioButtonFem.isChecked()) {
            userU.setSexo("f");

        }
        UsuarioDAO dao = new UsuarioDAO(getApplicationContext());
        dao.adicionausuario(userU);
        Toast.makeText(getApplicationContext(), "Conta criada com sucesso", Toast.LENGTH_LONG).show();

        Intent voltar = new Intent(CadastrarMedidasActivity.this, LoginActivity.class);
        startActivity(voltar);
    }
}

