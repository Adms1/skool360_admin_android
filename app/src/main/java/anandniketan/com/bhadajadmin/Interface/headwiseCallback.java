package anandniketan.com.bhadajadmin.Interface;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.MIS.HeadwiseStudent;

public interface headwiseCallback {


    void onResponse(List<HeadwiseStudent.Finalarray> data);

    void onFailure(String errorMessage);


}
