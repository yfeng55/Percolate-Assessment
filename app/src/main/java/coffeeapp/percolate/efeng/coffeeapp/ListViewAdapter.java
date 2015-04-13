package coffeeapp.percolate.efeng.coffeeapp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Coffee> {

    ArrayList<Coffee> coffeelist;
    LayoutInflater inflater;
    int Resource;
    ViewHolder holder;

    public ListViewAdapter(Context context, int resource, ArrayList<Coffee> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        coffeelist = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = inflater.inflate(Resource, null);

            //set view objects
            holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.tvName = (TextView) v.findViewById(R.id.tvName);
            holder.tvDescription = (TextView) v.findViewById(R.id.tvDesc);

            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
            //resets the image to remove flickering inside listview rows
            holder.imageview.setImageBitmap(null);
        }

        //get image
        new DownloadImageTask(holder.imageview).execute(coffeelist.get(position).getImageURL());
        holder.tvName.setText(coffeelist.get(position).getName());
        holder.tvDescription.setText(coffeelist.get(position).getDesc());
        return v;

    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvName;
        public TextView tvDescription;

    }

    //IMAGETASK
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap retrievedImage = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                retrievedImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                e.printStackTrace();
            }
            return retrievedImage;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
