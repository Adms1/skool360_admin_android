package com.skool360admin.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiClient;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

public class BaseApp extends Application {

    public static Context mAppcontext;

    public static Context getAppContext() {
        return mAppcontext;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        // FontsOverride.setDefaultFont(this, "DEFAULT", "Fonts/opensans_regular.ttf");
        mAppcontext = getApplicationContext();

//        FontsOverride.setDefaultFont(this, "MONOSPACE", "font/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SERIF", "font/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "font/TitilliumWeb-Regular.ttf");

        try {
//            new GetAPIURLTask(mAppcontext).execute();

            //Standard Filter
            // CALL Standard API HERE

            if (!Utils.checkNetwork(mAppcontext)) {
                Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), (Activity) getApplicationContext());
                return;
            }

            WebServices apiService = ApiClient.getClient().create(WebServices.class);

            Call<JsonObject> call = apiService.getBaseUrl(AppConfiguration.GET_API_URL);
            call.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {
                    Utils.dismissDialog();
                    if (response.body() == null) {
                        Utils.ping(mAppcontext, getString(R.string.something_wrong));
                        return;
                    }
                    if (response.body().get("succcess") == null) {
                        Utils.ping(mAppcontext, getString(R.string.something_wrong));
                        return;
                    }
                    if (response.body().get("succcess").getAsString().equalsIgnoreCase("0")) {
                        Utils.ping(mAppcontext, getString(R.string.false_msg));
                        return;
                    }
                    if (response.body().get("succcess").getAsString().equalsIgnoreCase("1")) {
                        PrefUtils.getInstance(BaseApp.mAppcontext).setValue("live_base_url", response.body().get("appsUrl").getAsString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    Utils.dismissDialog();
                    t.printStackTrace();
                    t.getMessage();
                    Utils.ping(mAppcontext, getString(R.string.something_wrong));
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        android.support.multidex.MultiDex.install(base);
    }

}
