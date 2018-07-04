package com.andersen.dogsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ActTextViewInFrameLayout extends AppCompatActivity {
    private FrameLayout parentFrameLayout;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentFrameLayout = new FrameLayout(this);
        ViewGroup.LayoutParams llParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(parentFrameLayout, llParams);

        textView1 = createTextView(createParams(Gravity.START | Gravity.TOP), "First method Text");
        parentFrameLayout.addView(textView1);
        textView2 = createTextView(createParams(Gravity.CENTER), "SECOND Text");
        parentFrameLayout.addView(textView2);
        textView3 = createTextView(createParams(Gravity.END | Gravity.BOTTOM), "Third");
        parentFrameLayout.addView(textView3);
    }

    private TextView createTextView(ViewGroup.LayoutParams layoutParams, String str) {
        TextView textView = new TextView(this);
        textView.setText(str);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    private FrameLayout.LayoutParams createParams(int gravity) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = gravity;
        return params;
    }
}
