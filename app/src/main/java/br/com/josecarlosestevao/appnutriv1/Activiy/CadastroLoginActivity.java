package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import br.com.josecarlosestevao.appnutriv1.Constantes.ConversorImagem;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Login.LoginActivity;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

public class CadastroLoginActivity extends AppCompatActivity {
    private static final int ID_RETORNO_TIRA_FOTO_OBJETO = 5678;
    private static final int RESULT_GALERIA = 1234;
    private static final int MENU_FOTO = Menu.FIRST;
    private static final int MENU_GALERIA = 2;

    EditText nomeEditText, senhaEditText, confirmarSenhaEditText, pesoEditText, emailEditText;
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
        setContentView(R.layout.activity_cadastro_login);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        RESULT_GALERIA);
            }

        }

        if (usuario == null) {
            usuario = new Usuario();
        }
        if (userU == null) {
            userU = new Usuario();
        }

        simpleSwitch1 = (Switch) findViewById(R.id.switch1);
        nomeEditText = (EditText) findViewById(R.id.nomeEditText);
        senhaEditText = (EditText) findViewById(R.id.senhaEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        confirmarSenhaEditText = (EditText) findViewById(R.id.confirmarsenhaEditText);
        criarContaBtn = (Button) findViewById(R.id.criarContaBtn);
        campoFotoObjeto = (ImageView) findViewById(R.id.foto_objeto);
        pesoEditText = (EditText) findViewById(R.id.cadastro_peso);
        // cadastro_sexo_masc = (CheckBox) findViewById(R.id.cadastro_sexo_masc);
        //cadastro_sexo_fem = (CheckBox) findViewById(R.id.cadastro_sexo_masc);
        cadastro_data_nasc = (TextView) findViewById(R.id.cadastro_data_nasc);
        radioButtonMasc = (RadioButton) findViewById(R.id.radioButtonMasc);
        radioButtonFem = (RadioButton) findViewById(R.id.radioButtonFem);


        campoFotoObjeto.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, MENU_FOTO, 0, "TIRAR FOTO");
                menu.add(1, MENU_GALERIA, 1, "GALERIA");


            }
        });

        criarContaBtn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 try {
                                                     validaCampos();
                                                 } catch (IOException e) {
                                                     e.printStackTrace();
                                                 }

                                             }
                                         }

        );


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent intent;
        if (item.getItemId() == MENU_FOTO) {

            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent, ID_RETORNO_TIRA_FOTO_OBJETO);


            return true;
        } else if (item.getItemId() == MENU_GALERIA) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, RESULT_GALERIA);


        }
        return super.onContextItemSelected(item);
    }

    /**
     * Trata o resultado vindo de uma Action chamada através do método
     * startActivityForResult().
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ID_RETORNO_TIRA_FOTO_OBJETO && resultCode == RESULT_OK) {
            if (data != null) {
                // Recupera o objeto Bundle recebido do aplicativo de fotos.
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    /*
                     * Recupera o objeto Bitmap com a foto capturada, recebido
					 * através do objeto Bundle.
					 */
                    Bitmap foto = (Bitmap) bundle.get("data");
                    /*
                     * Define a imagem no componente ImageView da tela, para ser
					 * apresentada ao usuário.
					 */
                    campoFotoObjeto.setImageBitmap(foto);
                    /*
                     * Converte o objeto Bitmap com a foto em um array de bytes
					 * (byte[]) e o injeta no objeto ObjetoEmprestado para ser
					 * persistido no banco de dados.
					 */
                    byte[] x = ConversorImagem.converteBitmapPraByteArray(foto, 70);
                    //  usuario.setFoto(ConversorImagem.converteBitmapPraByteArray(foto, 70));
                    carregarfoto(x);

                }
            }

        } else if (requestCode == RESULT_GALERIA && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePath = {
                    MediaStore.Images.Media.DATA
            };
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex
                    = c.getColumnIndex(filePath[0]);

            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            campoFotoObjeto.setImageBitmap(thumbnail);
            byte[] x = ConversorImagem.converteBitmapPraByteArray(thumbnail, 70);
            carregarfoto(x);
            // bundle.putByteArray("foto", ConversorImagem.converteBitmapPraByteArray(thumbnail, 70));
            //  user.setFoto(x);
          /*Uri imageUri = data.getData();
                      Bitmap thumbnail = (BitmapFactory.decod     eFile(picturePath));
                      campoFotoObjeto.setImageBitmap(thumbnail); String[] colunaArquivo = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imageUri, colunaArquivo, null, null, null);

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(colunaArquivo[0]);

            String picturePath = cursor.getString(columnIndex);


            //Bitmap foto = BitmapFactory.decodeFile(picturePath.toString());
            Bitmap imageBitmap = BitmapFactory.decodeFile(picturePath.toString());

            if (foto != null) {

                campoFotoObjeto.setImageBitmap(foto);

            }else
                campoFotoObjeto.setImageBitmap(BitmapFactory.decodeFile(pictu}ePath.toString()));
*/
        }
    }

    private void validaCampos() throws IOException {

        String nome = nomeEditText.getText().toString();
        String senha = senhaEditText.getText().toString();
        String confirmarSenha = confirmarSenhaEditText.getText().toString();
        if (nome.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o campo usuario", Toast.LENGTH_LONG).show();
            nomeEditText.requestFocus();
            return;
        } else {
            // Valida se o email é valido
            if (!UsuarioDAO.validarEmail(emailEditText.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Email inválido", Toast.LENGTH_LONG).show();
                emailEditText.requestFocus();
                return;
            } else {
                // Valida campo senha
                if (senha.equals("") || confirmarSenha.equals("")) {
                    Toast.makeText(getApplicationContext(), "Campo senha ou confirmação de senha em branco", Toast.LENGTH_LONG).show();
                    senhaEditText.requestFocus();
                    return;
                } else {
                    // Valida se as senhas são diferentes
                    if (!senha.equals(confirmarSenha)) {
                        Toast.makeText(getApplicationContext(), "Senhas diferentes", Toast.LENGTH_LONG).show();
                        confirmarSenhaEditText.requestFocus();
                        return;
                    } else
                        criarConta();

                }
            }
        }
    }


    private void carregarfoto(byte[] x) {

        // nomeEditText.setText(usuario.getNome());


        campoFotoObjeto.setImageBitmap(ConversorImagem
                .converteByteArrayPraBitmap(x));
        userU.setFoto(x);
        bundle.putString("foto", x.toString());
        //validaCampos();

    }

    public void criarConta() throws IOException {

        //recebfoto = ConversorImagem.converteBitmapPraByteArray(foto, 70);
        if (userU == null) {
            userU = new Usuario();
        }


        userU.setNome(nomeEditText.getText().toString());
        userU.setSenha(senhaEditText.getText().toString());
        // userU.setFoto( campoFotoObjeto.setImageBitmap(ConversorImagem.converteByteArrayPraBitmap(x)));


        if (simpleSwitch1.isChecked()) {
            Intent i = new Intent(getApplicationContext(), CadastroNutricionistraActivity.class);

            bundle.putString("nome", nomeEditText.getText().toString());
            bundle.putString("senha", senhaEditText.getText().toString());
            bundle.putString("email", emailEditText.getText().toString());
            i.putExtras(bundle);
            startActivity(i);
        } else {
            Intent a = new Intent(getApplicationContext(), CadastrarMedidasActivity.class);
            bundle.putString("nome", nomeEditText.getText().toString());
            bundle.putString("senha", senhaEditText.getText().toString());
            bundle.putString("email", emailEditText.getText().toString());
            a.putExtras(bundle);
            startActivity(a);
        }


        /*Intent outratela = new Intent(CadastrarUsuarioActivity.this, CadastroMetricosActivity.class);



        bundle.putString("nome", nomeEditText.getText().toString());
        bundle.putString("senha", senhaEditText.getText().toString());
        outratela.putExtras(bundle);
        startActivity(outratela);*/

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RESULT_GALERIA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }   // A permissão foi concedida. Pode continuar

        } else {
            return;
        }
    }
}
