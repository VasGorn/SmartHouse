package onpu.vasiliy.smarthouse.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String postLogin(String reqUrl, String password){
        String response = null;
        try {
            String param = "password=" + URLEncoder.encode(password,"UTF-8");

            response = postRequest(reqUrl, param);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String toggleLamp(String reqUrl, String room, String state){
        String response = null;
        try {
            String param = "room=" + URLEncoder.encode(room,"UTF-8") +
                    "&state=" + URLEncoder.encode(state,"UTF-8");

            response = postRequest(reqUrl, param);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String setSecure(String reqUrl, String state){
        String response = null;
        try {
            String param = "secure=" + URLEncoder.encode(state,"UTF-8");

            response = postRequest(reqUrl, param);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String setSound(String reqUrl, String state){
        String response = null;
        try {
            String param = "sound=" + URLEncoder.encode(state,"UTF-8");

            response = postRequest(reqUrl, param);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String setGarage(String reqUrl, String state){
        String response = null;
        try {
            String param = "garage=" + URLEncoder.encode(state,"UTF-8");

            response = postRequest(reqUrl, param);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getData(String reqUrl, String state){
        String response = null;
        try {
            String param = "data=" + URLEncoder.encode(state,"UTF-8");

            response = postRequest(reqUrl, param);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response;
    }

    //---------------------------------------------------------------------------------------
    private String postRequest(String reqUrl, String param){
        String response = null;
        try {
            URL url = new URL(reqUrl);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
