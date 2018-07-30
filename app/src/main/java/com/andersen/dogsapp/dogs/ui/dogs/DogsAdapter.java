package com.andersen.dogsapp.dogs.ui.dogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.ui.AppTextView;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;

import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {
    private Context context;
    private List<Dog> dogs;
    private IRecyclerItemListener<Dog> listener;

    public DogsAdapter(Context context, List<Dog> dogs, IRecyclerItemListener listener) {
        this.context = context;
        this.dogs = dogs;
        this.listener = listener;
    }

    public void initAdapter(Context context, List<Dog> dogs, IRecyclerItemListener listener) {
        this.context = context;
        this.dogs.clear();
        this.dogs = dogs;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dogNameTextView;
        private TextView dogKindTextView;
        private ImageView dogImageView;
        private Dog dog;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(view1 -> {
                if (listener != null) {
                    listener.onRecyclerItemClick(dog);
                }
            });
            initViews(view);
        }

        private void initViews(View view) {
            dogNameTextView = AppTextView.newInstance(itemView, R.id.dog_name_recycler_textiview)
                    .style(context, R.style.BoldRobotoThin15sp)
                    .build();
            dogKindTextView = AppTextView.newInstance(itemView, R.id.dog_kind_recycler_textiview)
                    .style(context, R.style.BoldRobotoThin13sp)
                    .build();
            dogImageView = view.findViewById(R.id.dog_recycler_image_view);
        }

        private void setData(Dog dog) {
            this.dog = dog;
            dogNameTextView.setText(dog.getDogName());
            dogKindTextView.setText(dog.getDogKind());
            dogImageView.setImageResource(dog.getDogImageId(context));
        }
    }

    @Override
    public DogsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_dog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(dogs.get(position));
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }
}
