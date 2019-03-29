package anandniketan.com.bhadajadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayTransportChargesModel;
import anandniketan.com.bhadajadmin.R;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class VehicleRouteDetailListAdapter  extends RecyclerView.Adapter<VehicleRouteDetailListAdapter.MyViewHolder> {
    private Context context;
    List<FinalArrayTransportChargesModel> vehicleRouteModelList;
//    VehicleRouteDetailItemBinding binding;

    public VehicleRouteDetailListAdapter(Context mContext, List<FinalArrayTransportChargesModel> vehicleRouteModelList) {
        this.context = mContext;
        this.vehicleRouteModelList = vehicleRouteModelList;
    }


    @Override
    public VehicleRouteDetailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.vehicle_route_detail_item, parent, false);


//        View itemView = binding.getRoot();
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_route_detail_item, parent, false);
        return new VehicleRouteDetailListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VehicleRouteDetailListAdapter.MyViewHolder holder, int position) {
        String sr = String.valueOf(position + 1);
//        binding.indexTxt.setText(sr);
//        binding.vehiclenameTxt.setText(vehicleRouteModelList.get(position).getVehicle());
//        binding.routenameTxt.setText(vehicleRouteModelList.get(position).getRouteNm());

        holder.index_txt.setText(sr);
        holder.vehiclename_txt.setText(vehicleRouteModelList.get(position).getVehicle());
        holder.routename_txt.setText(vehicleRouteModelList.get(position).getRouteNm());
    }

    @Override
    public int getItemCount() {
        return vehicleRouteModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView index_txt,vehiclename_txt,routename_txt;
        public MyViewHolder(View itemView) {
            super(itemView);

            index_txt=(TextView)itemView.findViewById(R.id.index_txt);
            vehiclename_txt=(TextView)itemView.findViewById(R.id.vehiclename_txt);
            routename_txt=(TextView)itemView.findViewById(R.id.routename_txt);
        }
    }

}

