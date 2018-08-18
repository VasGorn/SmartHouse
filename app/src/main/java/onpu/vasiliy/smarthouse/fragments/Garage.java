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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import onpu.vasiliy.smarthouse.LampChangeTask;
import onpu.vasiliy.smarthouse.R;
import onpu.vasiliy.smarthouse.http.Const;
import onpu.vasiliy.smarthouse.http.HttpHandler;

public class Garage extends Fragment{
    private ToggleButton tbLamp;
    private Switch swtGate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garage,container,false);
        tbLamp = view.findViewById(R.id.tbLampGarage);
        swtGate = view.findViewById(R.id.swtGate);

        tbLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(getContext(),"toggle Garage on",Toast.LENGTH_SHORT).show();
                    new LampChangeTask().execute("garage","on");
                }else{
                    Toast.makeText(getContext(),"toggle Garage off",Toast.LENGTH_SHORT).show();
                    new LampChangeTask().execute("garage","off");
                }
            }
        });

        swtGate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    new GarageTask().execute("open");
                }else{
                    new GarageTask().execute("close");
                }
            }
        });

        return view;
    }

    private class GarageTask extends AsyncTask<String,Void,Void> {
        private String TAG = Garage.class.getSimpleName();
        private int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();
            String url = "http://" + Const.URL_SERVER + "/smart_house/set_garage.php";

            String jsonStr = sh.setGarage(url, params[0]);

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
