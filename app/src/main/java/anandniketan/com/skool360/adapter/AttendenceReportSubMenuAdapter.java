package anandniketan.com.skool360.adapter;

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

public class AttendenceReportSubMenuAdapter extends BaseAdapter {
    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Attendance%20Report/" + "In%20Out%20Summary.png",
            AppConfiguration.BASEURL_IMAGES + "Attendance%20Report/" + "Employee%20In%20Out%20Details.png",
            AppConfiguration.BASEURL_IMAGES + "Attendance%20Report/" + "Employee%20Present%20Details.png",
            //AppConfiguration.BASEURL_IMAGES + "Attendence Report/" + "",

    };
    public String[] mThumbNames = {"In Out Summary", "Employee In Out Details", "Employee Present Details"};

    private Context mContext;

    // Constructor
    public AttendenceReportSubMenuAdapter(Context c) {
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
        View line1 = null, line2 = null;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.sub_menu_grid_cell, null);

        imgGridOptions = convertView.findViewById(R.id.imgGridOptions);
        txtGridOptionsName = convertView.findViewById(R.id.txtGridOptionsName);
        line1 = convertView.findViewById(R.id.view_line1);
        line2 = convertView.findViewById(R.id.view_line2);

        String url = mThumbIds[position];
//        Log.d("url", url);
        Glide.with(mContext)
                .load(url)
                .into(imgGridOptions);

//        imgGridOptions.setImageResource(mThumbIds[position]);
        txtGridOptionsName.setText(mThumbNames[position]);

        if (position % 2 == 0) {
            line1.setVisibility(View.VISIBLE);
        } else {
            line1.setVisibility(View.GONE);
        }

        if (mThumbNames.length % 2 != 0) {

            if (position == mThumbNames.length - 1) {
                line2.setVisibility(View.GONE);
            } else {
                line2.setVisibility(View.VISIBLE);
            }

        } else {
            if (position == mThumbNames.length - 2 || position == mThumbNames.length - 1) {
                line2.setVisibility(View.GONE);
            } else {
                line2.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

}


