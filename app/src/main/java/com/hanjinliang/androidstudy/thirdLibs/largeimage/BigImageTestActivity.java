package com.hanjinliang.androidstudy.thirdLibs.largeimage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hanjinliang.androidstudy.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class BigImageTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image_test);

    }

    public void test(View view){
        ArrayList<String> images=new ArrayList<>();
        images.add("https://ws1.sinaimg.cn/large/610dc034ly1fh7hwi9lhzj20u011hqa9.jpg");
        images.add("https://ws1.sinaimg.cn/large/610dc034ly1fgllsthvu1j20u011in1p.jpg");
        images.add("https://ws1.sinaimg.cn/large/610dc034ly1fgj7jho031j20u011itci.jpg");
        images.add("https://ws1.sinaimg.cn/large/610dc034ly1fgi3vd6irmj20u011i439.jpg");
        images.add("https://ws1.sinaimg.cn/large/610dc034ly1fgepc1lpvfj20u011i0wv.jpg");
        images.add("https://ws1.sinaimg.cn/large/610dc034ly1fgdmpxi7erj20qy0qyjtr.jpg");
        images.add("https://ws1.sinaimg.cn/large/610dc034ly1fgchgnfn7dj20u00uvgnj.jpg");
        images.add("https://ws1.sinaimg.cn/large/d23c7564ly1fg6qckyqxkj20u00zmaf1.jpg");
        images.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");

        Bundle bundle = new Bundle();
        bundle.putInt("selet",5);
        bundle.putStringArrayList("imageuri",images);
        startActivity(new Intent(this, ViewBigImageActivity.class).putExtras(bundle));
    }
}
