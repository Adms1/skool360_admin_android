package com.skool360admin.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/20/2017.
 */

public class StaffAttendaceModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName(value = "msg", alternate = {"Year", "Message"})
    @Expose
    private String year;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStaffModel> finalArray = null;

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

    public List<FinalArrayStaffModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStaffModel> finalArray) {
        this.finalArray = finalArray;
    }
}
