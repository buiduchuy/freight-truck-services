package vn.edu.fpt.fts.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import vn.edu.fpt.fts.fragment.GoodsFragment;
import vn.edu.fpt.fts.fragment.TrackFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter{
	public TabsPagerAdapter(FragmentManager fm){
		super(fm);
	}
	
	@Override
	public int getCount() {
		return 2;
	}
	
	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new GoodsFragment();
			
		case 1:
			return new TrackFragment();
		
		}
		return null;
	}

}
