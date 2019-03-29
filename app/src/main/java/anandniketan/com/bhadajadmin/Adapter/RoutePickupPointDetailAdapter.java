package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Transport.PickupPointDetailModel;
import anandniketan.com.bhadajadmin.R;


/**
 * Created by admsandroid on 11/23/2017.
 */

public class RoutePickupPointDetailAdapter extends RecyclerView.Adapter<RoutePickupPointDetailAdapter.MyViewHolder> {
    private Context context;
    List<PickupPointDetailModel> array;
//   ListItemRouteDetailsBinding binding;

    public RoutePickupPointDetailAdapter(Context mContext, List<PickupPointDetailModel> array) {
        this.context = mContext;
        this.array = array;
    }


    @Override
    public RoutePickupPointDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_route_details, parent, false);


//        View itemView = binding.getRoot();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_route_details, parent, false);
        return new RoutePickupPointDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoutePickupPointDetailAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
        holder.index_txt.setText(sr);
        holder.pickup_point_txt.setText(array.get(position).getPickupPoint());
        holder.status_txt.setText(array.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView index_txt, pickup_point_txt, status_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            index_txt=(TextView)itemView.findViewById(R.id.index_txt);
                    pickup_point_txt=(TextView)itemView.findViewById(R.id.pickup_point_txt);
            status_txt=(TextView)itemView.findViewById(R.id.status_txt);

        }
    }

}

