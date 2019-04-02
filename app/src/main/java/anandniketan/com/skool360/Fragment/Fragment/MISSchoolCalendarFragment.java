package anandniketan.com.skool360.Fragment.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Adapter.SchoolCalendarAdapter;
import anandniketan.com.skool360.Model.MIStudentWiseCalendarModel;
import anandniketan.com.skool360.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MISSchoolCalendarFragment extends Fragment {

    private static List<MIStudentWiseCalendarModel.Datum> dataList = new ArrayList<>();
    private ExpandableListView expList;
    private int lastExpandedPosition = -1;
    private SchoolCalendarAdapter schoolCalendarAdapter;
    private List<String> lvHeader;
    private HashMap<String, ArrayList<String>> lvChild;
    private int page;
    private LinearLayout ll;
    private TextView tvNoRecord;

//    public MISSchoolCalendarFragment() {
//        Bundle args = new Bundle();
//        dataList = new ArrayList<>();
//        dataList = args.getParcelableArrayList("dataList");
////        args.putParcelableArrayList("dataList", (ArrayList<? extends Parcelable>) dataList);
//
//    }

    public static MISSchoolCalendarFragment newInstance(int page, List<MIStudentWiseCalendarModel.Datum> data) {
        MISSchoolCalendarFragment fragment = new MISSchoolCalendarFragment();
        Bundle args = new Bundle();

        args.putInt("page", page);
        args.putParcelableArrayList("dataList", (ArrayList<? extends Parcelable>) data);

//        dataList = data;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("page", 0);
        dataList = getArguments().getParcelableArrayList("dataList");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_misschool_calendar, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        expList = view.findViewById(R.id.schoolcalendar_lvExpstudentlist);
        tvNoRecord = view.findViewById(R.id.cal_tv_no_records);
        ll = view.findViewById(R.id.cal_ll);

//        expList.setNestedScrollingEnabled(true);


//        try {
//            if (getArguments() != null) {
//                dataList = getArguments().getParcelableArrayList("dataList");
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        if (dataList != null) {
            if (dataList.size() > 0) {

                tvNoRecord.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);

                fillArrays();

                schoolCalendarAdapter = new SchoolCalendarAdapter(getActivity(), lvHeader, lvChild);
                expList.setAdapter(schoolCalendarAdapter);
            } else {
                tvNoRecord.setVisibility(View.VISIBLE);
                ll.setVisibility(View.GONE);
            }
        }

        expList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (expandableListView.isGroupExpanded(i)) {
                    expandableListView.collapseGroup(i);
                } else {
                    expandableListView.expandGroup(i);
                }

                return true;
            }
        });

        expList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {

                    expList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }

        });
    }

    public void fillArrays() {
        lvHeader = new ArrayList<>();
        lvChild = new HashMap<>();
        for (int i = 0; i < dataList.size(); i++) {
            lvHeader.add(dataList.get(i).getName() + "|" + dataList.get(i).getDate() + "|" + dataList.get(i).getDays());
            Log.d("header", "" + lvHeader);
            ArrayList<String> row = new ArrayList<>();
            row.add(dataList.get(i).getGrade());
            Log.d("row", "" + row);
            lvChild.put(lvHeader.get(i), row);
            Log.d("child", "" + lvChild);
        }

    }

}
