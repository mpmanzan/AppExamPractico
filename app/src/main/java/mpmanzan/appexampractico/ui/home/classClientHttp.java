package mpmanzan.appexampractico.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

//import mx.gob.fonacot.creditoseguro.movil.util.CommonUtils;

public class classClientHttp {
    private Context mcontext;
    private String stID = "";
    private String stToken = "";
    private double dobLatitude;
    private double dobLongitude;
    private Integer intContIntentos;
    private Integer intContIntentosBuro =0;
    private Integer intContIntentosSimulador =0;
    private String[] Respclient = {"Ok", "Resp"};
    private String SMsgError = "";
    private boolean bBloqueApp = false;
    private String UrlLiverpool = "https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/plp?force-plp=true";
//    custom_dialogo msgDialg;

    public classClientHttp(Context context) {
        //Recupera context
        mcontext = context;
        //Recupera Token y UUID
 //       SharedPreferences shPreferencias = mcontext.getSharedPreferences("DatosCte", Context.MODE_PRIVATE);
        //stID = shPreferencias.getString("UID", "");
//        stID = "UIDDispositivo";
//        stToken = shPreferencias.getString("Token", "");
//        dobLatitude = Double.parseDouble(shPreferencias.getString("Latitude", ""));
//        dobLongitude = Double.parseDouble(shPreferencias.getString("Longitude", ""));
//        intContIntentos = Integer.parseInt(shPreferencias.getString("ContIntentos", ""));
//        IntcuaDialogoError intcuaDialogoError = new IntcuaDialogoError();
//        msgDialg = new custom_dialogo(mcontext, intcuaDialogoError, 2, "Salir", "");
//        msgDialg.setTitulo("Error");
    }

    public class ClientHttp extends AsyncTask<String, String, String[]> {
        private String[] list = {"0", "Error"};

        @Override
        protected String[] doInBackground(String... strings) {
            HttpsURLConnection httpsURLConnection = null;
            URL url = null;
            int code = 0;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                if (strings[1] == "POST") {
                    httpsURLConnection.setDoOutput(true);
                    httpsURLConnection.setDoInput(true);

                    httpsURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    httpsURLConnection.setRequestProperty("Accept", "application/json");
                    if (!TextUtils.isEmpty(stToken)) {
                        httpsURLConnection.setRequestProperty("Authorization", "Bearer " + stToken);
                    }

                    // Activar método POST
                    httpsURLConnection.setRequestMethod(strings[1]);
                    //httpsURLConnection.setConnectTimeout(5000);
                    httpsURLConnection.connect();

                    OutputStreamWriter writer = new OutputStreamWriter(httpsURLConnection.getOutputStream());
                    //String output = Envio;
                    String output = strings[2];
                    writer.write(output);
                    writer.flush();
                    writer.close();
                }

                code = httpsURLConnection.getResponseCode();
                if (code == HttpsURLConnection.HTTP_OK) {
                    InputStream InpString = new BufferedInputStream(httpsURLConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(InpString));
                    String stlinea = "";
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((stlinea = reader.readLine()) != null) {
                        stringBuffer.append(stlinea);
                    }
                    list[0] = String.valueOf(code);
                    list[1] = stringBuffer.toString();
                } else {
                    InputStream InpString = new BufferedInputStream(httpsURLConnection.getErrorStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(InpString));
                    String stlinea = "";
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((stlinea = reader.readLine()) != null) {
                        stringBuffer.append(stlinea);
                    }
                    list[0] = String.valueOf(code);
                    list[1] = stringBuffer.toString();
                }
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception Ex) {
                Ex.printStackTrace();
            } finally {
                if (httpsURLConnection != null)
                    httpsURLConnection.disconnect();
            }
            return list;
        }

        @Override
        protected void onPostExecute(String[] params) {
            super.onPostExecute(params);
        }
    }

