package com.infinitystudios.foss_mec;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static MenuItem menuItem;
    // private Integer last_id;
    private DBHandler dbHandler;
    private PagerAdapter pagerAdapter;
    private int id, status;
    private String title, description, imgUrl, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // tabbed activity display code
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("ACTIVITIES"));
        tabLayout.addTab(tabLayout.newTab().setText("EVENTS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

        dbHandler = new DBHandler(this, null, null, 1);
        /*try {
            last_id = Integer.parseInt(dbHandler.getMaxId());
        }
        catch (NumberFormatException e) {
            last_id = 0;
        }
        last_id=0;*/
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        ConnectivityManager connMgr = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            menu.getItem(1).setEnabled(true);
            menu.getItem(1).getIcon().setAlpha(255);
        }
        else {
            menu.getItem(1).setEnabled(false);
            menu.getItem(1).getIcon().setAlpha(120);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load:
                menuItem = item;
                menuItem.setActionView(R.layout.progressbar);
                menuItem.expandActionView();
                new DownloadJsonTask(new AsyncResult() {
                    @Override
                    public void onResult(JSONObject object) {
                        processJson(object);
                    }
                }).execute("https://spreadsheets.google.com/tq?key=1rKzPTY-3_bUQzEfXPmpKRQfskLXeNOja1WDEDYoExfE");
                // https://spreadsheets.google.com/tq?tq=SELECT%20*%20WHERE%20A>"+String.valueOf(last_id)+"&key=1rKzPTY-3_bUQzEfXPmpKRQfskLXeNOja1WDEDYoExfE
                break;
            default:
                break;
        }
        return true;
    }

    private void processJson(JSONObject object) {

        JSONArray rows = null;
        try {
            rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                id = columns.getJSONObject(0).getInt("v");
                title = columns.getJSONObject(1).getString("v");
                description = columns.getJSONObject(2).getString("v");
                imgUrl = columns.getJSONObject(3).getString("v");
                type = columns.getJSONObject(4).getString("v");
                status = columns.getJSONObject(5).getInt("v");

                // Log.v("status", String.valueOf(id)+" "+String.valueOf(status));

                if (!dbHandler.idExists(id) && status != 2) {
                    Event event = new Event(id, title, description, imgUrl, type, status);
                    dbHandler.addEvent(event);
                }

                if (dbHandler.idExists(id) && status == 2) dbHandler.deleteTask(id);


                /*
                if (status == 1 && id > last_id) { // if event is active and new
                    Event event = new Event(id, title, description, imgUrl, type, status);
                    dbHandler.addEvent(event);
                    // last_id = id;
                    // Log.v("event", String.valueOf(id));
                }
                else if (status == 0) { // event is marked inactive
                    Event event = new Event(id, title, description, imgUrl, type, status);
                    if (id > last_id) { dbHandler.addEvent(event); }
                    else { dbHandler.updateTask(event); }
                    // last_id = id;
                }
                else if (status == 2) { // delete event
                    dbHandler.deleteTask(id);
                }
                /*else if (id < 0) {
                    dbHandler.deleteTask((-1)*id);
                }*/
            }

            //Log.v("max id",String.valueOf(last_id));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Reloading the fragments
        OneF f1 = (OneF) getSupportFragmentManager().getFragments().get(0);
        getSupportFragmentManager().beginTransaction()
                .detach(f1)
                .attach(f1)
                .commit();
        TwoF f2 = (TwoF) getSupportFragmentManager().getFragments().get(1);
        getSupportFragmentManager().beginTransaction()
                .detach(f2)
                .attach(f2)
                .commit();
        menuItem.collapseActionView();
        menuItem.setActionView(null);
    }

}

