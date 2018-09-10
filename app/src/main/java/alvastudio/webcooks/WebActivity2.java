package alvastudio.webcooks;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity2 extends AppCompatActivity {
    WebView mWebView;
    boolean redirect = false;
    boolean loadingFinished = true;
    String urlLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(1);
        setContentView(R.layout.activity_web2);

        Bundle extras = getIntent().getExtras();
        urlLink = extras.getString("url");

        CookieManager.getInstance().setAcceptCookie(true);
        this.mWebView = (WebView) findViewById(R.id.web_view_2);
        this.mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!WebActivity2.this.loadingFinished) {
                    WebActivity2.this.redirect = true;
                }
                WebActivity2.this.loadingFinished = false;
                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                WebActivity2.this.loadingFinished = false;
            }

            public void onPageFinished(WebView view, String url) {
                if (!WebActivity2.this.redirect) {
                    WebActivity2.this.loadingFinished = true;
                }
                if (!WebActivity2.this.loadingFinished || WebActivity2.this.redirect) {
                    WebActivity2.this.redirect = false;
                    return;
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.mWebView, true);
        }

        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setGeolocationEnabled(true);
        this.mWebView.getSettings().setAppCacheEnabled(true);
        this.mWebView.getSettings().setDatabaseEnabled(true);
        this.mWebView.getSettings().setDomStorageEnabled(true);
        loadUrl(urlLink);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mWebView.saveState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mWebView.restoreState(savedInstanceState);
    }

    private void loadUrl(String url) {
        this.mWebView.loadUrl(url);
    }

    public void onBackPressed() {
        if (this.mWebView.canGoBack()) {
            this.mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


}
