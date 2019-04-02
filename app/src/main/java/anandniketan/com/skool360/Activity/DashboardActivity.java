package anandniketan.com.skool360.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anandniketan.com.skool360.Adapter.ExpandableListAdapterMenu;
import anandniketan.com.skool360.Adapter.MenuoptionItemAdapter;
import anandniketan.com.skool360.Fragment.Fragment.AccountFragment;
import anandniketan.com.skool360.Fragment.Fragment.ActivityLoggingFragment;
import anandniketan.com.skool360.Fragment.Fragment.AnnoucementListFragment;
import anandniketan.com.skool360.Fragment.Fragment.AnnouncementFragment;
import anandniketan.com.skool360.Fragment.Fragment.AttendenceReportFragment;
import anandniketan.com.skool360.Fragment.Fragment.CircularListFragment;
import anandniketan.com.skool360.Fragment.Fragment.DailyReportFragment;
import anandniketan.com.skool360.Fragment.Fragment.HRFragment;
import anandniketan.com.skool360.Fragment.Fragment.HolidayFragment;
import anandniketan.com.skool360.Fragment.Fragment.HomeFragment;
import anandniketan.com.skool360.Fragment.Fragment.LeftDetailFragment;
import anandniketan.com.skool360.Fragment.Fragment.MISFragment;
import anandniketan.com.skool360.Fragment.Fragment.MyLeaveFragment;
import anandniketan.com.skool360.Fragment.Fragment.PTMMainFragment;
import anandniketan.com.skool360.Fragment.Fragment.SMSFragment;
import anandniketan.com.skool360.Fragment.Fragment.SearchStaffFragment;
import anandniketan.com.skool360.Fragment.Fragment.SearchStudentFragment;
import anandniketan.com.skool360.Fragment.Fragment.StaffFragment;
import anandniketan.com.skool360.Fragment.Fragment.StaffLeaveFragment;
import anandniketan.com.skool360.Fragment.Fragment.StudentFragment;
import anandniketan.com.skool360.Fragment.Fragment.StudentPermissionFragment;
import anandniketan.com.skool360.Fragment.Fragment.StudentViewInquiryFragment;
import anandniketan.com.skool360.Fragment.Fragment.SummaryFragment;
import anandniketan.com.skool360.Model.MenuoptionItemModel;
import anandniketan.com.skool360.Model.PermissionDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.DialogUtils;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;


public class DashboardActivity extends FragmentActivity {
    public static String filename = "Valustoringfile";
    //    static ListView mDrawerList;
    static DrawerLayout mDrawerLayout;
    static RelativeLayout leftRl;
    static ExpandableListView mDrawerList;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public String[] mThumbNames = {"Student", "Staff", "HR", "Account", "SMS"/*"Transport", "Other"*/};
    Context mContext;
    ActionBarDrawerToggle mDrawerToggle;
    String MenuName[];
    String token;
    int dispPOS = 0;
    SharedPreferences SP;
    //
    ExpandableListAdapterMenu expandableListAdapterMenu;
    List<String> listDataHeader;
    HashMap<String, ArrayList<String>> listDataChild;
    ArrayList<String> imagesId = new ArrayList<String>();
    Fragment fragment = null;
    int myid;
    boolean first_time_trans = true;
    private ArrayList<MenuoptionItemModel> navDrawerItems_main;
    private MenuoptionItemAdapter adapter_menu_item;
    private String putData = "0";
    private FragmentManager fragmentManager = null;
    private DialogInterface dialogInterfacePosivtive, dialogInterfaceNegative;
    private TextView tvName, tvDesignation;
    private PrefUtils prefUtils;
    private Map<String, ArrayList<PermissionDataModel.Detaill>> hashMap;

