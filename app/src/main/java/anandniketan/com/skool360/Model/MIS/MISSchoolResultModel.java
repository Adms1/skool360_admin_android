package anandniketan.com.skool360.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISSchoolResultModel {

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

        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("ClassName")
        @Expose
        private String className;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("total")
        @Expose
        private Double total;
        @SerializedName("MarkGained")
        @Expose
        private Double markGained;
        @SerializedName("Percentage")
        @Expose
        private String percentage;

        @SerializedName("Grade")
        @Expose
        private String grade;

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Double getMarkGained() {
            return markGained;
        }

        public void setMarkGained(Double markGained) {
            this.markGained = markGained;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }
}
