package anandniketan.com.skool360.adapter.Student;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Model.Student.GalleryDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<GalleryDataModel.GalleryFinalArray> dataList;
    private OnEditRecordWithPosition onUpdateRecordRef;
    private String editstatus, deletestatus;

    public GalleryAdapter(Context mContext, ArrayList<GalleryDataModel.GalleryFinalArray> dataList, String editstatus, String deletestatus, OnEditRecordWithPosition onUpdateRecordRef) {

        this.context = mContext;
        this.dataList = dataList;
        this.onUpdateRecordRef = onUpdateRecordRef;
        this.editstatus = editstatus;
        this.deletestatus = deletestatus;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//        final StudentAttendanceFinalArray filter = filteredDataModel.getFinalArray().get(position);
        holder.title_txt.setText(dataList.get(position).getTitle());
        holder.comment_txt.setText(dataList.get(position).getComment());
        holder.date_txt.setText(dataList.get(position).getDate());

        holder.delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deletestatus.equalsIgnoreCase("true")) {
                    callDeleteGalleryDataApi(position, dataList.get(position).getGalleryid());


                } else {
                    Utils.ping(context, "Access Denied");
                }
            }
        });

        holder.edit_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editstatus.equalsIgnoreCase("true")) {

                    onUpdateRecordRef.getEditpermission(position);
                } else {
                    Utils.ping(context, "Access Denied");
                }
            }
        });

    }

    private void removeAt(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataList.size());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void callDeleteGalleryDataApi(final int pos, String galleryid) {
        Utils.showDialog(context);
        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<JsonObject> call = apiService.deleteGalleryData(AppConfiguration.BASEURL + "DeleteGallery?GalleryID=" + galleryid + "&LocationID=" + PrefUtils.getInstance(context).getStringValue("LocationID", "0"));
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull final retrofit2.Response<JsonObject> response) {
                Utils.dismissDialog();
                if (response.body() != null) {

                    if (response.body().get("Success").getAsString().equalsIgnoreCase("false")) {

                        return;
                    }

                    if (response.body().get("Success").getAsString().equalsIgnoreCase("True")) {

                        removeAt(pos);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                // Log error here since request failed
                Utils.dismissDialog();
                Log.e("permissionnnnn", t.toString());
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title_txt, comment_txt, date_txt;
        ImageView edit_iv, delete_iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.title_txt);
            comment_txt = itemView.findViewById(R.id.comment_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            edit_iv = itemView.findViewById(R.id.edit_iv);
            delete_iv = itemView.findViewById(R.id.delete_iv);

        }
    }

}
