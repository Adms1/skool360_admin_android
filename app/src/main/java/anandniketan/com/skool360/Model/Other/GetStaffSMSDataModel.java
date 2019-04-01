package anandniketan.com.skool360.Model.Other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 12/21/2017.
 */

public class GetStaffSMSDataModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("ActiveStudentCount")
    @Expose
    private Integer activeStudentCount;
    @SerializedName("ActiveTeacherCount")
    @Expose
    private Integer activeTeacherCount;
    @SerializedName("TotalStudentCount")
    @Expose
    private Integer totalStudentCount;
    @SerializedName("TotalTeacherCount")
    @Expose
    private Integer totalTeacherCount;
    @SerializedName("TotalAdminCount")
    @Expose
    private Integer totalAdminCount;
    @SerializedName("ActiveadminCount")
    @Expose
    private Integer activeadminCount;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArraySMSDataModel> finalArray = new ArrayList<FinalArraySMSDataModel>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getActiveStudentCount() {
        return activeStudentCount;
    }

    public void setActiveStudentCount(Integer activeStudentCount) {
        this.activeStudentCount = activeStudentCount;
    }

    public Integer getActiveTeacherCount() {
        return activeTeacherCount;
    }

    public void setActiveTeacherCount(Integer activeTeacherCount) {
        this.activeTeacherCount = activeTeacherCount;
    }

    public Integer getTotalStudentCount() {
        return totalStudentCount;
    }

    public void setTotalStudentCount(Integer totalStudentCount) {
        this.totalStudentCount = totalStudentCount;
    }

    public Integer getTotalTeacherCount() {
        return totalTeacherCount;
    }

    public void setTotalTeacherCount(Integer totalTeacherCount) {
        this.totalTeacherCount = totalTeacherCount;
    }

    public Integer getTotalAdminCount() {
        return totalAdminCount;
    }

    public void setTotalAdminCount(Integer totalAdminCount) {
        this.totalAdminCount = totalAdminCount;
    }

    public Integer getActiveadminCount() {
        return activeadminCount;
    }

    public void setActiveadminCount(Integer activeadminCount) {
        this.activeadminCount = activeadminCount;
    }

    public List<FinalArraySMSDataModel> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArraySMSDataModel> finalArray) {
        this.finalArray = finalArray;
    }

}
