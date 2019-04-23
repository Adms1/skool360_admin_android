package anandniketan.com.skool360.Utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import anandniketan.com.skool360.Model.PermissionDataModel;
import anandniketan.com.skool360.R;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by admsandroid on 11/20/2017.
 */

public class Utils {
    public static Dialog dialog;
    public static String parentFolderName = "Skool 360 Teacher";
    public static String childAnnouncementFolderName = "Pdf";
    public static String childCircularFolderName = "Word";
    Context context;

    public static boolean isNetworkConnected(Context ctxt) {
        ConnectivityManager cm = (ConnectivityManager) ctxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }

    public static boolean checkNetwork(Context context) {
        boolean wifiAvailable = false;
        boolean mobileAvailable = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    wifiAvailable = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    mobileAvailable = true;
        }
        return wifiAvailable || mobileAvailable;
    }

    public static void showCustomDialog(String title, String str, Activity activity) {
        // custom dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_simple_dailog_ok, null);

        dialogBuilder.setView(dialogView);

        TextView txt_message_dialog = dialogView.findViewById(R.id.txt_message_dialog);
        txt_message_dialog.setText(str);

        TextView txt_title_dialog = dialogView.findViewById(R.id.txt_title_dialog);
        txt_title_dialog.setText(title);

        TextView btn_ok = dialogView.findViewById(R.id.btn_ok);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public static void showDialog(Context context) {
        if (dialog == null) {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progressbar_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

    }

    public static void hideKeyboard(Activity context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((null == context.getCurrentFocus()) ? null : context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void ping(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void pong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String getTodaysDate() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);


        String mDAY, mMONTH, mYEAR;

        mDAY = Integer.toString(dd);
        mMONTH = Integer.toString(mm);
        mYEAR = Integer.toString(yy);

        if (dd < 10) {
            mDAY = "0" + mDAY;
        }
        if (mm < 10) {
            mMONTH = "0" + mMONTH;
        }

        return mDAY + "/" + mMONTH + "/" + mYEAR;
    }

    public static String getCurrentDateTime(String whichFormat) {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat(whichFormat);
        String dateToStr = format.format(today);
        return dateToStr;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getFilePathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static HashMap<String, String> getValuesFromJson(String data, String keyPrefix) {
        HashMap<String, String> dataMap = new HashMap<>();
        try {

            JSONObject dataObject = new JSONObject(data);
            JSONArray dataArray = dataObject.getJSONArray("FinalArray");

            JSONObject innerData = dataArray.getJSONObject(0);


            Iterator<String> keyList = innerData.keys();

            ArrayList<String> keys = new ArrayList<String>();
            while (keyList.hasNext()) {
                String key = keyList.next();
                try {
                    // Object value = innerData.get(key);
                    keys.add(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ArrayList<String> values = new ArrayList<String>();

            for (int countvalue = 0; countvalue < keys.size(); countvalue++) {
                String value = innerData.optString(keys.get(countvalue));
                values.add(value);

            }


            for (int pos = 0; pos < values.size(); pos++) {
                dataMap.put(keys.get(pos) + keyPrefix, values.get(pos));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dataMap;
    }

    public static void callPermissionDetail(final Context mcontext, String usrid, String locationid) {
        WebServices apiService = ApiClient.getClient().create(WebServices.class);
//
        Call<PermissionDataModel> call = apiService.getPermissionData(AppConfiguration.BASEURL + "GetPermissionData?UserID=" + usrid + "&LocationID=" + locationid);
        call.enqueue(new Callback<PermissionDataModel>() {
            //
            @Override
            public void onResponse(Call<PermissionDataModel> call, retrofit2.Response<PermissionDataModel> response) {
                Utils.dismissDialog();
                if (response.body() == null) {

                    return;
                }

                if (response.body().getSuccess().equalsIgnoreCase("false")) {

                    return;
                }

                if (response.body().getSuccess().equalsIgnoreCase("True")) {

                    if (response.body().getFinalarray() != null) {
                        for (int i = 0; i < response.body().getFinalarray().size(); i++) {

                            HashMap<String, PermissionDataModel.Detaill> child = new HashMap<>();
//                            ArrayList<HashMap<String, PermissionDataModel.Detaill>> arrayList = new ArrayList<>();
                            for (int j = 0; j < response.body().getFinalarray().get(i).getDetail().size(); j++) {

                                child.put(response.body().getFinalarray().get(i).getDetail().get(j).getPagename(), response.body().getFinalarray().get(i).getDetail().get(j));

                            }
//                            arrayList.add(child);

                            PrefUtils.getInstance(mcontext).saveMap(mcontext, response.body().getFinalarray().get(i).getName(), child);
//                            PrefUtils.getInstance(LoginActivity.this).saveArr(LoginActivity.this, response.body().getFinalarray().get(i).getName(), arrayList);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<PermissionDataModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("permissionnnnn", t.toString());
            }
        });
    }

    public static void showUserBirthdayWish(Context context, String Bday) {

        try {
            if (PrefUtils.getInstance(context).getStringValue("user_birthday_wish", "").equalsIgnoreCase("0")) {
                if (PrefUtils.getInstance(context).getStringValue("user_birthday", "") != null) {
                    if (PrefUtils.getInstance(context).getStringValue("user_birthday", "").equals("")) {

                        PrefUtils.getInstance(context).setValue("user_birthday", Bday);

                        String Bday_local = PrefUtils.getInstance(context).getStringValue("user_birthday", "");

                        String[] BdayArray = Bday_local.split("/");
                        String date = BdayArray[0];
                        String month = BdayArray[1];

                        if (Utils.checkBirthdayOfUser(Integer.parseInt(month), Integer.parseInt(date))) {

                            String flag = PrefUtils.getInstance(context).getStringValue("user_birthday_wish", "");
                            if (flag != null && !flag.equalsIgnoreCase("")) {
                                if (flag.equalsIgnoreCase("0")) {
//                                    PrefUtils.getInstance(context).setValue("user_birthday_wish", "1");
                                    PrefUtils.getInstance(context).setValue("user_birthday", "N/A");
                                    DialogUtils.showGIFDialog(context, "Skool 360");

                                }
                            }
                        }
                    } else {

//                            Utility.setPref(context, "user_birthday", Bday);
//
//                            String Bday_local = Utility.getPref(context, "user_birthday");
//
//                            String[] BdayArray = Bday_local.split("/");
//                            String date = BdayArray[0];
//                            String month = BdayArray[1];
//
//                            if (Utility.checkBirthdayOfUser(Integer.parseInt(month), Integer.parseInt(date))) {
//                                String flag = Utility.getPref(context, "user_birthday_wish");
//                                if (flag != null && !flag.equalsIgnoreCase("")) {
//                                    if (flag.equalsIgnoreCase("0")) {
//                                        Utility.setPref(context, "user_birthday_wish", "1");
//
//                                        Utility.setPref(context, "user_birthday", "N/A");
//                                        DialogUtils.showGIFDialog(context, "Anand Niketan Bhadaj");
//
//                                    }
//                                }
//                            }
                    }
                }
            } else {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean checkBirthdayOfUser(int month, int date) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH) + 1;
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        return (month == monthOfYear) && (date == dayOfMonth);
    }

}
