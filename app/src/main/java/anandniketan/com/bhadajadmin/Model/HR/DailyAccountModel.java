package anandniketan.com.bhadajadmin.Model.HR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyAccountModel {

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

        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("EmployeeName")
        @Expose
        private String employeeName;
        @SerializedName("BankBalance")
        @Expose
        private String bankBalance;
        @SerializedName("PettyCash")
        @Expose
        private String pettyCash;
        @SerializedName("Expenses")
        @Expose
        private String expenses;
        @SerializedName("FeesRecieved")
        @Expose
        private String feesRecieved;
        @SerializedName("DepositedInBank")
        @Expose
        private String depositedInBank;
        @SerializedName("Other")
        @Expose
        private String other;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public String getBankBalance() {
            return bankBalance;
        }

        public void setBankBalance(String bankBalance) {
            this.bankBalance = bankBalance;
        }

        public String getPettyCash() {
            return pettyCash;
        }

        public void setPettyCash(String pettyCash) {
            this.pettyCash = pettyCash;
        }

        public String getExpenses() {
            return expenses;
        }

        public void setExpenses(String expenses) {
            this.expenses = expenses;
        }

        public String getFeesRecieved() {
            return feesRecieved;
        }

        public void setFeesRecieved(String feesRecieved) {
            this.feesRecieved = feesRecieved;
        }

        public String getDepositedInBank() {
            return depositedInBank;
        }

        public void setDepositedInBank(String depositedInBank) {
            this.depositedInBank = depositedInBank;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

    }

}
