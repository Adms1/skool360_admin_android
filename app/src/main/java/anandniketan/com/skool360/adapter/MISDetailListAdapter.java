package anandniketan.com.skool360.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.skool360.Fragment.fragment.mis.MISAccountFeesDetailFragment;
import anandniketan.com.skool360.Fragment.fragment.mis.MISAccountStudentFeesFragment;
import anandniketan.com.skool360.Model.MIS.MISAccountModel;
import anandniketan.com.skool360.Model.MIS.MISNewAdmissionModel;
import anandniketan.com.skool360.Model.MIS.MISStaffModel;
import anandniketan.com.skool360.Model.MIS.MISStaffNewDetailModel;
import anandniketan.com.skool360.Model.MIS.MISStudentModel;
import anandniketan.com.skool360.Model.MIS.MISTaskReportDetailModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.DialogUtils;
import anandniketan.com.skool360.Utility.Utils;

public class MISDetailListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    SpannableStringBuilder discriptionSpanned;
    private Context context;
    private List<T> baseDataModel;
    private String discriptionStr;
    private int type;
    private String requestType;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    public MISDetailListAdapter(Context mContext, List<T> baseDataModel, int type, String requestType) {
        this.context = mContext;
        this.baseDataModel = baseDataModel;
        this.type = type;
        this.requestType = requestType;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewType = type;
        switch (viewType) {
            case 0:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_total_student_detail, parent, false);
                return new StudentCommonViewHolder(itemView);
            case 1:
                View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout._list_item_mis_absent3day_student, parent, false);
                return new StudentConsistentViewHolder(itemView1);
            case 2:
                View itemView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_staff_detail_list, parent, false);
                return new StaffCommonViewHolder(itemView2);
            case 3:
                View itemView3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_account, parent, false);
                return new AccountCommonViewHolder(itemView3);
            case 4:
                View itemView4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_account_gradewise_list, parent, false);
                return new AccountGradeWiseViewHolder(itemView4);

            case 5:
                View itemView5 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_account_student_list, parent, false);
                return new AccountStudentWiseViewHolder(itemView5);
            case 6:
                View itemView6 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_new_admission, parent, false);
                return new NAViewHolder(itemView6);

            case 7:
                View itemView7 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_staff_new_list, parent, false);
                return new StaffNewViewHolder(itemView7);

            case 8:
                View itemView8 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mis_task_report_innerlist, parent, false);
                return new TaskReportViewHolder(itemView8);

        }


        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        switch (type) {
            case 0:
                if (holder instanceof MISDetailListAdapter.StudentCommonViewHolder) {
                    if (!requestType.equalsIgnoreCase("ANT")) {
                        final MISStudentModel.StudentDatum datum = (MISStudentModel.StudentDatum) baseDataModel.get(position);

                        ((StudentCommonViewHolder) holder).grade_txt.setText(String.valueOf(datum.getGrade()));
                        ((StudentCommonViewHolder) holder).grade_txt.setVisibility(View.GONE);
                        ((StudentCommonViewHolder) holder).section_txt.setText(String.valueOf(datum.getGrade() + "-" + datum.getSection()));
                        ((StudentCommonViewHolder) holder).student_txt.setText(String.valueOf(datum.getStudentName()));
                        ((StudentCommonViewHolder) holder).grno_txt.setText(String.valueOf(datum.getGRNO()));
                        ((StudentCommonViewHolder) holder).status_txt.setText(String.valueOf(datum.getAttendanceStatus()));

                        ((StudentCommonViewHolder) holder).grno_txt.setVisibility(View.GONE);


                        if (requestType.equalsIgnoreCase("Total")) {
                            ((StudentCommonViewHolder) holder).status_txt.setVisibility(View.VISIBLE);


                        }
                        if (requestType.equalsIgnoreCase("Present")) {

                            ((StudentCommonViewHolder) holder).status_txt.setVisibility(View.GONE);

                            ((StudentCommonViewHolder) holder).grno_txt.setVisibility(View.GONE);


                        } else if (requestType.equalsIgnoreCase("Absent") || requestType.equalsIgnoreCase("Leave")) {
                            ((StudentCommonViewHolder) holder).status_txt.setVisibility(View.GONE);

                            ((StudentCommonViewHolder) holder).grno_txt.setVisibility(View.GONE);
                            ((StudentCommonViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                            ((StudentCommonViewHolder) holder).ivSMS.setVisibility(View.VISIBLE);


                            ((StudentCommonViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setData(Uri.parse("tel:" + datum.getPhoneNo()));
                                        context.startActivity(intent);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });

                            ((StudentCommonViewHolder) holder).ivSMS.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    DialogUtils.createConfirmDialog(context, R.string.app_name, R.string.msg_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {


                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                                }
                            });
                        }

                    } else {
                        final MISStudentModel.StudentDatum datum = (MISStudentModel.StudentDatum) baseDataModel.get(position);
                        ((StudentCommonViewHolder) holder).grade_txt.setText(String.valueOf(datum.getGrade()));
                        ((StudentCommonViewHolder) holder).section_txt.setText(String.valueOf(datum.getGrade() + "-" + datum.getSection()));
                        ((StudentCommonViewHolder) holder).student_txt.setText(String.valueOf(datum.getStudentName()));
                        ((StudentCommonViewHolder) holder).grno_txt.setVisibility(View.GONE);
                        ((StudentCommonViewHolder) holder).status_txt.setText("");
                        ((StudentCommonViewHolder) holder).tv_tname.setVisibility(View.GONE);
                        ((StudentCommonViewHolder) holder).tv_tname.setText(String.valueOf(datum.getClassTeacher()));
                        ((StudentCommonViewHolder) holder).status_txt.setVisibility(View.GONE);
                        ((StudentCommonViewHolder) holder).grno_txt.setVisibility(View.GONE);

                        ((StudentCommonViewHolder) holder).ivPhone.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));
                        ((StudentCommonViewHolder) holder).ivSMS.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));


                        ((StudentCommonViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                        ((StudentCommonViewHolder) holder).ivSMS.setVisibility(View.VISIBLE);

                        ((StudentCommonViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + datum.getPhoneNo()));
                                    context.startActivity(intent);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                        ((StudentCommonViewHolder) holder).ivSMS.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogUtils.createConfirmDialog(context, R.string.app_name, R.string.msg_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                            }
                        });


                    }
                }


                break;


            case 1:
                if (holder instanceof MISDetailListAdapter.StudentConsistentViewHolder) {


                    if (requestType.equalsIgnoreCase("ConsistentAbsent")) {

                        final MISStudentModel.StudentDatum datum = (MISStudentModel.StudentDatum) baseDataModel.get(position);

                        ((StudentConsistentViewHolder) holder).grade_txt.setText(String.valueOf(datum.getGrade()));
                        ((StudentConsistentViewHolder) holder).grade_txt.setVisibility(View.GONE);

                        ((StudentConsistentViewHolder) holder).section_txt.setText(String.valueOf(datum.getGrade() + "-" + datum.getSection()));
                        ((StudentConsistentViewHolder) holder).studentname_txt.setText(String.valueOf(datum.getStudentName()));


                        ((StudentConsistentViewHolder) holder).grno_txt.setText(String.valueOf(datum.getGRNO()));
                        ((StudentConsistentViewHolder) holder).grno_txt.setVisibility(View.GONE);


                        ((StudentConsistentViewHolder) holder).absentfrom_txt.setText(String.valueOf(datum.getAbsentFrom()) + " days");

                        ((StudentConsistentViewHolder) holder).class_teacher_txt.setText(String.valueOf(datum.getClassTeacher1()));

                        ((StudentConsistentViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                        ((StudentConsistentViewHolder) holder).ivSMS.setVisibility(View.VISIBLE);

                        ((StudentConsistentViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + datum.getPhoneNo()));
                                    context.startActivity(intent);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                        ((StudentConsistentViewHolder) holder).ivSMS.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogUtils.createConfirmDialog(context, R.string.app_name, R.string.msg_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                            }
                        });
                    } else if (requestType.equalsIgnoreCase("Attendance less then 70%")) {

                        final MISStudentModel.StudentDatum datum = (MISStudentModel.StudentDatum) baseDataModel.get(position);

                        ((StudentConsistentViewHolder) holder).grade_txt.setText(String.valueOf(datum.getGrade()));
                        ((StudentConsistentViewHolder) holder).grade_txt.setVisibility(View.GONE);

                        ((StudentConsistentViewHolder) holder).section_txt.setText(String.valueOf(datum.getGrade() + "-" + datum.getSection()));
                        ((StudentConsistentViewHolder) holder).studentname_txt.setText(String.valueOf(datum.getStudentName()));


                        ((StudentConsistentViewHolder) holder).grno_txt.setText(String.valueOf(datum.getGRNO()));
                        ((StudentConsistentViewHolder) holder).grno_txt.setVisibility(View.GONE);


                        ((StudentConsistentViewHolder) holder).absentfrom_txt.setVisibility(View.VISIBLE);
                        ((StudentConsistentViewHolder) holder).absentfrom_txt.setText(String.valueOf(datum.getPercentage()));

                        ((StudentConsistentViewHolder) holder).class_teacher_txt.setText(String.valueOf(datum.getClassTeacher()));

                        ((StudentConsistentViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);

                        ((StudentConsistentViewHolder) holder).ivSMS.setVisibility(View.VISIBLE);


                        ((StudentConsistentViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + datum.getPhoneNo()));
                                    context.startActivity(intent);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                        ((StudentConsistentViewHolder) holder).ivSMS.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogUtils.createConfirmDialog(context, R.string.app_name, R.string.msg_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                            }
                        });
                    }


                }
                break;


            case 2:
                if (holder instanceof MISDetailListAdapter.StaffCommonViewHolder) {

                    if (!requestType.equalsIgnoreCase("ANT")) {

                        if (requestType.equalsIgnoreCase("Work Plan") || requestType.equalsIgnoreCase("CW Submitted") || requestType.equalsIgnoreCase("HW Submitted")) {
                            MISStaffModel.FinalArray datum1 = (MISStaffModel.FinalArray) baseDataModel.get(position);
                            ((StaffCommonViewHolder) holder).name_txt.setText(String.valueOf(datum1.getTeacherName()));
                            ((StaffCommonViewHolder) holder).code_txt.setText(String.valueOf(datum1.getStandard() + "-" + datum1.getClass_()));
                            ((StaffCommonViewHolder) holder).dept_txt.setText(String.valueOf(datum1.getSubject()));

                            ((StaffCommonViewHolder) holder).status_txt.setVisibility(View.GONE);
                        } else {


                            MISStaffModel.StaffDatum datum = (MISStaffModel.StaffDatum) baseDataModel.get(position);
                            ((StaffCommonViewHolder) holder).name_txt.setText(String.valueOf(datum.getStaffName()));
                            ((StaffCommonViewHolder) holder).code_txt.setText(String.valueOf(datum.getStaffCode()));
                            ((StaffCommonViewHolder) holder).dept_txt.setText(String.valueOf(datum.getDepartment()));

                            if (requestType.equalsIgnoreCase("Total")) {
                                ((StaffCommonViewHolder) holder).status_txt.setVisibility(View.VISIBLE);
                                ((StaffCommonViewHolder) holder).status_txt.setText(String.valueOf(datum.getAttendanceStatus()));
                            } else {
                                ((StaffCommonViewHolder) holder).status_txt.setVisibility(View.GONE);
                            }
                        }


                    } else {
                        MISStaffModel.ANT datum = (MISStaffModel.ANT) baseDataModel.get(position);

                        ((StaffCommonViewHolder) holder).name_txt.setText(String.valueOf(datum.getClassTeacher()));
                        ((StaffCommonViewHolder) holder).code_txt.setText(String.valueOf(datum.getTeacherCode()));
                        ((StaffCommonViewHolder) holder).dept_txt.setText(String.valueOf(datum.getGrade()));
                        ((StaffCommonViewHolder) holder).status_txt.setText(String.valueOf(datum.getSection()));

                    }
                }
                break;

            case 3:
                final MISAccountModel.ClassDatum datum = (MISAccountModel.ClassDatum) baseDataModel.get(position);

                ((AccountCommonViewHolder) holder).grade_txt.setText(String.valueOf(datum.getStandard()));
                ((AccountCommonViewHolder) holder).fees_txt.setText(String.valueOf(datum.getTotalAmount()));
                ((AccountCommonViewHolder) holder).collection_txt.setText(String.valueOf(datum.getRecievedAmount()));
                ((AccountCommonViewHolder) holder).duecollection_txt.setText(String.valueOf(datum.getDueAmount()));

                ((AccountCommonViewHolder) holder).fees_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "Fees Details");
                        bundle.putString("requestType", requestType);
                        bundle.putString("requestTitle", requestType);
                        bundle.putString("TermID", AppConfiguration.TermId);
                        bundle.putString("Date", Utils.getTodaysDate());
                        bundle.putString("subtitle", ((AccountCommonViewHolder) holder).grade_txt.getText().toString() + "- " + requestType);
                        bundle.putString("countdata", ((AccountCommonViewHolder) holder).duecollection_txt.getText().toString());
                        bundle.putString("feesType", "Fees");
                        bundle.putString("StandardID", datum.getStandardID());
                        AppConfiguration.StandardId = datum.getStandardID();
                        AppConfiguration.pageInnerTitle = requestType;
                        AppConfiguration.Coundata = ((AccountCommonViewHolder) holder).duecollection_txt.getText().toString();
                        AppConfiguration.Subtitle = ((AccountCommonViewHolder) holder).grade_txt.getText().toString() + "- " + requestType;
                        AppConfiguration.Grade = ((AccountCommonViewHolder) holder).grade_txt.getText().toString();

                        fragment = new MISAccountFeesDetailFragment();
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                .replace(R.id.frame_container, fragment).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 67;
                    }
                });

                ((AccountCommonViewHolder) holder).collection_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {


                            Bundle bundle = new Bundle();
                            bundle.putString("title", "Fees Details");
                            bundle.putString("requestType", requestType);
                            bundle.putString("requestTitle", requestType);
                            bundle.putString("TermID", AppConfiguration.TermId);
                            bundle.putString("Date", Utils.getTodaysDate());
                            bundle.putString("subtitle", ((AccountCommonViewHolder) holder).grade_txt.getText().toString() + "- " + requestType);
                            bundle.putString("countdata", ((AccountCommonViewHolder) holder).duecollection_txt.getText().toString());
                            bundle.putString("feesType", "Collection");
                            bundle.putString("StandardID", datum.getStandardID());
                            AppConfiguration.StandardId = datum.getStandardID();
                            AppConfiguration.pageInnerTitle = requestType;
                            AppConfiguration.Coundata = ((AccountCommonViewHolder) holder).duecollection_txt.getText().toString();
                            AppConfiguration.Subtitle = ((AccountCommonViewHolder) holder).grade_txt.getText().toString() + "- " + requestType;
                            AppConfiguration.Grade = ((AccountCommonViewHolder) holder).grade_txt.getText().toString();

                            fragment = new MISAccountFeesDetailFragment();
                            fragment.setArguments(bundle);
                            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                    .replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
                            AppConfiguration.firsttimeback = true;
                            AppConfiguration.position = 67;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                ((AccountCommonViewHolder) holder).duecollection_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("title", "Fees Details");
                            bundle.putString("requestType", requestType);
                            bundle.putString("requestTitle", requestType);
                            bundle.putString("TermID", AppConfiguration.TermId);
                            bundle.putString("Date", Utils.getTodaysDate());
                            bundle.putString("subtitle", ((AccountCommonViewHolder) holder).grade_txt.getText().toString() + "- " + requestType);
                            bundle.putString("countdata", ((AccountCommonViewHolder) holder).duecollection_txt.getText().toString());
                            bundle.putString("feesType", "Dues");
                            bundle.putString("StandardID", datum.getStandardID());
                            AppConfiguration.StandardId = datum.getStandardID();
                            AppConfiguration.pageInnerTitle = requestType;
                            AppConfiguration.Coundata = ((AccountCommonViewHolder) holder).duecollection_txt.getText().toString();
                            AppConfiguration.Subtitle = ((AccountCommonViewHolder) holder).grade_txt.getText().toString() + "- " + requestType;
                            AppConfiguration.Grade = ((AccountCommonViewHolder) holder).grade_txt.getText().toString();

                            fragment = new MISAccountFeesDetailFragment();
                            fragment.setArguments(bundle);
                            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
                            AppConfiguration.firsttimeback = true;
                            AppConfiguration.position = 67;

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                break;


            case 4:
                final MISAccountModel.Student datum1 = (MISAccountModel.Student) baseDataModel.get(position);
                ((MISDetailListAdapter.AccountGradeWiseViewHolder) holder).student_txt.setText(String.valueOf(datum1.getStudentName()));
                ((MISDetailListAdapter.AccountGradeWiseViewHolder) holder).section_txt.setText(String.valueOf(datum1.getClass_()));
                ((MISDetailListAdapter.AccountGradeWiseViewHolder) holder).fees_txt.setText(String.valueOf(datum1.getTotalAmount()));
                ((MISDetailListAdapter.AccountGradeWiseViewHolder) holder).collection_txt.setText(String.valueOf(datum1.getRecievedAmount()));
                ((MISDetailListAdapter.AccountGradeWiseViewHolder) holder).duecollection_txt.setText(String.valueOf(datum1.getDueAmount()));


                ((MISDetailListAdapter.AccountGradeWiseViewHolder) holder).fees_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "Fees Details");
                        bundle.putString("requestType", requestType);
                        bundle.putString("requestTitle", ((AccountGradeWiseViewHolder) holder).student_txt.getText().toString() + ", " + AppConfiguration.Grade + "-" + ((AccountGradeWiseViewHolder) holder).section_txt.getText().toString());
                        bundle.putString("TermID", AppConfiguration.TermId);
                        bundle.putString("Date", Utils.getTodaysDate());
                        bundle.putString("subtitle", ((AccountGradeWiseViewHolder) holder).student_txt.getText().toString());
                        bundle.putString("countdata", ((AccountGradeWiseViewHolder) holder).duecollection_txt.getText().toString());
                        bundle.putString("feesType", "Fees");
                        bundle.putString("StudentID", String.valueOf(datum1.getStudentID()));
                        bundle.putString("StandardID", AppConfiguration.StandardId);


                        fragment = new MISAccountStudentFeesFragment();
                        fragment.setArguments(bundle);
                        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out).replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 67;
                    }
                });

                ((MISDetailListAdapter.AccountGradeWiseViewHolder) holder).collection_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("title", "Fees Details");
                            bundle.putString("requestType", requestType);
                            bundle.putString("requestTitle", ((AccountGradeWiseViewHolder) holder).student_txt.getText().toString() + ", " + AppConfiguration.Grade + "-" + ((AccountGradeWiseViewHolder) holder).section_txt.getText().toString());
                            bundle.putString("TermID", AppConfiguration.TermId);
                            bundle.putString("Date", Utils.getTodaysDate());
                            bundle.putString("subtitle", ((AccountGradeWiseViewHolder) holder).student_txt.getText().toString());
                            bundle.putString("countdata", ((AccountGradeWiseViewHolder) holder).duecollection_txt.getText().toString());
                            bundle.putString("feesType", "Fees");
                            bundle.putString("StudentID", String.valueOf(datum1.getStudentID()));
                            bundle.putString("StandardID", AppConfiguration.StandardId);


                            fragment = new MISAccountStudentFeesFragment();
                            fragment.setArguments(bundle);
                            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                    .replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
                            AppConfiguration.firsttimeback = true;
                            AppConfiguration.position = 67;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                ((MISDetailListAdapter.AccountGradeWiseViewHolder) holder).duecollection_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("title", "Fees Details");
                            bundle.putString("requestType", requestType);
                            bundle.putString("requestTitle", ((AccountGradeWiseViewHolder) holder).student_txt.getText().toString() + ", " + AppConfiguration.Grade + "-" + ((AccountGradeWiseViewHolder) holder).section_txt.getText().toString());
                            bundle.putString("TermID", AppConfiguration.TermId);
                            bundle.putString("Date", Utils.getTodaysDate());
                            bundle.putString("subtitle", ((AccountGradeWiseViewHolder) holder).student_txt.getText().toString());
                            bundle.putString("countdata", ((AccountGradeWiseViewHolder) holder).duecollection_txt.getText().toString());
                            bundle.putString("feesType", "Fees");
                            bundle.putString("StudentID", String.valueOf(datum1.getStudentID()));
                            bundle.putString("StandardID", AppConfiguration.StandardId);


                            fragment = new MISAccountStudentFeesFragment();
                            fragment.setArguments(bundle);
                            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                                    .replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
                            AppConfiguration.firsttimeback = true;
                            AppConfiguration.position = 67;

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }
                });

                break;

            case 5:
                final MISAccountModel.FinalArray datum2 = (MISAccountModel.FinalArray) baseDataModel.get(position);
                ((AccountStudentWiseViewHolder) holder).detail_txt.setText(String.valueOf(datum2.getLedgerName()));
                ((AccountStudentWiseViewHolder) holder).term1_txt.setText(String.valueOf(datum2.getTerm1Amt()));
                ((AccountStudentWiseViewHolder) holder).term2_txt.setText(String.valueOf(datum2.getTerm2Amt()));
                break;

            case 6:

                final MISNewAdmissionModel.FinalArray datum3 = (MISNewAdmissionModel.FinalArray) baseDataModel.get(position);

                if (requestType.equalsIgnoreCase("FeesNotPaid")) {

                    ((NAViewHolder) holder).date_txt.setText(String.valueOf(datum3.getInqDate()));
                    ((NAViewHolder) holder).name_txt.setText(String.valueOf(datum3.getName()));
                    ((NAViewHolder) holder).grade_txt.setText(String.valueOf(datum3.getGrade()));
                    ((NAViewHolder) holder).gender_txt.setText(String.valueOf(datum3.getGender()));
                    ((NAViewHolder) holder).current_status_txt.setText(String.valueOf(datum3.getCurrentStatus()));
                    ((NAViewHolder) holder).phone_txt.setText(String.valueOf(datum3.getMobileNo()));
                    ((NAViewHolder) holder).current_status_txt.setVisibility(View.GONE);

                    ((NAViewHolder) holder).phone_txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + datum3.getMobileNo()));
                                context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                } else {
//                    final MISNewAdmissionModel.FinalArray datum3 = (MISNewAdmissionModel.FinalArray) baseDataModel.get(position);
                    ((NAViewHolder) holder).date_txt.setText(String.valueOf(datum3.getInqDate()));
                    ((NAViewHolder) holder).name_txt.setText(String.valueOf(datum3.getName()));
                    ((NAViewHolder) holder).grade_txt.setText(String.valueOf(datum3.getGrade()));
                    ((NAViewHolder) holder).gender_txt.setText(String.valueOf(datum3.getGender()));
                    ((NAViewHolder) holder).current_status_txt.setText(String.valueOf(datum3.getCurrentStatus()));


                    if (!requestType.equalsIgnoreCase("Rejected")) {
                        ((NAViewHolder) holder).phone_txt.setText(String.valueOf(datum3.getMobileNo()));
                        ((NAViewHolder) holder).phone_txt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + ((NAViewHolder) holder).phone_txt.getText().toString()));
                                context.startActivity(intent);
                            }
                        });
                    } else {
                        ((NAViewHolder) holder).phone_txt.setTextColor(ContextCompat.getColor(context, R.color.black));
                        ((NAViewHolder) holder).phone_txt.setText(String.valueOf(datum3.getReason()));
                    }

                    ((NAViewHolder) holder).phone_txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + datum3.getMobileNo()));
                                context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }


                break;
            case 7:
                final MISStaffNewDetailModel.FinalArray dataList = (MISStaffNewDetailModel.FinalArray) baseDataModel.get(position);

                if (requestType.equalsIgnoreCase("Total")) {

                    ((StaffNewViewHolder) holder).age_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).since_txt.setVisibility(View.VISIBLE);

                    ((StaffNewViewHolder) holder).name_txt.setText(String.valueOf(dataList.getName()));
                    ((StaffNewViewHolder) holder).status_txt.setText(String.valueOf(dataList.getStatus()));
                    ((StaffNewViewHolder) holder).age_txt.setText(String.valueOf(dataList.getAge()));
                    ((StaffNewViewHolder) holder).since_txt.setText(String.valueOf(dataList.getSince()));
                    ((StaffNewViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setVisibility(View.GONE);
                    ((StaffNewViewHolder) holder).reason_txt.setVisibility(View.GONE);


                    ((StaffNewViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + dataList.getPhoneNo()));
                                context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });


                } else if (requestType.equalsIgnoreCase("Absent")) {

                    ((StaffNewViewHolder) holder).name_txt.setText(String.valueOf(dataList.getName()));
                    ((StaffNewViewHolder) holder).status_txt.setText(String.valueOf(dataList.getStatus()));
                    ((StaffNewViewHolder) holder).status_txt.setVisibility(View.GONE);
                    ((StaffNewViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setVisibility(View.VISIBLE);

                    ((StaffNewViewHolder) holder).age_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).since_txt.setVisibility(View.VISIBLE);

                    ((StaffNewViewHolder) holder).leave_txt.setText(String.valueOf(dataList.getCode()));
                    ((StaffNewViewHolder) holder).reason_txt.setVisibility(View.GONE);
                    ((StaffNewViewHolder) holder).age_txt.setText(String.valueOf(dataList.getAge()));
                    ((StaffNewViewHolder) holder).since_txt.setText(String.valueOf(dataList.getSince()));

                    ((StaffNewViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + dataList.getPhoneNo()));
                                context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });


                } else if (requestType.equalsIgnoreCase("Leave")) {

                    ((StaffNewViewHolder) holder).name_txt.setText(String.valueOf(dataList.getName()));
                    ((StaffNewViewHolder) holder).status_txt.setText(String.valueOf(dataList.getStatus()));
                    ((StaffNewViewHolder) holder).status_txt.setVisibility(View.GONE);

                    ((StaffNewViewHolder) holder).age_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).since_txt.setVisibility(View.VISIBLE);

                    ((StaffNewViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setText(String.valueOf(dataList.getLeaveDays()));
                    ((StaffNewViewHolder) holder).reason_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).reason_txt.setText(String.valueOf(dataList.getLeaveReason()));
                    ((StaffNewViewHolder) holder).age_txt.setText(String.valueOf(dataList.getAge()));
                    ((StaffNewViewHolder) holder).since_txt.setText(String.valueOf(dataList.getSince()));

                    ((StaffNewViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + dataList.getPhoneNo()));
                                context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                } else if (requestType.equalsIgnoreCase("Staff absent more then 10%")) {

                    ((StaffNewViewHolder) holder).name_txt.setText(String.valueOf(dataList.getName()));
                    ((StaffNewViewHolder) holder).status_txt.setText(String.valueOf(dataList.getStatus()));
                    ((StaffNewViewHolder) holder).status_txt.setVisibility(View.GONE);

                    ((StaffNewViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setText(String.valueOf(dataList.getCode()));
                    ((StaffNewViewHolder) holder).reason_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).reason_txt.setText(String.valueOf(dataList.getDepartment()));

                    ((StaffNewViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + dataList.getPhoneNo()));
                                context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                } else if (requestType.equalsIgnoreCase("More then 3 days leave")) {

                    ((StaffNewViewHolder) holder).name_txt.setText(String.valueOf(dataList.getName()));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);
                    params.gravity = Gravity.CENTER;
                    ((StaffNewViewHolder) holder).status_txt.setLayoutParams(params);
                    ((StaffNewViewHolder) holder).status_txt.setText(String.valueOf(dataList.getDepartment()));

                    ((StaffNewViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setText(String.valueOf(dataList.getDays()));
                    ((StaffNewViewHolder) holder).reason_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).reason_txt.setVisibility(View.GONE);

                    ((StaffNewViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + dataList.getPhoneNo()));
                                context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                } else if (requestType.equalsIgnoreCase("More then 3 days absent")) {

                    ((StaffNewViewHolder) holder).name_txt.setText(String.valueOf(dataList.getName()));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);
                    params.gravity = Gravity.CENTER;
                    ((StaffNewViewHolder) holder).status_txt.setLayoutParams(params);
                    ((StaffNewViewHolder) holder).status_txt.setText(String.valueOf(dataList.getDepartment()));

                    ((StaffNewViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).leave_txt.setText(String.valueOf(dataList.getDays()));
                    ((StaffNewViewHolder) holder).reason_txt.setVisibility(View.VISIBLE);
                    ((StaffNewViewHolder) holder).reason_txt.setVisibility(View.GONE);

                    ((StaffNewViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + dataList.getPhoneNo()));
                                context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                }

                break;

            case 8:

                final MISTaskReportDetailModel.FinalArray misTaskReportDetailModel = (MISTaskReportDetailModel.FinalArray) baseDataModel.get(position);

                ((TaskReportViewHolder) holder).name_txt.setText(misTaskReportDetailModel.getName());
                ((TaskReportViewHolder) holder).dept_txt.setText(misTaskReportDetailModel.getDepartment());
                ((TaskReportViewHolder) holder).code_txt.setText(String.valueOf(misTaskReportDetailModel.getCode()));
                ((TaskReportViewHolder) holder).ivPhone.setVisibility(View.VISIBLE);
                ((TaskReportViewHolder) holder).ivPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("tel:" + misTaskReportDetailModel.getPhoneNo()));
                            context.startActivity(intent);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                break;


        }
    }


    @Override
    public int getItemCount() {
        return baseDataModel == null ? 0 : baseDataModel.size();
    }


    public class StudentCommonViewHolder extends RecyclerView.ViewHolder {

        TextView grade_txt, section_txt, student_txt, grno_txt, status_txt, tv_tname;
        ImageView ivPhone, ivSMS;

        public StudentCommonViewHolder(View itemView) {
            super(itemView);
            grade_txt = itemView.findViewById(R.id.grade_txt);
            section_txt = itemView.findViewById(R.id.section_txt);
            student_txt = itemView.findViewById(R.id.student_txt);
            grno_txt = itemView.findViewById(R.id.grno_txt);
            status_txt = itemView.findViewById(R.id.status_txt);
            ivPhone = itemView.findViewById(R.id.iv_phone);
            ivSMS = itemView.findViewById(R.id.iv_sms);
            tv_tname = itemView.findViewById(R.id.teacher_name_txt);
        }
    }

    public class StudentConsistentViewHolder extends RecyclerView.ViewHolder {

        TextView grade_txt, section_txt, studentname_txt, grno_txt, absentfrom_txt, class_teacher_txt;
        ImageView ivPhone, ivSMS;

        public StudentConsistentViewHolder(View itemView) {
            super(itemView);
            grade_txt = itemView.findViewById(R.id.grade_txt);
            section_txt = itemView.findViewById(R.id.section_txt);
            studentname_txt = itemView.findViewById(R.id.studentname_txt);
            grno_txt = itemView.findViewById(R.id.grno_txt);
            absentfrom_txt = itemView.findViewById(R.id.absentfrom_txt);
            class_teacher_txt = itemView.findViewById(R.id.class_teacher_txt);
            ivPhone = itemView.findViewById(R.id.iv_phone);
            ivSMS = itemView.findViewById(R.id.iv_sms);

        }
    }

    public class StaffCommonViewHolder extends RecyclerView.ViewHolder {

        TextView name_txt, code_txt, dept_txt, status_txt;

        public StaffCommonViewHolder(View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.name_txt);
            code_txt = itemView.findViewById(R.id.code_txt);
            dept_txt = itemView.findViewById(R.id.dept_txt);
            status_txt = itemView.findViewById(R.id.status_txt);
        }
    }

    public class AccountCommonViewHolder extends RecyclerView.ViewHolder {

        TextView grade_txt, fees_txt, collection_txt, duecollection_txt;

        public AccountCommonViewHolder(View itemView) {
            super(itemView);
            grade_txt = itemView.findViewById(R.id.grade_txt);
            fees_txt = itemView.findViewById(R.id.fees_txt);
            collection_txt = itemView.findViewById(R.id.collection_txt);
            duecollection_txt = itemView.findViewById(R.id.duecollection_txt);
        }
    }

    public class AccountGradeWiseViewHolder extends RecyclerView.ViewHolder {

        TextView student_txt, section_txt, fees_txt, collection_txt, duecollection_txt;

        public AccountGradeWiseViewHolder(View itemView) {
            super(itemView);
            student_txt = itemView.findViewById(R.id.student_txt);
            section_txt = itemView.findViewById(R.id.section_txt);
            fees_txt = itemView.findViewById(R.id.fees_txt);
            collection_txt = itemView.findViewById(R.id.collection_txt);
            duecollection_txt = itemView.findViewById(R.id.duecollection_txt);
        }
    }

    public class AccountStudentWiseViewHolder extends RecyclerView.ViewHolder {

        TextView detail_txt, term1_txt, term2_txt;

        public AccountStudentWiseViewHolder(View itemView) {
            super(itemView);
            detail_txt = itemView.findViewById(R.id.detail_txt);
            term1_txt = itemView.findViewById(R.id.term1_txt);
            term2_txt = itemView.findViewById(R.id.term2_txt);
        }
    }

    public class NAViewHolder extends RecyclerView.ViewHolder {

        TextView date_txt, name_txt, grade_txt, gender_txt, current_status_txt, phone_txt;

        public NAViewHolder(View itemView) {
            super(itemView);
            date_txt = itemView.findViewById(R.id.date_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            grade_txt = itemView.findViewById(R.id.grade_txt);
            gender_txt = itemView.findViewById(R.id.gender_txt);
            current_status_txt = itemView.findViewById(R.id.current_status_txt);
            phone_txt = itemView.findViewById(R.id.phone_txt1);
        }
    }

    public class StaffNewViewHolder extends RecyclerView.ViewHolder {

        TextView name_txt, status_txt, reason_txt, leave_txt, phone_txt, age_txt, since_txt;
        ImageView ivPhone;

        public StaffNewViewHolder(View itemView) {
            super(itemView);
            status_txt = itemView.findViewById(R.id.status_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            reason_txt = itemView.findViewById(R.id.reason_txt);
            leave_txt = itemView.findViewById(R.id.leave_txt);
            phone_txt = itemView.findViewById(R.id.phone_txt);
            ivPhone = itemView.findViewById(R.id.iv_phone);
            age_txt = itemView.findViewById(R.id.age_txt);
            since_txt = itemView.findViewById(R.id.since_txt);
        }
    }

    public class TaskReportViewHolder extends RecyclerView.ViewHolder {

        TextView dept_txt, name_txt, code_txt;
        ImageView ivPhone;

        public TaskReportViewHolder(View itemView) {
            super(itemView);
            dept_txt = itemView.findViewById(R.id.dept_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            code_txt = itemView.findViewById(R.id.code_txt);
            ivPhone = itemView.findViewById(R.id.iv_phone);
        }
    }
}
