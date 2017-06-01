package com.luminous.doit.fragToRead;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luminous.doit.R;

import java.util.Random;


public class ReadData extends AppCompatActivity {
    public static final String PAGE_ID = "pageID";
    public static final String PAGE_ARTICLE="pageArticle";
    public static final String PAGE_SUMMARY="pageSummary";
    public static final String PAGE_URL="pageURL";
    public static final String PAGE_DATE="pageDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        Intent pageFront = getIntent();
        String pageArticle = pageFront.getStringExtra(PAGE_ARTICLE);
        String pageSummary = pageFront.getStringExtra(PAGE_SUMMARY);
        String pageURL = pageFront.getStringExtra(PAGE_URL);
        String pageDateString = pageFront.getStringExtra(PAGE_DATE);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);

        TextView readDataShow = (TextView)findViewById(R.id.read_data_show);
        ImageView readDataPic = (ImageView)findViewById(R.id.read_data_pic);

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(pageArticle);

        Random random = new Random();
        int order = random.nextInt(7)+1;
        StringBuilder builder = new StringBuilder();
        readDataShow.setText(pageSummary+ "\n"+pageDateString+"\n");
        switch (order){
            case 1:Glide.with(ReadData.this).load(R.drawable.luna).into(readDataPic);
                   break;
            case 2:Glide.with(ReadData.this).load(R.drawable.miku).into(readDataPic);
                break;
            case 3:Glide.with(ReadData.this).load(R.drawable.yamoto).into(readDataPic);
                break;
            case 4:Glide.with(ReadData.this).load(R.drawable.star).into(readDataPic);
                break;
            case 5:Glide.with(ReadData.this).load(R.drawable.ballon).into(readDataPic);
                break;
            case 6:Glide.with(ReadData.this).load(R.drawable.dark).into(readDataPic);
                break;
            case 7:Glide.with(ReadData.this).load(R.drawable.green).into(readDataPic);
                break;
            default:
                break;
        }
        WebView readShowWeb= (WebView)findViewById(R.id.read_show_web);
        readShowWeb.getSettings().setJavaScriptEnabled(true);
        readShowWeb.setWebViewClient(new WebViewClient());
        readShowWeb.loadUrl(pageURL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
