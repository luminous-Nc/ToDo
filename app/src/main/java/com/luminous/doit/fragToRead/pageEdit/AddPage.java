package com.luminous.doit.fragToRead.pageEdit;

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
import com.luminous.doit.fragToDo.InsetMessage;
import com.luminous.doit.fragToRead.page.Read_Page;

import java.util.Date;

import static com.luminous.doit.R.id.read_add_article;

public class AddPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);
        TextView readAddArticle = (TextView) findViewById(read_add_article);
        TextView readAddSummary = (TextView) findViewById(R.id.read_add_summary);
        TextView readAddURL = (TextView) findViewById(R.id.read_add_URL);

        Toolbar ToolBar=(Toolbar) findViewById(R.id.read_add_toolbar);
        setSupportActionBar(ToolBar);
        android.support.v7.app.ActionBar addActionBar = getSupportActionBar();
        addActionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_add_toolbar_item,menu);
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
        return super.onOptionsItemSelected(item);
    }
    public void adddata(){
        EditText readAddArticle = (EditText) findViewById(read_add_article);
        EditText readAddSummary = (EditText) findViewById(R.id.read_add_summary);
        EditText readAddURL = (EditText) findViewById(R.id.read_add_URL);



        String newArticle = readAddArticle.getText().toString();
        String newSummary = readAddSummary.getText().toString();
        String newURL = readAddURL.getText().toString();
        Date newDate = new Date();
        if(newArticle.equals("")||newArticle==null){
            Toast.makeText(AddPage.this,"你还没有输入标题呀",Toast.LENGTH_SHORT).show();
            Log.e("wrong","1");
            readAddArticle.setHintTextColor(ContextCompat.getColor(getBaseContext(),R.color.insertMessage_when_wrong));
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
}
