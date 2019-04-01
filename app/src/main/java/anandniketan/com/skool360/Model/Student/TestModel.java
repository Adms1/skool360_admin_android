package anandniketan.com.skool360.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestModel {

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

    public static class FinalArray {

        @SerializedName("TestName")
        @Expose
        private String testName;
        @SerializedName("TestID")
        @Expose
        private Integer testID;

        @SerializedName("CheckedStatus")
        @Expose
        private String checkedStatus;


        public String getCheckedStatus() {
            return checkedStatus;
        }

        public void setCheckedStatus(String checkedStatus) {
            this.checkedStatus = checkedStatus;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public Integer getTestID() {
            return testID;
        }

        public void setTestID(Integer testID) {
            this.testID = testID;
        }

    }
}
