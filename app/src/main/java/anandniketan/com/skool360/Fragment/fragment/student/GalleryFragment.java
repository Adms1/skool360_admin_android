package anandniketan.com.skool360.Fragment.fragment.student;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import anandniketan.com.skool360.Interface.OnEditRecordWithPosition;
import anandniketan.com.skool360.Model.Student.GalleryDataModel;
import anandniketan.com.skool360.R;
import anandniketan.com.skool360.Utility.ApiClient;
import anandniketan.com.skool360.Utility.ApiHandler;
import anandniketan.com.skool360.Utility.AppConfiguration;
import anandniketan.com.skool360.Utility.PathUtil;
import anandniketan.com.skool360.Utility.PrefUtils;
import anandniketan.com.skool360.Utility.Utils;
import anandniketan.com.skool360.Utility.WebServices;
import anandniketan.com.skool360.activity.DashboardActivity;
import anandniketan.com.skool360.adapter.PhotoAdapter;
import anandniketan.com.skool360.adapter.Student.GalleryAdapter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

public class GalleryFragment extends Fragment implements OnEditRecordWithPosition, DatePickerDialog.OnDateSetListener {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static String dateFinal;
    List<String> imagesEncodedList;
    String imageEncoded, viewstatus, deletestatus, editstatus, galid;
    ArrayList<String> mArrayUri = new ArrayList<>();
    Uri mImageUri;
    ArrayList<GalleryDataModel.photosFinalArray> photoArray = new ArrayList<>();
    private RecyclerView rvList, rvPhotoList;
    private GalleryAdapter galleryAdapter;
    private PhotoAdapter photoAdapter;
    private TextView noRecords, tvTitle, tvComment;
    private LinearLayout llHeader;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    private TextView tvHeader, tvDate;
    private Button btnBack, btnMenu;
    private Calendar calendar;
    private int Year, Month, Day;
    private DatePickerDialog datePickerDialog;
    private OnEditRecordWithPosition onUpdateRecordRef;
    private boolean isRecordInUpdate = false;
    private Button btnAdd, btnPhoto, btnCancel;
    private ArrayList<GalleryDataModel.GalleryFinalArray> galleryArr;
    private ArrayList<GalleryDataModel.photosFinalArray> photoArr;
    //    private InsertPhotoAdapter insertPhotoAdapter;
    private String pdfFilePath = "";
    private ArrayList<String> imgArray;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onUpdateRecordRef = this;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            viewstatus = bundle.getString("galleryviewstatus");
            deletestatus = bundle.getString("gallerydeletestatus");
            editstatus = bundle.getString("galleryupdatestatus");
        }

        rvList = view.findViewById(R.id.gallery_list);
        noRecords = view.findViewById(R.id.txtNoRecords);
        llHeader = view.findViewById(R.id.list_header);
        rvPhotoList = view.findViewById(R.id.photo_list);
        btnAdd = view.findViewById(R.id.gallery_search_btn);
        btnCancel = view.findViewById(R.id.gallery_cancel_btn);

        tvTitle = view.findViewById(R.id.gallery_title_txt);
        tvComment = view.findViewById(R.id.gellery_comment_txt);
        btnPhoto = view.findViewById(R.id.gallery_photo_btn);

        tvHeader = view.findViewById(R.id.textView3);
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnmenu);
        tvDate = view.findViewById(R.id.gellery_date_txt);

        tvHeader.setText(R.string.gallery);
        rvPhotoList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        setListners();
        callGalleryData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(getActivity(), "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public void setListners() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.onLeft();
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
                    // do your stuff..

                    Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(gallery, 111);

//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 111);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragment = new StudentFragment();
                fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.frame_container, fragment).commit();
                }
            }
        });

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        if (TextUtils.isEmpty(AppConfiguration.fromDate) && TextUtils.isEmpty(AppConfiguration.toDate)) {
            AppConfiguration.fromDate = Utils.getTodaysDate();
        }

        tvDate.setText(AppConfiguration.fromDate);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(GalleryFragment.this, Year, Month, Day);
                datePickerDialog.setMaxDate(calendar);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePickerDialog");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvTitle.getText().toString().equalsIgnoreCase("") && !tvComment.getText().toString().equalsIgnoreCase("")) {

                    if (btnAdd.getText().toString().equalsIgnoreCase("Update")) {

                        if (editstatus.equalsIgnoreCase("true")) {

                            callInsertUpdateGalleryDataApi(galid);

                        } else {
                            Utils.ping(getActivity(), "Access Denied");
                        }
                    } else {

                        callInsertUpdateGalleryDataApi("0");

                    }
                } else {
                    Utils.ping(getContext(), "Please Enter all Details");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnAdd.setText(R.string.Add);

                tvTitle.setText("");
                tvDate.setText(Utils.getTodaysDate());
                tvComment.setText("");

                photoArray.clear();
                photoAdapter.notifyDataSetChanged();

                btnCancel.setVisibility(View.GONE);

            }
        });
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = null;

        file = PathUtil.getFile(getActivity(), Uri.parse(fileUri));

        RequestBody requestFile;
        if (file == null) {
            // create RequestBody instance from file
            requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), fileUri.substring(14));

        } else {
            // create RequestBody instance from file
            requestFile =
                    RequestBody.create(
                            MediaType.parse(Objects.requireNonNull(getActivity().getContentResolver().getType(Uri.parse(fileUri)))),
                            file
                    );
        }

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData("multipart/form-data", partName, requestFile);
    }

    private void uploadPdf() {
        //upload file using retrofit 2
        try {

            Utils.showDialog(getActivity());

            List<MultipartBody.Part> parts = new ArrayList<>();

            if (mArrayUri != null && imgArray != null) {
                for (int i = 0; i < mArrayUri.size(); i++) {
                    parts.add(prepareFilePart(imgArray.get(i), mArrayUri.get(i)));
                }
            }

            Call<ResponseBody> call = ApiHandler.getApiServiceForFileUplod().uploadMultipleFile(parts);

            call.enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                    Utils.dismissDialog();
                    if (response.isSuccessful()) {

                        callGalleryData();
                        if (!isRecordInUpdate) {
                            Utils.ping(getActivity(), "Gallery Added");
                        } else {
                            Utils.ping(getActivity(), "Gallery updated successfully");
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Utils.ping(getActivity(), "Please add photo");
                    Utils.dismissDialog();
                }
            });

        } catch (Exception ex) {
            Utils.dismissDialog();
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            // When an Image is picked
            if (requestCode == 111 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<>();
                if (data.getData() != null) {

                    mImageUri = data.getData();

//                    imgpath = new ArrayList<>();
                    // Get the cursor
                    Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                    mArrayUri.add(mImageUri.toString());

                    GalleryDataModel.photosFinalArray photosFinalArray = new GalleryDataModel.photosFinalArray();
                    photosFinalArray.setDetailid("1");
                    photosFinalArray.setImagepath(mImageUri.toString());
                    photoArray.add(photosFinalArray);

//                    if(btnAdd.getText().toString().equalsIgnoreCase("update")){
//                        photoAdapter.notifyDataSetChanged();
//                    }else {
                    photoAdapter = new PhotoAdapter(getActivity(), photoArray, new OnEditRecordWithPosition() {
                        @Override
                        public void getEditpermission(int pos) {
                            mArrayUri.remove(pos);
                            photoAdapter.notifyDataSetChanged();
                        }
                    });
                    rvPhotoList.setAdapter(photoAdapter);
//                    }
//                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
//                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
//                            .getLayoutParams();
//                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri.toString());
                            // Get the cursor
                            Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            GalleryDataModel.photosFinalArray photosFinalArray = new GalleryDataModel.photosFinalArray();
                            photosFinalArray.setDetailid("1");
                            photosFinalArray.setImagepath(mArrayUri.toString());
                            photoArray.add(photosFinalArray);

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

//                            if(btnAdd.getText().toString().equalsIgnoreCase("update")){
                            photoAdapter = new PhotoAdapter(getActivity(), photoArray, new OnEditRecordWithPosition() {
                                @Override
                                public void getEditpermission(int pos) {
                                    mArrayUri.remove(pos);
                                    photoAdapter.notifyDataSetChanged();
                                }
                            });
                            photoAdapter.notifyDataSetChanged();
//                            }else {
//                                photoAdapter = new PhotoAdapter(getActivity(), imgpath);
//                                rvPhotoList.setAdapter(photoAdapter);
//                            }
//                            rvList.setVerticalSpacing(gvGallery.getHorizontalSpacing());
//                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
//                                    .getLayoutParams();
//                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void callGalleryData() {
        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        Call<GalleryDataModel> call = apiService.getGalleryData(AppConfiguration.BASEURL + "GetGalleryData" + "?LocationID=" + PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));
        call.enqueue(new Callback<GalleryDataModel>() {
            //
            @Override
            public void onResponse(@NonNull Call<GalleryDataModel> call, @NonNull final retrofit2.Response<GalleryDataModel> response) {
                Utils.dismissDialog();
                if (response.body() != null) {

                    if (response.body().getSuccess().equalsIgnoreCase("false")) {
                        noRecords.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                        llHeader.setVisibility(View.GONE);
                        return;
                    }

                    if (response.body().getSuccess().equalsIgnoreCase("True")) {

                        rvList.setVisibility(View.VISIBLE);
                        noRecords.setVisibility(View.GONE);
                        llHeader.setVisibility(View.VISIBLE);

                        if (response.body().getFinalArray() != null) {

                            galleryArr = response.body().getFinalArray();

                            galleryAdapter = new GalleryAdapter(getActivity(), response.body().getFinalArray(), "true", "true", onUpdateRecordRef);
                            rvList.setAdapter(galleryAdapter);
//                            galleryAdapter.notifyDataSetChanged();

                        }

                    }
                } else {
                    Utils.dismissDialog();
                    rvList.setVisibility(View.GONE);
                    llHeader.setVisibility(View.GONE);
                    noRecords.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GalleryDataModel> call, @NonNull Throwable t) {
                // Log error here since request failed
                Utils.dismissDialog();
                Log.e("gallery", t.toString());
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear + 1, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;
        AppConfiguration.fromDate = dateFinal;
        tvDate.setText(dateFinal);

    }

    @Override
    public void getEditpermission(int pos) {

        isRecordInUpdate = true;
        btnAdd.setText("Update");

        btnCancel.setVisibility(View.VISIBLE);

        mArrayUri = new ArrayList<>();
        photoArray = new ArrayList<>();

        try {
            if (galleryArr != null) {
                if (galleryArr.size() > 0) {

                    GalleryDataModel.GalleryFinalArray dataList = galleryArr.get(pos);

                    if (dataList != null) {

                        galid = dataList.getGalleryid();

                        tvDate.setText(dataList.getDate());
                        tvTitle.setText(dataList.getTitle());
                        tvComment.setText(dataList.getComment());

                        for (int i = 0; i < galleryArr.get(pos).getPhotos().size(); i++) {
                            mArrayUri.add(galleryArr.get(pos).getPhotos().get(i).getImagepath());
                        }

                        photoArray.addAll(galleryArr.get(pos).getPhotos());

//                        photoAdapter.notifyDataSetChanged();
                        photoAdapter = new PhotoAdapter(getActivity(), photoArray, new OnEditRecordWithPosition() {
                            @Override
                            public void getEditpermission(int pos) {
                                mArrayUri.remove(pos);
                                photoAdapter.notifyDataSetChanged();
                            }
                        });
                        rvPhotoList.setAdapter(photoAdapter);

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void callInsertUpdateGalleryDataApi(String id) {

        Utils.showDialog(getActivity());

        WebServices apiService = ApiClient.getClient().create(WebServices.class);

        String images;
        imgArray = new ArrayList<>();
        for (int i = 0; i < mArrayUri.size(); i++) {
            if (mArrayUri.get(i).startsWith("content:")) {
                try {
                    imgArray.add("Image_" + System.currentTimeMillis() + ".png");
//                    imgArray.add("" + (i + 1) + "_image_" + PathUtil.getFile(getActivity(), Uri.parse(mArrayUri.get(i))).getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imgArray.add(mArrayUri.get(i).substring(15));
            }
        }

        if (imgArray.size() > 0) {

            Call<GalleryDataModel> call = apiService.getGalleryData(AppConfiguration.BASEURL + "InsertGallery?Title=" + tvTitle.getText().toString() + "&Date=" + tvDate.getText().toString() + "&Comment=" + tvComment.getText().toString() + "&Phohtos=" + TextUtils.join(",", imgArray) + "&ID=" + id + "&LocationID=" + PrefUtils.getInstance(getActivity()).getStringValue("LocationID", "0"));

            call.enqueue(new Callback<GalleryDataModel>() {

                @Override
                public void onResponse(@NonNull Call<GalleryDataModel> call, @NonNull final retrofit2.Response<GalleryDataModel> response) {
                    Utils.dismissDialog();
                    if (response.body() != null) {

                        if (response.body().getSuccess().equalsIgnoreCase("false")) {
                            noRecords.setVisibility(View.VISIBLE);
                            rvList.setVisibility(View.GONE);
                            llHeader.setVisibility(View.GONE);
                            return;
                        }

                        if (response.body().getSuccess().equalsIgnoreCase("True")) {

                            uploadPdf();

                            tvTitle.setText("");
                            tvDate.setText(Utils.getTodaysDate());
                            tvComment.setText("");

                            photoAdapter = new PhotoAdapter(getActivity(), new ArrayList<GalleryDataModel.photosFinalArray>(), new OnEditRecordWithPosition() {
                                @Override
                                public void getEditpermission(int pos) {
                                    mArrayUri.remove(pos);
                                    photoAdapter.notifyDataSetChanged();
                                }
                            });
//                        photoAdapter.notifyDataSetChanged();
                            rvPhotoList.setAdapter(photoAdapter);

                            rvList.setVisibility(View.VISIBLE);
                            noRecords.setVisibility(View.GONE);
                            llHeader.setVisibility(View.VISIBLE);

                            if (response.body().getFinalArray() != null) {

                                galleryArr = response.body().getFinalArray();

//                            galleryAdapter = new GalleryAdapter(getActivity(), response.body().getFinalArray(), "true", "true", onUpdateRecordRef);
//                            rvList.setAdapter(galleryAdapter);
//                            galleryAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        Utils.dismissDialog();
                        rvList.setVisibility(View.GONE);
                        llHeader.setVisibility(View.GONE);
                        noRecords.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GalleryDataModel> call, @NonNull Throwable t) {
                    // Log error here since request failed
                    Utils.dismissDialog();
                    Log.e("permissionnnnn", t.toString());
                }
            });
        } else {
            Utils.dismissDialog();
            Utils.ping(getContext(), "Please Select atleast one Photo");
        }
    }

}
