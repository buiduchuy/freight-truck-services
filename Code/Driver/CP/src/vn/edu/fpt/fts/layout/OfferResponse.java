package vn.edu.fpt.fts.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class OfferResponse extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.activity_offer_response, container, false);
		Button button = (Button) v.findViewById(R.id.button3);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager mng = getActivity().getSupportFragmentManager();
	    		FragmentTransaction trs = mng.beginTransaction();
	    		SendOffer frag = new SendOffer();
	    		trs.replace(R.id.content_frame, frag);
	    		trs.addToBackStack(null);
	    		trs.commit();
			}
		});
		return v;
	}
}
