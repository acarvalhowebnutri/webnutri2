package br.com.josecarlosestevao.appnutriv1.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.josecarlosestevao.appnutriv1.Activiy.CadastroLoginActivity;
import br.com.josecarlosestevao.appnutriv1.Activiy.MainActivity;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.NutricionistaDao;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.NutricionistaDrawerActivity;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.SQLite.DatabaseHelper;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

public class LoginActivity extends Activity {

    public static final String Nome = "Nome";
    private static final String MANTER_CONECTADO = "manter_conectado";
    Cursor cursor;
    SessionManager session;
    private UsuarioDAO usuarioDAO;
    private Button entrarPacienteBtn, entrarNutricionistaBtn;
    private EditText nomeEditText, senhaEditText;
    private TextView criarContaTextView, esqueceuContaTextView;
    private CheckBox manterConectado;
    private DatabaseHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_login);
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO(getApplicationContext());
        }

        mDBHelper = new DatabaseHelper(this);

        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()) {
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)) {
                //Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
inserirUsuarioTeste();
        } catch (IOException e) {
            e.printStackTrace();
        }
        session = new SessionManager(getApplicationContext());

        entrarPacienteBtn = (Button) findViewById(R.id.entrarPaciente);
        entrarNutricionistaBtn = (Button) findViewById(R.id.entrarNutricionista);

        nomeEditText = (EditText) findViewById(R.id.usuario);
        senhaEditText = (EditText) findViewById(R.id.senha);
        senhaEditText = (EditText) findViewById(R.id.senha);
        manterConectado = (CheckBox) findViewById(R.id.manterConectado);
        criarContaTextView = (TextView) findViewById(R.id.criarTxtView);
        esqueceuContaTextView = (TextView) findViewById(R.id.esqueceuTxtView);
        // botaoLoginFacebook = (LoginButton) findViewById(R.id.facebook);


      //  Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();




        entrarPacienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UsuarioDAO dao = new UsuarioDAO(getApplicationContext());
                String nome = nomeEditText.getText().toString();
                String senha = senhaEditText.getText().toString();
                String senhaValidacao = dao.pesquisarUsuario(nome);
                if (nome.equals("")) {
                    Toast.makeText(getApplicationContext(), "Preencha o campo usuario", Toast.LENGTH_LONG).show();
                    return;
                }

                // Valida campo senha
                if (senha.equals("")) {
                    Toast.makeText(getApplicationContext(), "Campo senha ou confirmação de senha em branco", Toast.LENGTH_LONG).show();
                    return;
                }


                if (senha.equals(senhaValidacao)) {

                    session.createLoginSession(nome);


                    Intent i = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(i);
                    finish();
                    Toast.makeText(LoginActivity.this, "login feito com sucesso", Toast.LENGTH_LONG).show();


                } else {
                    String mensagemErro =
                            getString(R.string.erro_autenticacao);

                    Toast toast = Toast.makeText(LoginActivity.this, mensagemErro, Toast.LENGTH_SHORT);
                    toast.show();
                    Toast.makeText(LoginActivity.this, "Nome de usuário ou senha não corresponde", Toast.LENGTH_LONG).show();
                }
            }
        });

        entrarNutricionistaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NutricionistaDao dao = new NutricionistaDao(getApplicationContext());
                String nome = nomeEditText.getText().toString();
                String senha = senhaEditText.getText().toString();
                String senhaValidacao = dao.pesquisarNutricionista(nome);
                if (nome.equals("")) {
                    Toast.makeText(getApplicationContext(), "Preencha o campo usuario", Toast.LENGTH_LONG).show();
                    return;
                }

                // Valida campo senha
                if (senha.equals("")) {
                    Toast.makeText(getApplicationContext(), "Campo senha ou confirmação de senha em branco", Toast.LENGTH_LONG).show();
                    return;
                }


                if (senha.equals(senhaValidacao)) {

                    session.createLoginSession(nome);


                    Intent i = new Intent(getApplicationContext(), NutricionistaDrawerActivity.class);

                    startActivity(i);
                    finish();
                    Toast.makeText(LoginActivity.this, "login feito com sucesso", Toast.LENGTH_LONG).show();


                } else {
                    String mensagemErro =
                            getString(R.string.erro_autenticacao);

                    Toast toast = Toast.makeText(LoginActivity.this, mensagemErro, Toast.LENGTH_SHORT);
                    toast.show();
                    Toast.makeText(LoginActivity.this, "Nome de usuário ou senha não corresponde", Toast.LENGTH_LONG).show();
                }
            }
        });


        criarContaTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  abd Start The Activity
                Intent i = new Intent(getApplicationContext(), CadastroLoginActivity.class);
                startActivity(i);
            }
        });

        esqueceuContaTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  abd Start The Activity

            }
        });




    }

    private void inserirUsuarioTeste() throws IOException {


      /*  Usuario usuario = new Usuario ();
        usuario.setNome("a");
        usuario.setSenha("a");
        UsuarioDAO dao = new UsuarioDAO(getApplication());

            dao.adicionausuario(usuario);
        Toast.makeText(getApplicationContext(), "cadastrado usuario de teste", Toast.LENGTH_LONG).show();


*/
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        usuarioDAO.close();
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity", "DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}