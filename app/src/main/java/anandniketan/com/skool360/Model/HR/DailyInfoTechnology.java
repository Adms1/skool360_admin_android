package anandniketan.com.skool360.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyInfoTechnology {


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

        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("EmployeeName")
        @Expose
        private String employeeName;
        @SerializedName("LaptopIssued")
        @Expose
        private Integer laptopIssued;
        @SerializedName("TabletIssued")
        @Expose
        private Integer tabletIssued;
        @SerializedName("Printing")
        @Expose
        private String printing;
        @SerializedName("IT")
        @Expose
        private String iT;
        @SerializedName("Other")
        @Expose
        private String other;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public Integer getLaptopIssued() {
            return laptopIssued;
        }

        public void setLaptopIssued(Integer laptopIssued) {
            this.laptopIssued = laptopIssued;
        }

        public Integer getTabletIssued() {
            return tabletIssued;
        }

        public void setTabletIssued(Integer tabletIssued) {
            this.tabletIssued = tabletIssued;
        }

        public String getPrinting() {
            return printing;
        }

        public void setPrinting(String printing) {
            this.printing = printing;
        }

        public String getIT() {
            return iT;
        }

        public void setIT(String iT) {
            this.iT = iT;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

    }

}
