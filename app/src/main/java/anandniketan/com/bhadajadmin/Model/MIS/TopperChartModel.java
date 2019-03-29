package anandniketan.com.bhadajadmin.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopperChartModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("FinalArray")
    @Expose
    private ArrayList<FinalArray> finalarray;

    @SerializedName("GradeData")
    @Expose
    private ArrayList<GradeData> gradedata;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<FinalArray> getFinalarray() {
        return finalarray;
    }

    public ArrayList<GradeData> getGradedata() {
        return gradedata;
    }

    public void setGradedata(ArrayList<GradeData> gradedata) {
        this.gradedata = gradedata;
    }

    public void setFinalarray(ArrayList<FinalArray> finalarray) {
        this.finalarray = finalarray;
    }

    public class FinalArray {
        @SerializedName("Term")
        @Expose
        private String term;

        @SerializedName("Data")
        @Expose
        private ArrayList<MISClassWiseResultModel.FinalArray> data;

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public ArrayList<MISClassWiseResultModel.FinalArray> getData() {
            return data;
        }

        public void setData(ArrayList<MISClassWiseResultModel.FinalArray> data) {
            this.data = data;
        }
    }

    public class GradeData {

        @SerializedName("Grade")
        @Expose
        private String grade;

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }

}
