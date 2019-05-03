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
    @SerializedName("IsUserView")
    @Expose
    private Boolean isUserView;
    @SerializedName("Page_URL")
    @Expose
    private String pageURL;
    @SerializedName("VisibleStatus")
    @Expose
    private String visibleStatus;
    @SerializedName("VisibleIsUpdate")
    @Expose
    private String visibleIsUpdate;
    @SerializedName("VisibleIsDelete")
    @Expose
    private String visibleIsDelete;
    @SerializedName("VisibleIsView")
    @Expose
    private String visibleIsView;

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

    public Integer getpKPageID() {
        return pKPageID;
    }

    public void setpKPageID(Integer pKPageID) {
        this.pKPageID = pKPageID;
    }

    public Boolean getUserUpdate() {
        return isUserUpdate;
    }

    public void setUserUpdate(Boolean userUpdate) {
        isUserUpdate = userUpdate;
    }

    public Boolean getUserDelete() {
        return isUserDelete;
    }

    public void setUserDelete(Boolean userDelete) {
        isUserDelete = userDelete;
    }

    public Boolean getUserView() {
        return isUserView;
    }

    public void setUserView(Boolean userView) {
        isUserView = userView;
    }

    public String getVisibleStatus() {
        return visibleStatus;
    }

    public void setVisibleStatus(String visibleStatus) {
        this.visibleStatus = visibleStatus;
    }

    public String getVisibleIsUpdate() {
        return visibleIsUpdate;
    }

    public void setVisibleIsUpdate(String visibleIsUpdate) {
        this.visibleIsUpdate = visibleIsUpdate;
    }

    public String getVisibleIsDelete() {
        return visibleIsDelete;
    }

    public void setVisibleIsDelete(String visibleIsDelete) {
        this.visibleIsDelete = visibleIsDelete;
    }

    public String getVisibleIsView() {
        return visibleIsView;
    }

    public void setVisibleIsView(String visibleIsView) {
        this.visibleIsView = visibleIsView;
    }
}
