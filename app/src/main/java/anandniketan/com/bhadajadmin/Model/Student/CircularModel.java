package anandniketan.com.bhadajadmin.Model.Student;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CircularModel implements Parcelable{

    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    protected CircularModel(Parcel in) {
        success = in.readString();
        finalArray = in.createTypedArrayList(FinalArray.CREATOR);
    }

    public static final Creator<CircularModel> CREATOR = new Creator<CircularModel>() {
        @Override
        public CircularModel createFromParcel(Parcel in) {
            return new CircularModel(in);
        }

        @Override
        public CircularModel[] newArray(int size) {
            return new CircularModel[size];
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
        parcel.writeTypedList(finalArray);
    }


    public static class FinalArray implements Parcelable {

        @SerializedName("Subject")
        @Expose
        private String subject;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("Discription")
        @Expose
        private String discription;
        @SerializedName("CircularPDF")
        @Expose
        private String circularPDF;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("PK_CircularID")
        @Expose
        private Integer pKCircularID;
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
            subject = in.readString();
            date = in.readString();
            discription = in.readString();
            circularPDF = in.readString();
            status = in.readString();
            if (in.readByte() == 0) {
                pKCircularID = null;
            } else {
                pKCircularID = in.readInt();
            }
            standard = in.readString();
            standardID = in.readString();
            scheduleDate = in.readString();
            scheduleTime = in.readString();
            gradeStatus = in.readString();
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

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getCircularPDF() {
            return circularPDF;
        }

        public void setCircularPDF(String circularPDF) {
            this.circularPDF = circularPDF;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getPKCircularID() {
            return pKCircularID;
        }

        public void setPKCircularID(Integer pKCircularID) {
            this.pKCircularID = pKCircularID;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(subject);
            parcel.writeString(date);
            parcel.writeString(discription);
            parcel.writeString(circularPDF);
            parcel.writeString(status);
            if (pKCircularID == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(pKCircularID);
            }
            parcel.writeString(standard);
            parcel.writeString(standardID);
            parcel.writeString(scheduleDate);
            parcel.writeString(scheduleTime);
            parcel.writeString(gradeStatus);
        }
    }
}
