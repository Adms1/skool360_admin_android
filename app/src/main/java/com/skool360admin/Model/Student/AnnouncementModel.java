package com.skool360admin.Model.Student;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnnouncementModel implements Parcelable {


    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    protected AnnouncementModel(Parcel in) {
        success = in.readString();
    }

    public static final Creator<AnnouncementModel> CREATOR = new Creator<AnnouncementModel>() {
        @Override
        public AnnouncementModel createFromParcel(Parcel in) {
            return new AnnouncementModel(in);
        }

        @Override
        public AnnouncementModel[] newArray(int size) {
            return new AnnouncementModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(success);
    }


    public static class FinalArray implements Parcelable {

        @SerializedName("SubjectName")
        @Expose
        private String subjectName;
        @SerializedName("Ann_Desc")
        @Expose
        private String annDesc;
        @SerializedName("AnnoucementPDF")
        @Expose
        private String annoucementPDF;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("AnnStatus")
        @Expose
        private String annStatus;
        @SerializedName("PK_AnnouncmentID")
        @Expose
        private Integer pKAnnouncmentID;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("StandardID")
        @Expose
        private String standardID;
        @SerializedName("ScheduleDate")
        @Expose
        private String scheduleDate;
        @SerializedName("ScheduleTime")
        @Expose
        private String scheduleTime;
        @SerializedName("GradeStatus")
        @Expose
        private String gradeStatus;

        protected FinalArray(Parcel in) {
            subjectName = in.readString();
            annDesc = in.readString();
            annoucementPDF = in.readString();
            createDate = in.readString();
            annStatus = in.readString();
            if (in.readByte() == 0) {
                pKAnnouncmentID = null;
            } else {
                pKAnnouncmentID = in.readInt();
            }
            standard = in.readString();
            standardID = in.readString();
            scheduleDate = in.readString();
            scheduleTime = in.readString();
            gradeStatus = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(subjectName);
            dest.writeString(annDesc);
            dest.writeString(annoucementPDF);
            dest.writeString(createDate);
            dest.writeString(annStatus);
            if (pKAnnouncmentID == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(pKAnnouncmentID);
            }
            dest.writeString(standard);
            dest.writeString(standardID);
            dest.writeString(scheduleDate);
            dest.writeString(scheduleTime);
            dest.writeString(gradeStatus);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<FinalArray> CREATOR = new Creator<FinalArray>() {
            @Override
            public FinalArray createFromParcel(Parcel in) {
                return new FinalArray(in);
            }

            @Override
            public FinalArray[] newArray(int size) {
                return new FinalArray[size];
            }
        };

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getAnnDesc() {
            return annDesc;
        }

        public void setAnnDesc(String annDesc) {
            this.annDesc = annDesc;
        }

        public String getAnnoucementPDF() {
            return annoucementPDF;
        }

        public void setAnnoucementPDF(String annoucementPDF) {
            this.annoucementPDF = annoucementPDF;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getAnnStatus() {
            return annStatus;
        }

        public void setAnnStatus(String annStatus) {
            this.annStatus = annStatus;
        }

        public Integer getPKAnnouncmentID() {
            return pKAnnouncmentID;
        }

        public void setPKAnnouncmentID(Integer pKAnnouncmentID) {
            this.pKAnnouncmentID = pKAnnouncmentID;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getStandardID() {
            return standardID;
        }

        public void setStandardID(String standardID) {
            this.standardID = standardID;
        }

        public String getScheduleDate() {
            return scheduleDate;
        }

        public void setScheduleDate(String scheduleDate) {
            this.scheduleDate = scheduleDate;
        }

        public String getScheduleTime() {
            return scheduleTime;
        }

        public void setScheduleTime(String scheduleTime) {
            this.scheduleTime = scheduleTime;
        }

        public String getGradeStatus() {
            return gradeStatus;
        }

        public void setGradeStatus(String gradeStatus) {
            this.gradeStatus = gradeStatus;
        }

    }
}
