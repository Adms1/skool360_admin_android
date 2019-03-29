package com.skool360admin.Adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Fragment.Fragment.MISStudentWiseResultInnerTab;
import anandniketan.com.bhadajadmin.Interface.ResponseCallBack;
import anandniketan.com.bhadajadmin.Model.MIS.MISStudentResultDataModel;
import anandniketan.com.bhadajadmin.Model.MIS.MIStudentWiseResultModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.Utils;

public class ExapandableSchoolResultAdapter extends BaseExpandableListAdapter {
    ProgressBar progressBar;
    int countItem = 0;
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<MISStudentResultDataModel.TermDatum>> listChildData;
    private String standard = "", standardIDS = "";
    private int annousID;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private ResponseCallBack responseCallBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabsTitles;
    private List<MIStudentWiseResultModel.FinalArray> dataList;

    public ExapandableSchoolResultAdapter(Context context, List<String> listDataHeader, HashMap<String, ArrayList<MISStudentResultDataModel.TermDatum>> listDataChild, ResponseCallBack responseCallBack) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
        this.responseCallBack = responseCallBack;
    }

    @Override
    public List<MISStudentResultDataModel.TermDatum> getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final List<MISStudentResultDataModel.TermDatum> childData = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_mis_student_wise_result, null);
        }

        tabLayout = convertView.findViewById(R.id.tabLayout);
        viewPager = convertView.findViewById(R.id.viewPager);
        progressBar = convertView.findViewById(R.id.inner_list_loader);

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return (this.listChildData.get(this._listDataHeader.get(groupPosition)).size() - this.listChildData.get(this._listDataHeader.get(groupPosition)).size()) + 1;
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
            convertView = infalInflater.inflate(R.layout.list_item_mis_school_result_group_list_item, null);
        }
        TextView grade_txt, section_txt, student_txt, percentage_txt, view_txt;
        ImageView ivArrow;


        grade_txt = convertView.findViewById(R.id.grade_txt);
        section_txt = convertView.findViewById(R.id.section_txt);
        student_txt = convertView.findViewById(R.id.student_txt);
        percentage_txt = convertView.findViewById(R.id.percentage_txt);
        view_txt = convertView.findViewById(R.id.view_txt);
        ivArrow = convertView.findViewById(R.id.iv_indicatior);

        grade_txt.setText(String.valueOf(headerTitle1) + "-" + String.valueOf(headerTitle2));
        section_txt.setText(String.valueOf(headerTitle2));
        student_txt.setText(String.valueOf(headerTitle3));
        percentage_txt.setText(String.valueOf(headerTitle4));


        if (isExpanded) {
            ivArrow.setImageResource(R.drawable.arrow_1_42_down);
        } else {
            ivArrow.setImageResource(R.drawable.arrow_1_42);
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


    public void onResponse(List<MIStudentWiseResultModel.FinalArray> data1) {

        if (tabLayout != null && viewPager != null && progressBar != null) {

            tabLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            try {

                if (tabLayout.getTabCount() > 0) {
                    tabLayout.removeAllTabs();
                }

                tabsTitles = new String[data1.size()];

                for (int count = 0; count < data1.size(); count++) {
                    tabLayout.addTab(tabLayout.newTab().setText(data1.get(count).getTerm()));
                    tabsTitles[count] = data1.get(count).getTerm();
                }

                ViewPagerAdapter adapter = new ViewPagerAdapter(((FragmentActivity) _context).getSupportFragmentManager(), data1.size(), data1);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(0);
                tabLayout.setupWithViewPager(viewPager);

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        viewPager.setCurrentItem(position);
                        //  tabLayout.getTabAt(position).select();

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });


//                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                    @Override
//                    public void onTabSelected(TabLayout.Tab tab) {
//                        viewPager.setCurrentItem(tab.getPosition());
//
//                    }
//
//                    @Override
//                    public void onTabUnselected(TabLayout.Tab tab) {
//
//                    }
//
//                    @Override
//                    public void onTabReselected(TabLayout.Tab tab) {
//
//                    }
//                });

//                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//                tabLayout.addOnTabSelectedListener(onTabSelectedListener(viewPager));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public void onFailure(String errorMessage) {
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        Utils.ping(_context, errorMessage);
    }


    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        Fragment fragment = null;
        List<MIStudentWiseResultModel.FinalArray> datumList;

        public ViewPagerAdapter(FragmentManager fm, int NumOfTabs, List<MIStudentWiseResultModel.FinalArray> datumList) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
            this.datumList = datumList;
        }

        @Override
        public Fragment getItem(int position) {
//            try {

            switch (position) {
                case 0:
                    return MISStudentWiseResultInnerTab.newInstance(datumList.get(position).getData());

                case 1:
                    return MISStudentWiseResultInnerTab.newInstance(datumList.get(position).getData());

                default:
                    return null;
            }

//                for (int i = 0; i < mNumOfTabs; i++) {
//                    if (position == 0) {
//                    if(fragment == null) {
//                        if (fragment == null) {
//                        fragment = MISStudentWiseResultInnerTab.newInstance(datumList.get(position).getData());
//                            if(fragment.isAdded()){
//                                String fragmentTag = getFragmentTag(R.id.viewPager,position);
//                              //  fragment = ((FragmentActivity)_context).getSupportFragmentManager().findFragmentByTag(fragmentTag);
//                            }else{
//                                fragment = MISStudentWiseResultInnerTab.newInstance(datumList.get(position).getData());
//                            }
//                        }else{

//                        }else{
////                        }
//
//                        String fragmentTag = getFragmentTag(R.id.viewPager,position);
//                        fragment = ((FragmentActivity)_context).getSupportFragmentManager().findFragmentByTag(fragmentTag);
//                    }
//                    }else{
//                        }
//                        String fragmentTag = getFragmentTag(R.id.viewPager,position);
//                        fragment = ((FragmentActivity)_context).getSupportFragmentManager().findFragmentByTag(fragmentTag);
//                    }
//                        break;
//                    }
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            return fragment;

        }

        private String getFragmentTag(int viewPagerId, int fragmentPosition) {
            return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
        }

        @Override
        public int getCount() {
            return datumList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabsTitles[position];
            //return "Blank";
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (object != null) {
                return ((Fragment) object).getView() == view;
            } else {
                return false;
            }
        }
    }
}
