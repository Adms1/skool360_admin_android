package anandniketan.com.skool360.Fragment.fragment.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import anandniketan.com.skool360.Fragment.fragment.account.AccountFragment;
import anandniketan.com.skool360.Fragment.fragment.hr.HRFragment;
import anandniketan.com.skool360.Fragment.fragment.mis.MISFragment;
import anandniketan.com.skool360.Fragment.fragment.sms.SMSFragment;
import anandniketan.com.skool360.Fragment.fragment.staff.StaffFragment;
import anandniketan.com.skool360.Fragment.fragment.student.StudentFragment;
import anandniketan.com.skool360.Model.Notification.NotificationModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.ImageAdapter;
import anandniketan.com.skool360.databinding.FragmentHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private TextView notificationNumber;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        rootView = fragmentHomeBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        notificationNumber = rootView.findViewById(R.id.tv_notification);

        initViews();
        setListners();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Utils.callPermissionDetail(getActivity(), PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"),
                PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));

        callNotification();
    }

    public void initViews() {
        fragmentHomeBinding.gridView.setAdapter(new ImageAdapter(mContext));

        String Bday = PrefUtils.getInstance(mContext).getStringValue("DOB", "");

        if (Bday != null) {
            if (!TextUtils.isEmpty(Bday)) {
                Utils.showUserBirthdayWish(getActivity(), Bday);
            }
        }
    }

    public void setListners() {

        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 0;
        fragmentHomeBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        fragmentHomeBinding.btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new NotificationFragment();
                fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                }
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 1;
            }
        });

        fragmentHomeBinding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new StudentFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 1;

//                    Utils.ping(getActivity(), "Access Denied");

                } else if (position == 1) {
                    fragment = new StaffFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 2;
//
////                    Utils.ping(getActivity(), "Access Denied");
//
                } else if (position == 2) {
                    fragment = new HRFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 5;

//                    Utils.ping(getActivity(), "Access Denied");

                } else if (position == 3) {
                    fragment = new AccountFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 4;

//                    Utils.ping(getActivity(), "Access Denied");

                } else if (position == 4) {
                    fragment = new SMSFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 3;

//                    Utils.ping(getActivity(), "Access Denied");

                } else if (position == 5) {
                    fragment = new MISFragment();
                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 6;

                }
                /* else if (position == 6) {
                    fragment = new OtherFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 7;
                }*/
            }
        });
    }

    private void callNotification() {
        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<NotificationModel> call = apiService.getNotification(PrefUtils.getInstance(getActivity()).getStringValue("StaffID", "0"), PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        call.enqueue(new Callback<NotificationModel>() {

            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull final retrofit2.Response<NotificationModel> response) {
                Utils.dismissDialog();
                if (response.body() != null) {

                    if (response.body().getSuccess().equalsIgnoreCase("false")) {
                        notificationNumber.setVisibility(View.GONE);
                        fragmentHomeBinding.btnNotification.setEnabled(false);

                        return;
                    }

                    if (response.body().getSuccess().equalsIgnoreCase("True")) {

                        if (response.body().getFinalarray().size() > 0) {
                            notificationNumber.setText(String.valueOf(response.body().getFinalarray().size()));
                        } else {
                            notificationNumber.setVisibility(View.GONE);
                            fragmentHomeBinding.btnNotification.setEnabled(false);
                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {

            }
        });
    }

}

