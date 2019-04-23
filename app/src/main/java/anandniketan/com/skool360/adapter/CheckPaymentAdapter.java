package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Model.Account.FinalArrayAccountFeesModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.databinding.ListGroupCheckPaymentBinding;
import anandniketan.com.skool360.databinding.ListItemCheckPaymentBinding;

/**
 * Created by admsandroid on 2/2/2018.
 */

public class CheckPaymentAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<FinalArrayAccountFeesModel>> _listDataChild;

    public CheckPaymentAdapter(Context _context, List<String> listDataHeader,
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
        ListItemCheckPaymentBinding itembinding;
        ArrayList<FinalArrayAccountFeesModel> detail = getChild(groupPosition, 0);
        if (convertView == null) {

        }
        itembinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_check_payment, parent, false);
        convertView = itembinding.getRoot();

        Glide.with(_context)
                .load(AppConfiguration.BASEURL_ICONS + "CanteenIcon.png")
                .into(itembinding.imgBulletPayment);

        itembinding.receipeNoValueTxt.setText("₹" + " " + detail.get(childPosition).getReceiptNo());
        itembinding.termTxt.setText("₹" + " " + detail.get(childPosition).getTerm());
        itembinding.admissionFeeValueTxt.setText("₹" + " " + detail.get(childPosition).getAdmissionFee());
        itembinding.tutionFeeValueTxt.setText("₹" + " " + detail.get(childPosition).getTutionFee());
        itembinding.transportFeeValueTxt.setText("₹" + " " + detail.get(childPosition).getTransportFee());
        itembinding.imprestValueTxt.setText("₹" + " " + detail.get(childPosition).getImprestFee());
        itembinding.lateFeesValueTxt.setText("₹" + " " + detail.get(childPosition).getLateFees());
        itembinding.waiveOffValueTxt.setText("₹" + " " + detail.get(childPosition).getDiscountFee());
        itembinding.previousOutstandingValueTxt.setText("₹" + " " + detail.get(childPosition).getPreviousFees());
        itembinding.totalPaidFeeValueTxt.setText("₹" + " " + detail.get(childPosition).getPaidFee());
        itembinding.currentOutstandingValueTxt.setText("₹" + " " + detail.get(childPosition).getCurrentOutstandingFees());

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
        ListGroupCheckPaymentBinding groupbinding;
        String headerTitle = (String) getGroup(groupPosition);
        String[] spiltvalue = headerTitle.split("\\|");
        if (convertView == null) {

        }
        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_check_payment, parent, false);
        convertView = groupbinding.getRoot();

        groupbinding.grnnoTxt.setText(spiltvalue[0]);
        groupbinding.dateTxt.setText(spiltvalue[1]);
        groupbinding.statusTxt.setText(spiltvalue[2]);
        groupbinding.amountTxt.setText("₹" + " " + spiltvalue[3]);
        groupbinding.chequeNoTxt.setText(spiltvalue[4]);

        if (isExpanded) {
            groupbinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.present));
        } else {
            groupbinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.absent));
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


