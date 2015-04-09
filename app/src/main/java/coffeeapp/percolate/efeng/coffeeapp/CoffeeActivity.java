package coffeeapp.percolate.efeng.coffeeapp;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CoffeeActivity extends ActionBarActivity {

    private final static String apiEndpoint = "https://coffeeapi.percolate.com/api/coffee/?api_key=";
    private final static String apiKey = "WuVbkuUsCXHPx3hsQzus4SE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffeelist_layout);

        String url = apiEndpoint + apiKey;
        Log.i("endpoint url", url);

        new apiCallTask().execute("https://coffeeapi.percolate.com/api/coffee/?api_key=WuVbkuUsCXHPx3hsQzus4SE");

    }

    // AsyncTask for making a GET request
    protected class apiCallTask extends AsyncTask<String, Void, JSONArray> {

        protected JSONArray doInBackground(String... urls){
            try{
                // create connection
                URL request = new URL(urls[0]);
                HttpURLConnection urlConnection = JsonUtil.createConnection(request);

                // create JSON object from content
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return new JSONArray(JsonUtil.getResponseText(in));

            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }


        protected void onPostExecute(JSONArray result) {

            ArrayList<HashMap<String, String>> maplist = new ArrayList<HashMap<String, String>>();

            //store in a hashmap
            for (int i = 0; i < result.length(); i++) {

                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject e = null;

                try {
                    e = result.getJSONObject(i);

                    //store all of the JSONObject's properties in the hashmap
                    map.put("desc", e.getString("desc"));
                    map.put("image_url", e.getString("image_url"));
                    map.put("id", e.getString("id"));
                    map.put("name", e.getString("name"));
                    maplist.add(map);

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

            for (int i = 0; i < maplist.size(); i++) {
                HashMap<String, String> entry = maplist.get(i);
                Log.i("JSON OUTPUT", entry.get("name"));
            }

        }
    }

}
