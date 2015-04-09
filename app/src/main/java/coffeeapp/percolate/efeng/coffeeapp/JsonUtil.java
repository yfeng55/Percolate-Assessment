package coffeeapp.percolate.efeng.coffeeapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class JsonUtil {

    public static HttpURLConnection createConnection(URL urlToRequest){

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection)urlToRequest.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        urlConnection.setConnectTimeout(500);
        urlConnection.setReadTimeout(500);

        return urlConnection;
    }

    public static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    public static ArrayList<HashMap<String, String>> JsonToArrayList(JSONArray result, ArrayList<HashMap<String, String>> maplist){
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

        return maplist;
    }


}
