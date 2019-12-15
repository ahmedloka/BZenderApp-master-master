package apps.pixel.bzender.activities.webViewContactUs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.dialog.DialogLoader;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "webViewActivity";
    private DialogLoader dialogLoader;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_web_view);


        dialogLoader = new DialogLoader();

        Log.d(TAG, "WebViewActivity: " + "OPENED");
        initViews();
    }

    private void initViews() {
        mWebView = findViewById(R.id.mWebView);

        Intent intent = getIntent();
        try {
            String url = intent.getExtras().get(Constant.URL_KEY).toString();
            Log.d(TAG, "initViews: " + url);
            openWebSite(url);
        } catch (NullPointerException e) {
            Constant.showErrorDialog(this, getString(R.string.please_downlaod_whats_app));
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void openWebSite(String url) {
        mWebView = new WebView(this);
        mWebView.getSettings().setJavaScriptEnabled(true); // enable javascript
        final Activity activity = this;

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (!dialogLoader.isAdded()) {
                    dialogLoader.show(getSupportFragmentManager(), "5");
                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (dialogLoader.isAdded()) {
                    dialogLoader.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
             //   Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        mWebView.loadUrl(url);
        setContentView(mWebView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NavUtils.navigateUpFromSameTask(this);
        Animatoo.animateSlideLeft(this);
    }
}
