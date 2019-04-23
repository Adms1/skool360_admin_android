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

import anandniketan.com.skool360.Model.Account.AccountFeesCollectionModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.databinding.ListGroupReceiptDetailBinding;
import anandniketan.com.skool360.databinding.ListItemReceiptBinding;


/**
 * Created by admsandroid on 11/27/2017.
 */

public class ExpandableListAdapterReceipt extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<AccountFeesCollectionModel>> _listDataChild;

    public ExpandableListAdapterReceipt(Context _context, List<String> listDataHeader,
                                        HashMap<String, ArrayList<AccountFeesCollectionModel>> listDataChild) {
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
        ListItemReceiptBinding itembinding;
        ArrayList<AccountFeesCollectionModel> detail = getChild(groupPosition, 0);
        if (convertView == null) {

        }
        itembinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_receipt, parent, false);
        convertView = itembinding.getRoot();

        Glide.with(_context)
                .load(AppConfiguration.BASEURL_ICONS + "CanteenIcon.png")
                .into(itembinding.imgBulletPayment);
        itembinding.receipeNoValueTxt.setText("₹" + " " + detail.get(childPosition).getReceiptNo());
        itembinding.modeOfPaymentValueTxt.setText("₹" + " " + detail.get(childPosition).getPayMode());
        itembinding.admissionFeeValueTxt.setText("₹" + " " + detail.get(childPosition).getAdmissionFee());
        itembinding.tutionFeeValueTxt.setText("₹" + " " + detail.get(childPosition).getTuitionFee());
        itembinding.transportFeeValueTxt.setText("₹" + " " + detail.get(childPosition).getTransport());
        itembinding.imprestValueTxt.setText("₹" + " " + detail.get(childPosition).getImprestFee());
        itembinding.lateFeesValueTxt.setText("₹" + " " + detail.get(childPosition).getLatesFee());
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
    public ArrayList<AccountFeesCollectionModel> getChild(int groupPosition, int childPosititon) {
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
        ListGroupReceiptDetailBinding groupbinding;
        String headerTitle = (String) getGroup(groupPosition);
        String[] spiltvalue = headerTitle.split("\\|");
        if (convertView == null) {

        }
        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_receipt_detail, parent, false);
        convertView = groupbinding.getRoot();

        groupbinding.dateTxt.setText(spiltvalue[0]);
        groupbinding.amountTxt.setText("₹" + " " + spiltvalue[1]);
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

