package com.andersen.dogsapp.dogs;

import android.support.v7.widget.GridLayoutManager;

public class MySpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    @Override
    public int getSpanSize(int position) {
        // 2 column size for first row
        if(position % 2 == 0){
            return 2;
        } else
            return 1;
    }
}
