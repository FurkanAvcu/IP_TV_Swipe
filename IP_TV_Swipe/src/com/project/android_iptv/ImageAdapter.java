package com.project.android_iptv;

import android.content.Context;            
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.project.android_iptv.R;


public class ImageAdapter extends BaseAdapter {
 Context mcontext;
 public static  ImageView imageview;
 public ImageAdapter(Context c)
 {
  mcontext=c;
 }
 public int getCount() {  //eleman sayýsýný tutuyo
  // TODO Auto-generated method stub
  return MainActivity.kanal_sayisi;
 }
   //secilen elemanýn konumunu tutuyo
 public Object getItem(int position) {
  // TODO Auto-generated method stub
  return null;
 }

 public long getItemId(int position) {
  // TODO Auto-generated method stub
  return 0;
 }


 public View getView(int position, View convertView, ViewGroup parent) {
  // TODO Auto-generated method stub
  
  if(convertView==null){
   imageview=new ImageView(mcontext);
   imageview.setLayoutParams(new GridView.LayoutParams(200,200));
   imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
   imageview.setPadding(5, 5, 5, 5);
  }
  else
   imageview=(ImageView) convertView;
   imageview.setImageResource( R.drawable.tv_logo2);
   if (MainActivity.bitmap_hazir)
   	imageview.setImageBitmap(MainActivity.bitmapArray.get(position));
 //  System.out.println("position="+position);
  return imageview;
 };
 
}