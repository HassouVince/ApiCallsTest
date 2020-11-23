package fr.systemathic.vlabstestjava.Repository;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.Photo;
import retrofit2.Call;
import retrofit2.Callback;

public class PhotoRepos extends BaseRepos{

    public static PhotoRepos INSTANCE;
    private PhotoRepos(){ super(); }

    public static PhotoRepos getInstance(){
        if(INSTANCE == null)
            INSTANCE = new PhotoRepos();
        return INSTANCE;
    }

    public void getPhotosByAlbumId(int albumId,Callback<List<Photo>> retrofitCallback){
        Call<List<Photo>> call = service.getPhotosByAlbumId(albumId);
        call.enqueue(retrofitCallback);
    }
}
