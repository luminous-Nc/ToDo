package com.luminous.doit.fragToRead.pageEdit;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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
    private String allContent;
    private Uri uri;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void dealPicStream(Intent intent){

        ClipData clipData = intent.getClipData();
        for(int i = 0; i< clipData.getItemCount();i++){
            ClipData.Item item =  clipData.getItemAt(0);
            allContent = (String) item.getText();
        }
        pageURL =getShareURL(allContent);
        pageArticle=getShareTitle(allContent);

    }

    void dealMultiplePicStream(Intent intent){
        ArrayList<Uri> arrayList = intent.getParcelableArrayListExtra(intent.EXTRA_STREAM);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_share_page);

            Intent intent = getIntent();
            dealPicStream(intent);//只留一个接受单张图片分享类型的接口


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
                adddata();
        }
        return super.onOptionsItemSelected(item);
    }
    public void adddata(){
        EditText readShareArticle = (EditText) findViewById(R.id.read_share_article);
        EditText readShareSummary = (EditText) findViewById(R.id.read_share_summary);
        EditText readShareURL = (EditText) findViewById(R.id.read_share_URL);
        String newArticle = readShareArticle.getText().toString();
        String newSummary = readShareSummary.getText().toString();
        String newURL = readShareURL.getText().toString();
        Date newDate = new Date();

        if(newArticle.equals("")||newArticle==null){
            Toast.makeText(AddSharePage.this,"你还没有输入标题呀",Toast.LENGTH_SHORT).show();
            Log.e("wrong","1");
            readShareArticle.setHintTextColor(ContextCompat.getColor(getBaseContext(),R.color.insertMessage_when_wrong));
            return;
        }
        //SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        //String DateForInt = format.format(newDate);
        //Integer DateForID = Integer.parseInt(DateForInt);
        Read_Page read_page = new Read_Page(1,newArticle,newSummary,newURL,newDate);
        read_page.save();
        Toast.makeText(this,"阅读内容已添加",Toast.LENGTH_SHORT).show();
        finish();
    }
    private String getShareURL(String allContent){
        return    allContent = allContent.substring(allContent.indexOf("http"));//,allContent.indexOf(" ")-1);
    }
    private String getShareTitle(String allContent){

        if(allContent.indexOf("【")!=-1){
            return allContent.substring(allContent.indexOf("【")+1,allContent.indexOf("】"));
        }else {
            return "";
        }
    }
}
