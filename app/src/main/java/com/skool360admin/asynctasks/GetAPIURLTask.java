package com.skool360admin.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.WebServicesCall;

import static anandniketan.com.bhadajadmin.Utility.AppConfiguration.BASE_API_CONTAINER;
import static anandniketan.com.bhadajadmin.Utility.AppConfiguration.GET_API_URL;

public class GetAPIURLTask extends AsyncTask<Void, Void, Boolean> {
    HashMap<String, String> param = new HashMap<String, String>();
    private Context context;
    private int GET = 0,POST = 1;

    public GetAPIURLTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        String responseString = null;
        boolean success = false;
        try {
            responseString = WebServicesCall.RunScript(GET_API_URL,GET);
            success = parseJson(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean parseJson(String responseString) {
        try {
            JSONObject reader = new JSONObject(responseString);
            String readerString = reader.getString("succcess");
            String apiUrl  =  reader.getString("appsUrl");

            AppConfiguration.BASEURL = apiUrl+BASE_API_CONTAINER;
            AppConfiguration.LIVE_BASE_URL = apiUrl;

            PrefUtils.getInstance(context).setValue("live_base_url",AppConfiguration.LIVE_BASE_URL);//result.get("TermID"));

            if (readerString.equalsIgnoreCase("1")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
