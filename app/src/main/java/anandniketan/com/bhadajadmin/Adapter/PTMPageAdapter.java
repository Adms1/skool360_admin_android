package anandniketan.com.bhadajadmin.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import anandniketan.com.bhadajadmin.Fragment.Fragment.CreateFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.InboxFragment;
import anandniketan.com.bhadajadmin.Fragment.Fragment.SentFragment;


/**
 * Created by admsandroid on 10/25/2017.
 */

public class PTMPageAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PTMPageAdapter(FragmentManager fm, int tabCount) {
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
                InboxFragment tab1 = new InboxFragment();
                return tab1;
            case 1:
                SentFragment tab2 = new SentFragment();
                return tab2;
            case 2:
                CreateFragment tab3 = new CreateFragment();
                return tab3;
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



