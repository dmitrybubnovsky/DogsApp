package com.andersen.dogsapp.dogs.data.web;

public interface ICallback<T> {

    void onResponse(T t);
}
