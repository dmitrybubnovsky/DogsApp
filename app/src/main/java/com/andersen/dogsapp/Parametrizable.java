package com.andersen.dogsapp;

public interface Parametrizable<T> {
    Class<T> createLayoutParams(T n, int hor, int ver);
}
