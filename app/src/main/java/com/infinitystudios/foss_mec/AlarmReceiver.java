package com.infinitystudios.foss_mec;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adarsh on 29-06-2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private int last_id;
    DBHandler dbHandler;
    @Override
    public void onReceive(final Context context, Intent intent) {
        dbHandler = new DBHandler(context, null, null, 1);

        try {
            last_id = Integer.parseInt(dbHandler.getMaxId());
        } catch (NumberFormatException e) {
            last_id = 0;
        }
        new DownloadJsonTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object, context);
            }
        }).execute("https://spreadsheets.google.com/tq?tq=SELECT%20*%20WHERE%20A>\""+String.valueOf(last_id)+"\"&key=1rKzPTY-3_bUQzEfXPmpKRQfskLXeNOja1WDEDYoExfE");
    }

    private void processJson(JSONObject object, Context context) {

        JSONArray rows = null;
        try {
            rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                int id = columns.getJSONObject(0).getInt("v");
                if (id > last_id) {
                    String title = columns.getJSONObject(1).getString("v");

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                    mBuilder.setSmallIcon(R.mipmap.logo);
                    mBuilder.setContentTitle("FOSS MEC");
                    mBuilder.setContentText(title);
                    mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(r,mBuilder.build());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
