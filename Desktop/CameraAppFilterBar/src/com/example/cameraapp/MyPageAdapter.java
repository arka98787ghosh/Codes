package com.example.cameraapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyPageAdapter extends PagerAdapter {

    private Context mContext;

    public MyPageAdapter(Context context) {
        this.mContext = context;
    }

    // As per docs, you may use views as key objects directly 
    // if they aren't too complex
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.filter_layout, null);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // Important: page takes all available width by default,
    // so let's override this method to fit 5 pages within single screen
    @Override
    public float getPageWidth(int position) {
        return 0.2f;
    }
}
