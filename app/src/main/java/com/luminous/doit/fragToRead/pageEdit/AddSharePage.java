package com.luminous.doit.fragToRead.pageEdit;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.doit.R;
import com.luminous.doit.fragToRead.page.Read_Page;

import java.util.ArrayList;
import java.util.Date;

public class AddSharePage extends AppCompatActivity {
    private String pageArticle;
    private String pageSummary;
    private String pageURL;
    private Uri uri;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void dealTextMessage(Intent intent){
        pageArticle = intent.getStringExtra(Intent.EXTRA_TITLE);
        pageSummary = intent.getStringExtra(Intent.EXTRA_TEXT);
        ClipData clipData = intent.getClipData();
        ClipData.Item item =  clipData.getItemAt(0);
        String a= (String) item.getText();
        pageURL = a;

        Uri b = item.getUri();
    }

    void dealPicStream(Intent intent){
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
    }

    void dealMultiplePicStream(Intent intent){
        ArrayList<Uri> arrayList = intent.getParcelableArrayListExtra(intent.EXTRA_STREAM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_share_page);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                dealTextMessage(intent);
            } else if (type.startsWith("image/")) {
                dealPicStream(intent);
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                dealMultiplePicStream(intent);
            }}
            TextView readShareArticle = (TextView) findViewById(R.id.read_share_article);
            TextView readShareSummary = (TextView) findViewById(R.id.read_share_summary);
            TextView readShareURL = (TextView) findViewById(R.id.read_share_URL);

            readShareArticle.setText(pageArticle);
            readShareSummary.setText(pageSummary);
            readShareURL.setText(pageURL);

            Toolbar toolBar = (Toolbar) findViewById(R.id.read_share_toolbar);
            toolBar.setTitle("收藏新文章");
            setSupportActionBar(toolBar);
            android.support.v7.app.ActionBar addActionBar = getSupportActionBar();
            addActionBar.setDisplayHomeAsUpEnabled(true);
        }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_share_toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.read_share_sure:

                TextView readShareArticle = (TextView) findViewById(R.id.read_share_article);
                TextView readShareSummary = (TextView) findViewById(R.id.read_share_summary);
                TextView readShareURL = (TextView) findViewById(R.id.read_share_URL);

                String newArticle = readShareArticle.getText().toString();
                String newSummary = readShareSummary.getText().toString();
                String newURL = readShareURL.getText().toString();
                Date newDate = new Date();

                //SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                //String DateForInt = format.format(newDate);
                //Integer DateForID = Integer.parseInt(DateForInt);
                Read_Page read_page = new Read_Page(1,newArticle,newSummary,newURL,newDate);
                read_page.save();
                Toast.makeText(this,"阅读内容已添加",Toast.LENGTH_SHORT).show();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
