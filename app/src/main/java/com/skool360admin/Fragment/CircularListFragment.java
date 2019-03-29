package com.skool360admin.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.ExpandableListCircular;
import anandniketan.com.bhadajadmin.Interface.OnUpdateRecord;
import anandniketan.com.bhadajadmin.Interface.onDeleteWithId;
import anandniketan.com.bhadajadmin.Model.Student.AnnouncementModel;
import anandniketan.com.bhadajadmin.Model.Student.CircularModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiHandler;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CircularListFragment extends Fragment implements onDeleteWithId,OnUpdateRecord {


    private View rootView;
    private Context mContext;
    private ExpandableListCircular expandableListCircular;
    private ExpandableListView expandableListView;
    private FloatingActionButton fabAdd;
    private List<AnnouncementModel> finalArrayAnnouncement;
    private List<CircularModel.FinalArray> finalArrayAnnouncementFinal;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<CircularModel.FinalArray>> listDataChild;
    private TextView txtNoRecordsAnnouncement;
    private Button btnMenuLinear;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private onDeleteWithId onDeleteWithIdRef;
    private OnUpdateRecord onUpdateRecordRef;
    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public CircularListFragment() {
        mContext = getActivity();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        onDeleteWithIdRef = this;
        onUpdateRecordRef = this;
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 11;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_circular_list,container,false);

        fabAdd = rootView.findViewById(R.id.fab_add_annoucement);
        expandableListView = rootView.findViewById(R.id.annoucement_list);
        txtNoRecordsAnnouncement = rootView.findViewById(R.id.txt_empty_view);
        btnMenuLinear = rootView.findViewById(R.id.btnmenu);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.circular);

        setListners();
        callCircularListApi();

    }

    public void setListners() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new CircularFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container,fragment).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 59;
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new StudentFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right).replace(R.id.frame_container,fragment).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 11;
            }
        });
    }


    private void callCircularListApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error),getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getCircularDataAdmin(getDetail(),new retrofit.Callback<CircularModel>() {
            @Override
            public void success(CircularModel announcementModel, Response response) {
                Utils.dismissDialog();
                if (announcementModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (announcementModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (announcementModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    txtNoRecordsAnnouncement.setVisibility(View.VISIBLE);
                    expandableListView.setVisibility(View.GONE);
                    return;
                }
                if (announcementModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayAnnouncementFinal = announcementModel.getFinalArray();
                    if (finalArrayAnnouncementFinal != null) {
                        txtNoRecordsAnnouncement.setVisibility(View.GONE);
                        expandableListView.setVisibility(View.VISIBLE);
                        fillExpLV();
                        expandableListCircular = new ExpandableListCircular(getActivity(), listDataHeader, listDataChild, onDeleteWithIdRef, onUpdateRecordRef, PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student").get("Circular").getIsuserview(), PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student").get("Circular").getIsuserupdate(), PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student").get("Circular").getIsuserdelete());
                        expandableListView.setAdapter(expandableListCircular);
                    } else {
                        txtNoRecordsAnnouncement.setVisibility(View.VISIBLE);
                        expandableListView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext,getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }


    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<CircularModel.FinalArray>>();
        for (int i = 0; i < finalArrayAnnouncementFinal.size(); i++) {
            listDataHeader.add(finalArrayAnnouncementFinal.get(i).getSubject()+"|"+finalArrayAnnouncementFinal.get(i).getDate()+"|"+finalArrayAnnouncementFinal.get(i).getStatus());
            Log.d("header", "" + listDataHeader);
            ArrayList<CircularModel.FinalArray> row = new ArrayList<CircularModel.FinalArray>();
            row.add(finalArrayAnnouncementFinal.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    @Override
    public void deleteRecordWithId(String id) {
        callDeleteCircular(id);
    }


    public void callDeleteCircular(String Id) {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().deleteCircular(getDeleteAnnouncementParams(Id),new retrofit.Callback<CircularModel>() {
            @Override
            public void success(CircularModel permissionModel, Response response) {
                Utils.dismissDialog();
                if (permissionModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("false")) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (permissionModel.getSuccess().equalsIgnoreCase("True")) {
                    Utils.ping(mContext,"Circular deleted successfully");
                    callCircularListApi();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getDeleteAnnouncementParams(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("CircularID",id);
        return map;
    }


    @Override
    public void onUpdateRecord(List<AnnouncementModel.FinalArray> dataList) {

    }

    @Override
    public void onUpdateRecordCircular(List<CircularModel.FinalArray> dataList) {

    }

}
