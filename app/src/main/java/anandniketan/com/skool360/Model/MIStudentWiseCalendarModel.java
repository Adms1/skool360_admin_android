package anandniketan.com.skool360.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MIStudentWiseCalendarModel {

    @SerializedName("Success")
    @Expose
    private String success;

    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalarray;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArray> getFinalarray() {
        return finalarray;
    }

    public void setFinalarray(List<FinalArray> finalarray) {
        this.finalarray = finalarray;
    }

    public static class FinalArray implements Parcelable {
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
        @SerializedName("Type")
        @Expose
        private String type;
        @SerializedName("Data")
        @Expose
        private ArrayList<Datum> data;

        protected FinalArray(Parcel in) {
            type = in.readString();
            data = in.createTypedArrayList(Datum.CREATOR);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<Datum> getData() {
            return data;
        }

        public void setData(ArrayList<Datum> data) {
            this.data = data;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(type);
            dest.writeTypedList(data);
        }
    }

    public static class Datum implements Parcelable {

        public static final Creator<Datum> CREATOR = new Creator<Datum>() {
            @Override
            public Datum createFromParcel(Parcel in) {
                return new Datum(in);
            }

            @Override
            public Datum[] newArray(int size) {
                return new Datum[size];
            }
        };
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("Days")
        @Expose
        private String days;
        @SerializedName("Grade")
        @Expose
        private String grade;

        protected Datum(Parcel in) {
            name = in.readString();
            date = in.readString();
            days = in.readString();
            grade = in.readString();

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeString(date);
            parcel.writeString(days);
            parcel.writeString(grade);

        }

    }

}
