package anandniketan.com.skool360.Model.MIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MISTaskReportModel {

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

    public class FinalArray {

        @SerializedName("Task")
        @Expose
        private String task;
        @SerializedName("Total")
        @Expose
        private Integer total;
        @SerializedName("Done")
        @Expose
        private Integer done;
        @SerializedName("NotDone")
        @Expose
        private Integer notDone;

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getDone() {
            return done;
        }

        public void setDone(Integer done) {
            this.done = done;
        }

        public Integer getNotDone() {
            return notDone;
        }

        public void setNotDone(Integer notDone) {
            this.notDone = notDone;
        }

    }

}
