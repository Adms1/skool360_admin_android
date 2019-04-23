package anandniketan.com.skool360.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.skool360.Model.MIS.HeadwiseStudent;
import anandniketan.com.skool360.Model.MIS.MISHeadwiseFee;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.SpacesItemDecoration;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

public class HeadwiseFeesAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    private ArrayList<MISHeadwiseFee.FinalArray> finalArrayGetFeeModels;
    private Fragment fragment = null;
    private Bundle bundle;
    private FragmentManager fragmentManager = null;
    private String termid;
    private HeaderwiseStudentDetailAdapter misFinanceReportAdapter;
    private ArrayList<HeadwiseStudent.Finalarray> financedataValues;

    public HeadwiseFeesAdapter(Context context, ArrayList<MISHeadwiseFee.FinalArray> finalArrayGetFeeModels, String termid) {
        this.context = context;
        this.finalArrayGetFeeModels = finalArrayGetFeeModels;
        this.termid = termid;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return finalArrayGetFeeModels.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        View itemView = convertView;
        if (convertView == null)

            holder = new ViewHolder();

        itemView = inflater.inflate(R.layout.headwise_list_item, null);

        holder.classtxt = itemView.findViewById(R.id.head_item_class_txt);
        holder.stndrdtxt = itemView.findViewById(R.id.head_item_stndrd_txt);
        holder.phonetxt = itemView.findViewById(R.id.head_item_phone_txt);
        holder.ivview = itemView.findViewById(R.id.head_item_view_txt);
        holder.rvList = itemView.findViewById(R.id.headwisestudent_rv_finance_list);
        holder.ll = itemView.findViewById(R.id.headwisestudent_linear_finance_recyler_grid);


        holder.classtxt.setText(finalArrayGetFeeModels.get(position).getStandard() + "-" + finalArrayGetFeeModels.get(position).getClassName());
        holder.stndrdtxt.setText(finalArrayGetFeeModels.get(position).getStudentName());
        holder.phonetxt.setText(finalArrayGetFeeModels.get(position).getMobileNo());

        holder.rvList.setNestedScrollingEnabled(false);

        final ViewHolder finalHolder = holder;
        final ViewHolder finalHolder1 = holder;
        final ViewHolder finalHolder2 = holder;
        holder.ivview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                notifyDataSetChanged();

                if (finalHolder.ll.getVisibility() != View.VISIBLE) {

                    finalHolder.ll.setVisibility(View.VISIBLE);

                    finalHolder.ivview.setImageResource(R.drawable.arrow_1_42_down);

                    if (!Utils.checkNetwork(context)) {
                        Utils.showCustomDialog(context.getResources().getString(R.string.internet_error), context.getResources().getString(R.string.internet_connection_error), (Activity) context);
                        return;
                    }

                    WebServices apiService =
                            ApiClient.getClient().create(WebServices.class);
                    Call<HeadwiseStudent> call = apiService.getHeadWiseFeesCollectionStudent(AppConfiguration.BASEURL +  "GetHeadDetailByStudentID?StudentID=" + finalArrayGetFeeModels.get(position).getStudentID() + "&Term=" + termid + "&LocationID=" + PrefUtils.getInstance(context).getStringValue("LocationID", "0"));

                    call.enqueue(new Callback<HeadwiseStudent>() {

                        @Override
                        public void onResponse(Call<HeadwiseStudent> call, retrofit2.Response<HeadwiseStudent> response) {
//                progressBar.setVisibility(View.GONE);
                            HeadwiseStudent staffSMSDataModel = response.body();
                            if (staffSMSDataModel == null) {
                                Utils.ping(context, context.getString(R.string.something_wrong));
                                return;
                            }
                            if (staffSMSDataModel.getSuccess() == null) {
                                Utils.ping(context, context.getString(R.string.something_wrong));
                                return;
                            }
                            if (staffSMSDataModel.getSuccess().equalsIgnoreCase("false")) {
                                Utils.ping(context, context.getString(R.string.false_msg));

                                return;
                            }
                            if (staffSMSDataModel.getSuccess().equalsIgnoreCase("True")) {

                                try {

                                    financedataValues = staffSMSDataModel.getFinalArray();

                                    misFinanceReportAdapter = new HeaderwiseStudentDetailAdapter(context, financedataValues);
                                    finalHolder1.rvList.setLayoutManager(new GridLayoutManager(context, financedataValues.size(), OrientationHelper.HORIZONTAL, false));
                                    finalHolder1.rvList.addItemDecoration(new SpacesItemDecoration(0));
                                    finalHolder1.rvList.setAdapter(misFinanceReportAdapter);

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<HeadwiseStudent> call, Throwable t) {
                            t.getMessage();
                        }
                    });

                } else {
                    finalHolder2.ll.setVisibility(View.GONE);

                    finalHolder2.ivview.setImageResource(R.drawable.arrow_1_42);
                }
            }
        });

        return itemView;
    }

    class ViewHolder {
        TextView classtxt, stndrdtxt, phonetxt;
        ImageView ivview;
        LinearLayout ll;
        RecyclerView rvList;

    }
}