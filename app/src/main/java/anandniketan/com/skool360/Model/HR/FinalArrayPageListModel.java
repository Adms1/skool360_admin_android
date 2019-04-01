package anandniketan.com.skool360.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 12/20/2017.
 */

public class FinalArrayPageListModel {
    @SerializedName("PK_PageID")
    @Expose
    private Integer pKPageID;
    @SerializedName("Page_UnderName")
    @Expose
    private String pageUnderName;
    @SerializedName("Page_Nam")
    @Expose
    private String pageNam;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("IsUserUpdate")
    @Expose
    private Boolean isUserUpdate;
    @SerializedName("IsUserDelete")
    @Expose
    private Boolean isUserDelete;
    @SerializedName("Page_URL")
    @Expose
    private String pageURL;

    public Integer getPKPageID() {
        return pKPageID;
    }

    public void setPKPageID(Integer pKPageID) {
        this.pKPageID = pKPageID;
    }

    public String getPageUnderName() {
        return pageUnderName;
    }

    public void setPageUnderName(String pageUnderName) {
        this.pageUnderName = pageUnderName;
    }

    public String getPageNam() {
        return pageNam;
    }

    public void setPageNam(String pageNam) {
        this.pageNam = pageNam;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsUserUpdate() {
        return isUserUpdate;
    }

    public void setIsUserUpdate(Boolean isUserUpdate) {
        this.isUserUpdate = isUserUpdate;
    }

    public Boolean getIsUserDelete() {
        return isUserDelete;
    }

    public void setIsUserDelete(Boolean isUserDelete) {
        this.isUserDelete = isUserDelete;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

}
