package vn.edu.fpt.fts.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import vn.edu.fpt.fts.fragment.DealFragment;
import vn.edu.fpt.fts.fragment.InformationFragment;

public class GoodsDetailPagerAdapter extends FragmentPagerAdapter{
	public GoodsDetailPagerAdapter(FragmentManager fm){
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
			return new DealFragment();
			
		case 1:
			return new InformationFragment();			
		
		}
		return null;
	}
}
