package coffeeapp.percolate.efeng.coffeeapp;
import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
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

public class CoffeeActivity extends Activity {

    private final static String apiEndpoint = "https://coffeeapi.percolate.com/api/coffee/?api_key=";
    private final static String apiKey = "WuVbkuUsCXHPx3hsQzus4SE";

    private ListView lv;
    protected ArrayList<Coffee> coffeelist;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffeelist_layout);

        coffeelist = new ArrayList<Coffee>();

        //API GET Request
        String url = apiEndpoint + apiKey;
        //Log.i("endpoint url", url);
        new apiCallTask().execute(url);

        //set view objects and adapter
        lv = (ListView) findViewById(R.id.listview);
        adapter = new ListViewAdapter(getApplicationContext(), R.layout.row, coffeelist);
        lv.setAdapter(adapter);

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

            //store all values from JSONArray into coffeelist (an ArrayList of Coffee Objects)
            coffeelist = JsonUtil.JsonToArrayList(result, coffeelist);
            adapter.notifyDataSetChanged();

            //Log all names in the coffeelist
            for (int i = 0; i < coffeelist.size(); i++) {
                Coffee entry = coffeelist.get(i);
                Log.i("JSON OUTPUT", entry.getName());
            }

        }
    }

}
