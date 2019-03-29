package com.skool360admin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeWorkModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("FinalArray")
    @Expose
    private ArrayList<HomeworkFinalArray> finalarray;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<HomeworkFinalArray> getFinalarray() {
        return finalarray;
    }

    public void setFinalarray(ArrayList<HomeworkFinalArray> finalarray) {
        this.finalarray = finalarray;
    }

    public class HomeworkFinalArray {

        @SerializedName("StudentID")
        @Expose
        private String studentid;

        @SerializedName("StudentName")
        @Expose
        private String studentname;

        @SerializedName("HomeWorkID")
        @Expose
        private String homeworkid;

        @SerializedName("HomeWorkDetailID")
        @Expose
        private String homeworkdetailid;

        @SerializedName("HomeWorkStatus")
        @Expose
        private String homeworkstatus;

        @SerializedName("DoneCheck")
        @Expose
        private String donecheck;

        @SerializedName("notDoneCheck")
        @Expose
        private String notdonecheck;

        public String getStudentid() {
            return studentid;
        }

        public void setStudentid(String studentid) {
            this.studentid = studentid;
        }

        public String getStudentname() {
            return studentname;
        }

        public void setStudentname(String studentname) {
            this.studentname = studentname;
        }

        public String getHomeworkid() {
            return homeworkid;
        }

        public void setHomeworkid(String homeworkid) {
            this.homeworkid = homeworkid;
        }

        public String getHomeworkdetailid() {
            return homeworkdetailid;
        }

        public void setHomeworkdetailid(String homeworkdetailid) {
            this.homeworkdetailid = homeworkdetailid;
        }

        public String getHomeworkstatus() {
            return homeworkstatus;
        }

        public void setHomeworkstatus(String homeworkstatus) {
            this.homeworkstatus = homeworkstatus;
        }

        public String getDonecheck() {
            return donecheck;
        }

        public void setDonecheck(String donecheck) {
            this.donecheck = donecheck;
        }

        public String getNotdonecheck() {
            return notdonecheck;
        }

        public void setNotdonecheck(String notdonecheck) {
            this.notdonecheck = notdonecheck;
        }
    }
}
