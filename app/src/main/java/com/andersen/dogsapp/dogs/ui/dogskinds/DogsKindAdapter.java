package com.andersen.dogsapp.dogs.ui.dogskinds;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.camera.PictureUtils;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.retrofitapi.IResponseImageCallback;
import com.andersen.dogsapp.dogs.ui.AppTextView;
import com.andersen.dogsapp.dogs.ui.DogImageUtils;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;

import java.io.File;
import java.util.List;

public class DogsKindAdapter extends RecyclerView.Adapter<DogsKindAdapter.ViewHolder> {
    public static final String TAG = "#";
    private Context context;
    private IRecyclerItemListener<DogKind> listener;
    private IResponseImageCallback<String, ImageView, DogKind> responseCallback;
    private List<DogKind> dogsKinds;

    public DogsKindAdapter(Context context, IRecyclerItemListener listener) {
        this.context = context;
//        dogsKinds = DogKindSourceCoordinator.getDogKinds();
        this.listener = listener;
    }

    public void setBreeds(List<DogKind> dogsKinds) {
        this.dogsKinds = dogsKinds;
    }

    public void setResponseBreedCallbackListener(IResponseImageCallback responseCallback) {
        this.responseCallback = responseCallback;
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
            dogKindInstance = dogsKinds.get(position);
            String dogKindImageString = dogKindInstance.getUriImageString();
            String dogKindName = dogKindInstance.getKind();

            dogKindTextView.setText(dogKindName);
            if(dogKindInstance.getUriImageString().isEmpty()){
                Log.d(TAG, "DogsKindAdapter: this dogKindImageString isEmpty");
                responseCallback.onResponseImageListener(dogKindName, dogKindImageView, dogKindInstance);
            } else {
//                dogKindImageView.setImageResource(getImageId(context, dogKindImageString));
                Log.d(TAG, "DogsKindAdapter dogKindImageString - " + dogKindImageString);

                Uri uri = Uri.parse(dogKindImageString);
                dogKindImageView.setImageURI(uri);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(context, position);
    }

    @Override
    public DogsKindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dog_kind_item, parent, false);
        return new ViewHolder(view);
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
