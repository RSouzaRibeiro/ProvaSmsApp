package br.com.exemplosms.dados;


import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;


/**
 * Created by Rafael on 18/10/2017.
 */
public class ConstantesWS {

    private static final String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://192.168.0.103/prova/ContatoWS.asmx";
    private static String TOKEN = "12345678920";
    private static final String METHOD_NAME = "HelloWorld";
    private static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;


    public void testeHelloWord() {

        try {
            SoapObject soap = new SoapObject(NAMESPACE, METHOD_NAME);


            //loginProp.type = PropertyInfo.STRING_CLASS;

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(soap);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;

            for(int i = 0; i < result.getPropertyCount(); i++){
                SoapObject objSoap = (SoapObject)result.getProperty(i);

                Log.i("TAG",objSoap.getProperty(0).toString());

            }

        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
