package com.skool360admin.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarkSyllabusModel {



    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    public String getSuccess() {
        return success; }

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

        @SerializedName("Term")
        @Expose
        private String term;
        @SerializedName("Type")
        @Expose
        private String type;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("ClassName")
        @Expose
        private String className;
        @SerializedName("TestName")
        @Expose
        private String testName;
        @SerializedName("Status")
        @Expose
        private String status;

        @SerializedName("PermissionID")
        @Expose
        private Integer permissionID;

        private String checkStatus;

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getPermissionID() {
            return permissionID;
        }

        public void setPermissionID(Integer permissionID) {
            this.permissionID = permissionID;
        }

        public String getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(String checkStatus) {
            this.checkStatus = checkStatus;
        }
    }

}
