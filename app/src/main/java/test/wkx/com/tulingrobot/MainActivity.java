package test.wkx.com.tulingrobot;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{

        private TabLayout tabLayout;
        private ViewPager viewPager;
        private TabLayout.Tab tab_msg;
        private TabLayout.Tab tab_host;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initView();
            initEvents();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void initView()
        {
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
            {
                private String[] mTitles = new String[]{"消息", "个人中心"};

                @Override
                public Fragment getItem(int position)
                {
                    if (position == 0)
                    {
                        return new MsgFragment();
                    }
                    else
                    {
                        return new HostFragment();
                    }

                }

                @Override
                public int getCount()
                {
                    return mTitles.length;
                }

                @Override
                public CharSequence getPageTitle(int position)
                {
                    return mTitles[position];
                }
            });

            tabLayout.setupWithViewPager(viewPager);
            tab_msg = tabLayout.getTabAt(0);
            tab_host = tabLayout.getTabAt(1);
            tab_msg.setIcon(getResources().getDrawable(R.drawable.down_msg, null));
            tab_host.setIcon(getResources().getDrawable(R.drawable.down_host, null));

        }

        private void initEvents()
        {
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
            {

                @Override
                public void onTabSelected(TabLayout.Tab tab)
                {//viewpager跟着tab
                    if (tab == tabLayout.getTabAt(0))
                    {
                        viewPager.setCurrentItem(0);
                    }
                    if (tab == tabLayout.getTabAt(1))
                    {
                        viewPager.setCurrentItem(1);
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab)
                {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab)
                {

                }
            });
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev)
        {
            if (ev.getAction() == MotionEvent.ACTION_DOWN)
            {
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev))
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                    {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
            if (getWindow().superDispatchTouchEvent(ev))
            {
                return true;
            }
            return onTouchEvent(ev);
        }

        private boolean isShouldHideInput(View v, MotionEvent event)
        {
            if (v != null && (v instanceof EditText))
            {
                int[] location = {0, 0};
                v.getLocationOnScreen(location);
                int left = location[0];
                int top = location[1];
                if (event.getX() < left || (event.getX() > left + v.getWidth())
                        || event.getY() < top || (event.getY() > top + v.getHeight()))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            return false;
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onStop() {
            super.onStop();
        }

}
