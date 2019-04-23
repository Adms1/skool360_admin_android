package anandniketan.com.skool360.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import anandniketan.com.skool360.R;

public class InsertPhotoAdapter extends RecyclerView.Adapter<InsertPhotoAdapter.MyViewHolder> {

    ArrayList<Uri> mArrayUri;
    private Context ctx;
    private int pos;
    private LayoutInflater inflater;
    private ImageView ivGallery;

    public InsertPhotoAdapter(Context ctx, ArrayList<Uri> mArrayUri) {

        this.ctx = ctx;
        this.mArrayUri = mArrayUri;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.image_iv.setImageURI(mArrayUri.get(position));

        holder.delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (status.equalsIgnoreCase("true")) {

                removeAt(position);
//                    onDeleteWithIdRef.deleteRecordWithId(String.valueOf(dataList.get(position).getImagepath()));
//                } else {
//                    Utils.ping(context, "Access Denied");
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayUri.size();
    }

    private void removeAt(int position) {
        mArrayUri.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mArrayUri.size());
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
