package com.andersen.dogsapp.dogs.ui.dogskinds;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DogKind;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.ui.AppTextView;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;

import java.util.List;

public class DogsKindAdapter extends RecyclerView.Adapter<DogsKindAdapter.ViewHolder> {
    private Context context;
    private IRecyclerItemListener<Dog> listener;
    private DogKind instance;
    private List<String> kinds;
    private List<String> images;

    public DogsKindAdapter(Context context) {
        this.context = context;
        instance = DogKind.get();
        kinds = instance.kindsList();
        images = instance.imagesList();
    }

    public void initAdapter(Context context) {
        this.context = context;
        instance = DogKind.get();
        kinds = instance.kindsList();
        images = instance.imagesList();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dogKindTextView;
        private ImageView dogKindImageView;
        private Bundle bundle;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(view1 -> {
                if (listener != null) {
                    listener.onRecyclerItemClick(bundle);
                }
            });
            initViews(view);
        }

        private void initViews(View view) {
            dogKindTextView = AppTextView.newInstance(itemView, R.id.dog_kind_item_textiview)
                    .style(context, R.style.TextViewTitleItem)
                    .build();
            dogKindImageView = view.findViewById(R.id.dog_kind_image_view);
        }
    }

    @Override
    public DogsKindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_dog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dogKindTextView.setText(kinds.get(position));
        holder.dogKindImageView.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return kinds.size();
    }
}
