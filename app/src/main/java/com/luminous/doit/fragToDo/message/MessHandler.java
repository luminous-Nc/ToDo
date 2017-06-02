package com.luminous.doit.fragToDo.message;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.luminous.doit.database.MySQLiteHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by 90418 on 2017-05-30.
 */

public class MessHandler  {
        private MySQLiteHelper helper;
        public MessHandler(Context context){
            helper = MySQLiteHelper.getIntance(context);
        }
        public ArrayList<Message> getAllMessages(){
            SQLiteDatabase database = helper.getWritableDatabase();
            Calendar c = Calendar.getInstance();
            String thisdate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
            Log.e("thisdate",thisdate);
            Cursor cursor = database.query("message",null," date = '"+thisdate+"'",null,null,null," startTime",null);
            ArrayList<Message> messages = new ArrayList<>();
            if(cursor.moveToFirst()){
                Log.e("findData","don't do");
                do {
                    String start = cursor.getString(cursor.getColumnIndex("startTime"));
                    String stop = cursor.getString(cursor.getColumnIndex("stopTime"));
                    String message = cursor.getString(cursor.getColumnIndex("message"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String finish = cursor.getString(cursor.getColumnIndex("finish"));
                    Message mess = new Message.Builder().setDate(date)
                            .setMessage(message).setTimeStart(start).setTimeStop(stop).setFinish(finish).build();
                    messages.add(mess);
                }while (cursor.moveToNext());
            }
            database.close();
            Log.e("findData","Done");
            return messages;
        }
        public void deleMessage(String startTime){
            SQLiteDatabase database = helper.getWritableDatabase();
            Calendar c = Calendar.getInstance();
            String thisdate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
            String sql = "delete from message where startTime = '"+startTime+"' and date = '"+thisdate+"'";
            database.execSQL(sql);
            database.close();
        }
        public void deleMessage(String startTime,String thisdate){
            SQLiteDatabase database = helper.getWritableDatabase();
            Calendar c = Calendar.getInstance();
            String sql = "delete from message where startTime = '"+startTime+"' and date = '"+thisdate+"'";
            database.execSQL(sql);
            database.close();
        }
        public void finish(String startTime){
            SQLiteDatabase database = helper.getWritableDatabase();
            Calendar c = Calendar.getInstance();
            String thisdate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
            String sql = "update message set finish = 'true' where startTime = '"+startTime+"' and date = '"+thisdate+"'";
            database.execSQL(sql);
            database.close();
        }
        public void restart(String startTime){
            SQLiteDatabase database = helper.getWritableDatabase();
            Calendar c = Calendar.getInstance();
            String thisdate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
            String sql = "update message set finish = 'false' where startTime = '"+startTime+"' and date = '"+thisdate+"'";
            database.execSQL(sql);
            database.close();
        }
    public int findFalseMessageCount(){
        SQLiteDatabase database = helper.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        String thisdate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        Log.e("thisdate",thisdate);
        Cursor cursor = database.query("message",null," date = '"+thisdate+"' and finish = 'false' ",null,null,null," startTime",null);
        int count = 0;
        if(cursor.moveToFirst()){
            Log.e("findData","don't do");
            do {
                count++;
            }while (cursor.moveToNext());
        }
        database.close();
        Log.e("findData","Done");
        return count;
    }

}
