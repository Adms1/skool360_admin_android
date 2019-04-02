package anandniketan.com.skool360.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import anandniketan.com.skool360.Activity.DashboardActivity;
import anandniketan.com.skool360.Fragment.Fragment.LeaveRequestFragment;
import anandniketan.com.skool360.Fragment.Fragment.SuggestionFragment;
import anandniketan.com.skool360.Model.Notification.NotificationModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.DialogUtils;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import retrofit2.Call;
import retrofit2.Callback;

// Antra 19/02/2019

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewholder> {

    private Context context;
    private ArrayList<NotificationModel.FinalArray> finalArrays;

    public NotificationAdapter(Context context, ArrayList<NotificationModel.FinalArray> finalArrays) {
        this.context = context;
        this.finalArrays = finalArrays;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item_list, viewGroup, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, final int i) {

        viewholder.title.setText(finalArrays.get(i).getName());
        viewholder.type.setText("(" + finalArrays.get(i).getType() + ")");
        viewholder.discription.setText(finalArrays.get(i).getDepartment());

        if (finalArrays.get(i).getFlag().equalsIgnoreCase("true")) {
            viewholder.llMain.setBackground(context.getResources().getDrawable(R.drawable.greybg_border, null));
        } else {
            viewholder.llMain.setBackground(context.getResources().getDrawable(R.drawable.linear_light_border, null));
        }

        if (finalArrays.get(i).getNotificationtype().equalsIgnoreCase("StudentLeave") || finalArrays.get(i).getNotificationtype()
                .equalsIgnoreCase("StaffLeave")) {
            Picasso.get().load(AppConfiguration.BASEURL_ICONS + "leave.png")
                    .resize(100, 100).into(viewholder.notitype);
        } else {
            Picasso.get().load(AppConfiguration.BASEURL_ICONS + finalArrays.get(i).getNotificationtype() + ".png")
                    .resize(100, 100).into(viewholder.notitype);
        }

        viewholder.notitype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(finalArrays.get(i).getFlag().equalsIgnoreCase("true")) {
                switch (finalArrays.get(i).getNotificationtype()) {
                    case "BirthdayWish":

                        DialogUtils.createConfirmDialog(context, R.string.birthday_wishes, R.string.ask_to_wish,
                                "Yes", "No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        callBirthdaywish(i);
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                }).show();

                        break;

                    case "StudentLeave":

                        Fragment fragment = new LeaveRequestFragment();
                        Bundle bundle = new Bundle();

                        bundle.putString("type", finalArrays.get(i).getType());
                        bundle.putString("ntype", "notification");
                        bundle.putString("sdate", finalArrays.get(i).getDate());
                        bundle.putString("stuid", finalArrays.get(i).getPkid());

//                    bundle.putString("requeststatus", requeststatus);
//                    bundle.putString("requestupdatestatus", requestupdatestatus);
//                    bundle.putString("requestdeletestatus", requestdeletestatus);

                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = ((DashboardActivity) context).getSupportFragmentManager();
                        if (fragmentManager != null) {
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                    .replace(R.id.frame_container, fragment).commit();
                        }
//                            AppConfiguration.firsttimeback = true;
//                            AppConfiguration.position = 52;

                        break;

                    case "Suggestion":
                        Fragment fragment1 = new SuggestionFragment();

                        Bundle bundle1 = new Bundle();

                        bundle1.putString("type", finalArrays.get(i).getType());
                        bundle1.putString("ntype", "notification");
                        bundle1.putString("sdate", finalArrays.get(i).getDate());
                        bundle1.putString("stuid", finalArrays.get(i).getPkid());
////                            bundle1.putString("leavedeletestatus", permissionMap.get("Suggestion").getIsuserdelete());
////                            bundle1.putString("leaveupdatestatus", permissionMap.get("Suggestion").getIsuserupdate());
////                            bundle1.putString("leaveviewstatus", permissionMap.get("Suggestion").getIsuserview());
//
                        fragment1.setArguments(bundle1);
//
                        FragmentManager fragmentManager1 = ((DashboardActivity) context).getSupportFragmentManager();
                        if (fragmentManager1 != null) {
                            fragmentManager1.beginTransaction()
                                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                    .replace(R.id.frame_container, fragment1).commit();
                        }
//                            AppConfiguration.firsttimeback = true;
//                            AppConfiguration.position = 11;

                        break;
                }
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return finalArrays.size();
    }

    private void callBirthdaywish(int i) {
        Utils.showDialog(context);

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<JsonObject> call = apiService.getBirthdaywish(PrefUtils.getInstance(context).getStringValue("StaffID", "0"), finalArrays.get(i).getType(), finalArrays.get(i).getId(), PrefUtils.getInstance(context).getStringValue("LocationID", "0"));
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull final retrofit2.Response<JsonObject> response) {
                Utils.dismissDialog();
                if (response.body() != null) {

                    if (response.body().get("Success").getAsString().equalsIgnoreCase("false")) {

                        return;
                    }

                    if (response.body().get("Success").getAsString().equalsIgnoreCase("True")) {
                        Utils.ping(context, "success");
                        notifyDataSetChanged();

                    }
                } else {
                    Utils.dismissDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                // Log error here since request failed
                Utils.dismissDialog();

                Log.e("bithdaywishhh", t.toString());
            }
        });
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView title, discription, type;
        ImageView user, notitype;
        RelativeLayout llMain;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            llMain = itemView.findViewById(R.id.noti_llMain);
            title = itemView.findViewById(R.id.noti_name);
            discription = itemView.findViewById(R.id.noti_department);
            user = itemView.findViewById(R.id.noti_user);
            type = itemView.findViewById(R.id.noti_type);
            notitype = itemView.findViewById(R.id.noti_notitype);

        }
    }

}
