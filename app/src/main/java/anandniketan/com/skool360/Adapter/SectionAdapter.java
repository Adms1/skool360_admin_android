package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.List;

import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Model.Account.SectionDetailModel;
import anandniketan.com.skool360.R;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.viewholder> {

    private Context context;
    private OnEditRecordWithPosition onEditRecordWithPosition;
    private List<SectionDetailModel> arrayList;
    private RadioButton selected = null;

    public SectionAdapter(Context context, OnEditRecordWithPosition onEditRecordWithPosition, List<SectionDetailModel> arrayList) {

        this.context = context;
        this.onEditRecordWithPosition = onEditRecordWithPosition;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public SectionAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_checkbox, viewGroup, false);

        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SectionAdapter.viewholder viewholder, final int i) {
        viewholder.radioButton.setText(arrayList.get(i).getSection());

        //by default last radio button selected
//        if (i == arrayList.size() - 1) {
        if (selected == null) {
            viewholder.radioButton.setChecked(true);
            selected = viewholder.radioButton;
        }
//        }

        viewholder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected != null) {

                    selected.setChecked(false);

                }
                selected = viewholder.radioButton;

                if (viewholder.radioButton.isChecked()) {

                    arrayList.get(i).setCheckstatus("1");
                    onEditRecordWithPosition.getEditpermission(i);

                } else {
                    arrayList.get(i).setCheckstatus("0");
                }

            }
        });

        if (arrayList.get(i).getCheckstatus().equalsIgnoreCase("1")) {
            viewholder.radioButton.setChecked(true);
        } else {
            viewholder.radioButton.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        RadioButton radioButton;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.radio_button);

        }
    }
}
