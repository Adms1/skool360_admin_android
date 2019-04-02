package anandniketan.com.skool360.asynctasks;

import android.os.AsyncTask;

import java.util.HashMap;

import anandniketan.com.skool360.Model.login.LogInModel;

public class VerifyLoginAsyncTask extends AsyncTask<Void, Void, LogInModel> {
    HashMap<String, String> param;

    public VerifyLoginAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected LogInModel doInBackground(Void... params) {
        String responseString = null;
        LogInModel result = new LogInModel();
        try {

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(LogInModel result) {
        super.onPostExecute(result);
    }
}