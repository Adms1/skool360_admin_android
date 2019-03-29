package anandniketan.com.bhadajadmin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/22/2017.
 */

public class PickupPointDetailModel {
    @SerializedName("PickupPoint")
    @Expose
    private String pickupPoint;
    @SerializedName("PickupPointID")
    @Expose
    private Integer pickupPointID;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public Integer getPickupPointID() {
        return pickupPointID;
    }

    public void setPickupPointID(Integer pickupPointID) {
        this.pickupPointID = pickupPointID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
