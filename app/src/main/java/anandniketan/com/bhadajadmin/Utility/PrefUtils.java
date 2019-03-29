package anandniketan.com.bhadajadmin.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import anandniketan.com.bhadajadmin.Model.PermissionDataModel;

public class PrefUtils {

    private static PrefUtils mSharedPreferenceUtils;
    protected Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;
    private String APP_PREF = "BhadajAdmin";
    public static String KEY_USERID = "";
    public static final String LOGIN_KEY = "BhadajAdmin.login";
    public static final String LANGUGAE_KEY = "BhadajAdmin.language";
    public static final String USERNAME_KEY = "BhadajAdmin.username";
    public static final String EMAIL_KEY = "BhadajAdmin.email";
    public static final String MOB_KEY = "BhadajAdmin.mobile";
    public static final String ADDRESS_KEY = "BhadajAdmin.address";
    public static final String SHARED_PREF = "BhadajAdmin.fcmTokenId";
    public static final String isFirstTimeKey = "isFirstTime";


    private PrefUtils(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    /**
     * Creates single instance of SharedPreferenceUtils
     *
     * @param context context of Activity or Service
     * @return Returns instance of SharedPreferenceUtils
     */
    public static synchronized PrefUtils getInstance(Context context) {

        if (mSharedPreferenceUtils == null) {
            mSharedPreferenceUtils = new PrefUtils(context.getApplicationContext());
        }
        return mSharedPreferenceUtils;
    }

    /**
     * Stores String value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    public void setValue(String key, String value) {
        mSharedPreferencesEditor.putString(key, value);
        mSharedPreferencesEditor.commit();
        mSharedPreferencesEditor.apply();
    }

    public void setUserLogin(){
        mSharedPreferencesEditor.putBoolean(LOGIN_KEY, true);
        mSharedPreferencesEditor.commit();
        mSharedPreferencesEditor.apply();
    }

    public boolean getUserLogin(){
       return mSharedPreferences.getBoolean(LOGIN_KEY,false);
    }

    public void setUserLogout(){
        mSharedPreferencesEditor.putBoolean(LOGIN_KEY, false);
        mSharedPreferencesEditor.commit();
        mSharedPreferencesEditor.apply();
    }

    public void setLangugae(){
        mSharedPreferencesEditor.putBoolean(LANGUGAE_KEY, true);
        mSharedPreferencesEditor.commit();
        mSharedPreferencesEditor.apply();
    }

    public boolean isLanguageSelected(){
        return mSharedPreferences.getBoolean(LANGUGAE_KEY,false);
    }


    public void setIsFirstTime(boolean value){
        mSharedPreferencesEditor.putBoolean(isFirstTimeKey, value);
        mSharedPreferencesEditor.commit();
        mSharedPreferencesEditor.apply();
}

    public boolean isFirstTime(){
        return mSharedPreferences.getBoolean(isFirstTimeKey,false);
    }

    /**
     * Stores int value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    public void setValue(String key, int value) {
        mSharedPreferencesEditor.putInt(key, value);
        mSharedPreferencesEditor.commit();
        mSharedPreferencesEditor.apply();
    }

    /**
     * Stores Double value in String format in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    public void setValue(String key, double value) {
        setValue(key, Double.toString(value));
    }

    /**
     * Stores long value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    public void setValue(String key, long value) {
        mSharedPreferencesEditor.putLong(key, value);
        mSharedPreferencesEditor.commit();
        mSharedPreferencesEditor.apply();
    }

    /**
     * Stores boolean value in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    public void setValue(String key, boolean value) {
        mSharedPreferencesEditor.putBoolean(key, value);
        mSharedPreferencesEditor.commit();
        mSharedPreferencesEditor.apply();
    }

    /**
     * Retrieves String value from preference
     *
     * @param key          key of preference
     * @param defaultValue default value if no key found
     */
    public String getStringValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    /**
     * Retrieves int value from preference
     *
     * @param key          key of preference
     * @param defaultValue default value if no key found
     */
    public int getIntValue(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Retrieves long value from preference
     *
     * @param key          key of preference
     * @param defaultValue default value if no key found
     */
    public long getLongValue(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    /**
     * Retrieves boolean value from preference
     *
     * @param keyFlag      key of preference
     * @param defaultValue default value if no key found
     */
    public boolean getBoolanValue(String keyFlag, boolean defaultValue) {
        return mSharedPreferences.getBoolean(keyFlag, defaultValue);
    }




    /**
     * Removes key from preference
     *
     * @param key key of preference that is to be deleted
     */
    public void removeKey(String key) {
        if (mSharedPreferencesEditor != null) {
            mSharedPreferencesEditor.remove(key);
            mSharedPreferencesEditor.commit();
            mSharedPreferencesEditor.apply();
        }
    }

    public void clear() {
        mSharedPreferencesEditor.clear().commit();
    }

    public void saveMap(Context context, String key, Map<String, PermissionDataModel.Detaill> inputMap) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(inputMap);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public HashMap<String, PermissionDataModel.Detaill> loadMap(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, "");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, PermissionDataModel.Detaill>>() {
        }.getType();
        HashMap<String, PermissionDataModel.Detaill> obj = gson.fromJson(json, type);
        return obj;
    }

    public void saveArr(Context context, String key, ArrayList<HashMap<String, PermissionDataModel.Detaill>> arrayList) {
        SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor collection = db.edit();
        Gson gson = new Gson();
        String arrayList1 = gson.toJson(arrayList);

        collection.putString(key, arrayList1);
        collection.commit();
    }

    public ArrayList<HashMap<String, PermissionDataModel.Detaill>> loadArr(Context context, String key) {
        SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String arrayListString = db.getString(key, null);
        Type type = new TypeToken<ArrayList<HashMap<String, PermissionDataModel.Detaill>>>() {
        }.getType();
        ArrayList<HashMap<String, PermissionDataModel.Detaill>> arrayList = gson.fromJson(arrayListString, type);
        return arrayList;
    }

}
