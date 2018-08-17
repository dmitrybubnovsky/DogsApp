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
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.ICallback;
import com.andersen.dogsapp.dogs.ui.AppTextView;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DogsKindAdapter extends RecyclerView.Adapter<DogsKindAdapter.ViewHolder> {
    public static final String TAG = "#";
    private Context context;
    private IRecyclerItemListener<DogKind> listener;
    private List<DogKind> dogsKinds;

    public DogsKindAdapter(Context context, IRecyclerItemListener listener) {
        this.context = context;
//        dogsKinds = DogKindSource.getDogKinds();
        this.listener = listener;
    }

    public void setBreeds(List<DogKind> dogsKinds) {
        this.dogsKinds = dogsKinds;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dogKindTextView;
        private ImageView dogKindImageView;
        public DogKind dogKindInstance;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener((View view1) -> {
                if (listener != null) {
                    listener.onRecyclerItemClick(dogKindInstance);
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

        private void setData(Context context, int position) {
            String dogKindString = dogsKinds.get(position).getKind();

            DataRepository.get().getBreedsImage(dogKindString, new ICallback<String>() {
                @Override
                public void onResponseICallback(String breedString) {
                    dogKindInstance.setImageString(breedString);
                    Log.d(TAG, "onResponseICallback "+ dogKindInstance.geUriImageString());
                    Picasso.get()
                            .load(dogKindInstance.geUriImageString())
                            .placeholder(R.drawable.afghan_hound)
                            .error(R.drawable.afghan_hound)
                            .into(dogKindImageView);
                }
            });

            dogKindTextView.setText(dogKindString);

//            String imageResourceString = dogsKinds.get(position).geUriImageString();
            String imageResourceString = "chinook";
//            dogKindImageView.setImageResource(getImageId(context, imageResourceString));

            dogKindInstance = new DogKind();
            dogKindInstance.setKind(dogKindString);
        }
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
        return dogsKinds.size();
    }

    private int getImageId(Context context, String imageString) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(imageString, "drawable", context.getPackageName());
        return resourceId;
    }
}
