package anandniketan.com.bhadajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class TransportChargesModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayTransportChargesModel> finalArray = new ArrayList<FinalArrayTransportChargesModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayTransportChargesModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayTransportChargesModel> finalArray) {
        this.finalArray = finalArray;
    }

}
