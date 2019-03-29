package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Account.FinalArrayAccountFeesModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.databinding.ListGroupFeesStructureDetailBinding;
import anandniketan.com.bhadajadmin.databinding.ListItemFeeStructureDetailBinding;


/**
 * Created by admsandroid on 11/24/2017.
 */

public class FeesStructureExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<FinalArrayAccountFeesModel>> _listDataChild;

    public FeesStructureExpandableListAdapter(Context _context, List<String> listDataHeader,
                                              HashMap<String, ArrayList<FinalArrayAccountFeesModel>> listDataChild) {
        this._context = _context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ListItemFeeStructureDetailBinding itembinding;
        ArrayList<FinalArrayAccountFeesModel> detail = getChild(groupPosition, 0);
        if (convertView == null) {

        }
            itembinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_fee_structure_detail, parent, false);
            convertView = itembinding.getRoot();
            itembinding.admissionFeesTerm1Txt.setText("₹" + " " + detail.get(childPosition).getTerm1AdmissionFee());
            itembinding.cautionFeesTerm1Txt.setText("₹" + " " + detail.get(childPosition).getTerm1CautionMoney());
            itembinding.tutionfeesTerm1Txt.setText("₹" + " " + detail.get(childPosition).getTerm1TuitionFee());
            itembinding.imprestTerm1Txt.setText("₹" + " " + detail.get(childPosition).getTerm1Imprest());
            itembinding.tutionfeesTerm2Txt.setText("₹" + " " + detail.get(childPosition).getTerm2TuitionFee());


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public ArrayList<FinalArrayAccountFeesModel> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ListGroupFeesStructureDetailBinding groupbinding;
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {

        }
        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_fees_structure_detail, parent, false);
        convertView = groupbinding.getRoot();

        groupbinding.standardNameTxt.setText(headerTitle);
        if (isExpanded) {
            groupbinding.arrowImg.setImageResource(R.drawable.arrow_1_42_down);
        } else {
            groupbinding.arrowImg.setImageResource(R.drawable.arrow_1_42);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

