package fr.systemathic.vlabstestjava.Adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.Album;
import fr.systemathic.vlabstestjava.Models.Photo;
import fr.systemathic.vlabstestjava.R;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> {

    private List<Album> albums;
    public AlbumsAdapter(List<Album> albums) {
        this.albums = albums;
    }

    @NonNull
    @Override
    public AlbumsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_albums, viewGroup, false);
        return new AlbumsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsViewHolder viewHolder, int i) {
        viewHolder.display(albums.get(i));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class AlbumsViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;

        public AlbumsViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgItemAlbums);
            textView = itemView.findViewById(R.id.tvItemAlbums);
        }

        public void display(Album album){
            textView.setText(album.getTitle());
        }
    }

    public List<Album> getAlbums() {
        return albums;
    }
}
