package anandniketan.com.skool360.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import anandniketan.com.skool360.Model.IconHeaderModel;
import anandniketan.com.skool360.R;

/**
 * Created by admsandroid on 11/17/2017.
 */

public class StudentSubMenuAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<IconHeaderModel> newArr;

    // Constructor
    public StudentSubMenuAdapter(Context c, ArrayList<IconHeaderModel> newArr) {
        mContext = c;
        this.newArr = newArr;
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

        String url = newArr.get(position).getUrl();
//        Log.d("url", url);
        Glide.with(mContext)
                .load(url)
                .into(imgGridOptions);

//        imgGridOptions.setImageResource(mThumbIds[position]);
        txtGridOptionsName.setText(newArr.get(position).getName());

        if (position % 2 == 0) {
            line1.setVisibility(View.VISIBLE);
        } else {
            line1.setVisibility(View.GONE);
        }

        if (newArr.size() % 2 != 0) {

            if (position == newArr.size() - 1) {
                line2.setVisibility(View.GONE);
            } else {
                line2.setVisibility(View.VISIBLE);
            }

        } else {
            if (position == newArr.size() - 2 || position == newArr.size() - 1) {
                line2.setVisibility(View.GONE);
            } else {
                line2.setVisibility(View.VISIBLE);
            }
        }

        return convertView;

    }

    @Override
    public int getCount() {
        return newArr.size();
    }

    @Override
    public Object getItem(int position) {
        return newArr.get(position);
    }

    class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgGridOptions = null;
        TextView txtGridOptionsName = null;
        View line, line1, line3;

        public Viewholder(View convertView) {
            super(convertView);
            imgGridOptions = convertView.findViewById(R.id.imgGridOptions);
            txtGridOptionsName = convertView.findViewById(R.id.txtGridOptionsName);
//        line = convertView.findViewById(R.id.left_view);
            line1 = convertView.findViewById(R.id.view_line1);
//        line3 = convertView.findViewById(R.id.left_view3);


        }

    }
}


