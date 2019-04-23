package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Model.Student.GalleryDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.AppConfiguration;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<GalleryDataModel.photosFinalArray> dataList;
    private OnEditRecordWithPosition onViewClick;

    public PhotoAdapter(Context mContext, ArrayList<GalleryDataModel.photosFinalArray> dataList, OnEditRecordWithPosition onViewClick) {
        this.context = mContext;
        this.dataList = dataList;
        this.onViewClick = onViewClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        if (dataList.get(position).getDetailid().equalsIgnoreCase("1")) {
            Log.d("imagename11111", "" + Uri.parse(dataList.get(position).getImagepath()));

            holder.image_iv.setImageURI(Uri.parse(dataList.get(position).getImagepath()));
        } else {

            String newurl = dataList.get(position).getImagepath().substring(1).replaceAll("(?<!http:)//", "/");
            Log.d("imagename", AppConfiguration.LIVE_BASE_URL + dataList.get(position).getImagepath().substring(1).replaceAll("(?<!http:)//", "/"));
            Picasso.get().load(AppConfiguration.LIVE_BASE_URL + newurl).into(holder.image_iv);

//            Glide.with(context)
//                    .load(AppConfiguration.LIVE_BASE_URL + dataList.get(position).getImagepath()).preload()
//                    .dontAnimate()
//                    .into(holder.image_iv);
        }

        holder.delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
                onViewClick.getEditpermission(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_iv;
        Button delete_iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            image_iv = itemView.findViewById(R.id.image_iv);
            delete_iv = itemView.findViewById(R.id.delete_iv);

        }
    }

}

