package com.infinitystudios.foss_mec;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Event> events;
    private Integer last_id;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandler = new DBHandler(this, null, null, 1);
        last_id = Integer.parseInt(dbHandler.getMaxId());

        events = new ArrayList<Event>();

        ConnectivityManager connMgr = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // btnLoad.setEnabled(true);
        } else {
            // btnLoad.setEnabled(false);
            Log.v("NOT CONNECTED","true");
            // NOT CONNECTED
        }

        new DownloadJsonTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?tq=SELECT%20*%20WHERE%20A>"+String.valueOf(last_id)+"&key=1rKzPTY-3_bUQzEfXPmpKRQfskLXeNOja1WDEDYoExfE");





        // tabbed activity display code
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("ACTIVITIES"));
        tabLayout.addTab(tabLayout.newTab().setText("EVENTS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    private void processJson(JSONObject object) {


        JSONArray rows = null;
        try {
            rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                int id = columns.getJSONObject(0).getInt("v");
                if (id > last_id) {
                    String title = columns.getJSONObject(1).getString("v");
                    String description = columns.getJSONObject(2).getString("v");
                    String ImageURL = columns.getJSONObject(3).getString("v");
                    String type = columns.getJSONObject(4).getString("v");

                    Event event = new Event(id, title, description, ImageURL, type);
                    dbHandler.addEvent(event);
                    Log.v("event", String.valueOf(id));
                }
            }

            Log.v("max id",String.valueOf(last_id));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
