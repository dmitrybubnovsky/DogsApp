package com.andersen.dogsapp;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomTextView {
    private TextView textView;

    static CustomTextView getInstance(Context context, int textViewId, String str){
        CustomTextView instance = new CustomTextView(context, textViewId,  str);
        return instance;
    }

    static CustomTextView getInstance(Context context, String str){
        CustomTextView instance = new CustomTextView(context, str);
        return instance;
    }

    TextView getCustomTextView(){
        return textView;
    }

    private CustomTextView(Context context, int textViewId, String str){
        textView = new TextView(context);
        textView.setId(textViewId);
        textView.setText(str);
    }

    private CustomTextView(Context context, String str){
        textView = new TextView(context);
        textView.setText(str);

    }
}
