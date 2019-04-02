package anandniketan.com.skool360.Fragment.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Adapter.PTMPageAdapter;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.databinding.FragmentPtmmainBinding;


public class PTMMainFragment extends Fragment {
    View view;
    PTMPageAdapter adapter;
    FragmentPtmmainBinding ptmmainBinding;
    private View rootView;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public PTMMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ptmmainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ptmmain, container, false);

        rootView = ptmmainBinding.getRoot();
        mContext = getActivity();
        init();
        setListner();
        return rootView;

    }

    public void init() {
//Initializing the tablayout

        ptmmainBinding.tabLayoutPtm.addTab(ptmmainBinding.tabLayoutPtm.newTab().setText("Inbox"), true);
        ptmmainBinding.tabLayoutPtm.addTab(ptmmainBinding.tabLayoutPtm.newTab().setText("Sent"));
        ptmmainBinding.tabLayoutPtm.addTab(ptmmainBinding.tabLayoutPtm.newTab().setText("Create"));
        ptmmainBinding.tabLayoutPtm.setTabMode(TabLayout.MODE_FIXED);
        ptmmainBinding.tabLayoutPtm.setTabGravity(TabLayout.GRAVITY_FILL);


        adapter = new PTMPageAdapter(getFragmentManager(), ptmmainBinding.tabLayoutPtm.getTabCount());
//Adding adapter to pager
        ptmmainBinding.pager.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            ptmmainBinding.view.setVisibility(View.GONE);
        } else {
            ptmmainBinding.view.setVisibility(View.VISIBLE);
        }
    }

    public void setListner() {
        ptmmainBinding.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.onLeft();
            }
        });
        ptmmainBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new OtherFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        ptmmainBinding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                ptmmainBinding.tabLayoutPtm));
        ptmmainBinding.tabLayoutPtm.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ptmmainBinding.pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
}
