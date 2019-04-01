package anandniketan.com.skool360.Model.Student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsistentAbsentStudentModel {
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("Days")
    @Expose
    private Integer days;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
