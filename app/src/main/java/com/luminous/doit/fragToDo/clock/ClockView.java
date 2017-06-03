package com.luminous.doit.fragToDo.clock;

/**
 * Created by 90418 on 2017-05-30.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.luminous.doit.R;

import java.util.Calendar;
import java.util.Date;

/**
 * 摘自网上
 */

public class ClockView extends View {
    private Paint circlePaint,dialPaint,numberPaint;

    private float mWidth,mHeight;//view 的宽高

    private float circleRadius;//圆的半径

    private float circleX,circleY;//圆心X,Y坐标
    private int second,minute;
    private double hour;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                invalidate();
            }
        }
    };

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        startClock();
    }

    private void initPaint(){
        //刻盘圆，小时刻度，时针和分针的画笔
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(ContextCompat.getColor(getContext(),R.color.clockCircle_Hour_Minute));

        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(10);

        //分钟刻度的画笔
        dialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dialPaint.setColor(ContextCompat.getColor(getContext(),R.color.clockSecondLine));
        dialPaint.setStrokeWidth(5);

        //数字的画笔
        numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setColor(ContextCompat.getColor(getContext(),R.color.clockNumber));
        numberPaint.setStrokeWidth(1);
        numberPaint.setTextSize(40);//数字的大小
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        if(mWidth<mHeight){
            //圆的半径为view的宽度的一半再减9，防止贴边
            circleRadius = mWidth/2-9;
            circleX = mWidth/2;
            circleY = mHeight/2;
        } else{
            circleRadius = mHeight/2-9;
            circleX = mWidth/2;
            circleY = mHeight/2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTimes();
        drawCirclePoint(canvas);
        drawCircle(canvas);
        drawDial(canvas);
        drawPointer(canvas);
    }

    /**圆心
     * @param canvas
     */
    private void drawCirclePoint(Canvas canvas){
        canvas.drawCircle(circleX,circleY,5,circlePaint);
    }

    private void drawCircle(Canvas canvas){
        canvas.drawCircle(circleX,circleY,circleRadius,circlePaint);
    }

    /**画刻度及时间
     * @param canvas
     */
    private void drawDial(Canvas canvas){
        //时钟用长一点的刻度，画笔用画圆的画笔
        Point hourStartPoint = new Point(circleX,circleY-circleRadius);
        Point hourEndPoint = new Point(circleX,circleY-circleRadius+40);
        //分钟的刻度要稍微短一些，画笔用画圆的画笔
        Point startPoint2 = new Point(circleX,circleY-circleRadius);
        Point endPoint2 = new Point(circleX,circleY-circleRadius+10);
        //开始画刻度和数字，总共60个刻度，12个时钟刻度，被5整除画一个时钟刻度，被其余的为分针刻度
        String clockNumber;
        for(int i=0;i<60;i++){
            if(i%5==0){
                if(i==0){
                    clockNumber = "12";
                } else{
                    clockNumber = String.valueOf(i/5);
                }
                //时针刻度
                canvas.drawLine(hourStartPoint.getX(),hourStartPoint.getY(),hourEndPoint.getX(),hourEndPoint.getY(),circlePaint);
                //画数字，需在时针刻度末端加30(距离圆边缘的距离
                if (i%3==0){
                canvas.drawText(clockNumber,circleX-numberPaint.measureText(clockNumber)/2,hourEndPoint.getY()+40,numberPaint);}
            } else{
                //画分针刻度
                canvas.drawLine(startPoint2.getX(),startPoint2.getY(),endPoint2.getX(),endPoint2.getY(),circlePaint);
            }
            //画布旋转6度
            canvas.rotate(360/60,circleX,circleY);
        }
    }

    /**画指针
     * X点坐标 cos(弧度)*r
     * Y点坐标 sin(弧度)*r
     * toRadians将角度转成弧度
     * 安卓坐标系与数学坐标系不同的地方是X轴是相反的，所以为了调整方向，需要将角度+270度
     * @param canvas
     */
    private void drawPointer(Canvas canvas){
        canvas.translate(circleX,circleY);
        float hourX = (float) Math.cos(Math.toRadians(hour*30+270))*circleRadius*0.5f;
        float hourY = (float) Math.sin(Math.toRadians(hour*30+270))*circleRadius*0.5f;
        float minuteX = (float) Math.cos(Math.toRadians(minute*6+270))*circleRadius*0.8f;
        float minuteY = (float) Math.sin(Math.toRadians(minute*6+270))*circleRadius*0.8f;
        float secondX = (float) Math.cos(Math.toRadians(second*6+270))*circleRadius*0.8f;
        float secondY = (float) Math.sin(Math.toRadians(second*6+270))*circleRadius*0.8f;
        canvas.drawLine(0,0,hourX,hourY,circlePaint);
        canvas.drawLine(0,0,minuteX,minuteY,circlePaint);
        canvas.drawLine(0,0,secondX,secondY,dialPaint);
        //一秒重绘一次
        handler.sendEmptyMessageDelayed(0,1000);
    }

    public void startClock(){
        setTimes();
        invalidate();
    }

    private void setTimes(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        second = getTimes(date,Calendar.SECOND);
        minute = getTimes(date,Calendar.MINUTE);
        hour = getTimes(date,Calendar.HOUR)+minute/12*0.2;
    }

    private int getTimes(Date date,int calendarField){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendarField);
    }

    public void stopClock(){
        handler.removeMessages(0);
    }
}
