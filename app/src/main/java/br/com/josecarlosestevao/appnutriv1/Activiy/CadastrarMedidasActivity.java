package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.josecarlosestevao.appnutriv1.Constantes.SelectDateFragment;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Login.LoginActivity;
import br.com.josecarlosestevao.appnutriv1.Metas.Metas;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

public class CadastrarMedidasActivity extends AppCompatActivity {

    private static final int ID_RETORNO_TIRA_FOTO_OBJETO = 5678;
    private static final int RESULT_GALERIA = 1234;
    private static final int MENU_FOTO = Menu.FIRST;
    private static final int MENU_GALERIA = 2;
    public FirebaseAuth mAuth;
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
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadasta_usuario);

        mAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser == null || userU.getId() != null) {
                    return;
                }

                // nutricionista.setId(Long.valueOf(firebaseUser.getUid()));
                UsuarioDAO dao = new UsuarioDAO(getApplicationContext());
                dao.cadastrarUsuarioNoFirebase(userU);
            }
        };


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
                                                     userU.setSexo("homem");
                                                 } else if (selecionouSexoFem = radioButtonFem.isChecked()) {
                                                     userU.setSexo("mulher");

                                                 }
                                                 userU.setCrn("sem dados " + bundle.getString("nome"));
                                                 userU.setTipo("paciente");
                                                 salvarPaciente();


                                                 //UsuarioDAO dao = new UsuarioDAO(getApplicationContext());
                                                 //dao.cadastrarUsuarioNoFirebase(userU);
                                                 //  dao.adicionausuario(userU);
                                                 /*Toast.makeText(getApplicationContext(), "Conta criada com sucesso", Toast.LENGTH_LONG).show();

                                                 Intent voltar = new Intent(CadastrarMedidasActivity.this, LoginActivity.class);
                                                 startActivity(voltar);*/
                                             }
                                         }

        );


    }


    public void salvarPaciente() {
        mAuth.createUserWithEmailAndPassword(userU.getEmail(), userU.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String idfb = user.getUid();
                            userU.setId(idfb);

                            UsuarioDAO d = new UsuarioDAO(getApplicationContext());
                            salvarMetas(userU);
                            d.cadastrarUsuarioNoFirebase(userU);
                            Toast.makeText(getApplicationContext(), "Conta criada com sucesso", Toast.LENGTH_LONG).show();
                            Intent voltar = new Intent(CadastrarMedidasActivity.this, LoginActivity.class);
                            startActivity(voltar);
                            //    FirebaseUser user = mAuth.getCurrentUser();
                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Nao foi possivel fazer cadastro", Toast.LENGTH_LONG).show();

                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void salvarMetas(Usuario paciente) {

        Metas metas = new Metas();

        //   metas.setUsuario(paciente);
        metas.setIdPacienteFB(paciente.getId());

        UsuarioDAO dao = new UsuarioDAO(getApplicationContext());
        dao.cadastraMetasDriFirebase(metas);

    }
}

