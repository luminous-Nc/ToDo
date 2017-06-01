package com.luminous.doit.database;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by 90418 on 2017-05-30.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static MySQLiteHelper helper;
    private MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    private MySQLiteHelper(Context context){
        super(context, ConsDb.DATABASE_NAME, null, ConsDb.DATABASE_VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTabe = "create table "+ConsDb.TAB_NAME+" ( startTime varchar(20) , stopTime varchar(20) ,message varchar(50) ,date varchar(20),finish varchar(20))";
        db.execSQL(createTabe);
        Log.e("createData","Done");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    public synchronized static MySQLiteHelper getIntance(Context context){
        if(helper==null){
            helper = new MySQLiteHelper(context);
        }
        return helper;
    }
}
