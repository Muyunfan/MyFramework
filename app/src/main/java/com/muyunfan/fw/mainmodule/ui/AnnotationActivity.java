package com.muyunfan.fw.mainmodule.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.muyunfan.fw.R;
import com.muyunfan.fw.widget.utils.annotation.AnnotationUtils;
import com.muyunfan.fw.widget.utils.annotation.FindViewById;

public class AnnotationActivity extends AppCompatActivity {

    @FindViewById(R.id.tv_string)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        AnnotationUtils.injectViews(this);

        textView.setText("hello world");
    }
}
