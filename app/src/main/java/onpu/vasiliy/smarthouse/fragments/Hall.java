package onpu.vasiliy.smarthouse.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import onpu.vasiliy.smarthouse.LampChangeTask;
import onpu.vasiliy.smarthouse.R;
import onpu.vasiliy.smarthouse.http.Const;
import onpu.vasiliy.smarthouse.http.HttpHandler;

public class Hall extends Fragment{
    private ToggleButton tbLamp;
    private TextView txtTempr;
    private TextView txtHum;
    private Button btnRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_hall,container,false);
        tbLamp = view.findViewById(R.id.tbLampHall);
        txtTempr = view.findViewById(R.id.txtTempeValue);
        txtHum = view.findViewById(R.id.txtHumidValue);
        btnRefresh = view.findViewById(R.id.btnRefresh);

        tbLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(getContext(),"toggle Hall on",Toast.LENGTH_SHORT).show();
                    new LampChangeTask().execute("hall","on");
                }else{
                    Toast.makeText(getContext(),"toggle Hall off",Toast.LENGTH_SHORT).show();
                    new LampChangeTask().execute("hall","off");
                }
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TempHumTask().execute("1");
            }
        });

        return view;
    }


    private class TempHumTask extends AsyncTask<String,Void,Void> {
        private String TAG = Garage.class.getSimpleName();
        private int success;
        private int tempr;
        private int hum;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = "http://" + Const.URL_SERVER + "/smart_house/get_tempe_hum.php";

            String jsonStr = sh.getData(url, params[0]);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    success = jsonObj.getInt("success");
                    tempr = jsonObj.getInt("tempr");
                    hum = jsonObj.getInt("hum");

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
            if(success == 1) {
                txtTempr.setText(String.valueOf(tempr));
                txtHum.setText(String.valueOf(hum));
            }else if(success == 2){
                txtTempr.setText("N/A");
                txtHum.setText("N/A");
            }
            super.onPostExecute(result);
        }
    }
}
