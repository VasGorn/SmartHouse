package onpu.vasiliy.smarthouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import onpu.vasiliy.smarthouse.http.HttpHandler;

public class LogIn extends AppCompatActivity implements View.OnClickListener{
    private EditText etIP;
    private EditText etPassword;
    private CheckBox cbRemember;
    private Button btnConnect;

    private String ip;
    private String password;

    private final String PREF_NAME = "MyPref";
    private final String KEY_IPSERVER = "username";
    private final String KEY_PASSWORD = "password";
    private final String KEY_ISLOGIN = "isLogIn";
    private final int PRIVATE_MODE = 0;

    private String TAG = LogIn.class.getSimpleName();
    private int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        etIP = findViewById(R.id.etIP);
        etPassword = findViewById(R.id.etPassword);
        btnConnect = findViewById(R.id.btnConnect);
        cbRemember =findViewById(R.id.cbRemember);

        btnConnect.setOnClickListener(this);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        boolean isLogIn = pref.getBoolean(KEY_ISLOGIN,false);

        if(isLogIn){
            String serverIP = pref.getString(KEY_IPSERVER,null);
            String passwordSaved = pref.getString(KEY_PASSWORD,null);
            etIP.setText(serverIP);
            etPassword.setText(passwordSaved);
            cbRemember.setChecked(true);
        }

        cbRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cbRemember.isChecked()){
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(KEY_ISLOGIN, false);
                    editor.apply();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnConnect:
                ip = etIP.getText().toString().trim();
                password = etPassword.getText().toString();

                if(ip.equals("") || password.equals("")){
                    Toast toast = Toast.makeText(LogIn.this, "Ошибка: некоторые поля пустые", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM,0,100);
                    toast.show();
                    return;
                }

                //request authentication with remote server
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(); break;
        }
    }

    private class AsyncDataClass extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler sh = new HttpHandler();
            //String jsonStr = sh.makeServiceCall(URL_ORDERS_FOR_MASTER);
            String url = "http://" + ip + "/smart_house/get_success.php";

            String jsonStr = sh.postLogin(url,password);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    success = jsonObj.getInt("success");

                    if(success != 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Неверный IP или пароль!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Ошибка соединения с сервером!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            if(success == 1){
                boolean isRemember = cbRemember.isChecked();

                if(isRemember){
                    saveLoginAndPassword();
                }

                Intent intent = new Intent(LogIn.this, Control.class);
                startActivity(intent);
            }
        }
    }

    private void saveLoginAndPassword(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(KEY_ISLOGIN, true);
        editor.putString(KEY_IPSERVER, ip);
        editor.putString(KEY_PASSWORD, password);

        editor.apply();

    }

}
