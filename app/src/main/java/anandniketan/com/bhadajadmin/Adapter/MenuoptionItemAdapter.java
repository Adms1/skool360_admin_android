package anandniketan.com.bhadajadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;


/**
 * Created by admsandroid on 11/16/2017.
 */

public class MenuoptionItemAdapter extends BaseAdapter {

    private Context context;

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Home.png",
            AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Student.png",
            AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Staff.png",
            AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Account.png",
            AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Transport.png",
            AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_HR.png",
            AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Other.png",
            AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Logout.png"
    };

    public String[] mThumbNames = {"Home", "Student", "Staff", "Account", "Transport", "HR", "Other", "Logout"};


    public MenuoptionItemAdapter(Context context) {
        this.context = context;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.menu_drawer_item, null);
        }
        ImageView img = convertView.findViewById(R.id.image_menu);
//        img.setImageResource(mThumbIds[position]);
        String url = mThumbIds[position];
//        Log.d("url", url);
        Glide.with(context)
                .load(url)
                .into(img);
        TextView txtTitle = convertView.findViewById(R.id.title);
        txtTitle.setText(mThumbNames[position]);
        return convertView;
    }

}

