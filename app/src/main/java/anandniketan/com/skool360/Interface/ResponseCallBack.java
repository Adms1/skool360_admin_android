package anandniketan.com.skool360.Interface;

import java.util.List;

import anandniketan.com.skool360.Model.MIS.MIStudentWiseResultModel;

public interface ResponseCallBack {
    void onResponse(List<MIStudentWiseResultModel.FinalArray> data);

    void onFailure(String errorMessage);
}
