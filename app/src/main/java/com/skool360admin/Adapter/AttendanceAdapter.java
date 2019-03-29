package com.skool360admin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import anandniketan.com.bhadajadmin.Model.Student.StandardWiseAttendanceModel;
import anandniketan.com.bhadajadmin.Model.Student.StudentAttendanceModel;
import anandniketan.com.bhadajadmin.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admsandroid on 1/31/2018.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {
    private Context context;
    private StudentAttendanceModel attendanceModel;
    ImageLoader imageLoader;

    public AttendanceAdapter(Context mContext, StudentAttendanceModel attendanceModel) {
        this.context = mContext;
        this.attendanceModel = attendanceModel;
    }


    @Override
    public AttendanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_list_item, parent, false);
        return new AttendanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AttendanceAdapter.MyViewHolder holder, int position) {
        imageLoader = ImageLoader.getInstance();

        final StandardWiseAttendanceModel detail = attendanceModel.getFinalArray().get(0).getStudentDetail().get(position);
        try {
//            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                    .cacheInMemory(true)
//                    .cacheOnDisk(true)
//                    .imageScaleType(ImageScaleType.EXACTLY)
//                    .displayer(new FadeInBitmapDisplayer(300))
//                    .build();
//            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                    context)
//                    .threadPriority(Thread.MAX_PRIORITY)
//                    .defaultDisplayImageOptions(defaultOptions)
//                    .memoryCache(new WeakMemoryCache())
//                    .denyCacheImageMultipleSizesInMemory()
//                    .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
//                    .build();
//            imageLoader.init(config.createDefault(context));
//            imageLoader.displayImage(detail.getStudentImage(), holder.profile_image);

            if(!TextUtils.isEmpty(detail.getStudentImage())){
                Glide.with(context).load(detail.getStudentImage()).apply(new RequestOptions().placeholder(R.drawable.person_placeholder).error(R.drawable.person_placeholder)).into(holder.profile_image);
            }else{
                Glide.with(context).load(R.drawable.person_placeholder).into(holder.profile_image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.student_name_txt.setText(detail.getStudentName());
        holder.attendance_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                    // checkedId is the RadioButton selected
                    switch (checkedId) {
                        case R.id.present_chk:
                            detail.setAttendenceStatus("1");
                            break;

                        case R.id.absent_chk:
                            detail.setAttendenceStatus("0");
                            break;

                        case R.id.leave_chk:
                            detail.setAttendenceStatus("-1");
                            break;

//                        case R.id.onduty_chk:
//                            detail.setAttendenceStatus("3");
                    }

                }
            }
        });
        switch (Integer.parseInt(detail.getAttendenceStatus())) {
            case 0:
                holder.absent_chk.setChecked(true);
                break;
            case 1:
                holder.present_chk.setChecked(true);
                break;
            case -1:
                holder.leave_chk.setChecked(true);
                break;
            case -2:
                holder.present_chk.setChecked(true);
                break;
//            case 3:
//                holder.onduty_chk.setChecked(true);
//                holder.onduty_chk.setClickable(false);
//                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return attendanceModel.getFinalArray().get(0).getStudentDetail().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView student_name_txt;
        CircleImageView profile_image;
        RadioGroup attendance_group;
        RadioButton present_chk, absent_chk, leave_chk, onduty_chk;

        public MyViewHolder(View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            student_name_txt = itemView.findViewById(R.id.student_name_txt);
            attendance_group = itemView.findViewById(R.id.attendance_group);
            present_chk = itemView.findViewById(R.id.present_chk);
            absent_chk = itemView.findViewById(R.id.absent_chk);
            leave_chk = itemView.findViewById(R.id.leave_chk);
            //onduty_chk = (RadioButton) itemView.findViewById(R.id.onduty_chk);

        }
    }

}

