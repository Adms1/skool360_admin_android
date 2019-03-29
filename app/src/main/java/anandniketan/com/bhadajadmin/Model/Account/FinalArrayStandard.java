package anandniketan.com.bhadajadmin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class FinalArrayStandard {
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("StandardID")
    @Expose
    private Integer standardID;
    @SerializedName("SectionDetail")
    @Expose
    private List<SectionDetailModel> sectionDetail = new ArrayList<SectionDetailModel>();
    @SerializedName("CheckedStatus")
    @Expose
    private String checkedStatus;
    @SerializedName("StandardClass")
    @Expose
    private String standardClass;

    @SerializedName("ClassID")
    @Expose
    private Integer classID;

    public String getStandardClass() {
        return standardClass;
    }

    public void setStandardClass(String standardClass) {
        this.standardClass = standardClass;
    }

    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Integer getStandardID() {
        return standardID;
    }

    public void setStandardID(Integer standardID) {
        this.standardID = standardID;
    }

    public List<SectionDetailModel> getSectionDetail() {
        return sectionDetail;
    }

    public void setSectionDetail(List<SectionDetailModel> sectionDetail) {
        this.sectionDetail = sectionDetail;
    }

    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus;
    }
}
