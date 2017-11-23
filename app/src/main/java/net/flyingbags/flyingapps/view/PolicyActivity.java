package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import net.flyingbags.flyingapps.R;

public class PolicyActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        WebView webView_policy = (WebView) findViewById(R.id.webView_policy);
        webView_policy.loadUrl("http://flyingbags.net/terms.html");
    }
}
