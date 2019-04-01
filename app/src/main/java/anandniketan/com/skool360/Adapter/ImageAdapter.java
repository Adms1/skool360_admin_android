package anandniketan.com.skool360.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;


/**
 * Created by admsandroid on 11/16/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Main/" + "Student.png",
            AppConfiguration.BASEURL_IMAGES + "Main/" + "Staff.png",
            AppConfiguration.BASEURL_IMAGES + "Main/" + "HR.png",
            AppConfiguration.BASEURL_IMAGES + "Main/" + "Account.png",
            AppConfiguration.BASEURL_IMAGES + "Main/" + "SMS.png",
            AppConfiguration.BASEURL_IMAGES + "Main/" + "Account.png",

           /* AppConfiguration.BASEURL_IMAGES + "Main/" + "Transport.png",
            AppConfiguration.BASEURL_IMAGES + "Main/" + "other.png",*/
    };

    public String[] mThumbNames = {"Student","Staff","HR","Account","SMS","MIS"/*"Transport", "Other"*/};

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgGridOptions = null;
        TextView txtGridOptionsName = null;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.grid_cell, null);

        imgGridOptions = convertView.findViewById(R.id.imgGridOptions);
        txtGridOptionsName = convertView.findViewById(R.id.txtGridOptionsName);

        String url = mThumbIds[position];
//        Log.d("url", url);
        Glide.with(mContext)
                .load(url)
                .into(imgGridOptions);
//        imgGridOptions.setImageResource(mThumbIds[position]);
        txtGridOptionsName.setText(mThumbNames[position]);
        return convertView;
    }

}
