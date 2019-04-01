package anandniketan.com.skool360.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class GetPageListModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayPageListModel> finalArray = new ArrayList<FinalArrayPageListModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayPageListModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayPageListModel> finalArray) {
        this.finalArray = finalArray;
    }

}
