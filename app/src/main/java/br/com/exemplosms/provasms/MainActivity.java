package br.com.exemplosms.provasms;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.exemplosms.adapters.ContatoAdapter;
import br.com.exemplosms.dados.ConstantesWS;
import br.com.exemplosms.dados.ContatosWS;
import br.com.exemplosms.model.Contato;

public class MainActivity extends AppCompatActivity {

    private EditText edtMensagem;
    private EditText edtNumero;
    private Button btnEnviarSms;
    private Button btnCadastrarContato;
    ContatoAdapter contatoAdapter;
    private  RecyclerView rv;
private Spinner spContatos;
    Activity activity = this;

    ContatosWS contatosWS;
    ArrayList<Contato> contatos;

    private SmsManager smsManager;

    @Override
    protected void onRestart() {
        super.onRestart();
        listarContatos(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMensagem = (EditText)findViewById(R.id.edtMensagem);
        edtNumero = (EditText)findViewById(R.id.edtNumero);
        btnEnviarSms = ( Button)findViewById(R.id.btnEnviarSMS);
        btnCadastrarContato = (Button)findViewById(R.id.btnCadastrarContato);


         rv = (RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));



        listarContatos(1);


        btnEnviarSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    String numTelOrigem = edtNumero.getText().toString();
                    String mensagem = edtMensagem.getText().toString();

                    smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(numTelOrigem, null, mensagem, null, null);
                    Toast.makeText(activity, "SMS Enviado com Sucesso", Toast.LENGTH_LONG).show();

                }catch (Exception e){

                }finally {
                    edtNumero.setText("");
                    edtMensagem.setText("");
                }



            }
        });
        btnCadastrarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(MainActivity.this, CadastroContatosActivity.class);
                startActivity(intent);
            }
        });

    }




    protected void listarContatos(final int idUsuario){

        contatosWS = new ContatosWS();
        contatos = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {

                contatos =  contatosWS.ListarContatosPorUsuario(idUsuario);


                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                         contatoAdapter = new ContatoAdapter(contatos, edtNumero );

                         rv.setAdapter(contatoAdapter);
                    }
                });
            }



        }).start();
    }
}
