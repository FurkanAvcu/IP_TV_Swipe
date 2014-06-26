package com.project.android_iptv.adapter;


import com.project.android_iptv.Turkish_National_Channels;
import com.project.android_iptv.Categories;
import com.project.android_iptv.Turkish_channels;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new Turkish_channels();
		case 1:
			// Games fragment activity
			return new Turkish_National_Channels();
		case 2:
			// Movies fragment activity
			return new Categories();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
