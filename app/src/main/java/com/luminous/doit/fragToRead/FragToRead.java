package com.luminous.doit.fragToRead;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luminous.doit.R;
import com.luminous.doit.fragToRead.Popwindow.PopOptionUtil;
import com.luminous.doit.fragToRead.Popwindow.RecyclerItemClickListener;
import com.luminous.doit.fragToRead.page.PageAdapter;
import com.luminous.doit.fragToRead.page.Read_Page;
import com.luminous.doit.fragToRead.pageEdit.AddPage;
import com.luminous.doit.fragToRead.pageEdit.ChangePage;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.luminous.doit.MainActivity.ifNew;
import static org.litepal.LitePalApplication.getContext;


public class FragToRead extends Fragment {

    public static final String TAG = "ReadLater";

    private List<Read_Page> pageList= new ArrayList<>();//pageList是含有若干Page类的数组,是page类经过修饰加工后生成的
    private PageAdapter adapter_page;//适配器 这里写了下面就不能写PageAdapter类名 否则会有空对象
    PopOptionUtil mPop;

    LayoutToRead layoutToRead;
    public FragToRead(Context context){
        layoutToRead= new LayoutToRead(context);}


    private class LayoutToRead extends LinearLayout {
            public LayoutToRead(Context context) {
                super(context);
                LayoutInflater.from(context).inflate(R.layout.toread,this);
            }
    }//把toread布局映射到具体的实例上
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        LitePal.getDatabase();

        initPages();

        adapter_page = new PageAdapter(pageList);//构造了一个适配器的实例adapter_page
        final RecyclerView readlist = (RecyclerView) layoutToRead.findViewById(R.id.read_list);
        readlist.setAdapter(adapter_page);//recyclist绑定适配器adapterpage
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());//构造了一个管理器实例layoutManger
        readlist.setLayoutManager(layoutManager);//recyclist绑定管理器layoutManger
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                layoutToRead.findViewById(R.id.toreadtab_collapsing_toolbar);

        mPop = new PopOptionUtil(getContext());

        readlist.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), readlist,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }

                    @Override
                    public void onItemLongClick(View view, final int position) {

                        mPop.setOnPopClickEvent(new PopOptionUtil.PopClickEvent() {
                            @Override
                            public void onPreClick(){

                                Read_Page page = pageList.get(position);//mRead_Page 类表中的具体一个类
                                Intent openChangePage = new Intent (getActivity(),ChangePage.class);
                                openChangePage.putExtra(ChangePage.PAGE_ARTICLE, page.getPageArticle());
                                openChangePage.putExtra(ChangePage.PAGE_SUMMARY, page.getPageSummary());
                                openChangePage.putExtra(ChangePage.PAGE_URL, page.getPateURL());
                                openChangePage.putExtra(ChangePage.ID,page.getId());

                                startActivity(openChangePage);

                                mPop.dismiss();
                                Log.e("pop","dismiss");
                            }

                            @Override
                            public void onNextClick() {
                                //删除item
                                int id = pageList.get(position).getId();
                                String pageArticle = pageList.get(position).getPageArticle();
                                deleteData(id);

                                pageList.remove(position);//把数据从pagelist中remove掉

                                adapter_page.notifyItemRemoved(position);//显示移除的动画
                                adapter_page.notifyItemRangeChanged(0, pageList.size());//对于被删掉的位置及其后range大小范围内的view进行重新onBindViewHolder
                                // 这个需要设置，因为删除后item的position会改变

                                mPop.dismiss();
                                Log.e("pop","dismiss");
                            }
                        });
                        mPop.show(view);

                    }
                }));
        FloatingActionButton readAdd = (FloatingActionButton)layoutToRead.findViewById(R.id.read_add);
        readAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent openAddPage = new Intent (getActivity(),AddPage.class);
                startActivity(openAddPage);
            }
        }); return layoutToRead;
       }

        private void initPages(){
            Log.e("init","initPages");
            Log.e("init ifnew",Integer.toString(ifNew));
            if (ifNew == 3) {
                Log.e("init","initPages");
                Read_Page read_page = new Read_Page(1,"欢迎使用稍后阅读","有太多文章看不完？利用碎片时间阅读吧","",new Date());
                Read_Page read_page1=new Read_Page(2,"要添加文章？","任意网页中点击分享，选择ToDo即可添加","",new Date());
                Read_Page read_page2=new Read_Page(3,"要修改文章？","长按文章，即可编辑或删除","",new Date());
                read_page.save();
                read_page1.save();
                read_page2.save();
            };//pages是自己定义的类的数组
            changePages();
        }
    private void changePages(){
        List<Read_Page> newList = DataSupport.order("pageDate desc").find(Read_Page.class);
        pageList.addAll(newList);//注意要将数据复制过来，而不是直接使用，不然无法更新数据
    }
    @Override
    public void onResume() {
        super.onResume();
        pageList.clear(); //去掉之前的数据
        List<Read_Page> newList = DataSupport.order("pageDate desc").find(Read_Page.class);
        pageList.addAll(newList);//注意要将数据复制过来，而不是直接使用，不然无法更新数据
        adapter_page.notifyDataSetChanged();
    }
    public void deleteData(int id){
        Log.d(TAG,"delete:"+id);
        DataSupport.deleteAll(Read_Page.class, "id = ?",String.valueOf(id));
    }
}