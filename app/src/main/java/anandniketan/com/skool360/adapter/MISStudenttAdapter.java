package anandniketan.com.skool360.adapter;

// created by Antra 09/01/2019

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Model.MIS.MISStudentModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.DialogUtils;

public class MISStudenttAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<MISStudentModel.StudentDatum>> child = new HashMap<>();
    private int type;
    private String requestType;

    public MISStudenttAdapter(Context _context, List<String> listDataHeader, HashMap<String, ArrayList<MISStudentModel.StudentDatum>> child, int type, String requestType) {
        this._context = _context;
        this._listDataHeader = listDataHeader;
        this.child = child;
        this.type = type;
        this.requestType = requestType;
    }

    @Override
    public MISStudentModel.StudentDatum getChild(int groupPosition, int childPosititon) {
        return this.child.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final MISStudentModel.StudentDatum childData = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            switch (type) {

                case 0:
                    convertView = infalInflater.inflate(R.layout.list_item_mis_total_student_detail, null);
                    break;

                case 1:
                    convertView = infalInflater.inflate(R.layout._list_item_mis_absent3day_student, null);
                    break;
            }

        }

        TextView grade_txt, section_txt, student_txt, grno_txt, status_txt, tv_tname;
        ImageView ivPhone, ivSMS;
        TextView studentname_txt, absentfrom_txt, class_teacher_txt;

        studentname_txt = convertView.findViewById(R.id.studentname_txt);
        absentfrom_txt = convertView.findViewById(R.id.absentfrom_txt);
        class_teacher_txt = convertView.findViewById(R.id.class_teacher_txt);
        grade_txt = convertView.findViewById(R.id.grade_txt);
        section_txt = convertView.findViewById(R.id.section_txt);
        student_txt = convertView.findViewById(R.id.student_txt);
        grno_txt = convertView.findViewById(R.id.grno_txt);
        status_txt = convertView.findViewById(R.id.status_txt);
        ivPhone = convertView.findViewById(R.id.iv_phone);
        ivSMS = convertView.findViewById(R.id.iv_sms);
        tv_tname = convertView.findViewById(R.id.teacher_name_txt);

