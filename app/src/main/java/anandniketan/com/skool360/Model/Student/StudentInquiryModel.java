package anandniketan.com.skool360.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentInquiryModel {

        @SerializedName("Success")
        @Expose
        private String success;
        @SerializedName("Year")
        @Expose
        private String year;
        @SerializedName("FinalArray")
        @Expose
        private List<FinalArray> finalArray = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<FinalArray> getFinalArray() {
            return finalArray;
        }

        public void setFinalArray(List<FinalArray> finalArray) {
            this.finalArray = finalArray;
        }


    public class FinalArray {

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("StudentID")
        @Expose
        private Integer studentID;
        @SerializedName("Grade")
        @Expose
        private String grade;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("Current Status")
        @Expose
        private String currentStatus;
        @SerializedName("Staus Detail")
        @Expose
        private List<StausDetail> stausDetail = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getStudentID() {
            return studentID;
        }

        public void setStudentID(Integer studentID) {
            this.studentID = studentID;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(String currentStatus) {
            this.currentStatus = currentStatus;
        }

        public List<StausDetail> getStausDetail() {
            return stausDetail;
        }

        public void setStausDetail(List<StausDetail> stausDetail) {
            this.stausDetail = stausDetail;
        }

    }


    public class StausDetail {

        @SerializedName("Status")
        @Expose
        private String status1;
        @SerializedName("Date")
        @Expose
        private String date;

        private String studentId;

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }


        public String getStatus1() {
            return status1;
        }

        public void setStatus1(String status1) {
            this.status1 = status1;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}
