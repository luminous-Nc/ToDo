package com.luminous.doit.fragToRead.page;
//recycleView 的适配器
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.luminous.doit.MainActivity;
import com.luminous.doit.R;
import com.luminous.doit.fragToDo.InsetMessage;
import com.luminous.doit.fragToDo.message.MessHandler;
import com.luminous.doit.fragToDo.message.Message;
import com.luminous.doit.fragToRead.ReadData;
import com.luminous.doit.fragToRead.pageEdit.ChangePage;

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
    public ViewHolder onCreateViewHolder (final ViewGroup parent, int viewType) {
        if (mContext ==null) {
            mContext=parent.getContext();//获取父对象的上下文
        }
        final View view = LayoutInflater.from(mContext).inflate(R.layout.read_page_item,parent,false);//加载滚动菜单中单项的布局
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
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onLongClick(View v) {
                final PopupWindow readPopupWindow = new PopupWindow(parent.getContext());
                readPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                readPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                readPopupWindow.setContentView(LayoutInflater.from(parent.getContext()).inflate(R.layout.pop, null));
                readPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置全透明背景
                //设置背景才会返回PopupBackgroundView，这是一个内部私有类继承至FrameLayout
                // 且该类完成了对onKey和onTouch事件的分发处理
                //未设置则返回contentView 并不进行分发处理
                readPopupWindow.setOutsideTouchable(true);//点击外部是否关闭
                readPopupWindow.setFocusable(true);//是否设置焦点来响应返回键
                //readPopupWindow.setAnimationStyle(R.style.anim);
                int[] location = new int[2];
                v.getLocationOnScreen(location);//获取该控件在屏幕中的绝对左边位置 并保存到数组location中

                Log.e("location x",Integer.toString(location[0]));
                Log.e("location y",Integer.toString(location[1]));

                Button popEdit = (Button)v.findViewById(R.id.pop_edit);
                Button popDelete=(Button)v.findViewById(R.id.pop_delete);

                popEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        Read_Page page = mRead_PageList.get(position);//mRead_Page 类表中的具体一个类
                        Intent openChangePage = new Intent (v.getContext(),ChangePage.class);
                        openChangePage.putExtra(ChangePage.PAGE_ARTICLE, page.getPageArticle());
                        openChangePage.putExtra(ChangePage.PAGE_SUMMARY, page.getPageSummary());
                        openChangePage.putExtra(ChangePage.PAGE_URL, page.getPateURL());
                        openChangePage.putExtra(ChangePage.ID,page.getId());
                        //startActivity(openChangePage);
                        readPopupWindow.dismiss();
                    }
                });

                readPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY ,(location[0] + v.getWidth() - 160),
                        location[1]);//控制pop窗口的起点位置,坐标原点在左上角
                Log.e("view x",Integer.toString(v.getWidth()));
                Log.e("view y",Integer.toString(v.getHeight()));
                Log.e("pop x",Integer.toString(readPopupWindow.getWidth()));
                Log.e("pop y",Integer.toString(readPopupWindow.getHeight()));
                return true;
            }
        });
                /*popupMennu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.menu_delete){
                            MessHandler handler = new MessHandler(parent.getContext());
                            int positon = viewHolder.getAdapterPosition();
                            Message m = messages.get(positon);
                            if(m.getFinish().equals("true")){
                                Toast.makeText(fragment.getContext(),"已经完成的事情就不能删除啦",Toast.LENGTH_LONG).show();
                            }else{
                                handler.deleMessage(m.getTimeStart());
                                messages = handler.getAllMessages();
                                notifyDataSetChanged();
                            }
                        }
                        if(item.getItemId() ==R.id.menu_change){
                            //编辑的方法
                            int positon = viewHolder.getAdapterPosition();
                            Message m = messages.get(positon);
                            if(m.getFinish().equals("true")){
                                Toast.makeText(fragment.getContext(),"已经完成的事情就不能更改啦",Toast.LENGTH_LONG).show();
                            }else{
                                Intent intent = new Intent(fragment.getActivity(),InsetMessage.class);
                                intent.putExtra("startTime",m.getTimeStart())
                                        .putExtra("stopTime",m.getTimeStop())
                                        .putExtra("message",m.getMessage());
                                fragment.startActivity(intent);
                            }
                        }
                        return false;
                    }
                });
                popupMennu.show();
                return true;
            }
        });*/

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
