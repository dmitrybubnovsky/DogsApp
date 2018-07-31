package com.andersen.dogsapp.dogs.ui.dogskinds;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DogKindSource;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.ui.AppTextView;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;

import java.util.List;

public class DogsKindAdapter extends RecyclerView.Adapter<DogsKindAdapter.ViewHolder> {
    public static final String TAG = "#";

    private Context context;
    private IRecyclerItemListener<DogKind> listener;
    private DogKindSource instance;
    private List<String> kinds;
    private List<String> images;


    public DogsKindAdapter(Context context, IRecyclerItemListener listener) {
        this.context = context;
        instance = DogKindSource.get();
        kinds = instance.kindsList();
        images = instance.imagesList();
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dogKindTextView;
        private ImageView dogKindImageView;
        public DogKind dogKindInfo;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener((View view1) -> {
                if (listener != null) {
                    listener.onRecyclerItemClick(dogKindInfo);
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

        private void setData(Context context, int position){
            dogKindTextView.setText(kinds.get(position));
            dogKindImageView.setImageResource(getImageId(context, images.get(position)));
            dogKindInfo = new DogKind();
            dogKindInfo.setKind(kinds.get(position));
            dogKindInfo.setImageString(images.get(position));
            Log.d(TAG, "___dogKindInfo."+dogKindInfo.getKind());
            Log.d(TAG, "___dogKindInfo."+dogKindInfo.getImageString());
        }
    }

    private int getImageId(Context context, String imageString) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(imageString, "drawable", context.getPackageName());
        return resourceId;
    }

    @Override
    public DogsKindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dog_kind_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(context, position);
    }

    @Override
    public int getItemCount() {
        return kinds.size();
    }
}
