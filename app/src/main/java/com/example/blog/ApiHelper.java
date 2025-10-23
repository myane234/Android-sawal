package com.example.blog;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kotlin.text.UStringsKt;

public class ApiHelper extends AsyncTask<String,Void,String> {
    String url;
    String method;
    String token = null;
    HttpURLConnection conn;

    public ApiHelper(String url, String method, String token) {
        try {
            this.conn = (HttpURLConnection) new URL(url).openConnection();
            this.conn.setRequestMethod(method);
            this.conn.setRequestProperty("Content-Type", "application/json");
            if (token != null) {
                this.conn.setRequestProperty("Authorization", "Bearer" + token);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
    String result = "";
    BufferedReader reader;

    protected String doInBackground(String... strings) {
        if (strings.length > 0) {
            try {
                DataOutputStream sender = new DataOutputStream(this.conn.getOutputStream());
                sender.writeBytes(strings[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            String readed = null;
            while(true){
                try {
                    readed = reader.readLine();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
                if (readed == null) {
                    break;
                }
                result += readed;
            }
        }
        return result;
    }
}