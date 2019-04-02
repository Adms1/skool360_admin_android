package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.StudentSubMenuAdapter;
import anandniketan.com.skool360.Model.IconHeaderModel;
import anandniketan.com.skool360.Model.PermissionDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.databinding.FragmentStudentPermissionBinding;

// Antra 29/01/2019
// Change main menu adapter

public class StudentPermissionFragment extends Fragment {

    public String[] mThumbIds = {
            AppConfiguration.BASEURL_IMAGES + "Permission/" + "Report%20Card.png",
            AppConfiguration.BASEURL_IMAGES + "Permission/" + "Online%20Payment.png",
            AppConfiguration.BASEURL_IMAGES + "Permission/" + "Marks_Syllabus.png",
            AppConfiguration.BASEURL_IMAGES + "Permission/" + "Suggestion.png",
    };
    public String[] mThumbNames = {"Report Card", "Online Payment", "Marks/Syllabus", "Suggestion"};
    private FragmentStudentPermissionBinding fragmentStudentPermissionBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private ArrayList<IconHeaderModel> newArr;
    private Map<String, PermissionDataModel.Detaill> permissionMap;

    private TextView tvHeader;
    private Button btnBack, btnMenu;

    public StudentPermissionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStudentPermissionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_permission, container, false);

        rootView = fragmentStudentPermissionBinding.getRoot();
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeader = view.findViewById(R.id.home_sname_txt);
        btnBack = view.findViewById(R.id.home_btnBack);
        btnMenu = view.findViewById(R.id.home_btnmenu);

        tvHeader.setText(R.string.student_permission);
        initViews();
        setListners();

    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 11;

        newArr = new ArrayList<>();
        permissionMap = PrefUtils.getInstance(getActivity()).loadMap(getActivity(), "Student");

        for (int i = 0; i < mThumbNames.length; i++) {
            if (permissionMap.containsKey(mThumbNames[i]) && permissionMap.get(mThumbNames[i]).getStatus().equalsIgnoreCase("true")) {

                IconHeaderModel iconHeaderModel = new IconHeaderModel();
                iconHeaderModel.setName(mThumbNames[i]);
                iconHeaderModel.setUrl(mThumbIds[i]);
                newArr.add(iconHeaderModel);
            }
        }

        Glide.with(mContext)
//                .load(AppConfiguration.BASEURL_IMAGES + "Student/" + "Permission.png")
                .load(AppConfiguration.BASEURL_IMAGES + "Main/" + "Permission.png")
                .into(fragmentStudentPermissionBinding.circleImageView);

        fragmentStudentPermissionBinding.studentPermissionSubmenuGridView.setAdapter(new StudentSubMenuAdapter(mContext, newArr));
    }

    public void setListners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        fragmentStudentPermissionBinding.studentPermissionSubmenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && permissionMap.get("Report Card").getStatus().equalsIgnoreCase("true")) {

                    fragment = new ResultPermisssionFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("reportcarddeletestatus", permissionMap.get("Report Card").getIsuserdelete());
                    bundle.putString("reportcardupdatestatus", permissionMap.get("Report Card").getIsuserupdate());
                    bundle.putString("reportcardviewstatus", permissionMap.get("Report Card").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 1 && permissionMap.get("Online Payment").getStatus().equalsIgnoreCase("true")) {
                    fragment = new OnlinePaymentFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("onlinepaydeletestatus", permissionMap.get("Online Payment").getIsuserdelete());
                    bundle.putString("onlinepayupdatestatus", permissionMap.get("Online Payment").getIsuserupdate());
                    bundle.putString("onlinepayviewstatus", permissionMap.get("Online Payment").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 2 && permissionMap.get("Marks/Syllabus").getStatus().equalsIgnoreCase("true")) {
                    fragment = new MarkSyllabusPermission();

                    Bundle bundle = new Bundle();

                    bundle.putString("markdeletestatus", permissionMap.get("Marks/Syllabus").getIsuserdelete());
                    bundle.putString("markupdatestatus", permissionMap.get("Marks/Syllabus").getIsuserupdate());
                    bundle.putString("markviewstatus", permissionMap.get("Marks/Syllabus").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (position == 3 && permissionMap.get("Suggestion").getStatus().equalsIgnoreCase("true")) {
                    fragment = new SuggestionPermissionFragment();

                    Bundle bundle = new Bundle();

                    bundle.putString("suggestiondeletestatus", permissionMap.get("Suggestion").getIsuserdelete());
                    bundle.putString("suggestionupdatestatus", permissionMap.get("Suggestion").getIsuserupdate());
                    bundle.putString("suggestionviewstatus", permissionMap.get("Suggestion").getIsuserview());

                    fragment.setArguments(bundle);

                    fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.frame_container, fragment).commit();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;
                } else {
                    Utils.ping(getActivity(), "Access Denied");
                }
            }
        });
    }
}

