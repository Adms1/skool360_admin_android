package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Fragment.Fragment.MISDataListFragment;
import anandniketan.com.skool360.Model.MIS.MISTaskReportModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;

public class MISTaskReportGridAdapter extends RecyclerView.Adapter<MISTaskReportGridAdapter.MyViewHolder> {

    private Context context;
    private List<MISTaskReportModel.FinalArray> dataValues = new ArrayList<MISTaskReportModel.FinalArray>();
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private Bundle bundle;
    private String termid;


    public MISTaskReportGridAdapter(Context mContext, List<MISTaskReportModel.FinalArray> dataValues, String termid) {
        this.context = mContext;
        this.dataValues = dataValues;
        this.termid = termid;
    }

    @Override
    public MISTaskReportGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_staff_grid_iten, parent, false);
        return new MISTaskReportGridAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MISTaskReportGridAdapter.MyViewHolder holder, final int position) {
        try {
            String sr = String.valueOf(position + 1);

            holder.dept_txt.setText(dataValues.get(position).getTask());
            holder.total_txt.setText(String.valueOf(dataValues.get(position).getTotal()));
            holder.done_txt.setText(String.valueOf(dataValues.get(position).getDone()));
            holder.notDone_txt.setText(String.valueOf(dataValues.get(position).getNotDone()));


            holder.total_txt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (dataValues.get(position).getTask().equalsIgnoreCase("Attendance")) {

                        fragment = new MISDataListFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Task Report");
                        bundle.putString("requestType", "Attendance");
                        bundle.putString("requestTitle", "Total Attendance");
                        bundle.putString("TermID", termid);
                        bundle.putString("countdata", holder.total_txt.getText().toString());
                        bundle.putString("Date", AppConfiguration.taskReportDate);
                        bundle.putString("coundata", holder.total_txt.getText().toString());

                        // bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 65;

                    } else if (dataValues.get(position).getTask().equalsIgnoreCase("Homework")) {
                        fragment = new MISDataListFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Task Report");
                        bundle.putString("requestType", "HWTotal");
                        bundle.putString("requestTitle", "Homework");
                        bundle.putString("TermID", termid);
                        bundle.putString("countdata", holder.total_txt.getText().toString());
                        bundle.putString("Date", AppConfiguration.taskReportDate);

                        // bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 65;
                    } else if (dataValues.get(position).getTask().equalsIgnoreCase("Classwork")) {

                        fragment = new MISDataListFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Task Report");
                        bundle.putString("requestType", "CWTotal");
                        bundle.putString("requestTitle", "Classwork");
                        bundle.putString("TermID", termid);
                        bundle.putString("Date", AppConfiguration.taskReportDate);
                        bundle.putString("countdata", holder.total_txt.getText().toString());

                        // bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 65;
                    }
                }

            });

            holder.done_txt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (dataValues.get(position).getTask().equalsIgnoreCase("Attendance")) {

                        fragment = new MISDataListFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Task Report");
                        bundle.putString("requestType", "AttendanceDone");
                        bundle.putString("requestTitle", "Attendance Done");
                        bundle.putString("TermID", termid);
                        bundle.putString("countdata", holder.total_txt.getText().toString());
                        bundle.putString("Date", AppConfiguration.taskReportDate);
                        bundle.putString("coundata", holder.done_txt.getText().toString());

                        // bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 65;

                    } else if (dataValues.get(position).getTask().equalsIgnoreCase("Homework")) {
                        fragment = new MISDataListFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Task Report");
                        bundle.putString("requestType", "HWDone");
                        bundle.putString("requestTitle", "Homework Done");
                        bundle.putString("TermID", termid);
                        bundle.putString("countdata", holder.done_txt.getText().toString());
                        bundle.putString("Date", AppConfiguration.taskReportDate);

                        // bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 65;
                    } else if (dataValues.get(position).getTask().equalsIgnoreCase("Classwork")) {

                        fragment = new MISDataListFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Task Report");
                        bundle.putString("requestType", "CWDone");
                        bundle.putString("requestTitle", "Classwork Done");
                        bundle.putString("TermID", termid);
                        bundle.putString("Date", AppConfiguration.taskReportDate);
                        bundle.putString("countdata", holder.total_txt.getText().toString());

                        // bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 65;
                    }
                }

            });


            holder.notDone_txt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (dataValues.get(position).getTask().equalsIgnoreCase("Attendance")) {

                        fragment = new MISDataListFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Task Report");
                        bundle.putString("requestType", "AttendanceNotDone");
                        bundle.putString("requestTitle", "Attendance Not Done");
                        bundle.putString("TermID", termid);
                        bundle.putString("countdata", holder.total_txt.getText().toString());
                        bundle.putString("Date", AppConfiguration.taskReportDate);
                        bundle.putString("coundata", holder.notDone_txt.getText().toString());

                        // bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 65;

                    } else if (dataValues.get(position).getTask().equalsIgnoreCase("Homework")) {
                        fragment = new MISDataListFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Task Report");
                        bundle.putString("requestType", "HWNotDone");
                        bundle.putString("requestTitle", "Homework Not Done");
                        bundle.putString("TermID", termid);
                        bundle.putString("countdata", holder.notDone_txt.getText().toString());
                        bundle.putString("Date", AppConfiguration.taskReportDate);

                        // bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 65;
                    } else if (dataValues.get(position).getTask().equalsIgnoreCase("Classwork")) {

                        fragment = new MISDataListFragment();
                        bundle = new Bundle();
                        bundle.putString("title", "Task Report");
                        bundle.putString("requestType", "CWNotDone");
                        bundle.putString("requestTitle", "Classwork Not Done");
                        bundle.putString("TermID", termid);
                        bundle.putString("Date", AppConfiguration.taskReportDate);
                        bundle.putString("countdata", holder.notDone_txt.getText().toString());

                        // bundle.putString("deptID",String.valueOf(dataValues.get(position).getDepartmentID()));
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .add(R.id.frame_container, fragment).addToBackStack(null).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 65;
                    }
                }

            });

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataValues == null ? 0 : dataValues.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dept_txt, total_txt, done_txt, notDone_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            dept_txt = itemView.findViewById(R.id.dept_txt);
            total_txt = itemView.findViewById(R.id.total_txt);
            done_txt = itemView.findViewById(R.id.absent_txt);
            notDone_txt = itemView.findViewById(R.id.leave_txt);

        }
    }
}
