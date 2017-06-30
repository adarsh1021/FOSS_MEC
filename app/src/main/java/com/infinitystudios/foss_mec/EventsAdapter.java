package com.infinitystudios.foss_mec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Adarsh on 28-06-2017.
 */

public class EventsAdapter extends ArrayAdapter<Event> {
    Context context;
    View v;
    private ArrayList<Event> events;
    public EventsAdapter(Context context, int resource, ArrayList<Event> events) {
        super(context, resource, events);
        this.context = context;
        this.events = events;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        v = convertView;
        if (v==null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.event_layout, null);
        }
        Event e = events.get(position);
        if (e!=null) {
            TextView tvTitle = (TextView) v.findViewById(R.id.tvEventTitle);
            TextView tvDescription = (TextView) v.findViewById(R.id.tvEventDescription);
            if (!e.getImageURL().toString().equals("#")) {
                ImageView ivUrl = (ImageView) v.findViewById(R.id.ivUrl);
                new DownloadImageTask(ivUrl)
                        .execute(e.getImageURL());
            }
            // TextView tvId = (TextView) v.findViewById(R.id.tvId);

            // tvId.setText(String.valueOf(e.getStatus()));
            tvTitle.setText(String.valueOf(e.getTitle()));
            tvDescription.setText(Html.fromHtml(String.valueOf(e.getDescription())));
        }
        return v;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}