////        final MISStudentModel.StudentDatum childData = (MISStudentModel.StudentDatum) baseDataModel.get(position);
//        grade_txt.setText(String.valueOf(childData.getGrade()));
//        section_txt.setText(String.valueOf(childData.getGrade() + "-" + childData.getSection()));
//        student_txt.setText(String.valueOf(childData.getStudentName()));
//        grno_txt.setVisibility(View.GONE);
//        status_txt.setText("");
//        tv_tname.setVisibility(View.GONE);
//        tv_tname.setText(String.valueOf(childData.getClassTeacher()));
//        status_txt.setVisibility(View.GONE);
//        grno_txt.setVisibility(View.GONE);

        ivPhone.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));
        ivSMS.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));

        switch (type) {
            case 0:
                if (!requestType.equalsIgnoreCase("ANT")) {
//                    final MISStudentModel.StudentDatum childData = (MISStudentModel.StudentDatum) baseDataModel.get(position);

                    grade_txt.setText(String.valueOf(childData.getGrade()));
                    grade_txt.setVisibility(View.GONE);
                    section_txt.setText(String.valueOf(childData.getGrade() + "-" + childData.getSection()));
                    student_txt.setText(String.valueOf(childData.getStudentName()));
                    grno_txt.setText(String.valueOf(childData.getGRNO()));
                    status_txt.setText(String.valueOf(childData.getAttendanceStatus()));

                    grno_txt.setVisibility(View.GONE);


                    if (requestType.equalsIgnoreCase("Total")) {
                        status_txt.setVisibility(View.VISIBLE);


                    } else if (requestType.equalsIgnoreCase("Present") || requestType.equalsIgnoreCase("Between alumini left")) {

                        status_txt.setVisibility(View.GONE);

                        grno_txt.setVisibility(View.GONE);


                    } else if (requestType.equalsIgnoreCase("Absent") || requestType.equalsIgnoreCase("Leave")) {
                        status_txt.setVisibility(View.GONE);

                        grno_txt.setVisibility(View.GONE);
                        ivPhone.setVisibility(View.VISIBLE);
                        ivSMS.setVisibility(View.VISIBLE);


                        ivPhone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + childData.getPhoneNo()));
                                    _context.startActivity(intent);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                        ivSMS.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogUtils.createConfirmDialog(_context, R.string.app_name, R.string.msg_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {
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
//                    final MISStudentModel.StudentDatum childData = (MISStudentModel.StudentDatum) baseDataModel.get(position);
                    grade_txt.setText(String.valueOf(childData.getGrade()));
                    section_txt.setText(String.valueOf(childData.getGrade() + "-" + childData.getSection()));
                    student_txt.setText(String.valueOf(childData.getStudentName()));
                    grno_txt.setVisibility(View.GONE);
                    status_txt.setText("");
                    tv_tname.setVisibility(View.GONE);
                    tv_tname.setText(String.valueOf(childData.getClassTeacher()));
                    status_txt.setVisibility(View.GONE);
                    grno_txt.setVisibility(View.GONE);

                    ivPhone.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));
                    ivSMS.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.7f));


                    ivPhone.setVisibility(View.VISIBLE);
                    ivSMS.setVisibility(View.VISIBLE);

                    ivPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + childData.getPhoneNo()));
                                _context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                    ivSMS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtils.createConfirmDialog(_context, R.string.app_name, R.string.msg_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {
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


                break;


            case 1:


                if (requestType.equalsIgnoreCase("ConsistentAbsent")) {

//                    final MISStudentModel.StudentDatum childData = (MISStudentModel.StudentDatum) baseDataModel.get(position);

                    grade_txt.setText(String.valueOf(childData.getGrade()));
                    grade_txt.setVisibility(View.GONE);

                    section_txt.setText(String.valueOf(childData.getGrade() + "-" + childData.getSection()));
                    studentname_txt.setText(String.valueOf(childData.getStudentName()));


                    grno_txt.setText(String.valueOf(childData.getGRNO()));
                    grno_txt.setVisibility(View.GONE);


                    absentfrom_txt.setText(String.valueOf(childData.getAbsentFrom()) + " days");

                    class_teacher_txt.setText(String.valueOf(childData.getClassTeacher1()));

                    ivPhone.setVisibility(View.VISIBLE);
                    ivSMS.setVisibility(View.VISIBLE);

                    ivPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + childData.getPhoneNo()));
                                _context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                    ivSMS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtils.createConfirmDialog(_context, R.string.app_name, R.string.msg_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {
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

//                    final MISStudentModel.StudentDatum childData = (MISStudentModel.StudentDatum) baseDataModel.get(position);

                    grade_txt.setText(String.valueOf(childData.getGrade()));
                    grade_txt.setVisibility(View.GONE);

                    section_txt.setText(String.valueOf(childData.getGrade() + "-" + childData.getSection()));
                    studentname_txt.setText(String.valueOf(childData.getStudentName()));


                    grno_txt.setText(String.valueOf(childData.getGRNO()));
                    grno_txt.setVisibility(View.GONE);


                    absentfrom_txt.setVisibility(View.VISIBLE);
                    absentfrom_txt.setText(String.valueOf(childData.getPercentage()));

                    class_teacher_txt.setText(String.valueOf(childData.getClassTeacher()));

                    ivPhone.setVisibility(View.VISIBLE);

                    ivSMS.setVisibility(View.VISIBLE);


                    ivPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("tel:" + childData.getPhoneNo()));
                                _context.startActivity(intent);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                    ivSMS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtils.createConfirmDialog(_context, R.string.app_name, R.string.msg_confirm_msg, "OK", "Cancel", new DialogInterface.OnClickListener() {
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
                break;
        }


        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.child.get(this._listDataHeader.get(groupPosition)).size();
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

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_mis_totlal_student, null);
        }
        TextView grade_txt, section_txt, student_txt, percentage_txt, view_txt;
        ImageView ivArrow;

        LinearLayout headerList = convertView.findViewById(R.id.child_header);
        LinearLayout headerList1 = convertView.findViewById(R.id.child_header_less);
        LinearLayout headerList2 = convertView.findViewById(R.id.child_header_absent3);
        LinearLayout headerList3 = convertView.findViewById(R.id.child_header_present);
        LinearLayout headerList4 = convertView.findViewById(R.id.child_header_total);
        if (isExpanded) {
            if (requestType.equalsIgnoreCase("Attendance less then 70%")) {
                headerList1.setVisibility(View.VISIBLE);
            } else if (requestType.equalsIgnoreCase("ConsistentAbsent")) {
                headerList2.setVisibility(View.VISIBLE);
            } else if (requestType.equalsIgnoreCase("Present") || requestType.equalsIgnoreCase("Between alumini left")) {
                headerList3.setVisibility(View.VISIBLE);
            } else if (requestType.equalsIgnoreCase("Total")) {
                headerList4.setVisibility(View.VISIBLE);
            } else {
                headerList.setVisibility(View.VISIBLE);
            }
        } else {
            headerList.setVisibility(View.GONE);
            headerList1.setVisibility(View.GONE);
            headerList2.setVisibility(View.GONE);
            headerList3.setVisibility(View.GONE);
            headerList4.setVisibility(View.GONE);
        }

        grade_txt = convertView.findViewById(R.id.txt_grade);
        section_txt = convertView.findViewById(R.id.txt_no);
        student_txt = convertView.findViewById(R.id.student_txt);
        percentage_txt = convertView.findViewById(R.id.percentage_txt);
        view_txt = convertView.findViewById(R.id.view_txt);
        ivArrow = convertView.findViewById(R.id.student_item_view_txt);

        grade_txt.setText(String.valueOf(headerTitle1));
        section_txt.setText(String.valueOf(headerTitle2));

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

}

