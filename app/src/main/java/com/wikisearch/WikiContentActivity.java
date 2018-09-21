package com.wikisearch;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WikiContentActivity extends BaseActivity {

    WebView wikiContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_content);

        //Get the page id from the extras
        String pageID = getIntent().getStringExtra("PAGE_ID");

        //load wiki content and in the web view
        wikiContent = findViewById(R.id.wiki_content);
        wikiContent.setWebViewClient(new WebViewClient());
        WebSettings webSettings = wikiContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wikiContent.loadUrl(urlBuilder(pageID));
    }

    //Build the url for the wiki API request
    private String urlBuilder(String pageId){

        String baseUrl = Constants.CONTENT_BASE_URL;
        String queryString = "curid=" + pageId;
        return baseUrl + queryString;
    }
}
