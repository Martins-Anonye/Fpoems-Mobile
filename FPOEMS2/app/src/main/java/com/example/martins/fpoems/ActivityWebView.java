package com.example.martins.fpoems;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ActivityWebView extends AppCompatActivity {
    WebView webviewresult;
    Button masscommbtn,audithbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        masscommbtn =(Button)findViewById(R.id.masscommbtn);
        audithbtn =(Button)findViewById(R.id.audithbtn);

        webviewresult = (WebView) findViewById(R.id.webviewresult);
        webviewresult.getSettings().setLoadsImagesAutomatically(true);
        webviewresult.getSettings().setJavaScriptEnabled(true);
        webviewresult.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        AdRequest adRequestint = new AdRequest.Builder().build();

        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2789057166231954/3411959102");
        interstitialAd.loadAd(adRequestint);
        interstitialAd.show();
      final  String audithurl ="https://portal.federalpolyoko.edu.ng/?AspxAutoDetectCookieSupport=1";
       final String masscomurl = "http://federalpolyokoexams.edu.ng/results/stwelcm.php";

        masscommbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webviewresult.loadUrl(masscomurl);

            }
        });



        audithbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webviewresult.loadUrl(audithurl);

            }
        });


        Bundle extras = getIntent().getExtras();
        // webviewresult.loadData();
      //  webviewresult.loadUrl("file:///android_asset/myresource.html");

        /*String data = "<html><body><h1>Hello, Javatpoint!</h1></body></html>";
        mywebview.loadData(data, "text/html", "UTF-8"); */


    }
}
