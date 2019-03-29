package anandniketan.com.bhadajadmin.Interface;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.MIS.MIStudentWiseResultModel;

public interface ResponseCallBack {
    void onResponse(List<MIStudentWiseResultModel.FinalArray> data);
    void onFailure(String errorMessage);
}
