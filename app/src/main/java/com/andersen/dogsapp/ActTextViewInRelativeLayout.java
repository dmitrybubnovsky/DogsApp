package com.andersen.dogsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActTextViewInRelativeLayout extends AppCompatActivity {
    private RelativeLayout parentRelLayout;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    private TextView createTextView (RelativeLayout.LayoutParams params, String str){
        TextView textView = new TextView(this);
        textView.setText(str);
        textView.setLayoutParams(params);
        return textView;
    }

    private RelativeLayout.LayoutParams createParams(int alignParams){
        RelativeLayout.LayoutParams layout_params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout_params.addRule(alignParams, RelativeLayout.TRUE);
        return layout_params;
    }

    private RelativeLayout.LayoutParams createParams(int alignParamsHor, int alignParamsVer){
        RelativeLayout.LayoutParams layout_params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout_params.addRule(alignParamsHor, RelativeLayout.TRUE);
        layout_params.addRule(alignParamsVer, RelativeLayout.TRUE);
        return layout_params;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        parentRelLayout = findViewById(R.id.paren_rel_layout);

        textView1 = createTextView(createParams(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.ALIGN_PARENT_TOP),
                                    "FIRST TEXT");
        parentRelLayout.addView(textView1);

        textView2 = createTextView(createParams(RelativeLayout.CENTER_IN_PARENT),
                                    "Second TEXT");
        parentRelLayout.addView(textView2);

        textView3 = createTextView(createParams(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.ALIGN_PARENT_BOTTOM),
                                    "Third TEXT");
        parentRelLayout.addView(textView3);

    }
}
