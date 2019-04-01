package anandniketan.com.skool360.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISFinanaceModel {

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;
    @SerializedName("CountData")
    @Expose
    private List<CountDatum> countData = null;

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

    public List<CountDatum> getCountData() {
        return countData;
    }

    public void setCountData(List<CountDatum> countData) {
        this.countData = countData;
    }

    public class CountDatum {

        @SerializedName("TotalFeesTerm1Count")
        @Expose
        private Integer totalFeesTerm1Count;
        @SerializedName("RecievedFeesTerm1Count")
        @Expose
        private Integer recievedFeesTerm1Count;
        @SerializedName("TotalFeesTerm2Count")
        @Expose
        private Integer totalFeesTerm2Count;
        @SerializedName("RecievedFeesTerm2Count")
        @Expose
        private Integer recievedFeesTerm2Count;
        @SerializedName("Fees pending more then 6 months")
        @Expose
        private Integer feesPendingMoreThen6Months;
        @SerializedName("Previous year pending fees")
        @Expose
        private Integer previousYearPendingFees;

        public Integer getTotalFeesTerm1Count() {
            return totalFeesTerm1Count;
        }

        public void setTotalFeesTerm1Count(Integer totalFeesTerm1Count) {
            this.totalFeesTerm1Count = totalFeesTerm1Count;
        }

        public Integer getRecievedFeesTerm1Count() {
            return recievedFeesTerm1Count;
        }

        public void setRecievedFeesTerm1Count(Integer recievedFeesTerm1Count) {
            this.recievedFeesTerm1Count = recievedFeesTerm1Count;
        }

        public Integer getTotalFeesTerm2Count() {
            return totalFeesTerm2Count;
        }

        public void setTotalFeesTerm2Count(Integer totalFeesTerm2Count) {
            this.totalFeesTerm2Count = totalFeesTerm2Count;
        }

        public Integer getRecievedFeesTerm2Count() {
            return recievedFeesTerm2Count;
        }

        public void setRecievedFeesTerm2Count(Integer recievedFeesTerm2Count) {
            this.recievedFeesTerm2Count = recievedFeesTerm2Count;
        }

        public Integer getFeesPendingMoreThen6Months() {
            return feesPendingMoreThen6Months;
        }

        public void setFeesPendingMoreThen6Months(Integer feesPendingMoreThen6Months) {
            this.feesPendingMoreThen6Months = feesPendingMoreThen6Months;
        }

        public Integer getPreviousYearPendingFees() {
            return previousYearPendingFees;
        }

        public void setPreviousYearPendingFees(Integer previousYearPendingFees) {
            this.previousYearPendingFees = previousYearPendingFees;
        }

    }





    public static class FinalArray {

        @SerializedName("Head")
        @Expose
        private String head;
        @SerializedName("TotalFeesTerm1")
        @Expose
        private String totalFeesTerm1;
        @SerializedName("RecievedFeesTerm1")
        @Expose
        private String recievedFeesTerm1;

        @SerializedName("DueTerm1")
        @Expose
        private String dueterm1;

        @SerializedName("TotalFeesTerm2")
        @Expose
        private String totalFeesTerm2;
        @SerializedName("RecievedFeesTerm2")
        @Expose
        private String recievedFeesTerm2;

        @SerializedName("DueTerm2")
        @Expose
        private String dueterm2;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getTotalFeesTerm1() {
            return totalFeesTerm1;
        }

        public void setTotalFeesTerm1(String totalFeesTerm1) {
            this.totalFeesTerm1 = totalFeesTerm1;
        }

        public String getRecievedFeesTerm1() {
            return recievedFeesTerm1;
        }

        public void setRecievedFeesTerm1(String recievedFeesTerm1) {
            this.recievedFeesTerm1 = recievedFeesTerm1;
        }

        public String getTotalFeesTerm2() {
            return totalFeesTerm2;
        }

        public void setTotalFeesTerm2(String totalFeesTerm2) {
            this.totalFeesTerm2 = totalFeesTerm2;
        }

        public String getRecievedFeesTerm2() {
            return recievedFeesTerm2;
        }

        public void setRecievedFeesTerm2(String recievedFeesTerm2) {
            this.recievedFeesTerm2 = recievedFeesTerm2;
        }

        public String getDueterm1() {
            return dueterm1;
        }

        public void setDueterm1(String dueterm1) {
            this.dueterm1 = dueterm1;
        }

        public String getDueterm2() {
            return dueterm2;
        }

        public void setDueterm2(String dueterm2) {
            this.dueterm2 = dueterm2;
        }
    }

}
