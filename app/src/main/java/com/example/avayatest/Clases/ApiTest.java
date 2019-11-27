package com.example.avayatest.Clases;

import android.util.Log;

import com.example.avayatest.Models.Parametros;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/***
 * Clase para metodos que consuman el endpoint de Avaya
 */
public class ApiTest {

    /***
     * Variable que guarda la url del endpoint de avaya
     */
    private static String BASE_URL = "http://breeze2-132.collaboratory.avaya.com/services/EventingConnector/events";

    /***
     * Metodo para hacer post request con un contenido multipart/form-data
     * @param params Modelo de Parametros de Avaya
     * @return Status Code
     */
    public static Integer requestPostMethod(Parametros params){

        String json = new Gson().toJson(params);
        Integer response = -1;

        MultipartFormDataTest multipart = null;
        try {
            multipart = new MultipartFormDataTest("http://breeze2-132.collaboratory.avaya.com/services/EventingConnector/events", "UTF-8");
            multipart.addFormField("family", "AAADEVRFID");
            multipart.addFormField("type", "AAADEVRFIDLOCALIZATION");
            multipart.addFormField("version", "1.0");
            multipart.addFormField("eventBody", json);
            response = multipart.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.e("doinbackground", "SERVER REPLIED:");

        return response;
    }
}
