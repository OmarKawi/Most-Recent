package com.example.omar.uktour11;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by omar on 12/25/2017.
 */

public class MainActivityFirst extends AppCompatActivity {

    private SectionStatePagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_avtivity_first);
        mSectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.containteromar);
        setupViewPager(mViewPager);


    }

    private void setupViewPager(ViewPager viewPager){
        SectionStatePagerAdapter adapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignIn(), "Fragment1");
        adapter.addFragment(new SignUp(), "Fragment2");

        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber)
    {
        mViewPager.setCurrentItem(fragmentNumber);
    }
}
