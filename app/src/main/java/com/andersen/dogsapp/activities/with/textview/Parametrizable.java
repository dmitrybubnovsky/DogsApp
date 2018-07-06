package com.andersen.dogsapp.activities.with.textview;

public interface Parametrizable<T> {
    Class<T> createLayoutParams(T n, int hor, int ver);
}
