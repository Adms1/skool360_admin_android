package com.skool360admin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class GetStandardModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayStandard> finalArray = new ArrayList<FinalArrayStandard>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayStandard> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayStandard> finalArray) {
        this.finalArray = finalArray;
    }

}
