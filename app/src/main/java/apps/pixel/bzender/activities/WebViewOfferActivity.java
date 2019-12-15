package apps.pixel.bzender.activities;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.dialog.DialogLoader;

public class WebViewOfferActivity extends AppCompatActivity {

    private DialogLoader dialogLoader;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_offer);


        initViews();
    }

    private void initViews() {
        dialogLoader = new DialogLoader();
        mWebView = findViewById(R.id.web_view);

        openWebSite();
    }

    private void openWebSite() {
        mWebView.getSettings().setJavaScriptEnabled(true); // enable javascript
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
                dialogLoader.dismiss();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                dialogLoader.dismiss();
            }
        });
        mWebView.loadUrl("http://".trim() + getIntent().getStringExtra(Constant.URL_DETAILS_KEY).trim().trim());
        Log.d("URL_IS_", "openWebSite: " + "  http://".trim() + getIntent().getStringExtra(Constant.URL_DETAILS_KEY).trim().trim());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NavUtils.navigateUpFromSameTask(this);
        Animatoo.animateSlideLeft(this);
    }

}
