package com.skool360admin.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

//created by Antra 11/01/2019

public class TransportMainModel {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private ArrayList<FinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(ArrayList<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public class FinalArray {
        @SerializedName("StandardData")
        @Expose
        private ArrayList<StandardDatum> standarddata = null;

        @SerializedName("StudentData")
        @Expose
        private ArrayList<StudentDatum> studentdata = null;

        public ArrayList<StandardDatum> getStandarddata() {
            return standarddata;
        }

        public void setStandarddata(ArrayList<StandardDatum> standarddata) {
            this.standarddata = standarddata;
        }

        public ArrayList<StudentDatum> getStudentdata() {
            return studentdata;
        }

        public void setStudentdata(ArrayList<StudentDatum> studentdata) {
            this.studentdata = studentdata;
        }
    }

    public class StandardDatum {
        @SerializedName("Standard")
        @Expose
        private String standard;

        @SerializedName("Transport")
        @Expose
        private String transport;

        @SerializedName("Personal")
        @Expose
        private String personal;

        @SerializedName("StandardID")
        @Expose
        private String standardid;

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getTransport() {
            return transport;
        }

        public void setTransport(String transport) {
            this.transport = transport;
        }

        public String getPersonal() {
            return personal;
        }

        public void setPersonal(String personal) {
            this.personal = personal;
        }

        public String getStandardid() {
            return standardid;
        }

        public void setStandardid(String standardid) {
            this.standardid = standardid;
        }
    }

    public class StudentDatum {

        @SerializedName("Grade")
        @Expose
        private String grade;

        @SerializedName("Section")
        @Expose
        private String section;

        @SerializedName("StudentName")
        @Expose
        private String studentname;

        @SerializedName("GRNO")
        @Expose
        private String grno;

        @SerializedName("PhoneNo")
        @Expose
        private String phoneno;

        @SerializedName("Route")
        @Expose
        private String route;

        @SerializedName("PickupPoint")
        @Expose
        private String pickuppoint;

        @SerializedName("DropPoint")
        @Expose
        private String droppoint;

        @SerializedName("PickupTime")
        @Expose
        private String pickuptime;

        @SerializedName("DropTime")
        @Expose
        private String droptime;

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getStudentname() {
            return studentname;
        }

        public void setStudentname(String studentname) {
            this.studentname = studentname;
        }

        public String getGrno() {
            return grno;
        }

        public void setGrno(String grno) {
            this.grno = grno;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public String getPickuppoint() {
            return pickuppoint;
        }

        public void setPickuppoint(String pickuppoint) {
            this.pickuppoint = pickuppoint;
        }

        public String getDroppoint() {
            return droppoint;
        }

        public void setDroppoint(String droppoint) {
            this.droppoint = droppoint;
        }

        public String getPickuptime() {
            return pickuptime;
        }

        public void setPickuptime(String pickuptime) {
            this.pickuptime = pickuptime;
        }

        public String getDroptime() {
            return droptime;
        }

        public void setDroptime(String droptime) {
            this.droptime = droptime;
        }
    }

}
