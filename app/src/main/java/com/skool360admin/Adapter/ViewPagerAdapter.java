package com.skool360admin.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.List;

import anandniketan.com.bhadajadmin.Fragment.Fragment.MISSchoolCalendar2Fragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.MISSchoolCalendarFragment;
import anandniketan.com.bhadajadmin.Model.MIStudentWiseCalendarModel;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment fragment = null;
    List<MIStudentWiseCalendarModel.FinalArray> datumList;
    private String[] tabsTitles;

    public ViewPagerAdapter(FragmentManager fm, int NumOfTabs, List<MIStudentWiseCalendarModel.FinalArray> datumList, String[] tabsTitles) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.datumList = datumList;
        this.tabsTitles = tabsTitles;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: // Fragment # 0 - This will show image

                return MISSchoolCalendarFragment.newInstance(0, datumList.get(position).getData());
            case 1: // Fragment # 1 - This will show image
                return MISSchoolCalendar2Fragment.newInstance(1, datumList.get(position).getData());
            default:
                return null;
        }

    }

    private String getFragmentTag(int viewPagerId, int fragmentPosition) {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
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
