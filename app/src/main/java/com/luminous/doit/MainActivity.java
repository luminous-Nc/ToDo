package com.luminous.doit;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.luminous.doit.fragToRead.FragToRead;
import com.luminous.doit.fragToDo.FragToDo;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> frags = new ArrayList<Fragment>();
    private  ArrayList<String> tabs = new ArrayList<String>();
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;//fragmentPager的装备齐
    private TabLayout tabLayout;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
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
                break;
            case 1:
                linearLayout.setBackgroundResource(R.color.toread_background);
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
        viewPager.setCurrentItem(0);
    }
}
