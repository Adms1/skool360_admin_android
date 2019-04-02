package anandniketan.com.skool360.Interface;

import java.util.List;

import anandniketan.com.skool360.Model.Student.AnnouncementModel;
import anandniketan.com.skool360.Model.Student.CircularModel;

public interface OnUpdateRecord {
    void onUpdateRecord(List<AnnouncementModel.FinalArray> dataList);

    void onUpdateRecordCircular(List<CircularModel.FinalArray> dataList);

}
