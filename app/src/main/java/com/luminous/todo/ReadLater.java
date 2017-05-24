package com.luminous.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReadLater extends AppCompatActivity {

    private Read_Page[] pages = {new Read_Page("标题1","摘要1"),new Read_Page("标题1","摘要1"),
            new Read_Page("标题2","摘要2"),new Read_Page("标题3","摘要3"),new Read_Page("标题4","摘要4"),
            new Read_Page("标题5","摘要5"),new Read_Page("标题6","摘要6"),new Read_Page("标题7","摘要7"),
            new Read_Page("标题8","摘要8"),new Read_Page("标题9","摘要9")};//pages是自己定义的类的数组

    private List<Read_Page> pageList= new ArrayList<>();//pageList是含有Page的数组
    private PageAdapter adapter_page;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readlater);

        TextView today_top= (TextView)  findViewById(R.id.today_top);
        today_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openToday = new Intent(ReadLater.this,MainActivity.class);
                startActivity(openToday);
            }
        });

        initPages();
        RecyclerView readlist = (RecyclerView) findViewById(R.id.read_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        readlist.setLayoutManager(layoutManager);
        PageAdapter adapter_page=new PageAdapter(pageList);
        readlist.setAdapter(adapter_page);
    }

    private void initPages(){
        pageList.clear();
        for (int i=0;i<50;i++) {
            Random random = new Random();
            int index = random.nextInt(pages.length);
            pageList.add(pages[index]);
        }
    }
}
