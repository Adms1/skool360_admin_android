package com.skool360admin.Model.MIS;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MISAccountModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("ClassData")
    @Expose
    private List<ClassDatum> classData = null;

    @SerializedName("Student")
    @Expose
    private List<Student> student = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<ClassDatum> getClassData() {
        return classData;
    }

    public void setClassData(List<ClassDatum> classData) {
        this.classData = classData;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }


    public List<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }


    public class FinalArray {

        @SerializedName("LedgerName")
        @Expose
        private String ledgerName;
        @SerializedName("Term1Amt")
        @Expose
        private String term1Amt;
        @SerializedName("Term2Amt")
        @Expose
        private String term2Amt;

        public String getLedgerName() {
            return ledgerName;
        }

        public void setLedgerName(String ledgerName) {
            this.ledgerName = ledgerName;
        }

        public String getTerm1Amt() {
            return term1Amt;
        }

        public void setTerm1Amt(String term1Amt) {
            this.term1Amt = term1Amt;
        }

        public String getTerm2Amt() {
            return term2Amt;
        }

        public void setTerm2Amt(String term2Amt) {
            this.term2Amt = term2Amt;
        }
    }


    public class ClassDatum {

        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("TotalAmount")
        @Expose
        private String totalAmount;
        @SerializedName("RecievedAmount")
        @Expose
        private String recievedAmount;
        @SerializedName("DueAmount")
        @Expose
        private String dueAmount;

        @SerializedName("StandardID")
        @Expose
        private String StandardID;


        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getRecievedAmount() {
            return recievedAmount;
        }

        public void setRecievedAmount(String recievedAmount) {
            this.recievedAmount = recievedAmount;
        }

        public String getDueAmount() {
            return dueAmount;
        }

        public void setDueAmount(String dueAmount) {
            this.dueAmount = dueAmount;
        }

        public String getStandardID() {
            return StandardID;
        }

        public void setStandardID(String standardID) {
            StandardID = standardID;
        }
    }

    public class Datum implements Parcelable {

        @SerializedName("TotalAmount")
        @Expose
        private String totalAmount;
        @SerializedName("RecievedAmount")
        @Expose
        private String recievedAmount;
        @SerializedName("DueAmount")
        @Expose
        private String dueAmount;

        protected Datum(Parcel in) {
            totalAmount = in.readString();
            recievedAmount = in.readString();
            dueAmount = in.readString();
        }

        public final Creator<Datum> CREATOR = new Creator<Datum>() {
            @Override
            public Datum createFromParcel(Parcel in) {
                return new Datum(in);
            }

            @Override
            public Datum[] newArray(int size) {
                return new Datum[size];
            }
        };

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getRecievedAmount() {
            return recievedAmount;
        }

        public void setRecievedAmount(String recievedAmount) {
            this.recievedAmount = recievedAmount;
        }

        public String getDueAmount() {
            return dueAmount;
        }

        public void setDueAmount(String dueAmount) {
            this.dueAmount = dueAmount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(totalAmount);
            parcel.writeString(recievedAmount);
            parcel.writeString(dueAmount);
        }
    }

    public class Student {

        @SerializedName("StudentID")
        @Expose
        private Integer studentID;
        @SerializedName("StudentName")
        @Expose
        private String studentName;
        @SerializedName("Class")
        @Expose
        private String _class;
        @SerializedName("GRNO")
        @Expose
        private String gRNO;
        @SerializedName("TotalAmount")
        @Expose
        private String totalAmount;
        @SerializedName("RecievedAmount")
        @Expose
        private String recievedAmount;
        @SerializedName("DueAmount")
        @Expose
        private String dueAmount;

        public Integer getStudentID() {
            return studentID;
        }

        public void setStudentID(Integer studentID) {
            this.studentID = studentID;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getClass_() {
            return _class;
        }

        public void setClass_(String _class) {
            this._class = _class;
        }

        public String getGRNO() {
            return gRNO;
        }

        public void setGRNO(String gRNO) {
            this.gRNO = gRNO;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getRecievedAmount() {
            return recievedAmount;
        }

        public void setRecievedAmount(String recievedAmount) {
            this.recievedAmount = recievedAmount;
        }

        public String getDueAmount() {
            return dueAmount;
        }

        public void setDueAmount(String dueAmount) {
            this.dueAmount = dueAmount;
        }
    }

}