//    public void HttpAutoriza() {
//        intContIntentos++;
//        //Fecha
//        Date dFechahoy = Calendar.getInstance().getTime();
//        Calendar calendar = Calendar.getInstance();
//        TimeZone localTZ = calendar.getTimeZone();
//        String format1 = "yyyy-MM-dd"; //will return 2017-01-31
//        String format2 = "dd"; //will return DAY only like 31
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        sdf.setTimeZone(localTZ);
//        String stFechahoy = sdf.format(dFechahoy);
//        //Json Envio
//        JSONObject jsonOUTPost = new JSONObject();
//        String stJson = "";
//        try {
//            jsonOUTPost.put("fechaPeticion", stFechahoy);
//            jsonOUTPost.put("uid", stID);
//            jsonOUTPost.put("intento", intContIntentos);
//            jsonOUTPost.put("usuario", "OOXmSROA1WIJuAkPpcIwIg==");
//            jsonOUTPost.put("contrasena", "4JH0O0R+4igIBJX2Va9Krg==");
//
//            stJson = jsonOUTPost.toString();
//        } catch (JSONException Ex) {
//            Ex.printStackTrace();
//        }
//
//        // Para envio del servico
//        try {
//            ClientHttp clientHttp = new ClientHttp();
//            Respclient = clientHttp.execute(mcontext.getString(R.string.UrlServAutoriza), "POST", stJson).get();
//            //Leer Json
//            JSONObject jsonObject = new JSONObject(Respclient[1]);
//            //
//            stToken = jsonObject.getString("token");
//            SharedPreferences shPreferencias = mcontext.getSharedPreferences("DatosCte", Context.MODE_PRIVATE);
//            SharedPreferences.Editor ShEdit = shPreferencias.edit();
//            ShEdit.putString("Token", stToken);
//            ShEdit.putString("ContIntentos", intContIntentos.toString());
//            ShEdit.commit();
//        } catch (InterruptedException Ex) {
//            Ex.printStackTrace();
//        } catch (ExecutionException Ex) {
//            Ex.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private String ControlError(String[] resphttp) {
        String Resp = "";

        switch (resphttp[0]) {
            case "401": //Error de autenticacion token
                //HttpAutoriza();
                Resp = "Reintento";
                break;
            case "400": //Error del servidor
                //Leer Json
                try {
                    if (resphttp[1].trim().equals("null")) {
                        Resp = "Error: null";
                    } else {
                        JSONObject jsonObject = new JSONObject(resphttp[1]);
                        Resp = "Error: " + jsonObject.getString("errorDescription");
                    }
                    break;
                } catch (JSONException e) {
                    Resp = e.getMessage();
                    break;
                }
        }
        return Resp;
    }


    public String[] HttpBuscarProducto(String producto, int Pagina, int NumProductosPag) {
        String Resp = "";
//        //Fecha
//        Date dFechahoy = Calendar.getInstance().getTime();
//        Calendar calendar = Calendar.getInstance();
//        TimeZone localTZ = calendar.getTimeZone();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        sdf.setTimeZone(localTZ);
//        String stFechahoy = sdf.format(dFechahoy);
//        SharedPreferences shPreferencias;
        // Para envio del servico
        ClientHttp clientHttp;
        intContIntentosBuro++;
        try {

            //Json Envio
//            JSONObject jsonOUTPost = new JSONObject();
//            String stJson = "";
//            try {
//                jsonOUTPost.put("uid", stID);
//                jsonOUTPost.put("fechaPeticion", stFechahoy);
//                jsonOUTPost.put("iIdSolicitud", intContIntentos); // cambiar este valor, no esta el real, solo uno de prueba
//                jsonOUTPost.put("byIntentos", intContIntentosBuro);
//                stJson = jsonOUTPost.toString();
//            } catch (JSONException Ex) {
//                Ex.printStackTrace();
//            }
//            label:
//            do {
                // do
                clientHttp = new ClientHttp();
                String sUrl = "https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/plp?force-plp=true";
                sUrl += "&searchstring="+ producto + "&page-number="+Pagina+"&number-of-items-per-page=" + NumProductosPag;
                Respclient = clientHttp.execute(sUrl,"GET").get();
                if (!Respclient[0].equals("200")) {
                    if (ControlError(Respclient).equals("Reintento")) {
                        clientHttp = null;
//                        continue label;
                    } else {

                    }
                }
                // do more stuff
//                break label;
//            } while (true);
            //Resp = Respclient[1];
            return Respclient;
        } catch (InterruptedException Ex) {
            Ex.printStackTrace();
            return null;
        } catch (ExecutionException Ex) {
            Ex.printStackTrace();
            return null;
        }
    }

}
