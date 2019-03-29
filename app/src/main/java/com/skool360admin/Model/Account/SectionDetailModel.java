package com.skool360admin.Model.Account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 11/24/2017.
 */

public class SectionDetailModel {
    @SerializedName("Section")
    @Expose
    private String section;
    @SerializedName("SectionID")
    @Expose
    private Integer sectionID;

    @SerializedName("checksttus")
    @Expose
    private String checkstatus = "0";

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

    public String getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(String checkstatus) {
        this.checkstatus = checkstatus;
    }
}
