package anandniketan.com.skool360.Fragment.fragment.student;

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

import anandniketan.com.skool360.Interface.OnUpdateRecord;
import anandniketan.com.skool360.Interface.onDeleteWithId;
import anandniketan.com.skool360.Model.Student.AnnouncementModel;
import anandniketan.com.skool360.Model.Student.CircularModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.ExpandableListAnnoucement;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AnnoucementListFragment extends Fragment implements onDeleteWithId, OnUpdateRecord {

    private View rootView;
    private Context mContext;
    private ExpandableListAnnoucement expandableListAnnoucementAdapter;
    private ExpandableListView expandableListView;
    private FloatingActionButton fabAddAnnouncement;
    private List<AnnouncementModel.FinalArray> finalArrayAnnouncementFinal;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<AnnouncementModel.FinalArray>> listDataChild;
    private TextView txtNoRecordsAnnouncement;
    private Button btnMenuLinear;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private onDeleteWithId onDeleteWithIdRef;
    private OnUpdateRecord onUpdateRecordRef;
    private Button backBtn;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public AnnoucementListFragment() {
        mContext = getActivity();
    }

    @Override
    public void onAttach(Context context) {
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
        rootView = inflater.inflate(R.layout.fragment_annoucement_list, container, false);

        fabAddAnnouncement = rootView.findViewById(R.id.fab_add_annoucement);
        expandableListView = rootView.findViewById(R.id.annoucement_list);
        txtNoRecordsAnnouncement = rootView.findViewById(R.id.txt_empty_view);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view1 = view.findViewById(R.id.header);
        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        tvHeader.setText(R.string.announcment);

        setListners();
        callAnnouncementListApi();

    }

    public void setListners() {
        fabAddAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AnnouncementFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                AppConfiguration.firsttimeback = true;

            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.onLeft();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new StudentFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 11;
            }
        });

    }


    private void callAnnouncementListApi() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().getAnnouncementData(getDetail(), new retrofit.Callback<AnnouncementModel>() {
            @Override
            public void success(AnnouncementModel announcementModel, Response response) {
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
                        expandableListAnnoucementAdapter = new ExpandableListAnnoucement(getActivity(), listDataHeader, listDataChild, onDeleteWithIdRef, onUpdateRecordRef,
                                PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student").get("Announcement").getIsuserview(),
                                PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student").get("Announcement").getIsuserupdate(),
                                PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student").get("Announcement").getIsuserdelete());
                        expandableListView.setAdapter(expandableListAnnoucementAdapter);
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
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }


    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        for (int i = 0; i < finalArrayAnnouncementFinal.size(); i++) {
            listDataHeader.add(finalArrayAnnouncementFinal.get(i).getSubjectName() + "|" + finalArrayAnnouncementFinal.get(i).getCreateDate() + "|" + finalArrayAnnouncementFinal.get(i).getAnnStatus());
            Log.d("header", "" + listDataHeader);
            ArrayList<AnnouncementModel.FinalArray> row = new ArrayList<AnnouncementModel.FinalArray>();
            row.add(finalArrayAnnouncementFinal.get(i));
            Log.d("row", "" + row);
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }

    }

    @Override
    public void deleteRecordWithId(String id) {
        callDeleteAnnouncement(id);
    }


    public void callDeleteAnnouncement(String announcementId) {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());
        ApiHandler.getApiService().deleteAnnouncement(getDeleteAnnouncementParams(announcementId), new retrofit.Callback<AnnouncementModel>() {
            @Override
            public void success(AnnouncementModel permissionModel, Response response) {
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
                    Utils.ping(mContext, getString(R.string.anns_delete_msg));
                    callAnnouncementListApi();
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
        map.put("PK_AnnouncmentID", id);
        map.put("LocationID", PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        return map;
    }

    @Override
    public void onUpdateRecord(List<AnnouncementModel.FinalArray> dataList) {

    }

    @Override
    public void onUpdateRecordCircular(List<CircularModel.FinalArray> dataList) {

    }
}
