package com.luminous.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "相关模块正在开发中", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView read_top= (TextView)  findViewById(R.id.read_top);
        read_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openRead = new Intent(MainActivity.this,ReadLater.class);
                startActivity(openRead);
            }
        });
    }

}
