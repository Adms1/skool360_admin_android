package com.skool360admin.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RangeChartModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("FinalArray")
    @Expose
    private ArrayList<FinalArray> finalarray;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<FinalArray> getFinalarray() {
        return finalarray;
    }

    public void setFinalarray(ArrayList<FinalArray> finalarray) {
        this.finalarray = finalarray;
    }

    public class FinalArray {

        @SerializedName("Range")
        @Expose
        private String range;

        @SerializedName("Count")
        @Expose
        private String count;

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

}
