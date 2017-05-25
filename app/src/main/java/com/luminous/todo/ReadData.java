package com.luminous.todo;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;



public class ReadData extends AppCompatActivity {

    public static final String PAGE_ARTICLE="pageArticle";
    public static final String PAGE_SUMMARY="pageSummary";
    public static final String PAGE_URL="pageURL";
    public  String  longword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        Intent pageFront = getIntent();
        String pageArticle = pageFront.getStringExtra(PAGE_ARTICLE);
        String pageSummary = pageFront.getStringExtra(PAGE_SUMMARY);
        String pageURL = pageFront.getStringExtra(PAGE_URL);

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
        int order = random.nextInt(3)+1;
        StringBuilder builder = new StringBuilder();
        switch (order){
            case 1: readDataShow.setText(pageSummary+ "\n"+pageURL+"\n\n\n"+LongText.heibai);
                       Glide.with(ReadData.this).load(R.drawable.luna).into(readDataPic);
                       break;
            case 2:readDataShow.setText(pageSummary+ "\n"+pageURL+"\n\n\n"+LongText.mianchaodahai);
                Glide.with(ReadData.this).load(R.drawable.miku).into(readDataPic);
                break;
            case 3:readDataShow.setText(pageSummary+ "\n"+pageURL+"\n\n\n"+LongText.shiguang);
                Glide.with(ReadData.this).load(R.drawable.yamoto).into(readDataPic);
                break;
            default:
                break;
        }



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
