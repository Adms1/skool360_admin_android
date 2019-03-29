package com.skool360admin.Interface;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.MIS.HeadwiseStudent;

public interface headwiseCallback {


    void onResponse(List<HeadwiseStudent.Finalarray> data);

    void onFailure(String errorMessage);


}