    public static void onLeft() {
        // TODO Auto-generated method stub
        mDrawerList.setSelectionAfterHeaderView();
        mDrawerLayout.openDrawer(leftRl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = this;
        prefUtils = PrefUtils.getInstance(mContext);

        callGetUserStatus();

        Initialize();
        displayView(0);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_launcher, // nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            @SuppressLint("NewApi")
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            @SuppressLint("NewApi")
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void callGetUserStatus() {
//        http://192.168.1.22:8086/MobileApp_Service.asmx/CheckStaffActive?UserID=51

        Utils.showDialog(DashboardActivity.this);
        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<JsonObject> call = apiService.getUserStatus(PrefUtils.getInstance(DashboardActivity.this).getStringValue("StaffID", "0"), PrefUtils.getInstance(DashboardActivity.this).getStringValue("LocationID", "0"));
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull final retrofit2.Response<JsonObject> response) {
                Utils.dismissDialog();
                if (response.body() != null) {

                    if (response.body().get("Success").getAsString().equalsIgnoreCase("False")) {

//                        Utils.ping(DashboardActivity.this, "You are not Active");
                        DialogUtils.createConfirmDialog(DashboardActivity.this, "You are not Active", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.exit(0);
                            }

                        }).show();
                        return;
                    }

                    if (response.body().get("Success").getAsString().equalsIgnoreCase("True")) {
//                        Utils.ping(DashboardActivity.this, "You are active");

                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                // Log error here since request failed
                Utils.dismissDialog();
                Log.e("permissionnnnn", t.toString());
            }
        });
    }

    private void Initialize() {
        // TODO Auto-generated method stub
        MenuName = getResources().getStringArray(R.array.menuoption1);
//        hashMap= PrefUtils.getInstance(DashboardActivity.this).loadMap(DashboardActivity.this);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        leftRl = findViewById(R.id.whatYouWantInLeftDrawer);
        mDrawerList = findViewById(R.id.list_slidermenu);
        navDrawerItems_main = new ArrayList<MenuoptionItemModel>();

        tvName = findViewById(R.id.teacher_name);
        tvDesignation = findViewById(R.id.teacher_designation);
        try {
            tvName.setText(prefUtils.getStringValue("Emp_Name", ""));
//            tvName.setText("Bhadaj");
            tvDesignation.setText(prefUtils.getStringValue("DesignationName", ""));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        mDrawerList.setAdapter(new MenuoptionItemAdapter(mContext));
        fillExpLV();

//        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        mDrawerList.setOnChildClickListener(new SlideMenuClickListener());
        mDrawerList.setOnGroupClickListener(new SlideMenuClickListener());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void displayView(int position) {
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                myid = fragment.getId();

//                Utils.ping(DashboardActivity.this, "Access Denied");
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 1:

                fragment = new StudentFragment();
                myid = fragment.getId();
//
//                Utils.ping(DashboardActivity.this, "Access Denied");
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 2:
                fragment = new StaffFragment();
                myid = fragment.getId();
//                Utils.ping(DashboardActivity.this, "Access Denied");
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 3:
                fragment = new HRFragment();
                myid = fragment.getId();
//                Utils.ping(DashboardActivity.this, "Access Denied");
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                break;
            case 4:
                fragment = new AccountFragment();
                myid = fragment.getId();
//                Utils.ping(DashboardActivity.this, "Access Denied");
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                fragment = new TransportFragment();
//                myid = fragment.getId();
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 5:
                fragment = new SMSFragment();
                myid = fragment.getId();
//                Utils.ping(DashboardActivity.this, "Access Denied");
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;

            case 6:
                fragment = new MISFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;

            case 7:
                DialogUtils.createConfirmDialog(DashboardActivity.this, R.string.app_name, R.string.logout_confirm_msg, "OK", "Cancel",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intentLogout = new Intent(DashboardActivity.this, LoginActivity.class);
                                PrefUtils.getInstance(DashboardActivity.this).clear();
                                startActivity(intentLogout);
                                finishAffinity();
                                System.exit(0);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                break;
            case 8:
                break;

        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragment instanceof HomeFragment) {
                if (first_time_trans) {
                    first_time_trans = false;
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
            } else {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
//            mDrawerLayout.closeDrawers();
        } else {
            // error in creating fragment
            Log.e("Dashboard", "Error in creating fragment");
        }
    }

    public void displayView1(String position) {
        switch (position) {
            case "SUMMARY":
                fragment = new SummaryFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "HOLIDAY":
                fragment = new HolidayFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "PTM":
                fragment = new PTMMainFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "ACTIVITY LOGGING":
                fragment = new ActivityLoggingFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case "ANNOUNCMENT":
                fragment = new AnnouncementFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
//            case "Quick Email":
//                fragment = new OtherFragment();
//                myid = fragment.getId();
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                break;
        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragment instanceof HomeFragment) {
                if (first_time_trans) {
                    first_time_trans = false;
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
            } else {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }

            // update selected item and title, then close the drawer
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawers();
        } else {
            // error in creating fragment
            Log.e("Dashboard", "Error in creating fragment");
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (AppConfiguration.firsttimeback) {
            if (AppConfiguration.position != 0) {
                if (AppConfiguration.position == 1) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragment = new HomeFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.frame_container, fragment).commit();
                } else if (AppConfiguration.position == 11) {
                    fragment = new StudentFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 1;
                } else if (AppConfiguration.position == 12) {
                    fragment = new SearchStudentFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;
                } else if (AppConfiguration.position == 13) {
                    fragment = new StudentPermissionFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;
                } else if (AppConfiguration.position == 2) {
                    fragment = new HomeFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (AppConfiguration.position == 21) {
                    fragment = new StaffFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 2;
                } else if (AppConfiguration.position == 22) {
                    fragment = new MyLeaveFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 21;
                } else if (AppConfiguration.position == 3) {
                    fragment = new HomeFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (AppConfiguration.position == 31) {
                    fragment = new SMSFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 3;
                } else if (AppConfiguration.position == 4) {
                    fragment = new HomeFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (AppConfiguration.position == 41) {
                    fragment = new AccountFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 4;
                } else if (AppConfiguration.position == 5) {
                    fragment = new HomeFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else if (AppConfiguration.position == 51) {
                    fragment = new HRFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 5;
                } else if (AppConfiguration.position == 52) {
                    fragment = new StaffLeaveFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 52;
                } else if (AppConfiguration.position == 53) {
                    fragment = new AttendenceReportFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 53;
                } else if (AppConfiguration.position == 54) {
                    fragment = new DailyReportFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 54;
                } else if (AppConfiguration.position == 55) {
                    fragment = new AnnoucementListFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 55;
                } else if (AppConfiguration.position == 56) {
                    fragment = new StudentViewInquiryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("status", PrefUtils.getInstance(DashboardActivity.this).loadMap(DashboardActivity.this, "Student").get("View Inquiry").getIsuserview());
                    fragment.setArguments(bundle);
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 56;
                } else if (AppConfiguration.position == 59) {
                    fragment = new CircularListFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 56;
                } else if (AppConfiguration.position == 61) {
                    fragment = new SearchStaffFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 61;
                } else if (AppConfiguration.position == 65) {
                    fragment = new HomeFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 5;
                } else if (AppConfiguration.position == 66) {
                    AppConfiguration.position = 65;
                    AppConfiguration.firsttimeback = true;
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                } else if (AppConfiguration.position == 67) {
                    fragment = new MISFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 5;
                } else if (AppConfiguration.position == 68) {

                    fragment = new LeftDetailFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 11;

                } else if (AppConfiguration.position == 58) {

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    }

//                    fragment = new StudentViewInquiryFragment();
//                    fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
//                    AppConfiguration.firsttimeback = true;
//                    AppConfiguration.position = 56;
                }
            }
            if (AppConfiguration.position != 65) {
                AppConfiguration.firsttimeback = false;
            }

        } else {

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                fragment = new StudentFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.frame_container, fragment).commit();
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 1;


            } else {
                finish();
                System.exit(0);
            }


        }
    }

    public void fillExpLV() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        ArrayList<String> finalImageArray = new ArrayList<String>();


        ArrayList<String> finalheaderArray = new ArrayList<>();
        finalheaderArray.add("DASHBOARD");
        finalheaderArray.add("STUDENT");
        finalheaderArray.add("STAFF");
        finalheaderArray.add("HR");
        finalheaderArray.add("ACCOUNT");
        // finalheaderArray.add("TRANSPORT");
        finalheaderArray.add("SMS");
        finalheaderArray.add("MIS");
        //  finalheaderArray.add("OTHER");
        finalheaderArray.add("LOGOUT");

        ArrayList<String> finalchildArray = new ArrayList<>();
        finalchildArray.add("SUMMARY");
        finalchildArray.add("HOLIDAY");
        finalchildArray.add("PTM");
        finalchildArray.add("ACTIVITY LOGGING");
        finalchildArray.add("ANNOUNCMENT");
        finalchildArray.add("QUICK EMAIL");

        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Home.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Student.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Staff.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_HR.png");

        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Account.png");
        //  imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Transport.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_SMS.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Account.png");
        //  imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Other.png");
        imagesId.add(AppConfiguration.BASEURL_IMAGES + "SideMenu/" + "Menu_Logout.png");

        for (int k = 0; k < imagesId.size(); k++) {
            finalImageArray.add(imagesId.get(k));
        }
        Log.d("finalImageArray", "" + finalImageArray);

        for (int i = 0; i < finalheaderArray.size(); i++) {
            listDataHeader.add(finalheaderArray.get(i) + "|" + imagesId.get(i));//+"|"+mThumbIds[k]
            Log.d("header", "" + listDataHeader);
            ArrayList<String> row = new ArrayList<String>();

            for (int j = 0; j < finalchildArray.size(); j++) {
                if (finalheaderArray.get(i).equalsIgnoreCase("OTHER")) {
                    row.add(finalchildArray.get(j));
                    Log.d("row", "" + row);
                }
            }
            listDataChild.put(listDataHeader.get(i), row);
            Log.d("child", "" + listDataChild);
        }
        expandableListAdapterMenu = new ExpandableListAdapterMenu(mContext, listDataHeader, listDataChild, imagesId);
        mDrawerList.setAdapter(expandableListAdapterMenu);
    }


    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements ListView.OnItemClickListener, ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
//            displayView(position);

        }

        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//            Utils.ping(mContext,""+expandableListAdapterMenu.getChild(i,i1));
            String str = expandableListAdapterMenu.getChild(i, i1);
            displayView1(str);
            return true;

        }

        @Override
        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//            Utils.ping(mContext,""+i);
            Utils.callPermissionDetail(DashboardActivity.this, PrefUtils.getInstance(DashboardActivity.this).getStringValue("StaffID", "0"),
                    PrefUtils.getInstance((DashboardActivity.this)).getStringValue("LocationID", "0"));
            displayView(i);
            return false;
        }
    }
}
