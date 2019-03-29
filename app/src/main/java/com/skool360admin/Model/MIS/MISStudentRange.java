package com.skool360admin.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISStudentRange {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }

    public class FinalArray {

        @SerializedName("StandardData")
        @Expose
        private List<RangeDatum> standardData = null;

        @SerializedName("Range")
        @Expose
        private String range;
        @SerializedName("Count")
        @Expose
        private String count;

        public List<RangeDatum> getStandardData() {
            return standardData;
        }

        public void setStandardData(List<RangeDatum> standardData) {
            this.standardData = standardData;
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    public class RangeDatum {
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("ClassName")
        @Expose
        private String classname;
        @SerializedName("StudentID")
        @Expose
        private String studentid;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("MarkGained")
        @Expose
        private String markgained;
        @SerializedName("Percentage")
        @Expose
        private String percentage;
        @SerializedName("Grade")
        @Expose
        private String grade;

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getStudentid() {
            return studentid;
        }

        public void setStudentid(String studentid) {
            this.studentid = studentid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getMarkgained() {
            return markgained;
        }

        public void setMarkgained(String markgained) {
            this.markgained = markgained;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }

}

