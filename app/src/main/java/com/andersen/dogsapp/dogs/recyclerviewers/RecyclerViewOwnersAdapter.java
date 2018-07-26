package com.andersen.dogsapp.dogs.recyclerviewers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import java.util.List;

public class RecyclerViewOwnersAdapter extends RecyclerView.Adapter<RecyclerViewOwnersAdapter.ViewHolder> {
    private Context context;
    private List<Owner> owners;
    private IRecyclerItemListener <Owner> listener;

    public RecyclerViewOwnersAdapter(Context context, List<Owner> owners, IRecyclerItemListener listener) {
        this.context = context;
        this.owners = owners;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView ownerFullNameTextView;
        private TextView preferredKindTextView;
        private TextView dogsQuantityTextView;
        private RelativeLayout relativeLayout;
        private Owner owner;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(view1 -> {
                if (listener != null) {
                    listener.onRecyclerItemClick(owner);
                }
            });
            initViews(view);
        }

        private void initViews (View view){
            ownerFullNameTextView = AppTextView.newInstance(itemView, R.id.owner_fullname_item_textview)
                    .style(context, R.style.TextViewTitleItem)
                    .build();
            preferredKindTextView = AppTextView.newInstance(itemView, R.id.preffered_dog_item_textview)
                    .style(context, R.style.BoldRobotoThin13sp)
                    .build();
            dogsQuantityTextView = view.findViewById(R.id.dogs_quantity_item_textview);
            relativeLayout = view.findViewById(R.id.owners_item_relativeLayout);
        }

        private void setData(Owner owner) {
            this.owner = owner;
            ownerFullNameTextView.setText(owner.getOwnerFullName());
            preferredKindTextView.setText(owner.getPreferedDogsKind());
            dogsQuantityTextView.setText(""+owner.getDogsQuantity());

        }
    }

    @Override
    public RecyclerViewOwnersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.owners_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(owners.get(position));
    }

    @Override
    public int getItemCount() {
        return owners.size();
    }
}