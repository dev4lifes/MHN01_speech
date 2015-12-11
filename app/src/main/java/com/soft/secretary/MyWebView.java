package com.soft.secretary;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by hoang on 25/11/2015.
 */
public class MyWebView extends AppCompatActivity {
    WebView mWebView;

    private final String SEARCH_GOOGLE = "https://www.google.com/search?q=";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        mWebView=(WebView)findViewById(R.id.webView);


        mWebView = (WebView) findViewById( R.id.webView ); //This is the id you gave
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);		 //Zoom Control on web (You don't need this
        //if ROM supports Multi-Touch
        mWebView.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
//            mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
//        mWebView.setScrollbarFadingEnabled(false);
//        mWebView.setScrollBarStyle(mWebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        mWebView.setInitialScale(1);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(true);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        searchTheWeb(url);
    }

    void searchTheWeb(String query) {
        if (query.equals("")) {
            return;
        }
        query = query.trim();
        mWebView.stopLoading();

        if (query.startsWith("www.")) {
            query = "http://" + query;
        } else if (query.startsWith("ftp.")) {
            query = "ftp://" + query;
        }

        boolean containsPeriod = query.contains(".");
        boolean isIPAddress = (TextUtils.isDigitsOnly(query.replace(".", ""))
                && (query.replace(".", "").length() >= 4) && query.contains("."));
        boolean aboutScheme = query.contains("about:");
        boolean validURL = (query.startsWith("ftp://") || query.startsWith("http://")
                || query.startsWith("file://") || query.startsWith("https://"))
                || isIPAddress;
        boolean isSearch = ((query.contains(" ") || !containsPeriod) && !aboutScheme);

        if (isIPAddress
                && (!query.startsWith("http://") || !query.startsWith("https://"))) {
            query = "http://" + query;
        }

        if (isSearch) {
            try {
                query = URLEncoder.encode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            mWebView.loadUrl(SEARCH_GOOGLE + query);
        } else if (!validURL) {
            mWebView.loadUrl("http://" + query);
        } else {
            mWebView.loadUrl(query);
        }
    }

    class MyWebViewClient extends WebViewClient {
        //
        @Override

        public void onPageFinished(WebView view, String url1) {

            Log.d("URL finish", url1);
            super.onPageFinished(view, url1);
        }

    }

    private class MyWebChromeClient extends WebChromeClient {

        //display alert message in Web View
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

            new AlertDialog.Builder(view.getContext())
                    .setMessage(message).setCancelable(true).show();
            result.confirm();
            return true;
        }

    }
}
