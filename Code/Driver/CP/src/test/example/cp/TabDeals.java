package test.example.cp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class TabDeals extends Fragment {

	private FragmentTabHost mTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.activity_main);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Đề nghị đã gửi"),
                Deals.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Đề nghị đang chờ"),
                Deals2.class, null);

        return mTabHost;
    }
    
    @Override
    public void onDestroyView() {
    super.onDestroyView();
    mTabHost = null;
    }
}
