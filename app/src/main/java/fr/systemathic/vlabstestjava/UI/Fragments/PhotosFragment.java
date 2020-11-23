package fr.systemathic.vlabstestjava.UI.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.systemathic.vlabstestjava.Adapters.PhotosAdapter;
import fr.systemathic.vlabstestjava.Models.Album;
import fr.systemathic.vlabstestjava.Models.Photo;
import fr.systemathic.vlabstestjava.R;
import fr.systemathic.vlabstestjava.Repository.PhotoRepos;
import fr.systemathic.vlabstestjava.Utils.ItemClickSupport;
import fr.systemathic.vlabstestjava.Utils.Utils;
import retrofit2.Call;
import retrofit2.Response;

public class PhotosFragment extends BaseFragment {

    public interface FragmentCallback{
        void onPhotoClick(Photo photo);
    }

    private RecyclerView rvPhotos;
    private PhotosAdapter adapter;
    private TextView tvTitle;

    private Album album;

    public static BaseFragment newInstance() {
        return new PhotosFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_photos;
    }

    @Override
    protected void initViews(View view, @Nullable Bundle data) {
        rvPhotos = view.findViewById(R.id.rvPhotos);
        tvTitle = view.findViewById(R.id.tvTitleAlbum);
        configureItemClick();
    }

    @Override
    protected void setViews(View view) {
        if(album == null){
            Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
            return;
        }

        tvTitle.setText(album.getTitle());
        getPhotos();
    }

    private void configureItemClick(){
        ItemClickSupport.addTo(rvPhotos,getFragmentLayout())
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ((FragmentCallback) getActivity()).onPhotoClick(adapter.getPhotos().get(position));
                    }
                });
    }

    private void getPhotos() {
        dialog.show();
        PhotoRepos.getInstance().getPhotosByAlbumId(album.getId(), new retrofit2.Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                dialog.dismiss();
                if(response.code() == 200)
                    setData(response.body());
                else
                    Utils.showFailureRequestMessage(getContext(),response.message());
            }
            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                dialog.dismiss();
                Utils.showFailureRequestMessage(getContext(),t.toString());
            }
        });
    }



    private void setData(List<Photo> photos){
        dialog.dismiss();
        rvPhotos.setLayoutManager(new GridLayoutManager(getContext(),4));
      //  rvPhotos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PhotosAdapter(getContext(),photos);
        rvPhotos.setAdapter(adapter);
        rvPhotos.setVisibility(View.VISIBLE);
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
