package com.luminous.todo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

    private Read_Page[] pages = {
            new Read_Page("标题0","直到火与血都沉寂","www.baidu.com"),
            new Read_Page("标题1","云都停住，去等待不知道的归宿","www.baidu.com"),
            new Read_Page("标题2","这段不知来处的路","www.baidu.com"),
            new Read_Page("标题3","渐渐消失的地平线在你脚下","www.baidu.com"),
            new Read_Page("标题4","彷徨和迷失是你付出的代价","www.baidu.com"),
            new Read_Page("标题5","那些字迹清晰又模糊","www.baidu.com"),
            new Read_Page("标题6","如果忘了自己怎么去抵达","www.baidu.com"),
            new Read_Page("标题7","翻山越岭而来的风吹痛了脸颊","www.baidu.com"),
            new Read_Page("标题8","雨落下去，等不及","www.baidu.com"),
            new Read_Page("标题9","那段沉默一切的秘密","www.baidu.com")};//pages是自己定义的类的数组

    private List<Read_Page> pageList= new ArrayList<>();//pageList是含有Page的数组
    private PageAdapter adapter_page;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readlater);
        PageAdapter adapter_page=new PageAdapter(pageList);
        RecyclerView readlist = (RecyclerView) findViewById(R.id.read_list);
        readlist.setAdapter(adapter_page);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        readlist.setLayoutManager(layoutManager);

        FloatingActionButton readAdd = (FloatingActionButton) findViewById(R.id.read_add);
        readAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent openAddPage = new Intent (ReadLater.this,AddPage.class);
                startActivity(openAddPage);
            }
        });
        TextView today_top= (TextView)  findViewById(R.id.today_top);
        today_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openToday = new Intent(ReadLater.this,MainActivity.class);
                startActivity(openToday);
            }
        });



        initPages();

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
