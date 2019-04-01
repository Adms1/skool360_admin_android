package anandniketan.com.skool360.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HeadwiseStudent {

    String Success;

    ArrayList<Finalarray> FinalArray;

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public ArrayList<Finalarray> getFinalArray() {
        return FinalArray;
    }

    public void setFinalArray(ArrayList<Finalarray> finalArray) {
        FinalArray = finalArray;
    }

    public class Finalarray {

        @SerializedName("Head")
        @Expose
        private String head;
        @SerializedName("Term1Amt")
        @Expose
        private String totalFeesTerm1;
        @SerializedName("Term1AmtRecieved")
        @Expose
        private String recievedFeesTerm1;
        @SerializedName("Term2Amt")
        @Expose
        private String totalFeesTerm2;
        @SerializedName("Term2AmtRecieved")
        @Expose
        private String recievedFeesTerm2;
        @SerializedName("Term1Due")
        @Expose
        private String term1due;
        @SerializedName("Term2Due")
        @Expose
        private String term2due;

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

        public String getTerm1due() {
            return term1due;
        }

        public void setTerm1due(String term1due) {
            this.term1due = term1due;
        }

        public String getTerm2due() {
            return term2due;
        }

        public void setTerm2due(String term2due) {
            this.term2due = term2due;
        }
    }

}
