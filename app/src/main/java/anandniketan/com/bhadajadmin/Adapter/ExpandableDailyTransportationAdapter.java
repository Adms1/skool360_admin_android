package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.HR.DailyTransportationModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.Utils;

public class ExpandableDailyTransportationAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<DailyTransportationModel.FinalArray>> listChildData;
    private String standard = "", standardIDS = "";
    private int annousID;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private String viewstatus;

    public ExpandableDailyTransportationAdapter(Context context, List<String> listDataHeader, HashMap<String, ArrayList<DailyTransportationModel.FinalArray>> listDataChild, String viewstatus) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
        this.viewstatus = viewstatus;

    }

    @Override
    public List<DailyTransportationModel.FinalArray> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<DailyTransportationModel.FinalArray> childData = getChild(groupPosition, 0);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_child_daily_transportation_list, null);
        }

        TextView route, drive, parents, vehicle, timing, other;

        route = convertView.findViewById(R.id.txt_route);
        drive = convertView.findViewById(R.id.txt_driver);
        parents = convertView.findViewById(R.id.txt_parents);
        vehicle = convertView.findViewById(R.id.txt_vehicle);
        timing = convertView.findViewById(R.id.txt_timing);
        other = convertView.findViewById(R.id.txt_other);

        route.setText(childData.get(childPosition).getRouteProblem());
        drive.setText(childData.get(childPosition).getDriverComplaint());
        parents.setText(childData.get(childPosition).getParentsComplaint());
        vehicle.setText(childData.get(childPosition).getVehicleProblem());
        timing.setText(childData.get(childPosition).getTimingProblem());
        other.setText(childData.get(childPosition).getOther());


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (viewstatus.equalsIgnoreCase("true")) {
            return this.listChildData.get(this._listDataHeader.get(groupPosition)).size();
        } else {
            Utils.ping(_context, "Access Denied");
            return 0;
        }
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

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_header_daily_transporation, null);
        }
        TextView txt_index, txt_date, txt_createby;
        ImageView iv_indicator;

        txt_index = convertView.findViewById(R.id.index_txt);
        txt_date = convertView.findViewById(R.id.date_txt);
        txt_createby = convertView.findViewById(R.id.createby_txt);
        iv_indicator = convertView.findViewById(R.id.iv_indicator);

        String index = String.valueOf(groupPosition + 1);

        txt_index.setText(index);
        txt_date.setText(headerTitle1);
        txt_createby.setText(headerTitle2);

        if (viewstatus.equalsIgnoreCase("true")) {
            if (isExpanded) {
                iv_indicator.setImageResource(R.drawable.arrow_1_42_down);
            } else {
                iv_indicator.setImageResource(R.drawable.arrow_1_42);
            }
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
