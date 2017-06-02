package com.luminous.doit.fragToRead.pageEdit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.doit.R;
import com.luminous.doit.fragToRead.page.Read_Page;

import java.util.Date;

public class AddPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);
        TextView readAddArticle = (TextView) findViewById(R.id.read_add_article);
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

                TextView readAddArticle = (TextView) findViewById(R.id.read_add_article);
                TextView readAddSummary = (TextView) findViewById(R.id.read_add_summary);
                TextView readAddURL = (TextView) findViewById(R.id.read_add_URL);

                String newArticle = readAddArticle.getText().toString();
                String newSummary = readAddSummary.getText().toString();
                String newURL = readAddURL.getText().toString();
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
