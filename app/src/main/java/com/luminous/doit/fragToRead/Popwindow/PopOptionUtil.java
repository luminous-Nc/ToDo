package com.luminous.doit.fragToRead.Popwindow;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.luminous.doit.R;

public class PopOptionUtil extends PopupWindow {

    private Context mContext;
    private int popupWidth;
    private int popupHeight;
    private PopupWindow popupWindow;
    private PopClickEvent mEvent;
    private Button popEdit;
    private Button popDelete;

    public interface PopClickEvent {
        public void onPreClick();
        public void onNextClick();
    }


    public PopOptionUtil(Context context) {
        mContext = context;
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.pop, null);//映射弹出窗口布局
        popEdit = (Button) popupView.findViewById(R.id.pop_edit);
        popDelete = (Button) popupView.findViewById(R.id.pop_delete);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//设置背景
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = popupView.getMeasuredWidth();//获取弹出窗口宽度
        popupHeight = popupView.getMeasuredHeight();//获取弹出窗口高度
    }

    public void show(View view) {//传入一个view
        initEvent();
        int[] location = new int[2];
        view.getLocationOnScreen(location);//获取该控件在屏幕中的绝对左边位置 并保存到数组location中
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() - (popupWidth)),
                location[1]+view.getHeight() - popupHeight);//控制pop窗口的起点位置,坐标原点在左上角
    }

    public void setOnPopClickEvent(PopClickEvent mEvent) {
        this.mEvent = mEvent;
    }

    private void initEvent() {
        if (mEvent != null) {
            popEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEvent.onPreClick();
                }
            });
            popDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEvent.onNextClick();
                }
            });

        }
    }

}