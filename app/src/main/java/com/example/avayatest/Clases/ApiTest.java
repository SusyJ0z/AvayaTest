package com.example.avayatest.Clases;

import com.example.avayatest.Models.Parametros;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class ApiTest {
    private static String BASE_URL = "http://breeze2-132.collaboratory.avaya.com/services/EventingConnector/events";

    public static void requestPostMethod(Parametros params){

        String json = new Gson().toJson(params);

        /*URL url = new URL(BASE_URL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("family", "AAADEVRFID");
        con.setRequestProperty("type", "AAADEVRFIDLOCALIZATION");
        con.setRequestProperty("version", "1.0");
        con.setRequestProperty("eventBody", json);*/
    }
}
