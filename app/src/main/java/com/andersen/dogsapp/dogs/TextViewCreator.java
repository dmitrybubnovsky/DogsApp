package com.andersen.dogsapp.dogs;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.andersen.dogsapp.R;

public class TextViewCreator {
    private TextView textView;

    public TextViewCreator(){
    }

    private TextViewCreator(Activity activity, int id){
        textView = activity.findViewById(id);
    }

    private TextViewCreator(Activity activity, int id, String str){
        textView = activity.findViewById(id);
        textView.setText(str);
    }

    private TextViewCreator(View view, int id, String str){
        textView = view.findViewById(id);
        textView.setText(str);
    }

    private TextViewCreator(Context context, View view, int id, String str, int style){
        textView = view.findViewById(id);
        textView.setText(str);
        textView.setTextAppearance(context, style);
    }

    protected TextView create(Context context, View view, int id, String str, int style){
        TextViewCreator textViewCreator = new TextViewCreator(context, view, id, str, style);
        return textViewCreator.textView;
    }

    protected TextView create(Activity activity, int id){
        TextViewCreator textViewCreator = new TextViewCreator(activity, id);
        return textViewCreator.textView;
    }

    protected TextView create(View view, int id, String str){
        TextViewCreator textViewCreator = new TextViewCreator(view, id, str);
        return textViewCreator.textView;
    }

    protected TextView create(Activity activity, int id, String str){
        TextViewCreator textViewCreator = new TextViewCreator(activity, id, str);
        return textViewCreator.textView;
    }
}
