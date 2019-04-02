package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360.Model.Account.FinalArrayAccountFeesModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.databinding.ListGroupFeesStructureDetailBinding;
import anandniketan.com.skool360.databinding.ListItemAccountSummaryNewBinding;


/**
 * Created by admsandroid on 11/27/2017.
 */

public class ExpandableListAdapterAccountSummary extends BaseExpandableListAdapter {

    String headerTitle;
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<FinalArrayAccountFeesModel>> _listDataChild;

    public ExpandableListAdapterAccountSummary(Context _context, List<String> listDataHeader,
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
        ListItemAccountSummaryNewBinding itembinding;
        ArrayList<FinalArrayAccountFeesModel> detail = getChild(groupPosition, 0);
        if (convertView == null) {

        }
        itembinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_account_summary_new, parent, false);
        convertView = itembinding.getRoot();
        if (headerTitle.equalsIgnoreCase("Admission Fees")) {
            itembinding.admissionFeesTxtView.setText("Admission Fees");
            itembinding.admissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getAdmissionFees());
            itembinding.receiptAdmissionFeesTxtView.setText("Admission Fees Receipt");
            itembinding.receiptAdmissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getReceiptAdmissionFees());
            itembinding.remainingFeesTxtView.setText("Remaining Admission Fees");
            itembinding.remainingFeesTxt.setText("₹" + " " + detail.get(childPosition).getRemainingAdmissionFees());
        } else if (headerTitle.equalsIgnoreCase("Tution Fees")) {
            itembinding.admissionFeesTxtView.setText("Tution Fees");
            itembinding.admissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getTutionFees());
            itembinding.receiptAdmissionFeesTxtView.setText("Tution Fees Receipt");
            itembinding.receiptAdmissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getReceiptTutionFees());
            itembinding.remainingFeesTxtView.setText("Remaining Tution Fees");
            itembinding.remainingFeesTxt.setText("₹" + " " + detail.get(childPosition).getRemainingTutionFees());
        } else if (headerTitle.equalsIgnoreCase("Caution Fees")) {
            itembinding.admissionFeesTxtView.setText("Caution Fees");
            itembinding.admissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getCautionFees());
            itembinding.receiptAdmissionFeesTxtView.setText("Caution Fees Receipt");
            itembinding.receiptAdmissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getReceiptCautionFees());
            itembinding.remainingFeesTxtView.setText("Remaining Caution Fees");
            itembinding.remainingFeesTxt.setText("₹" + " " + detail.get(childPosition).getRemainingCautionFees());
        } else if (headerTitle.equalsIgnoreCase("Transport Fees")) {
            itembinding.admissionFeesTxtView.setText("Transport Fees");
            itembinding.admissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getTransportFees());
            itembinding.receiptAdmissionFeesTxtView.setText("Transport Fees Receipt");
            itembinding.receiptAdmissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getReceiptTransportFees());
            itembinding.remainingFeesTxtView.setText("Remaining Transport Fees");
            itembinding.remainingFeesTxt.setText("₹" + " " + detail.get(childPosition).getRemainingTransportFees());
        } else if (headerTitle.equalsIgnoreCase("Imprest Fees")) {
            itembinding.admissionFeesTxtView.setText("Imprest Fees");
            itembinding.admissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getImprest());
            itembinding.receiptAdmissionFeesTxtView.setText("Imprest Fees Receipt");
            itembinding.receiptAdmissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getReceiptImprest());
            itembinding.remainingFeesTxtView.setText("Remaining Imprest Fees");
            itembinding.remainingFeesTxt.setText("₹" + " " + detail.get(childPosition).getRemainingImprest());
        } else if (headerTitle.equalsIgnoreCase("Late Fees")) {
            itembinding.admissionFeesTxtView.setText("Late Fees");
            itembinding.admissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getLateFees());
            itembinding.receiptAdmissionFeesTxtView.setText("Late Fees Receipt");
            itembinding.receiptAdmissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getReceiptLateFees());
            itembinding.remainingFeesTxtView.setText("Remaining Late Fees");
            itembinding.remainingFeesTxt.setText("₹" + " " + detail.get(childPosition).getRemainingLateFees());
        } else if (headerTitle.equalsIgnoreCase("Discount Fees")) {
            itembinding.admissionFeesTxtView.setText("Discount Fees");
            itembinding.admissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getDiscount());
            itembinding.receiptAdmissionFeesTxtView.setText("Discount Fees Receipt");
            itembinding.receiptAdmissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getReceiptDiscount());
            itembinding.remainingFeesTxtView.setText("Remaining Discount Fees");
            itembinding.remainingFeesTxt.setText("₹" + " " + detail.get(childPosition).getRemainingDiscount());
        } else if (headerTitle.equalsIgnoreCase("Fees")) {
            itembinding.admissionFeesTxtView.setText("Term Fees");
            itembinding.admissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getTotalTermFees());
            itembinding.receiptAdmissionFeesTxtView.setText("Term Fees Receipt");
            itembinding.receiptAdmissionFeesTxt.setText("₹" + " " + detail.get(childPosition).getReceiptTermFees());
            itembinding.remainingFeesTxtView.setText("Remaining Term Fees");
            itembinding.remainingFeesTxt.setText("₹" + " " + detail.get(childPosition).getRemainingTermFees());
        }
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
        headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {

        }
        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_fees_structure_detail, parent, false);
        convertView = groupbinding.getRoot();

        groupbinding.standardNameTxt.setText(headerTitle);
        groupbinding.standardNameTxt.setTextColor(_context.getResources().getColor(R.color.scheduleheadertextcolor));
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

