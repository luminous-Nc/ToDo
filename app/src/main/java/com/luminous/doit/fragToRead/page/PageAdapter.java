package com.luminous.doit.fragToRead.page;
//recycleView 的适配器
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.doit.R;
import com.luminous.doit.fragToDo.InsetMessage;
import com.luminous.doit.fragToDo.message.MessHandler;
import com.luminous.doit.fragToDo.message.Message;
import com.luminous.doit.fragToRead.ReadData;
import com.luminous.doit.fragToRead.pageEdit.ChangePage;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.List;

public class PageAdapter extends RecyclerView.Adapter <PageAdapter.ViewHolder> {

    private Context mContext;
    private Fragment mFragment;


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
    public PageAdapter (List<Read_Page> Read_PageList,final Fragment fragment) {
        mRead_PageList=Read_PageList;//把要展示的数据源(一个名为pagelist的包含read_page类的数组）传进来
        // 以后就用list mread来操作
        this.mFragment = fragment;
    }
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType) {
        if (mContext ==null) {
            mContext=parent.getContext();//获取父对象的上下文
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.read_page_item,parent,false);//加载滚动菜单中单项的布局
        final ViewHolder holder= new ViewHolder(view);//加载出来的布局传入构造函数中构造出Viewholder实例

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMennuRead = new PopupMenu(mContext, v, Gravity.RIGHT);
                popupMennuRead.getMenuInflater().inflate(R.menu.menu_read, popupMennuRead.getMenu());
                popupMennuRead.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_read_delete) {
                            int position = holder.getAdapterPosition();
                            int id = mRead_PageList.get(position).getId();
                            String pageArticle = mRead_PageList.get(position).getPageArticle();
                            deleteData(id);

                           mRead_PageList.remove(position);//把数据从pagelist中remove掉
                            notifyDataSetChanged();//对于被删掉的位置及其后range大小范围内的view进行重新onBindViewHolder
                            // 这个需要设置，因为删除后item的position会改变

                        }
                        if (item.getItemId() == R.id.menu_read_change) {
                            int position = holder.getAdapterPosition();
                            Read_Page page = mRead_PageList.get(position);//mRead_Page 类表中的具体一个类
                            Intent openChangePage = new Intent (mContext,ChangePage.class);
                            openChangePage.putExtra(ChangePage.PAGE_ARTICLE, page.getPageArticle());
                            openChangePage.putExtra(ChangePage.PAGE_SUMMARY, page.getPageSummary());
                            openChangePage.putExtra(ChangePage.PAGE_URL, page.getPateURL());
                            openChangePage.putExtra(ChangePage.ID,page.getId());
                            Log.e("change","openchangepage"+ChangePage.PAGE_ARTICLE);
                             mFragment.startActivity(openChangePage);
                        }
                        return false;
                    }
                });
                popupMennuRead.show();
                return true; }

        });


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
    public void deleteData(int id){
        Log.d("delete","delete:"+id);
        DataSupport.deleteAll(Read_Page.class, "id = ?",String.valueOf(id));
    }

}
