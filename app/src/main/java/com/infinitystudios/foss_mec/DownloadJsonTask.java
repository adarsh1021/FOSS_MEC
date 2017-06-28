package com.infinitystudios.foss_mec;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by Adarsh on 28-06-2017.
 */

public class DownloadJsonTask extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... params) {
        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(params[0]);
        } catch (IOException e) {
            return "Unable to get Json response.";
        }
    }
}
