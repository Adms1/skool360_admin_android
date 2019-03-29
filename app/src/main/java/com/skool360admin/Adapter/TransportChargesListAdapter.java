package com.skool360admin.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import anandniketan.com.bhadajadmin.Model.Transport.FinalArrayTransportChargesModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.databinding.TransportChargesListBinding;


/**
 * Created by admsandroid on 11/23/2017.
 */

public class TransportChargesListAdapter extends BaseAdapter {
    private Context mContext;
    private List<FinalArrayTransportChargesModel> transportChargesModelsList;
    TransportChargesListBinding binding;

    public TransportChargesListAdapter(Context mContext, List<FinalArrayTransportChargesModel> transportChargesModelsList) {
        this.mContext = mContext;
        this.transportChargesModelsList = transportChargesModelsList;
    }


    private class ViewHolder {

    }

    @Override
    public int getCount() {
        return transportChargesModelsList.size();
    }

    @Override
    public Object getItem(int position) {
        return transportChargesModelsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = null;
        convertView = null;
        if (convertView == null) {

        }

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.transport_charges_list, parent, false);
        convertView = binding.getRoot();

        try {
            binding.kmTxt.setText(transportChargesModelsList.get(position).getKm());
            binding.term1Txt.setText("₹" + " " + String.valueOf(transportChargesModelsList.get(position).getTerm1()));
            binding.term2Txt.setText("₹" + " " + String.valueOf(transportChargesModelsList.get(position).getTerm2()));

        } catch (Exception e) {
            e.printStackTrace();

        }
        return convertView;
    }


}


