package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.R;

/**
 * Created by admsandroid on 2/1/2018.
 */

public class ExpandableListAdapterMenu extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<String>> _listDataChild;
    private ArrayList<String> imagesId;

    public ExpandableListAdapterMenu(Context context, List<String> listDataHeader,
                                     HashMap<String, ArrayList<String>> listDataChild, ArrayList<String> imagesId) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
        this.imagesId = imagesId;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            convertView = infalInflater.inflate(R.layout.list_item_menu, null);
            TextView txtLecture;
            txtLecture = convertView.findViewById(R.id.txtLecture);
            txtLecture.setText(getChild(groupPosition, childPosition));

        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        String [] spiltValue=headerTitle.split("\\|");
        Log.d("headerTitle", headerTitle);

        if (convertView == null) {

        }
        LayoutInflater infalInflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        convertView = infalInflater.inflate(R.layout.list_group_menu, null);

        TextView lblListHeader = convertView.findViewById(R.id.title);
        ImageView image_menu = convertView.findViewById(R.id.image_menu);
        ImageView image_arrow = convertView.findViewById(R.id.image_arrow);

            lblListHeader.setText(spiltValue[0]);

        Glide.with(_context)
                .load(spiltValue[1])
                .into(image_menu);

        if (spiltValue[0].equalsIgnoreCase("OTHER")) {
            image_arrow.setVisibility(View.VISIBLE);
            if (isExpanded) {
                image_arrow.setBackgroundResource(R.drawable.arrow_1_42_down);
            } else {
                image_arrow.setBackgroundResource(R.drawable.arrow_1_42);
            }
        } else {
            image_arrow.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}






