package com.andersen.dogsapp.dogs;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class AppTextView {
    private TextView textView;

    public static AppTextView newInstance(Activity activity, int id) {
        return new AppTextView(activity, id);
    }

    public static AppTextView newInstance(View rootView, int id) {
        return new AppTextView(rootView, id);
    }

    private AppTextView(Activity activity, int id) {
        this.textView = activity.findViewById(id);
    }

    private AppTextView(View rootView, int id) {
        this.textView = rootView.findViewById(id);
    }

    public AppTextView text(String text) {
        textView.setText(text);
        return this;
    }

    public AppTextView style(Context context, int style) {
        textView.setTextAppearance(context, style);
        return this;
    }

    public TextView build() {
        return textView;
    }
}
