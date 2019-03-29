package anandniketan.com.bhadajadmin.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import anandniketan.com.bhadajadmin.Fragment.Fragment.OtherAccountSummaryFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.OtherAttendaceSummaryFragment;


/**
 * Created by admsandroid on 1/31/2018.
 */

public class SummarypagerAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public SummarypagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                OtherAttendaceSummaryFragment tab1 = new OtherAttendaceSummaryFragment();
                return tab1;
            case 1:
                OtherAccountSummaryFragment tab2 = new OtherAccountSummaryFragment();
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}



