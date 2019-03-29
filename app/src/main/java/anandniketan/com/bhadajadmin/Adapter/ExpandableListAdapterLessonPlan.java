package anandniketan.com.bhadajadmin.Adapter;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajadmin.Model.Staff.FinalArrayStaffModel;
import anandniketan.com.bhadajadmin.R;
import anandniketan.com.bhadajadmin.Utility.AppConfiguration;
import anandniketan.com.bhadajadmin.Utility.Utils;
import anandniketan.com.bhadajadmin.databinding.ListGroupLessonPlanDetailBinding;
import anandniketan.com.bhadajadmin.databinding.ListItemLessonPlanBinding;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by admsandroid on 2/9/2018.
 */

public class ExpandableListAdapterLessonPlan extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, ArrayList<FinalArrayStaffModel>> _listDataChild;
    SpannableStringBuilder chapterSpanned, keypointSpanned, objectiveSpanned, assessmentSpanned;
    String chapterStr, keypointkStr, objectiveStr, assessmentStr;
    ListItemLessonPlanBinding itembinding;
    ListGroupLessonPlanDetailBinding groupbinding;
    String file1;
    File filepdf;

    public ExpandableListAdapterLessonPlan(Context context, List<String> listDataHeader, HashMap<String, ArrayList<FinalArrayStaffModel>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;

    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ArrayList<FinalArrayStaffModel> detail = getChild(groupPosition, 0);
        if (convertView == null) {

        }
        itembinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_lesson_plan, parent, false);
        convertView = itembinding.getRoot();

        keypointkStr = detail.get(childPosition).getKeyPoint().replaceAll("\\n", "").trim();
        objectiveStr = detail.get(childPosition).getObjective().replaceAll("\\n", "").trim();
        assessmentStr = detail.get(childPosition).getAssessmentQuestion().replaceAll("\\n", "").trim();


        setText(keypointkStr, objectiveStr, assessmentStr);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public ArrayList<FinalArrayStaffModel> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        final String[] spiltValue = headerTitle.split("\\|");
        if (convertView == null) {

        }
        groupbinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.list_group_lesson_plan_detail, parent, false);
        convertView = groupbinding.getRoot();

        String sr = String.valueOf(groupPosition + 1);
        groupbinding.indexTxt.setText(sr);
        groupbinding.chapterNoTxt.setText(spiltValue[0]);
        chapterStr = spiltValue[1];
        chapterSpanned = (SpannableStringBuilder) Html.fromHtml(chapterStr);
        chapterSpanned = trimSpannable(chapterSpanned);
        groupbinding.chapterNameTxt.setText(chapterSpanned, TextView.BufferType.SPANNABLE);
        Log.d("id", spiltValue[3]);

        Glide.with(_context)
                .load(AppConfiguration.BASEURL_ICONS + "pdf.png")
                .into(groupbinding.pdfImg);
        Glide.with(_context)
                .load(AppConfiguration.BASEURL_ICONS + "Word.png")
                .into(groupbinding.wordImg);
        groupbinding.pdfImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String extStorageDirectory = "";
                String saveFilePath = null;
                long currentTime = Calendar.getInstance().getTimeInMillis();
                Log.d("date", "" + currentTime);
                Boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();

                if (isSDSupportedDevice && isSDPresent) {
                    // yes SD-card is present
                    Utils.ping(_context, "present");
                    extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    saveFilePath = String.valueOf(new File(extStorageDirectory, Utils.parentFolderName + "/" + Utils.childAnnouncementFolderName + "/" + currentTime).getPath());

                } else {
                    // Sorry
//                            Utility.ping(mContext, "notpresent");
//
                    File cDir = _context.getExternalFilesDir(null);
                    saveFilePath = String.valueOf(new File(cDir.getPath() + "/" + currentTime + "Code.pdf"));
                    Log.d("path", saveFilePath);

                }
