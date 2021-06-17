package stayabode.foodyHive.activities.consumers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.APIBaseURL;

import java.util.Set;

/**
 CashFreePayment Activity Created On ...
 **/
public class CashFreePaymentWebActivity extends AppCompatActivity {

    public static CashFreePaymentWebActivity webViewActivity;
    public static WebView webView;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBarColor));
        }
        setContentView(R.layout.web_view_authentication);

        webViewActivity = this;
        webView = findViewById(R.id.webView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(getIntent().getStringExtra("paymentLink"));


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if(url.contains(APIBaseURL.CHECKOUTURLLINK +"checkout"))
                {
                    Uri url1 = Uri.parse(webView.getUrl());
                    Set<String> paramNames = url1.getQueryParameterNames();
                    for (String key: paramNames) {
                        String orderID = url1.getQueryParameter(key);
                        String status = url1.getQueryParameter(key);
                    }
                    webView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.contains(APIBaseURL.CHECKOUTURLLINK + "checkout"))
                {
                    Uri url1 = Uri.parse(url);
                    String orderID = url1.getQueryParameter("OrderInvoiceNo");

                    String status = url1.getQueryParameter("status");



                    webView.setVisibility(View.GONE);
                    Intent intent=new Intent();
                    intent.putExtra("OrderID",orderID);
                    intent.putExtra("status",status);
                    setResult(103,intent);
                    finish();

                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
