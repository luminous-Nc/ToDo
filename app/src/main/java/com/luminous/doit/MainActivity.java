package com.luminous.doit;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.luminous.doit.fragToRead.FragToRead;
import com.luminous.doit.fragToDo.FragToDo;
import java.util.ArrayList;

import static com.luminous.doit.R.id.toolbar;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> frags = new ArrayList<Fragment>();
    private  ArrayList<String> tabs = new ArrayList<String>();
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;//fragmentPager的装备齐
    private TabLayout tabLayout;
    private LinearLayout linearLayout;

    public static int ifNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        SharedPreferences.Editor editor=getSharedPreferences("introduce",MODE_PRIVATE).edit();
        SharedPreferences readIfNew =getSharedPreferences("introduce",MODE_PRIVATE);
        ifNew = readIfNew.getInt("ifNew",1);
        Log.e("ifNEw",Integer.toString(ifNew));
        if (ifNew==1){
            editor.putInt("ifNew",3);
            editor.apply();
            ifNew=3;
            Log.e("changeifnew",Integer.toString(ifNew));
        }else{ifNew=2;}
    }

    private void initEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setColor(int position) {
        switch (position){
            case 0:
                linearLayout.setBackgroundResource(R.color.todo_background);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.todo_background));}
                break;
            case 1:
                linearLayout.setBackgroundResource(R.color.toread_background_tree);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.toread_background_tree));}
                break;
        }
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        linearLayout = (LinearLayout) findViewById(R.id.layout_of_tab);
        frags.add(new FragToDo(getBaseContext()));
        frags.add(new FragToRead(getBaseContext()));
        tabs.add("To Do");
        tabs.add("To Read");
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return frags.get(position);
            }
            @Override
            public int getCount() {
                return frags.size();
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs.get(position);
            }
        };
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.OnTabSelectedListener
        viewPager.setCurrentItem(0);
    }
    //int colorFrom = linearLayout.getDrawingCacheBackgroundColor();
    //int colorTo =
    //ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
}
