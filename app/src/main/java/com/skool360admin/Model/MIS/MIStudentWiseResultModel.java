package com.skool360admin.Model.MIS;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MIStudentWiseResultModel {

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

    public static class Datum implements Parcelable {

        @SerializedName("Subject")
        @Expose
        private String subject;
        @SerializedName("Term1Marks")
        @Expose
        private String term1Marks;
        @SerializedName("Term2Marks")
        @Expose
        private String term2Marks;

        protected Datum(Parcel in) {
            subject = in.readString();
            term1Marks = in.readString();
            term2Marks = in.readString();
        }

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

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTerm1Marks() {
            return term1Marks;
        }

        public void setTerm1Marks(String term1Marks) {
            this.term1Marks = term1Marks;
        }

        public String getTerm2Marks() {
            return term2Marks;
        }

        public void setTerm2Marks(String term2Marks) {
            this.term2Marks = term2Marks;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(subject);
            parcel.writeString(term1Marks);
            parcel.writeString(term2Marks);
        }
    }


    public class FinalArray {

        @SerializedName("Term")
        @Expose
        private String term;
        @SerializedName("Data")
        @Expose
        private List<Datum> data = null;

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }
}
