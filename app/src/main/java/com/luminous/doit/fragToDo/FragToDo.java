package com.luminous.doit.fragToDo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.luminous.doit.R;
import com.luminous.doit.fragToDo.clock.ClockView;
import com.luminous.doit.fragToDo.message.MessAdapter;
import com.luminous.doit.fragToDo.message.MessHandler;

/**
 * Created by 90418 on 2017-05-30.
 */

public class FragToDo extends Fragment {
    private LayoutTodo layoutTodo;
    //滑动控件MessageAdapter
    private RecyclerView recyclerView;
    //时钟控件
    private ClockView clockView;
    //文字控件
    private TextClock textClock;

    public  TextView textWelcome;
    //用于线程类停止滑动，防止出现滑动过界
    private int countTodo;
    private volatile boolean scroll=true;
    //更新向上滑动
    private final int SCROLL_UP = 0;
    //更新向下滑动
    private final int SCROLL_DOWN = 1;
    //将文字时钟隐藏，时钟出现
    private final int SET_COLOR = 2;
    //将时钟隐藏，文字时钟出现
    private final int SET_TIME =3;
    //更新UI
    private MessAdapter adapter;
    private MessHandler messHandler;
    private FloatingActionButton button;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //阻止继续滑动
            if(recyclerView.getTop()>1060||recyclerView.getTop()<200)
                scroll = false;
            //向上滑动
            if(msg.what==SCROLL_UP){
                recyclerView.layout(recyclerView.getLeft(),recyclerView.getTop()-5,recyclerView.getRight(),recyclerView.getBottom());
                recyclerView.postInvalidate();
            }
            //向下滑动时要将文字控件一起滑动，否则会出现空白
            if(msg.what==SCROLL_DOWN){
                recyclerView.layout(recyclerView.getLeft(),recyclerView.getTop()+5,recyclerView.getRight(),recyclerView.getBottom());
                textClock.layout(textClock.getLeft(),textClock.getTop(),textClock.getRight(),textClock.getBottom()+5);
                recyclerView.postInvalidate();
            }
            //将时钟隐藏，文字时钟出现
            if(msg.what==SET_TIME){
                clockView.setVisibility(View.GONE);
                textClock.setVisibility(View.VISIBLE);
            }
            //将文字时钟隐藏，时钟出现
            if(msg.what==SET_COLOR){
                clockView.setVisibility(View.VISIBLE);
                textClock.setVisibility(View.GONE);
            }
        }
    };
    public FragToDo(Context context){
        layoutTodo= new LayoutTodo(context);
        messHandler = new MessHandler(context);
        initView(context);
        initEvent();
    }

    private void initEvent() {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            private  int last;//开始时的高度
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    //触摸时间开始
                    case MotionEvent.ACTION_DOWN:
                        scroll = true;//下拉控制刷新
                        last = (int) event.getRawY();//开始时的高度
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //滑动距离
                        int dy = (int) event.getRawY() - last;
                        //新的高度
                        int r = recyclerView.getTop()+dy;
                        Log.e("r", String.valueOf(r));
                        Log.e("top", String.valueOf(recyclerView.getTop()));
                        //在滑动过程中超过了上界，则直接设置效果
                        if(r<200&&clockView.getVisibility()==View.VISIBLE){
                            Message m = new Message();
                            m.what = SET_TIME;
                            handler.sendMessage(m);
                        }
                        //所有滑动的范围
                        if(r>=190&&r<1066){
                            //控件为时钟时
                            if(clockView.getVisibility()==View.VISIBLE){
                                recyclerView.layout(recyclerView.getLeft(),r,recyclerView.getRight(),recyclerView.getBottom());
                                recyclerView.postInvalidate();
                            }else {
                                //控件为文字时，并且不能滑动
                                if (!recyclerView.canScrollVertically(-1)){
                                    recyclerView.layout(recyclerView.getLeft(),r,recyclerView.getRight(),recyclerView.getBottom());
                                    //同时设置时钟的底部
                                    textClock.layout(textClock.getLeft(),textClock.getTop(),textClock.getRight(),r);
                                    recyclerView.postInvalidate();
                                }
                            }
                        }
                        //持续滑动时
                        last =(int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //设置滚动过程
                        if(clockView.getVisibility()==View.VISIBLE){
                            if(recyclerView.getTop()<750){
                                //上滑
                                scroll();
                            }else {
                                //回到原位
                                recyclerView.layout(recyclerView.getLeft(),1066,recyclerView.getRight(),recyclerView.getBottom());
                            }
                        }else {
                            if(recyclerView.getTop()>400){
                                //下滑
                                scroll();
                            }else {
                                //回到原位
                                recyclerView.layout(recyclerView.getLeft(),200,recyclerView.getRight(),recyclerView.getBottom());
                            }
                        }
                        break;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),InsetMessage.class);
                startActivity(intent);
            }
        });
    }

    private void initView(Context context) {
        recyclerView = (RecyclerView) layoutTodo.findViewById(R.id.recycle);
        clockView = (ClockView) layoutTodo.findViewById(R.id.clock );
        textClock = (TextClock) layoutTodo.findViewById(R.id.textClock);
        textWelcome=(TextView)layoutTodo.findViewById(R.id.text_welcome);
        countTodo=messHandler.findFalseMessageCount();
        if (countTodo==0){textWelcome.setText("添加今天要做的事情吧");}
        else{textWelcome.setText("你今天还有"+countTodo+"件事没有做");}
        button = (FloatingActionButton) layoutTodo.findViewById(R.id.insert_button);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        //从这个中获得数据库中的ArrayList
        adapter = new  MessAdapter(this);
        messHandler = new MessHandler(context);
        recyclerView.setAdapter(adapter);
    }
    private void scroll() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message message = new Message();
                Message whenStop = new Message();
                if (clockView.getVisibility()==View.VISIBLE){
                    message.what = SCROLL_UP;
                    whenStop.what = SET_TIME;
                }else {
                    message.what = SCROLL_DOWN;
                    whenStop.what = SET_COLOR;
                }
                for(int i = 0 ; i<=80;i++){
                    Message message2 = new Message();
                    message2.copyFrom(message);
                    handler.sendMessage(message2);
                    Log.e("topinthread", String.valueOf(recyclerView.getTop()));
                    Log.e("scroll", String.valueOf(scroll));
                    if (!scroll)
                        break;
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendMessage(whenStop);
            }
        }.start();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return layoutTodo;
    }
    @Override
    public void onStart() {
        super.onStart();
        //这里写出当Fragment开始时设置Message
        Log.e("fragTay","start");
        adapter.initMessages();
        textWelcome=(TextView)layoutTodo.findViewById(R.id.text_welcome);
        countTodo=messHandler.findFalseMessageCount();
        if (countTodo==0){textWelcome.setText("添加要做的事情吧");}
        else{textWelcome.setText("你今天还有"+countTodo+"件事没有做");}

    }
    private class LayoutTodo extends LinearLayout {
        public LayoutTodo(Context context) {
            super(context);
            LayoutInflater.from(context).inflate(R.layout.todo,this);
        }
    }
}
