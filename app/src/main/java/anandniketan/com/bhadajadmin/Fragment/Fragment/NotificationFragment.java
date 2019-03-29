package anandniketan.com.bhadajadmin.Fragment.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import anandniketan.com.bhadajadmin.Activity.DashboardActivity;
import anandniketan.com.bhadajadmin.Adapter.NotificationAdapter;
import anandniketan.com.bhadajadmin.Model.Notification.NotificationModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.ApiClient;
import anandniketan.com.bhadajadmin.Utility.PrefUtils;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */

// Antra 19/02/2019

public class NotificationFragment extends Fragment {

    private RecyclerView rvList;
    private NotificationAdapter notificationAdapter;
    private LinearLayout llHeader;
    private TextView noRecords, header;
    private Button btnBack, btnMenu;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvList = view.findViewById(R.id.noti_list);
        llHeader = view.findViewById(R.id.noti_recycler_linear);
        noRecords = view.findViewById(R.id.noti_txtNoRecords);
        header = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);

        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        header.setText(R.string.notifications);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        callNotification();

    }

    private void callNotification() {
        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<NotificationModel> call = apiService.getNotification(PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"));
        call.enqueue(new Callback<NotificationModel>() {

            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull final retrofit2.Response<NotificationModel> response) {
                Utils.dismissDialog();
                if (response.body() != null) {

                    if (response.body().getSuccess().equalsIgnoreCase("false")) {
                        noRecords.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                        llHeader.setVisibility(View.GONE);
                        return;
                    }

                    if (response.body().getSuccess().equalsIgnoreCase("True")) {

                        rvList.setVisibility(View.VISIBLE);
                        noRecords.setVisibility(View.GONE);
                        llHeader.setVisibility(View.VISIBLE);

                        if (response.body().getFinalarray() != null) {

                            notificationAdapter = new NotificationAdapter(getActivity(), response.body().getFinalarray());
                            rvList.setAdapter(notificationAdapter);
//                            galleryAdapter.notifyDataSetChanged();

                        }

                    }
                } else {
                    Utils.dismissDialog();
                    rvList.setVisibility(View.GONE);
                    llHeader.setVisibility(View.GONE);
                    noRecords.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                // Log error here since request failed
                Utils.dismissDialog();
                Log.e("gallery", t.toString());
            }
        });
    }

}
