package apps.sharabash.bzender.activities.webPaymnet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.HashMap;

import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.dialog.DialogLoader;

public class WebPaymentActivity extends AppCompatActivity {
    private static final String TAG = "webViewActivity";
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private SharedPreferences sharedPreferences;
    private AppCompatImageView mBack;
    private DialogLoader dialogLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_web_payment);

        sharedPreferences = getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
        dialogLoader = new DialogLoader();

        initViews();

    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progressBar);
        mBack = findViewById(R.id.imageNavigationIcon);
        mBack.setOnClickListener(v -> {
            NavUtils.navigateUpFromSameTask(this);
            Animatoo.animateSlideLeft(this);
        });

        mWebView = findViewById(R.id.mWebView);

        Intent intent = getIntent();
        String url = intent.getExtras().get(Constant.PATYMENT_URL).toString();
        openWebSite(url);
        Log.d(TAG, "URL_IS :  " + url);
    }


    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    private void openWebSite(String url) {
       // mWebView = new WebView(this);
        mWebView.getSettings().setJavaScriptEnabled(true); // enable javascript

        mWebView.getSettings().setSaveFormData(false);

        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Authorization", "Bearer " + sharedPreferences.getString(Constant.UserID, ""));
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (dialogLoader.isAdded()) {
                    dialogLoader.dismiss();
                }

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (!dialogLoader.isAdded()) {
                    dialogLoader.show(getSupportFragmentManager(), "1");
                }
            }
        });
        mWebView.loadUrl(url, map);

        //setContentView(mWebView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NavUtils.navigateUpFromSameTask(this);
        Animatoo.animateSlideLeft(this);
    }

}
