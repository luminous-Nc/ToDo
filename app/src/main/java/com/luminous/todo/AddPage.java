package com.luminous.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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


        }
        return super.onOptionsItemSelected(item);
    }
}
