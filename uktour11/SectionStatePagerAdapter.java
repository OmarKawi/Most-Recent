package com.example.omar.uktour11;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 12/25/2017.
 */

public class SectionStatePagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public SectionStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public  void addFragment(Fragment fragment , String title)
    {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
