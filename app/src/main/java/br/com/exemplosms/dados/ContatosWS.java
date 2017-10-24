package br.com.exemplosms.dados;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.exemplosms.model.Contato;

/**
 * Created by Rafael on 19/10/2017.
 */

public class ContatosWS {


    private static final String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://192.168.0.103/prova/ContatoWS.asmx";
    private static String TOKEN = "1234567890";
    private static final String METHOD_NAME_LISTAR_CONTATOS = "ListarContatosPorUsuario";
    private static final String SOAP_ACTION_LISTAR_CONTATOS = NAMESPACE + METHOD_NAME_LISTAR_CONTATOS;
    private static final String METHOD_NAME_INSERIR_CONTATOS = "InsertContato";
    private static final String SOAP_ACTION_INSERIR_CONTATOS = NAMESPACE + METHOD_NAME_INSERIR_CONTATOS;

    public  ArrayList<Contato> ListarContatosPorUsuario(int ID_USUARIO) {
        ArrayList<Contato> contatos= new ArrayList<>();
        try {
            SoapObject soap = new SoapObject(NAMESPACE, METHOD_NAME_LISTAR_CONTATOS);

            soap.addProperty("TOKEN", TOKEN);
            soap.addProperty("ID_USUARIO", ID_USUARIO);
            //loginProp.type = PropertyInfo.STRING_CLASS;

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(soap);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            androidHttpTransport.call(SOAP_ACTION_LISTAR_CONTATOS, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;

             contatos = new Gson().fromJson(result.getProperty(0).toString(), new TypeToken< List<Contato>>(){
            }.getType());


            /*for(int i = 0; i < result.getPropertyCount(); i++){
                SoapObject objSoap = (SoapObject)result.getProperty(i);

                Log.i("TAG",objSoap.getProperty(0).toString());

            }*/
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  contatos;
    }

    public  boolean InsertContato( String ID_USUARIO, String NOME_CONTATO, String TEL_CONTATO ) {
        boolean resposta = false;
        try {
            SoapObject soap = new SoapObject(NAMESPACE, METHOD_NAME_INSERIR_CONTATOS);

            soap.addProperty("TOKEN", TOKEN);
            soap.addProperty("ID_USUARIO", Integer.parseInt(ID_USUARIO));
            soap.addProperty("NOME_CONTATO", NOME_CONTATO);
            soap.addProperty("TEL_CONTATO", TEL_CONTATO);
            //loginProp.type = PropertyInfo.STRING_CLASS;

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(soap);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            androidHttpTransport.call(SOAP_ACTION_INSERIR_CONTATOS, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;

            /*resposta = new Gson().fromJson(result.getProperty(0).toString(), new TypeToken< List<Contato>>(){
            }.getType());*/
            resposta = Boolean.parseBoolean(result.getProperty(0).toString());

            /*for(int i = 0; i < result.getPropertyCount(); i++){
                SoapObject objSoap = (SoapObject)result.getProperty(i);

                Log.i("TAG",objSoap.getProperty(0).toString());

            }*/
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  resposta;
    }
}
