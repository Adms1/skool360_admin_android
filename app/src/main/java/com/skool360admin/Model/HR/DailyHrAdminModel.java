package com.skool360admin.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyHrAdminModel {


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

        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("EmployeeName")
        @Expose
        private String employeeName;
        @SerializedName("TotalStrengthOfStudents")
        @Expose
        private String totalStrengthOfStudents;
        @SerializedName("NewAdmission")
        @Expose
        private String newAdmission;
        @SerializedName("Withdrawals")
        @Expose
        private String withdrawals;
        @SerializedName("StudentDatabaseUpdating")
        @Expose
        private String studentDatabaseUpdating;
        @SerializedName("OverallAdmin")
        @Expose
        private String overallAdmin;
        @SerializedName("NoOfTeacherPresent/Absent")
        @Expose
        private String noOfTeacherPresentAbsent;
        @SerializedName("TotalNumberOfVisitors")
        @Expose
        private String totalNumberOfVisitors;
        @SerializedName("AnyComplainofParentsbyPhone/Email")
        @Expose
        private String anyComplainofParentsbyPhoneEmail;
        @SerializedName("SuggestionByParent/Teacher")
        @Expose
        private String suggestionByParentTeacher;
        @SerializedName("Other")
        @Expose
        private String other;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public String getTotalStrengthOfStudents() {
            return totalStrengthOfStudents;
        }

        public void setTotalStrengthOfStudents(String totalStrengthOfStudents) {
            this.totalStrengthOfStudents = totalStrengthOfStudents;
        }

        public String getNewAdmission() {
            return newAdmission;
        }

        public void setNewAdmission(String newAdmission) {
            this.newAdmission = newAdmission;
        }

        public String getWithdrawals() {
            return withdrawals;
        }

        public void setWithdrawals(String withdrawals) {
            this.withdrawals = withdrawals;
        }

        public String getStudentDatabaseUpdating() {
            return studentDatabaseUpdating;
        }

        public void setStudentDatabaseUpdating(String studentDatabaseUpdating) {
            this.studentDatabaseUpdating = studentDatabaseUpdating;
        }

        public String getOverallAdmin() {
            return overallAdmin;
        }

        public void setOverallAdmin(String overallAdmin) {
            this.overallAdmin = overallAdmin;
        }

        public String getNoOfTeacherPresentAbsent() {
            return noOfTeacherPresentAbsent;
        }

        public void setNoOfTeacherPresentAbsent(String noOfTeacherPresentAbsent) {
            this.noOfTeacherPresentAbsent = noOfTeacherPresentAbsent;
        }

        public String getTotalNumberOfVisitors() {
            return totalNumberOfVisitors;
        }

        public void setTotalNumberOfVisitors(String totalNumberOfVisitors) {
            this.totalNumberOfVisitors = totalNumberOfVisitors;
        }

        public String getAnyComplainofParentsbyPhoneEmail() {
            return anyComplainofParentsbyPhoneEmail;
        }

        public void setAnyComplainofParentsbyPhoneEmail(String anyComplainofParentsbyPhoneEmail) {
            this.anyComplainofParentsbyPhoneEmail = anyComplainofParentsbyPhoneEmail;
        }

        public String getSuggestionByParentTeacher() {
            return suggestionByParentTeacher;
        }

        public void setSuggestionByParentTeacher(String suggestionByParentTeacher) {
            this.suggestionByParentTeacher = suggestionByParentTeacher;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

    }

}
