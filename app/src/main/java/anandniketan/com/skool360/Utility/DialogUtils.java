package anandniketan.com.skool360.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import anandniketan.com.skool360.R;

public class DialogUtils {


    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(context.getString(R.string.please_wait));
        return progressDialog;
    }

    public static Dialog createConfirmDialog(Context context, @StringRes int titleId, @StringRes int messageId, View view, DialogInterface.OnClickListener positiveClickListener, DialogInterface.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setView(view);
        builder.setPositiveButton(R.string.ok, positiveClickListener);
        builder.setNegativeButton(R.string.mdtp_cancel, negativeClickListener);

        return builder.create();
    }

    public static Dialog createConfirmDialog(Context context, @StringRes int titleId, @StringRes int messageId, String positiveTxt, String negativeTxt, DialogInterface.OnClickListener positiveClickListener, DialogInterface.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        // builder.setView(view);
        builder.setPositiveButton(positiveTxt, positiveClickListener);
        builder.setNegativeButton(negativeTxt, negativeClickListener);

        return builder.create();
    }

    public static Dialog createConfirmDialog(Activity context, String titleId, DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(titleId);
//        builder.setMessage(messageId);
        // builder.setView(view);
        builder.setPositiveButton(R.string.ok, positiveClickListener);

        return builder.create();
    }

    public static void showWebviewDialog(Context context, String webViewUrl) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_webview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        WebView webView = dialog.findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().getAllowContentAccess();
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(webViewUrl);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                dialog.findViewById(R.id.loader).setVisibility(View.GONE);
            }
        });

        Button btnClose = dialog.findViewById(R.id.close_btn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}

