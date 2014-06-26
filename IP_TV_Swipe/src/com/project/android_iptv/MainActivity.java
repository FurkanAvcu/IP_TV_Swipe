package com.project.android_iptv;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.project.android_iptv.adapter.TabsPagerAdapter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.project.android_iptv.R;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	
	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private MenuItem menuItem;
	
	public static Bitmap download_image1;
	public static ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
	public static ArrayList<Bitmap> bitmapArrayNational = new ArrayList<Bitmap>();
	public static int image_sayac=0;
	public static Boolean   bitmap_hazir=false,turkish_ch_bitti=false,turkish_ch_bitir=true,bitmap_hazir_national=false;
	public static String[] URL,URL_national;

	public static int kanal_sayisi,kanal_sayisi_national;
	// Tab titles
	private String[] tabs = { "Turkish Channels","Turkish Local Channels", "Categories" };
	public static ImageAdapter adapter;
	public static ImageAdapter_national adapter_national;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		    actionBar = getActionBar();
		    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
		        | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
		URL=Tv_logo_url_tr.URL;
		kanal_sayisi=URL.length;
		URL_national=Tv_logo_url_national.URL;
		kanal_sayisi_national=URL_national.length;
		adapter = new ImageAdapter(getApplicationContext());
		adapter_national =new ImageAdapter_national(getApplicationContext());;
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		//actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	  @Override
      public boolean onOptionsItemSelected(MenuItem item) {

				switch (item.getItemId()) {
                  case R.id.menuRefresh:
                	  menuItem = item;
                      menuItem.setActionView(R.layout.actionview);
                      menuItem.expandActionView();
                      
               bitmap_hazir=false;turkish_ch_bitti=false;turkish_ch_bitir=true;bitmap_hazir_national=false;
               Turkish_channels.grid.setAdapter(adapter);   
               Turkish_National_Channels.grid.setAdapter(adapter_national);
               
              		for (int i=0 ;i <kanal_sayisi;i++)
              		{
              			GetXMLTask task = new GetXMLTask();
              	        task.execute(new String[] { URL[i] });
              		}
              		
              		for (int i=0 ;i <kanal_sayisi_national;i++)
              		{
              			GetXMLTask task = new GetXMLTask();
              	        task.execute(new String[] { URL_national[i] });

              		}
                     return true;

                  }
                  return false;
	  }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	
	
	private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            
            return map;
        }
 
        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
        	

        	System.out.println("image_sayac"+image_sayac+"kanal sayisi national"+kanal_sayisi_national);
        	if(!turkish_ch_bitti)
        	bitmapArray.add(result);
        	else
        		bitmapArrayNational.add(result);
                       
      		 image_sayac++;
        
    	if (image_sayac==kanal_sayisi && turkish_ch_bitir)
    	{
    		turkish_ch_bitti=true;
    		turkish_ch_bitir=false;
    		bitmap_hazir=true;
    		image_sayac=0;
    		Turkish_channels.grid.setAdapter(adapter); 
    	}
    	if (turkish_ch_bitti && image_sayac==kanal_sayisi_national)
    	{   
    		bitmap_hazir_national=true;
    		image_sayac=0;
    		Turkish_National_Channels.grid.setAdapter(adapter_national); 
            menuItem.collapseActionView();
            menuItem.setActionView(null);
    	}
        }
        
 
        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
 
            try {
                stream = getHttpConnection(url);
                if (stream==null)
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tv_logo2);
          
                else
                {
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
             
            }
            return bitmap;
        }
 
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
 
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
 
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
		


}
