package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Login.LoginActivity;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.NutricionistaDao;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

public class CadastroNutricionistraActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    EditText crnEditText;
    Button criarContaBtn;
    UsuarioDAO loginAdapter;
    Bundle bundle = new Bundle();
    TextView cadastro_data_nasc;
    SessionManager session;
    private Nutricionista nutricionista;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nutricionistra);
        mAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser == null || nutricionista.getId() != null) {
                    return;
                }

                // nutricionista.setId(Long.valueOf(firebaseUser.getUid()));
                NutricionistaDao dao = new NutricionistaDao(getApplicationContext());
                dao.cadastrarNutricionistaNoFirebase(nutricionista);
            }
        };
        crnEditText = (EditText) findViewById(R.id.crnEditText);
        criarContaBtn = (Button) findViewById(R.id.criarContaBtn);


        criarContaBtn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 if (nutricionista == null) {
                                                     nutricionista = new Nutricionista();
                                                 }

                                                 Intent intent = getIntent();

                                                 Bundle bundle = intent.getExtras();
                                                 byte[] x = bundle.getByteArray("foto");
                                                 String crn = crnEditText.getText().toString();
                                                 nutricionista.setCrn(crn);
                                                 nutricionista.setFoto(x);
                                                 nutricionista.setNome(bundle.getString("nome"));
                                                 nutricionista.setSenha(bundle.getString("senha"));
                                                 nutricionista.setEmail(bundle.getString("email"));
                                                 nutricionista.setTipo("nutricionista");
                                                 salvarNutricionista();
                                                 //  String email = bundle.getString("email");
                                                 //String senha = bundle.getString("senha");


                                                 //     criarcContaNutricionista();
                                                 //    NutricionistaDao dao = new NutricionistaDao(getApplicationContext());
                                              /*   dao.adicionarNutricionista(usercad);
                                                 Toast.makeText(getApplicationContext(), "Conta criada com sucesso", Toast.LENGTH_LONG).show();

                                                 Intent voltar = new Intent(CadastroNutricionistraActivity.this, LoginActivity.class);
                                                 startActivity(voltar);*/

                                             }
                                         }

        );

    }


    public void salvarNutricionista() {
        mAuth.createUserWithEmailAndPassword(nutricionista.getEmail(), nutricionista.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String idfb = user.getUid();
                            nutricionista.setId(idfb);

                            NutricionistaDao d = new NutricionistaDao(getApplicationContext());
                            d.cadastrarNutricionistaNoFirebase(nutricionista);
                            Toast.makeText(getApplicationContext(), "Conta criada com sucesso", Toast.LENGTH_LONG).show();
                            Intent voltar = new Intent(CadastroNutricionistraActivity.this, LoginActivity.class);
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


}
