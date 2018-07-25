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
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import java.util.List;

public class RecyclerViewOwnersAdapter {
//public class RecyclerViewOwnersAdapter extends RecyclerView.Adapter<RecyclerViewOwnersAdapter.ViewHolder> {
//    private Context context;
//    private List<Owner> owners;
//    private ItemListener listener;
//
//    public RecyclerViewOwnersAdapter(Context context, List<Owner> owners, ItemListener listener) {
//        this.context = context;
//        this.owners = owners;
//        this.listener = listener;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        private TextView ownerNameTextView;
//        private TextView ownerSurnameTextView;
//        private TextView dogKindTextView;
//        private ImageView dogImageView;
//        private RelativeLayout relativeLayout;
//        private Dog dog;
//
//        public ViewHolder(View view) {
//            super(view);
//            view.setOnClickListener(view1 -> {
//                if (listener != null) {
//                    listener.onItemClick(dog);
//                }
//            });
//            initViews(view);
//        }
//
//        private void initViews (View view){
//            dogNameTextView = AppTextView.newInstance(itemView, R.id.dog_name_recycler_textiview)
//                    .style(context, R.style.BoldRobotoThin15sp)
//                    .build();
//            dogKindTextView = AppTextView.newInstance(itemView, R.id.dog_kind_recycler_textiview)
//                    .style(context, R.style.BoldRobotoThin13sp)
//                    .build();
//            dogImageView = view.findViewById(R.id.dog_recycler_image_view);
//            relativeLayout = view.findViewById(R.id.recycler_item_relativeLayout);
//        }
//
//        private void setData(Dog dog) {
//            this.dog = dog;
//            dogNameTextView.setText(dog.getDogName());
//            dogKindTextView.setText(dog.getDogKind());
//            dogImageView.setImageResource(dog.getDogImageId(context));
//        }
//    }
//
//    @Override
//    public RecyclerViewOwnersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_dog, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.setData(owners.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return owners.size();
//    }
//
//    public interface ItemListener {
//        void onItemClick(Dog dog);
//    }
}
