package com.project.android_iptv;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import com.project.android_iptv.R;


public class Turkish_channels extends Fragment {
	
	public static GridView grid ;
	public static String[] videourl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		videourl=Tv_Url_tr.videourl;
		View rootView = inflater.inflate(R.layout.fragment_turkish_channels, container, false);
		
		 grid = (GridView) rootView.findViewById(R.id.gridView1);
		 grid.setAdapter(new ImageAdapter(this.getActivity().getApplicationContext()));
		
		 grid.setOnItemClickListener(new OnItemClickListener() {

		   public void onItemClick(AdapterView parent, View v, int position,
		     long id) {			   
			   
			   launchVLC(videourl[position]);

		   }

		  });
		
		return rootView;
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    // TODO Add your menu entries here
	    super.onCreateOptionsMenu(menu, inflater);
	}

      
    public void launchVLC (String url)
    {
    	try{
    		Intent i = new Intent(Intent.ACTION_VIEW);
    		i.setComponent(new ComponentName("org.videolan.vlc.betav7neon", "org.videolan.vlc.betav7neon.gui.video.VideoPlayerActivity"));			
    		i.setData(Uri.parse(url));			
    		startActivity(i);
    	} 
    	catch (ActivityNotFoundException e){
    		Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=org.videolan.vlc.betav7neon");
    		Intent intent = new Intent (Intent.ACTION_VIEW, uri); 
    		startActivity(intent);
    	}
    }
}
