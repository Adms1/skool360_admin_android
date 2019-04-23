package anandniketan.com.skool360.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.skool360.Model.Account.FinalArrayStandard;
import anandniketan.com.skool360.Model.Student.FinalArrayStudentModel;
import anandniketan.com.skool360.R;


/**
 * Created by admsandroid on 1/30/2018.
 */

public class TestNameAdapter extends BaseAdapter {
    private Context mContext;
    private List<FinalArrayStudentModel.FinalArray> testnameList;
    private ArrayList<FinalArrayStandard> arrayList = new ArrayList<>();
    private ArrayList<String> IDs = new ArrayList<>();

    // Constructor
    public TestNameAdapter(Context c, List<FinalArrayStudentModel.FinalArray> testnameList) {
        mContext = c;
        this.testnameList = testnameList;
    }

    @Override
    public int getCount() {
        return testnameList.size();
    }

    @Override
    public Object getItem(int position) {
        return testnameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = null;
        convertView = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_test_name_checkbox, null);
            viewHolder.check_standard = convertView.findViewById(R.id.check_standard);
            final FinalArrayStudentModel.FinalArray standarObj = testnameList.get(position);
            try {
                viewHolder.check_standard.setText(standarObj.getTestName());
                viewHolder.check_standard.setTag(standarObj.getTestID());
                if (standarObj.getCheckedStatus().equalsIgnoreCase("1")) {
                    viewHolder.check_standard.setChecked(true);
                } else {
                    viewHolder.check_standard.setChecked(false);
                }

                viewHolder.check_standard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            if (IDs.size() > 0) {

                                if (!IDs.contains(testnameList.get(position).getTestID().toString())) {
                                    IDs.add(testnameList.get(position).getTestID().toString());
                                }

                            } else {
                                IDs.add(testnameList.get(position).getTestID().toString());
                            }

                            standarObj.setCheckedStatus("1");
                            testnameList.get(position).setCheckedStatus("1");

                            Log.d("testidarrayyyyy", IDs.toString());

                        } else {

                            if (IDs.size() > 0) {

                                IDs.remove(testnameList.get(position).getTestID().toString());

                            }

                            standarObj.setCheckedStatus("0");
                            testnameList.get(position).setCheckedStatus("0");

                            Log.d("testidarrayyyyy", IDs.toString());

                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    public ArrayList<String> getDatas() {
        return IDs;
    }

    private class ViewHolder {
        CheckBox check_standard;
    }

}
