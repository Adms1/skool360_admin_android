package anandniketan.com.bhadajadmin.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import anandniketan.com.bhadajadmin.R;

public abstract class BaseFragment extends Fragment {

    private TextView mTextViewTitle;
    private Button btnBack,btnMenu;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//
//        onCreateViewWithBase()
//
//
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateViewWithBaseHeader(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, @LayoutRes int layoutResID){
        LinearLayout fullView = (LinearLayout)getLayoutInflater().inflate(R.layout.fragment_base, null);

        mTextViewTitle = (TextView)fullView.findViewById(R.id.textView3);
        btnBack = (Button)fullView.findViewById(R.id.btnBack);


        btnMenu = (Button)fullView.findViewById(R.id.btnmenu);
        FrameLayout fragmentContainer = (FrameLayout) fullView.findViewById(R.id.fragment_container);
        return getLayoutInflater().inflate(layoutResID,fragmentContainer, true);

    }
    public void setTitle(String title){
        if(mTextViewTitle != null){
            mTextViewTitle.setText(title);
        }
    }

    private void setBack(Activity context){
        ((FragmentActivity)context).getSupportFragmentManager().popBackStackImmediate();
    }
//    private void setBackWithRefresh(Class<Fragment> targetFragment,Activity context){
//        if (fragment instanceof targetFragment) {
//
//        }
//        Fragment fragment = (Fragment) fragmentManager.getBackStackEntryAt(entry);
//        fragment =  targetFragment();
//        fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
//                .replace(R.id.frame_container, fragment).commit();
//        AppConfiguration.firsttimeback = true;
//        AppConfiguration.position = 1;
//    }



    protected abstract int getFragmentLayout();





}
