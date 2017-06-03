package com.luminous.doit.fragToRead.page;
//recycleView 的适配器
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luminous.doit.R;
import com.luminous.doit.fragToRead.ReadData;

import java.text.SimpleDateFormat;
import java.util.List;

public class PageAdapter extends RecyclerView.Adapter <PageAdapter.ViewHolder> {

    private Context mContext;

    public List<Read_Page> mRead_PageList;//包含read_page类的数组

    static class ViewHolder extends RecyclerView.ViewHolder {//ViewHolder类 负责滚动列表中显示的内容
        CardView cardView;
        TextView PageArticle;
        TextView PageSummary;

        public ViewHolder(View view) {//构造函数
            super(view);
            cardView = (CardView) view;//各具体变量与布局中各项控件相对应
            PageArticle = (TextView) view.findViewById(R.id.read_article);
            PageSummary = (TextView) view.findViewById(R.id.read_summary);
        }
    }
    public PageAdapter (List<Read_Page> Read_PageList) {
        mRead_PageList=Read_PageList;//把要展示的数据源(一个名为pagelist的包含read_page类的数组）传进来
        // 以后就用list mread来操作
    }
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType) {
        if (mContext ==null) {
            mContext=parent.getContext();//获取父对象的上下文
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.read_page_item,parent,false);//加载滚动菜单中单项的布局
        final ViewHolder holder= new ViewHolder(view);//加载出来的布局传入构造函数中构造出Viewholder实例

        holder.cardView.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int position = holder.getAdapterPosition();
            Read_Page page = mRead_PageList.get(position);//mRead_Page 类表中的具体一个类
            Intent toReadData = new Intent(mContext, ReadData.class);
            toReadData.putExtra(ReadData.PAGE_ARTICLE, page.getPageArticle());
            toReadData.putExtra(ReadData.PAGE_SUMMARY, page.getPageSummary());
            toReadData.putExtra(ReadData.PAGE_URL, page.getPateURL());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间显示的样例
            String DateforInt = format.format(page.getPageDate());
            toReadData.putExtra(ReadData.PAGE_DATE,DateforInt);
            mContext.startActivity(toReadData);
        }
        });
        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        holder.cardView.setBackgroundResource(R.color.page_when_press);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        holder.cardView.setBackgroundResource(R.color.page_when_normal);
                        break;
                    case MotionEvent.ACTION_UP:
                        holder.cardView.setBackgroundResource(R.color.page_when_normal);
                        break;
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {//每个子项滚动到屏幕中时会对它赋值
        Read_Page page = mRead_PageList.get(position);
        holder.PageArticle.setText(page.getPageArticle());
        holder.PageSummary.setText(page.getPageSummary());
    }
    @Override
    public int getItemCount() {
        return mRead_PageList.size();
    }//统计一共多少子项


}
