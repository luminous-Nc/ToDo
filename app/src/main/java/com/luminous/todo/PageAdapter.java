package com.luminous.todo;
//recycleView 的适配器
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PageAdapter extends RecyclerView.Adapter <PageAdapter.ViewHolder> {

    private Context mContext;

    private List<Read_Page> mRead_PageList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView PageArticle;
        TextView PageSummary;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            PageArticle = (TextView) view.findViewById(R.id.read_article);
            PageSummary = (TextView) view.findViewById(R.id.read_summary);
        }
    }
    public PageAdapter (List<Read_Page> Read_PageList) {
        mRead_PageList=Read_PageList;//把要展示的数据源传进来，以后就用list mread来操作
    }
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType) {
        if (mContext ==null) {
            mContext=parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.read_page_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        Read_Page page = mRead_PageList.get(position);
        holder.PageArticle.setText(page.getPageArticle());
        holder.PageSummary.setText(page.getPageSummary());
    }
    @Override
    public int getItemCount() {
        return mRead_PageList.size();
    }


}
