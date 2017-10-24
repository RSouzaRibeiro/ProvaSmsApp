package br.com.exemplosms.provasms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.exemplosms.dados.ContatosWS;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroContatosActivity extends AppCompatActivity {

    private EditText edtTelefone;
    private EditText edtNome;
    private Button btnCadastrar;


    ContatosWS contatosWS;
    Activity activity = this;

    ProgressDialog progressDialog ;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contatos);

        edtTelefone = (EditText)findViewById(R.id.edtTelefone);
        edtNome = (EditText)findViewById(R.id.edtNome);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);



        MaskEditTextChangedListener maskTEL = new MaskEditTextChangedListener("(##)#####-####", edtTelefone);
        edtTelefone.addTextChangedListener(maskTEL);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Gravando Contato\nAguarde...");
                progressDialog.setCancelable(false);


                builder = new AlertDialog.Builder(activity);
                builder.setMessage("Contato Salvo.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

                contatosWS = new ContatosWS();
                final String nomeContato = edtNome.getText().toString();
                final String telefoneContato = edtTelefone.getText().toString();
                if(nomeContato.equals("") || telefoneContato.equals("")){
                    builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Telefone ou Nome vazios\nFavor Verificar!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else {
                    progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                      final boolean resposta =   contatosWS.InsertContato("1",nomeContato,telefoneContato);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if(resposta){
                                    edtTelefone.setText("");
                                    edtNome.setText("");
                                    builder.create().show();
                                }
                                else {
                                    Toast.makeText(activity, "Erro ao Gravar!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
                }



            }
        });

    }
}
