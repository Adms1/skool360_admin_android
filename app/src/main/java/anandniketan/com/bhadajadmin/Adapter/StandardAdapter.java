package anandniketan.com.bhadajadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Account.FinalArrayStandard;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 1/24/2018.
 */

public class StandardAdapter extends BaseAdapter {
    private Context mContext;
    private List<FinalArrayStandard> standardModel;
    private ArrayList<FinalArrayStandard> arrayList = new ArrayList<>();
    private ArrayList<String> checkedItemsIds;

    // Constructor
    public StandardAdapter(Context c, List<FinalArrayStandard> standardModel) {
        mContext = c;
        this.standardModel = standardModel;
    }

    private class ViewHolder {
        CheckBox check_standard;
    }

    @Override
    public int getCount() {
        return standardModel.size();
    }

    @Override
    public Object getItem(int position) {
        return standardModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = null;
        convertView = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_standard_checkbox, null);
            viewHolder.check_standard = (CheckBox) convertView.findViewById(R.id.check_standard);
            final FinalArrayStandard standarObj = standardModel.get(position);
            try {
                viewHolder.check_standard.setText(standarObj.getStandard());
                viewHolder.check_standard.setTag(standarObj.getStandardID());
                if (standarObj.getCheckedStatus().equalsIgnoreCase("1")) {
                    viewHolder.check_standard.setChecked(true);
                } else {
                    viewHolder.check_standard.setChecked(false);
                }

                viewHolder.check_standard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            standarObj.setCheckedStatus("1");
                            standardModel.get(position).setCheckedStatus("1");
                        } else {
                            standarObj.setCheckedStatus("0");
                            standardModel.get(position).setCheckedStatus("0");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    public List<FinalArrayStandard> getDatas() {
        return standardModel;
    }
    public ArrayList<String> getCheckedStandards(){
        checkedItemsIds = new ArrayList<String>();
        if(standardModel != null){
            if(standardModel.size() > 0){
                for(int count = 0;count <standardModel.size();count++){
                    if(standardModel.get(count).getCheckedStatus().equalsIgnoreCase("1")){
                        checkedItemsIds.add(String.valueOf(standardModel.get(count).getStandardID()));
                    }
                }

            }
        }
        return checkedItemsIds;
    }





    public void disableSelection(){
        try {
            ViewHolder viewHolder = new ViewHolder();

            if (viewHolder != null) {
                viewHolder.check_standard.setEnabled(false);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void enableSelection(){
        try {
            ViewHolder viewHolder = new ViewHolder();

            if (viewHolder != null) {
                viewHolder.check_standard.setEnabled(true);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
