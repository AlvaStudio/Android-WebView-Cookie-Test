package alvastudio.webcooks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity {

    WebView siteWebView;
    String urlLink;

    String TAG = "WEBZZZ";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle extras = getIntent().getExtras();
        urlLink = extras.getString("url");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        if (siteWebView == null) {
            createWebView();
        }
    }

    /*
    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        Log.d(TAG, "RestoreInstance");
        siteWebView.restoreState(bundle);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.d(TAG, "SaveInstance");
        siteWebView.saveState(bundle);
    }
    */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && siteWebView.canGoBack()) {
            siteWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    private void createWebView() {
        Log.d(TAG, "CREATE WEB VIEW");
        siteWebView = (WebView)findViewById(R.id.web_view);

        setCookies(siteWebView);
        //clearCookies(this);

        initSettings(siteWebView);

        siteWebView.loadUrl(urlLink);
        siteWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.d(TAG, "onPageFinished: " + url);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    CookieManager.getInstance().flush();
                } else {
                    CookieSyncManager.getInstance().sync();
                }
            }

        });
        siteWebView.setWebChromeClient(new WebChromeClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }
        });

        if (Build.VERSION.SDK_INT >= 11) {
            siteWebView.setLayerType(2, null);
        }
    }

    private void initSettings(WebView siteWebView) {
        siteWebView.getSettings().setAllowContentAccess(true);
        siteWebView.getSettings().setAllowFileAccess(true);
        siteWebView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        siteWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        siteWebView.getSettings().setAppCacheEnabled(false);
        siteWebView.getSettings().setBlockNetworkImage(false);
        siteWebView.getSettings().setBlockNetworkLoads(false);
        siteWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        siteWebView.getSettings().setDatabaseEnabled(false);
        siteWebView.getSettings().setGeolocationEnabled(true);
        siteWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        siteWebView.getSettings().setLoadsImagesAutomatically(true);
        siteWebView.getSettings().setNeedInitialFocus(false);
        siteWebView.getSettings().setSaveFormData(true);
        siteWebView.getSettings().setSupportMultipleWindows(false);
        siteWebView.getSettings().setSupportZoom(true);
        siteWebView.getSettings().setDomStorageEnabled(true);
        siteWebView.getSettings().setAppCacheEnabled(true);
        siteWebView.getSettings().setJavaScriptEnabled(true);
        siteWebView.getSettings().setDisplayZoomControls(false);
        siteWebView.getSettings().setLoadWithOverviewMode(true);
        siteWebView.getSettings().setUseWideViewPort(true);
        siteWebView.getSettings().setBuiltInZoomControls(true);

        siteWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        siteWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        siteWebView.getSettings().setSavePassword(true);
    }

    private void setCookies(WebView webView) {
        CookieManager cookieManager = CookieManager.getInstance();

        cookieManager.setAcceptFileSchemeCookies(true);
        cookieManager.setAcceptCookie(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }

        String cookie = cookieManager.getCookie(urlLink);

        Log.d(TAG, "cookie : " + cookie);
    }

    private void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }
}
