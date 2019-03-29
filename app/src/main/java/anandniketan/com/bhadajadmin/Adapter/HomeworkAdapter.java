package anandniketan.com.bhadajadmin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Interface.getEmployeeCheck;
import anandniketan.com.bhadajadmin.Model.Staff.HomeWorkModel;
import anandniketan.com.bhadajadmin.R;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.myviewholder> {

    private Context context;
    private ArrayList<HomeWorkModel.HomeworkFinalArray> homeworkFinalArrays;
    private ArrayList<String> dataCheck = new ArrayList<>();
    private ArrayList<String> dataCheck1 = new ArrayList<>();
    private getEmployeeCheck listner, listner1;
    private String stduentIdStr, stduentIdStr1, FinalValue, FinalValue1;

    public HomeworkAdapter(Context context, ArrayList<HomeWorkModel.HomeworkFinalArray> homeworkFinalArrays, getEmployeeCheck listner, getEmployeeCheck listener1) {
        this.context = context;
        this.homeworkFinalArrays = homeworkFinalArrays;
        this.listner = listner;
        this.listner1 = listener1;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homework_list_item, viewGroup, false);
        return new myviewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder myViewHolder, final int i) {

        myViewHolder.stuname.setText(homeworkFinalArrays.get(i).getStudentname());

        try {
            myViewHolder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = group.findViewById(checkedId);
                    if (null != rb && checkedId > -1) {

                        // checkedId is the RadioButton selected
                        switch (checkedId) {
                            case R.id.homework_done_rb:
                                homeworkFinalArrays.get(i).setHomeworkstatus("1");
                                break;

                            case R.id.homework_notdone_rb:
                                homeworkFinalArrays.get(i).setHomeworkstatus("0");
                                break;
                        }

                    }
                }
            });

            switch (Integer.parseInt(homeworkFinalArrays.get(i).getHomeworkstatus())) {
                case 0:
                    myViewHolder.rbNotdone.setChecked(true);
                    break;
                case 1:
                    myViewHolder.rbDone.setChecked(true);
                    break;
                case -1:
                    myViewHolder.rbDone.setChecked(true);
                    break;
                default:
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (homeworkFinalArrays.get(i).getHomeworkstatus().equalsIgnoreCase("-1") || homeworkFinalArrays.get(i).getHomeworkstatus().equalsIgnoreCase("1")) {
////            myViewHolder.rbDone.setChecked(true);
////            myViewHolder.rbNotdone.setChecked(false);
//
//            homeworkFinalArrays.get(i).setDonecheck("1");
//            homeworkFinalArrays.get(i).setNotdonecheck("0");
//
//        } else if (homeworkFinalArrays.get(i).getHomeworkstatus().equalsIgnoreCase("0")) {
////            myViewHolder.rbNotdone.setChecked(true);
////            myViewHolder.rbDone.setChecked(false);
//
//            homeworkFinalArrays.get(i).setDonecheck("0");
//            homeworkFinalArrays.get(i).setNotdonecheck("1");
//
//        }
//
//        myViewHolder.rbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                stduentIdStr = "";
//                if (isChecked) {
//                    homeworkFinalArrays.get(i).setDonecheck("1");
//                    homeworkFinalArrays.get(i).setNotdonecheck("0");
//                    stduentIdStr = homeworkFinalArrays.get(i).getStudentid();
//                    FinalValue = stduentIdStr;
//                    dataCheck.add(FinalValue);
//                    Log.d("dataCheck", dataCheck.toString());
//                    listner.getEmployeeSMSCheck();
//                } else {
//                    homeworkFinalArrays.get(i).setDonecheck("0");
//                    homeworkFinalArrays.get(i).setNotdonecheck("1");
//                    dataCheck.remove(homeworkFinalArrays.get(i).getStudentid());
//                    Log.d("dataUnCheck", dataCheck.toString());
//                    listner.getEmployeeSMSCheck();
//                }
//            }
//        });
//        if (homeworkFinalArrays.get(i).getDonecheck().equalsIgnoreCase("1")) {
//            myViewHolder.rbDone.setChecked(true);
//        } else {
//            myViewHolder.rbDone.setChecked(false);
//        }
//
//        myViewHolder.rbNotdone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                stduentIdStr1 = "";
//                if (isChecked) {
//                    homeworkFinalArrays.get(i).setNotdonecheck("1");
//                    homeworkFinalArrays.get(i).setDonecheck("0");
//                    stduentIdStr1 = homeworkFinalArrays.get(i).getStudentid();
//                    FinalValue1 = stduentIdStr1;
//                    dataCheck1.add(FinalValue1);
//                    Log.d("dataCheck", dataCheck1.toString());
//                    listner1.getEmployeeSMSCheck();
//                } else {
//                    homeworkFinalArrays.get(i).setNotdonecheck("0");
//                    homeworkFinalArrays.get(i).setDonecheck("1");
//                    dataCheck1.remove(homeworkFinalArrays.get(i).getStudentid());
//                    Log.d("dataUnCheck", dataCheck1.toString());
//                    listner1.getEmployeeSMSCheck();
//                }
//            }
//        });
//        if (homeworkFinalArrays.get(i).getNotdonecheck().equalsIgnoreCase("1")) {
//            myViewHolder.rbNotdone.setChecked(true);
//        } else {
//            myViewHolder.rbNotdone.setChecked(false);
//        }

    }

    @Override
    public int getItemCount() {
        return homeworkFinalArrays.size();
    }

    public List<HomeWorkModel.HomeworkFinalArray> getDatas() {
        return homeworkFinalArrays;
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView stuname;
        RadioButton rbDone, rbNotdone;
        RadioGroup rg;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            stuname = itemView.findViewById(R.id.homework_studentname_txt);
            rbDone = itemView.findViewById(R.id.homework_done_rb);
            rbNotdone = itemView.findViewById(R.id.homework_notdone_rb);
            rg = itemView.findViewById(R.id.rg);

        }
    }

}
