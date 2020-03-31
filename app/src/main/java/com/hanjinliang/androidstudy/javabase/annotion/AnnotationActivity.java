package com.hanjinliang.androidstudy.javabase.annotion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.androidstudy.R;

import androidx.appcompat.app.AppCompatActivity;

public class AnnotationActivity extends AppCompatActivity {
    @BindView(R.id.button)
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        InjectUtils.inject(this);
    }

    @BindClick({R.id.button,R.id.textView})
    private void buttonClick(View view){
        switch (view.getId()){
            case R.id.button:
                ToastUtils.showShort("buttonClick");
                break;
            case R.id.textView:
                ToastUtils.showShort("textViewClick");
                break;
        }

    }
}
