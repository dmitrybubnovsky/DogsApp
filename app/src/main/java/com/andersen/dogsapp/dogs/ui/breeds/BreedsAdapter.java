package com.andersen.dogsapp.dogs.ui.breeds;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Breed;
import com.andersen.dogsapp.dogs.data.interfaces.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.data.web.retrofitapi.IResponseImageCallback;
import com.andersen.dogsapp.dogs.ui.AppTextView;

import java.util.List;

public class BreedsAdapter extends RecyclerView.Adapter<BreedsAdapter.ViewHolder> {
    public static final String TAG = "#";
    private Context context;
    private IRecyclerItemListener<Breed> listener;
    private IResponseImageCallback responseCallback;
    private List<Breed> breeds;

    public BreedsAdapter(Context context, IRecyclerItemListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }

    public void setResponseBreedCallbackListener(IResponseImageCallback responseCallback) {
        this.responseCallback = responseCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public BreedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dog_kind_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return breeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Breed breed;
        private TextView breedTextView;
        private ImageView breedImageView;
        private ProgressBar itemProgressBar;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener((View view1) -> {
                if (listener != null) {
                    listener.onRecyclerItemClick(breed);
                }
            });
            initViews(view);
        }

        private void initViews(View view) {
            breedTextView = AppTextView.newInstance(itemView, R.id.breed_item_textiview)
                    .style(context, R.style.TextViewTitleItem)
                    .build();
            breedImageView = view.findViewById(R.id.breed_image_view);
            itemProgressBar = view.findViewById(R.id.breeed_item_progressbar);
        }

        private void setData(int position) {
            breed = breeds.get(position);
            String breedImageString = breed.getUriImageString();
            String breedString = breed.getBreedString();

            breedTextView.setText(breedString);
            /*
             * если поле Breed imageString пусто, значит оно еще не сетилось,
             * тогда отправляемся в активити загружаем файл, сохраняем его путь
             * и update'им в БД это поле
             */
            if (breed.getUriImageString().isEmpty()) {
                itemProgressBar.setVisibility(View.VISIBLE);
                responseCallback.onResponseImageListener(breedString, breedImageView, breed, itemProgressBar);
            } else {
                itemProgressBar.setVisibility(View.GONE);
                Uri uri = Uri.parse(breedImageString);
                breedImageView.setImageURI(uri);
            }
        }
    }
}
