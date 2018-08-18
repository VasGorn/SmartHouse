package onpu.vasiliy.smarthouse.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import onpu.vasiliy.smarthouse.LampChangeTask;
import onpu.vasiliy.smarthouse.LogIn;
import onpu.vasiliy.smarthouse.R;
import onpu.vasiliy.smarthouse.http.Const;
import onpu.vasiliy.smarthouse.http.HttpHandler;

public class Alarms extends Fragment{
    private CheckBox cbSecure;
    private CheckBox cbSound;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm,container,false);
        cbSecure = view.findViewById(R.id.cbSecure);
        cbSound = view.findViewById(R.id.cbSound);

        cbSecure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Toast.makeText(getContext(),"toggle Secure on",Toast.LENGTH_SHORT).show();
                    new SecureTask().execute("on");
                }else{
                    //Toast.makeText(getContext(),"toggle Secure on",Toast.LENGTH_SHORT).show();
                    new SecureTask().execute("off");
                }
            }
        });

        cbSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    new SoundTask().execute("on");
                }else{
                    new SoundTask().execute("off");
                }
            }
        });

        return view;
    }

    private class SecureTask extends AsyncTask<String,Void,Void>{
        private String TAG = Alarms.class.getSimpleName();
        private int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = "http://" + Const.URL_SERVER + "/smart_house/set_secure.php";

            String jsonStr = sh.setSecure(url, params[0]);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    success = jsonObj.getInt("success");

                    if(success == 1) {
                        //TODO message to user
                    }else if(success == 2){

                    }

                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.e(TAG, "Couldn't get json from server.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
        }
    }

    private class SoundTask extends AsyncTask<String,Void,Void>{
        private String TAG = Alarms.class.getSimpleName();
        private int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = "http://" + Const.URL_SERVER + "/smart_house/set_sound.php";

            String jsonStr = sh.setSound(url, params[0]);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    success = jsonObj.getInt("success");

                    if(success == 1) {
                        //TODO message to user
                    }else if(success == 2){

                    }

                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.e(TAG, "Couldn't get json from server.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
        }
    }
}
