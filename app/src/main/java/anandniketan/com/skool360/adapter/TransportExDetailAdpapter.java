package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Model.MIS.TransportMainModel;
import anandniketan.com.skool360.R;

public class TransportExDetailAdpapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<TransportMainModel.StudentDatum>> child = new HashMap<>();

    public TransportExDetailAdpapter(Context _context, List<String> listDataHeader, HashMap<String, ArrayList<TransportMainModel.StudentDatum>> child) {
        this._context = _context;
        this._listDataHeader = listDataHeader;
        this.child = child;
    }

    @Override
    public TransportMainModel.StudentDatum getChild(int groupPosition, int childPosititon) {
        return this.child.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final TransportMainModel.StudentDatum childData = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = infalInflater.inflate(R.layout.list_item_detail_transport_child, null);

        }

        TextView routename, pickpoint, picktime, droppoint, droptime;
        ImageView viewtxt;

        routename = convertView.findViewById(R.id.transport_child_route);
        pickpoint = convertView.findViewById(R.id.transport_child_pp);
        picktime = convertView.findViewById(R.id.transport_child_pt);
        droppoint = convertView.findViewById(R.id.transport_child_dp);
        droptime = convertView.findViewById(R.id.transport_child_dt);

        routename.setText(String.valueOf(childData.getRoute()));
        pickpoint.setText(String.valueOf(childData.getPickuppoint()));
        picktime.setText(String.valueOf(childData.getPickuptime()));
        droppoint.setText(String.valueOf(childData.getDroppoint()));
        droptime.setText(String.valueOf(childData.getDroptime()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.child.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];
        String headerTitle4 = headerTitle[3];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_detail_transport, null);
        }

        if (isExpanded) {

        }

        TextView classnm, stuname, grno, phno;
        ImageView ivview;

        LinearLayout ll, ll1;

        ll = convertView.findViewById(R.id.vieww);
        ll1 = convertView.findViewById(R.id.vieww1);

        ll.setVisibility(View.GONE);
        ll1.setVisibility(View.VISIBLE);

        LinearLayout headerList = convertView.findViewById(R.id.child_header_route);

        classnm = convertView.findViewById(R.id.transportd_class_txt2);
        stuname = convertView.findViewById(R.id.transportd_stuname_txt2);
        grno = convertView.findViewById(R.id.transportd_grno_txt2);
        phno = convertView.findViewById(R.id.transportd_phno_txt2);
        ivview = convertView.findViewById(R.id.transportd_view_txt2);

        classnm.setTextColor(_context.getResources().getColor(R.color.black));
        stuname.setTextColor(_context.getResources().getColor(R.color.black));
        grno.setTextColor(_context.getResources().getColor(R.color.black));
        phno.setTextColor(_context.getResources().getColor(R.color.black));

        classnm.setText(String.valueOf(headerTitle1));
        stuname.setText(String.valueOf(headerTitle2));
        grno.setText(String.valueOf(headerTitle3));
        phno.setText(String.valueOf(headerTitle4));

        if (isExpanded) {
            headerList.setVisibility(View.VISIBLE);
            ivview.setImageResource(R.drawable.arrow_1_42_down);
        } else {
            headerList.setVisibility(View.GONE);
            ivview.setImageResource(R.drawable.arrow_1_42);
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

