package com.luminous.doit.fragToDo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.luminous.doit.R;
import com.luminous.doit.database.MySQLiteHelper;
import com.luminous.doit.fragToDo.message.MessHandler;
import com.luminous.doit.fragToDo.message.Message;

import java.util.Calendar;
import java.util.Date;
/**
 * Created by 90418 on 2017-05-30.
 */

public class InsetMessage extends AppCompatActivity {
    private TimePicker start;
    private TimePicker stop;
    private EditText massage;
    public MySQLiteHelper helper;
    private boolean isAdd = true;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_message);
        helper = MySQLiteHelper.getIntance(getBaseContext());
        Toolbar ToolBar=(Toolbar) findViewById(R.id.insert_message_toolbar);
        setSupportActionBar(ToolBar);
        android.support.v7.app.ActionBar addActionBar = getSupportActionBar();
        addActionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        initEvent();
    }

    private void initEvent() {
        massage.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN){
                    InputMethodManager imm=(InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive()){
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                    }
                }
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        start = (TimePicker) findViewById(R.id.start_time);
        stop = (TimePicker) findViewById(R.id.stop_time);
        massage = (EditText) findViewById(R.id.message);
        start.setIs24HourView(true);
        stop.setIs24HourView(true);
        Intent intent = getIntent();
        if(intent.getStringExtra("startTime")!=null){
            String[] startTime = intent.getStringExtra("startTime").split(":");
            start.setHour(Integer.valueOf(startTime[0]));
            start.setMinute(Integer.valueOf(startTime[1]));
            String[] stopTime = intent.getStringExtra("stopTime").split(":");
            stop.setHour(Integer.valueOf(stopTime[0]));
            stop.setMinute(Integer.valueOf(stopTime[1]));
            massage.setText(intent.getStringExtra("message"));
            isAdd = false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insert_message_item,menu);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.insert_message_sure:
                if(isAdd){
                    finishAdd();
                }else {
                    MessHandler handler = new MessHandler(getBaseContext());
                    handler.deleMessage(getIntent().getStringExtra("startTime"));
                    finishAdd();
                }
        }
        return super.onOptionsItemSelected(item);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void finishAdd(){
        String startTime = ""+ (start.getHour()<10 ? "0"+start.getHour():start.getHour())+":"+(start.getMinute()<10 ? "0"+start.getMinute():start.getMinute());
        String stopTime = ""+(stop.getHour()<10 ? "0"+stop.getHour():stop.getHour())+":"+(stop.getMinute()<10 ? "0"+stop.getMinute():stop.getMinute());
        String theMessage = massage.getText().toString();
        Log.e("message",theMessage);
        Log.e("startTIme",startTime);
        Log.e("stoptime",stopTime);
        if(theMessage.equals("")||theMessage==null){
            Toast.makeText(InsetMessage.this,"你还没有输入想做的事呢",Toast.LENGTH_LONG).show();
            Log.e("wrong","1");
            massage.setHintTextColor(ContextCompat.getColor(getBaseContext(),R.color.insertMessage_when_wrong));
            return;
        }
        if(startTime.equals(stopTime)){
            Log.e("wrong","2");
            Toast.makeText(InsetMessage.this,"把结束的时间设置的更长吧",Toast.LENGTH_LONG).show();
            return;
        }
        Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        Log.e("date",date);
        for(Message mess : new MessHandler(getBaseContext()).getAllMessages()){
            if(mess.getTimeStart().equals(startTime)){
                Toast.makeText(InsetMessage.this,"你在这段时间内已经有事情要做了",Toast.LENGTH_LONG).show();
                return;
            }
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into message values ('"+startTime+"','"+stopTime+"','"+theMessage+"','"+date+"','false')";
        db.execSQL(sql);
        db.close();
        this.finish();
        Toast.makeText(this,"待做事件已添加",Toast.LENGTH_SHORT).show();
    }

}
