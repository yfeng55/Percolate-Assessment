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

    TextView tv_results;
    EditText etResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffeeactivity_layout);

        tv_results = (TextView) findViewById(R.id.tv_results);
        etResponse = (EditText) findViewById(R.id.etResponse);

        tv_results.setText("kjhkjhkjh");

        String url = apiEndpoint + apiKey;
        Log.i("EEE", url);

        new apiCallTask().execute("https://coffeeapi.percolate.com/api/coffee/?api_key=WuVbkuUsCXHPx3hsQzus4SE");
        //new apiCallTask().execute("http://hmkcode.appspot.com/rest/controller/get.json");


    }


    // AsyncTask for making a GET request
    class apiCallTask extends AsyncTask<String, Void, JSONArray>{

        private Exception exception;

        protected JSONArray doInBackground(String... urls){

            HttpURLConnection urlConnection = null;
            try{
                // create connection
                URL urlToRequest = new URL(urls[0]);

                urlConnection = (HttpURLConnection)urlToRequest.openConnection();

                urlConnection.setConnectTimeout(500);
                urlConnection.setReadTimeout(500);

                // handle issues
                int statusCode = urlConnection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    // handle unauthorized (if service requires user login)
                } else if (statusCode != HttpURLConnection.HTTP_OK) {
                    // handle any other errors, like 404, 500,..
                }

                // create JSON object from content
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                return new JSONArray(getResponseText(in));

            }catch(Exception e){
                this.exception = e;
                return null;
            }

        }

        protected void onPostExecute(JSONArray result){

            //ArrayList jsonlist = new ArrayList();
            ArrayList<HashMap<String, String>> maplist = new ArrayList<HashMap<String, String>>();

            //store in a hashmap
            for(int i=0;i<result.length();i++){

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

//            Log.i("JSON RESPONSE", response);

            for(int i=0; i<maplist.size(); i++){
                HashMap<String, String> entry = maplist.get(i);
                Log.i("JSON OUTPUT", entry.get("name"));
            }

            //etResponse.setText(maplist.get(0));
        }
    }

    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }


    //Method for parsing JSON response Object
    public static void parseJson() {



    }
}
