package anandniketan.com.bhadajadmin.Interface;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Student.AnnouncementModel;
import anandniketan.com.bhadajadmin.Model.Student.CircularModel;

public interface OnUpdateRecord {
    void onUpdateRecord(List<AnnouncementModel.FinalArray> dataList);
    void onUpdateRecordCircular(List<CircularModel.FinalArray> dataList);

}
