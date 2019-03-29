package anandniketan.com.bhadajadmin.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DesignationModel {


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

        @SerializedName("DesignationId")
        @Expose
        private String designationId;
        @SerializedName("DesignationName")
        @Expose
        private String designationName;
        @SerializedName("DesignationCode")
        @Expose
        private String designationCode;

        public String getDesignationId() {
            return designationId;
        }

        public void setDesignationId(String designationId) {
            this.designationId = designationId;
        }

        public String getDesignationName() {
            return designationName;
        }

        public void setDesignationName(String designationName) {
            this.designationName = designationName;
        }

        public String getDesignationCode() {
            return designationCode;
        }

        public void setDesignationCode(String designationCode) {
            this.designationCode = designationCode;
        }

    }

}
