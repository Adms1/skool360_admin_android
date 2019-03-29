package anandniketan.com.bhadajadmin.Model.MIS;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISStudentResultDataModel {

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

    public static class Datum implements Parcelable{

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

        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("ClassName")
        @Expose
        private String className;
        @SerializedName("StudentID")
        @Expose
        private Integer studentID;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("total")
        @Expose
        private Double total;
        @SerializedName("MarkGained")
        @Expose
        private Double markGained;
        @SerializedName("Percentage")
        @Expose
        private String percentage;
        @SerializedName("TermData")
        @Expose
        private List<TermDatum> termData = null;

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Integer getStudentID() {
            return studentID;
        }

        public void setStudentID(Integer studentID) {
            this.studentID = studentID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Double getMarkGained() {
            return markGained;
        }

        public void setMarkGained(Double markGained) {
            this.markGained = markGained;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public List<TermDatum> getTermData() {
            return termData;
        }

        public void setTermData(List<TermDatum> termData) {
            this.termData = termData;
        }

    }

    public static class TermDatum implements Parcelable {

        @SerializedName("Term")
        @Expose
        private String term;
        @SerializedName("Data")
        @Expose
        private List<Datum> data = null;

        protected TermDatum(Parcel in) {
            term = in.readString();
        }

        public static final Creator<TermDatum> CREATOR = new Creator<TermDatum>() {
            @Override
            public TermDatum createFromParcel(Parcel in) {
                return new TermDatum(in);
            }

            @Override
            public TermDatum[] newArray(int size) {
                return new TermDatum[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(term);
        }
    }
}
