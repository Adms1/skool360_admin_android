package com.skool360admin.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveStatusModel {


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

        @SerializedName("StatusName")
        @Expose
        private String statusName;
        @SerializedName("LeaveStatusID")
        @Expose
        private Integer leaveStatusID;

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public Integer getLeaveStatusID() {
            return leaveStatusID;
        }

        public void setLeaveStatusID(Integer leaveStatusID) {
            this.leaveStatusID = leaveStatusID;
        }

    }
}
