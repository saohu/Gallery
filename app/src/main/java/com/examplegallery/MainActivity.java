package com.examplegallery;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private LinearLayout mLinear;
    private ImageSwitcher mSwitcher;
    List<Integer> imgids;
    private ImageView []imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinear = (LinearLayout) findViewById(R.id.mLinear);
        mSwitcher = (ImageSwitcher) findViewById(R.id.mSwitcher);
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView img = new ImageView(MainActivity.this);
                return img;
            }
        });
        imgids = getImageIds();
        init();
    }

    private void init() {
        imageViews = new ImageView[imgids.size()];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60,80);
        params.setMargins(0,0,5,0);
        for (int i =0;i<imageViews.length;i++){
            imageViews[i] = new ImageView(this);
            imageViews[i].setId(imgids.get(i));
            imageViews[i].setBackgroundResource(R.drawable.bg);
            imageViews[i].setImageResource(imgids.get(i));
            imageViews[i].setLayoutParams(params);
            imageViews[i].setOnClickListener(new MyListener());
            if (i!=0){
                imageViews[i].setImageAlpha(100);
            }else {
                imageViews[i].setImageAlpha(255);

            }
                mLinear.addView(imageViews[i]);
        }
    }

    private List<Integer> getImageIds() {
        List<Integer>imageIds = new ArrayList<Integer>();
        try {

        Field[] drawableFields = R.drawable.class.getFields();
        for (Field filed:drawableFields){
         if (filed.getName().startsWith("X_")){
             imageIds.add(filed.getInt(R.drawable.class));
         }
        }
        }catch (Exception e){
            e.printStackTrace();
        }

        return imgids;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mSwitcher.setImageResource(v.getId());
            setAlpha(imageViews);
            v.setAlpha(255);
        }
    }

    private void setAlpha(ImageView[] imageViews) {
    for (int i =0;i<imageViews.length;i++){
        imageViews[i].setImageAlpha(100);
    }
    }
}
