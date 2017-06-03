package com.luminous.doit.fragToRead.pageEdit;

import android.content.Intent;
import android.os.Bundle;
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

import org.litepal.crud.DataSupport;

import java.util.Date;

public class ChangePage extends AppCompatActivity {
    public static final String TAG = "ChangePage";

    public static final String ID = "id";
    public static final String PAGE_ARTICLE="pageArticle";
    public static final String PAGE_SUMMARY="pageSummary";
    public static final String PAGE_URL="pageURL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_page);
        TextView readChangeArticle = (TextView) findViewById(R.id.read_change_article);
        TextView readChangeSummary = (TextView) findViewById(R.id.read_change_summary);
        TextView readChangeURL = (TextView) findViewById(R.id.read_change_URL);

        Intent pageFront = getIntent();
        String pageArticle = pageFront.getStringExtra(PAGE_ARTICLE);
        String pageSummary = pageFront.getStringExtra(PAGE_SUMMARY);
        String pageURL = pageFront.getStringExtra(PAGE_URL);
        int id=pageFront.getIntExtra(ID,1);

        readChangeArticle.setText(pageArticle);
        readChangeSummary.setText(pageSummary);
        readChangeURL.setText(pageURL);

        Toolbar ToolBar=(Toolbar) findViewById(R.id.read_change_toolbar);
        setSupportActionBar(ToolBar);
        android.support.v7.app.ActionBar addActionBar = getSupportActionBar();
        addActionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_change_toolbar_item,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.read_add_sure:
            adddata();

        }
        return super.onOptionsItemSelected(item);}

    public void adddata(){
        EditText readChangeArticle = (EditText) findViewById(R.id.read_change_article);
        EditText readChangeSummary = (EditText) findViewById(R.id.read_change_summary);
        EditText readChangeURL = (EditText) findViewById(R.id.read_change_URL);
        Log.d(TAG,"载入了相关布局");
        Intent pageFront = getIntent();
        int id=pageFront.getIntExtra(ID,1);
        Log.d(TAG,"传递过来的id是："+ String.valueOf(id));
        String newArticle = readChangeArticle.getText().toString();
        String newSummary = readChangeSummary.getText().toString();
        String newURL = readChangeURL.getText().toString();
        Date newDate = new Date();
        if(newArticle.equals("")||newArticle==null){
            Toast.makeText(ChangePage.this,"你还没有输入标题呀",Toast.LENGTH_SHORT).show();
            Log.e("wrong","1");
            readChangeArticle.setHintTextColor(ContextCompat.getColor(getBaseContext(),R.color.insertMessage_when_wrong));
            return;
        }
        Log.d(TAG,"信息是："+"-->"+newArticle+"-->"+newSummary+"-->"+newURL);
        DataSupport.deleteAll(Read_Page.class, "id = ?",String.valueOf(id));
        Read_Page read_page = new Read_Page(1,newArticle,newSummary,newURL,newDate);
        read_page.save();
        Toast.makeText(this,"阅读内容已修改",Toast.LENGTH_SHORT).show();
        finish();

    }
}
