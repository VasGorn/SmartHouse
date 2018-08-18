package onpu.vasiliy.smarthouse;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import onpu.vasiliy.smarthouse.http.Const;
import onpu.vasiliy.smarthouse.http.HttpHandler;

public class LampChangeTask extends AsyncTask<String,Void,Void>{
    private String TAG = LampChangeTask.class.getSimpleName();
    private int success;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {

        HttpHandler sh = new HttpHandler();
        //String jsonStr = sh.makeServiceCall(URL_ORDERS_FOR_MASTER);
        String url = "http://" + Const.URL_SERVER;

        String jsonStr = sh.toggleLamp(url,params[0],params[1]);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                success = jsonObj.getInt("success");

                if(success != 1) {
                    //TODO make massage to user!!
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



