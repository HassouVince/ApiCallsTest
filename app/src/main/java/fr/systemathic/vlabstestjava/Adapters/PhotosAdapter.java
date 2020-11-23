package fr.systemathic.vlabstestjava.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.Photo;
import fr.systemathic.vlabstestjava.R;
import fr.systemathic.vlabstestjava.Utils.Utils;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {

    private List<Photo> photos;
    private Context context;
    public PhotosAdapter(Context context, List<Photo> photos) {
        this.photos = photos;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_photo, viewGroup, false);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder viewHolder, int i) {
        viewHolder.display(photos.get(i));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class PhotosViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public PhotosViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgItemPhoto);
        }

        public void display(final Photo photo){
            Utils.displayImageWithGlide(context,imageView,photo.getUrl());
        }
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