//
                Log.d("path", extStorageDirectory);

                String fileURL = "http://103.8.216.132/Backend/lessonplanpdf.aspx?ID=" + spiltValue[3];
                Log.d("URL", fileURL);

                if (!Utils.checkNetwork(_context)) {
                    Utils.showCustomDialog(_context.getResources().getString(R.string.internet_error), _context.getResources().getString(R.string.internet_connection_error), (Activity) _context);
                    return;
                }
                Ion.with(_context)
                        .load(fileURL)  // download url
                        .write(new File(saveFilePath))  // File no path
                        .setCallback(new FutureCallback<File>() {
                            //                                    @Override
                            public void onCompleted(Exception e, File file) {

                                if (file.length() > 0) {
                                    Utils.dismissDialog();
                                    Utils.ping(_context, "Download complete.");
                                    file1 = file.getPath();
                                    filepdf = file.getAbsoluteFile();
                                    Log.d("file11", "" + filepdf);
                                    showCustomNotification();
                                } else {
                                    Utils.dismissDialog();
                                    Utils.ping(_context, "Something error");
                                }
                            }


                        });
            }
        });

        groupbinding.wordImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String extStorageDirectory = "";
                String saveFilePath = null;
                long currentTime = Calendar.getInstance().getTimeInMillis();
                Log.d("date", "" + currentTime);
                Boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();

                if (isSDSupportedDevice && isSDPresent) {
                    // yes SD-card is present
//                            Utils.ping(_context, "present");
                    extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    saveFilePath = String.valueOf(new File(extStorageDirectory, Utils.parentFolderName + "/" + Utils.childAnnouncementFolderName + "/" + currentTime).getPath());

                } else {
                    // Sorry
//                            Utils.ping(_context, "notpresent");

                    File cDir = _context.getExternalFilesDir(null);
                    Log.d("*********", cDir.getPath());
                    saveFilePath = String.valueOf(new File(cDir.getPath() + "/" + currentTime + "word.doc"));
                    Log.d("path", saveFilePath);


                }
                Log.d("path", extStorageDirectory);

                final String fileURL = "http://103.8.216.132/Backend/LessonPlanWord.aspx?ID=" + spiltValue[3];
                Log.d("URL", fileURL);

                Ion.with(_context)
                        .load(fileURL)  // download url
                        .write(new File(saveFilePath))  // File no path
                        .setCallback(new FutureCallback<File>() {
                            //                                    @Override
                            public void onCompleted(Exception e, File file) {

                                if (file.length() > 0) {
                                    Utils.dismissDialog();
                                    Utils.ping(_context, "Download complete.");
                                    file1 = file.getPath();
                                    Log.d("file11", file1);
                                    filepdf = file.getAbsoluteFile();
                                    Log.d("file11", "" + filepdf);
                                    showCustomNotification();
                                } else {
                                    Utils.dismissDialog();
                                    Utils.ping(_context, "Something error");
                                }
                            }
                        });

            }

        });
        if (isExpanded) {
            groupbinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.present));
        } else {
            groupbinding.viewTxt.setTextColor(_context.getResources().getColor(R.color.absent));
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private void setText(String html, String html1, String html2) {

//        chapterSpanned = (SpannableStringBuilder) Html.fromHtml(html);
        keypointSpanned = (SpannableStringBuilder) Html.fromHtml(html);
        objectiveSpanned = (SpannableStringBuilder) Html.fromHtml(html1);
        assessmentSpanned = (SpannableStringBuilder) Html.fromHtml(html2);

//        chapterSpanned = trimSpannable(chapterSpanned);
        keypointSpanned = trimSpannable(keypointSpanned);
        objectiveSpanned = trimSpannable(objectiveSpanned);
        assessmentSpanned = trimSpannable(assessmentSpanned);

//        groupbinding.chapterNameTxt.setText(chapterSpanned, TextView.BufferType.SPANNABLE);

        itembinding.objectiveTxt.setText(objectiveSpanned, TextView.BufferType.SPANNABLE);
        itembinding.keypointTxt.setText(keypointSpanned, TextView.BufferType.SPANNABLE);
        itembinding.assesmentQuestionTxt.setText(assessmentSpanned, TextView.BufferType.SPANNABLE);


    }

    private SpannableStringBuilder trimSpannable(SpannableStringBuilder spannable) {
        int trimStart = 0;
        int trimEnd = 0;
        String text = spannable.toString();

        while (text.length() > 0 && text.startsWith("\n")) {
            text = text.substring(1);
            trimStart += 1;
        }
        while (text.length() > 0 && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
            trimEnd += 1;
        }
        return spannable.delete(0, trimStart).delete(spannable.length() - trimEnd, spannable.length());
    }

    private void showCustomNotification() {
// 11/01/2018 change by megha
        Intent openFile;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            File apkfile = new File(FileProvider.getUriForFile(mContext,Environment.DIRECTORY_DOWNLOADS,file1),file1);
//            Uri apkUri = FileProvider.getUriForFile(mContext,BuildConfig.APPLICATION_ID+".provider",apkfile);
//            String s = apkUri.toString();
//            Log.d("HERE!!", s);
//            openFile = new Intent(Intent.ACTION_INSTALL_PACKAGE);
//            openFile.setData(apkUri);
//            openFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////            mContext.startActivity(intent);
//
//        } else {
        File file = new File(file1);
        Log.d("DownloadfilePath", "File to download = " + String.valueOf(file));
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = file.getName().substring(file.getName().indexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);
        Log.d("type", type);

        openFile = new Intent(Intent.ACTION_VIEW, Uri.fromFile(file));
        openFile.setDataAndType(Uri.fromFile(file), type);

        PendingIntent p = PendingIntent.getActivity(_context, 0, openFile, 0);

        Notification noti = new NotificationCompat.Builder(_context)
                .setContentTitle("Download completed")
                .setContentText(file1)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(p).build();

        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) _context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);


    }
}





