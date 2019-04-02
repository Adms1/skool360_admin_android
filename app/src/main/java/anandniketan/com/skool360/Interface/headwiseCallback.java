package anandniketan.com.skool360.Interface;

import java.util.List;

import anandniketan.com.skool360.Model.MIS.HeadwiseStudent;

public interface headwiseCallback {


    void onResponse(List<HeadwiseStudent.Finalarray> data);

    void onFailure(String errorMessage);


}
