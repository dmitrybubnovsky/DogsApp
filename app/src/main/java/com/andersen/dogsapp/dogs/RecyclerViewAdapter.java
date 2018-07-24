package com.andersen.dogsapp.dogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andersen.dogsapp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Dog> dogs;
    private ItemListener listener;

    public RecyclerViewAdapter(Context context, List<Dog> dogs, ItemListener listener) {
        this.context = context;
        this.dogs = dogs;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dogNameTextView;
        TextView dogKindTextView;
        ImageView dogImageView;
        RelativeLayout relativeLayout;
        Dog dog;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            dogNameTextView = AppTextView.newInstance(itemView, R.id.dog_name_recycler_textiview)
                    .style(context, R.style.BoldRobotoThin15sp)
                    .build();
            dogKindTextView = AppTextView.newInstance(itemView, R.id.dog_kind_recycler_textiview)
                    .style(context, R.style.BoldRobotoThin13sp)
                    .build();
            dogImageView = view.findViewById(R.id.dog_recycler_image_view);
            relativeLayout = view.findViewById(R.id.recycler_item_relativeLayout);
        }

        public void setData(Dog dog) {
            this.dog = dog;
            dogNameTextView.setText(dog.getDogName());
            dogKindTextView.setText(dog.getDogKind());
            dogImageView.setImageResource(dog.getDogImageId(context));
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(dog);
            }
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerivew_item, parent, false);
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

    public interface ItemListener {
        void onItemClick(Dog dog);
    }
}