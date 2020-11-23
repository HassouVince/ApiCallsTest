package fr.systemathic.vlabstestjava.Repository;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.Album;
import retrofit2.Call;
import retrofit2.Callback;

public class AlbumRepos extends BaseRepos{

    public static AlbumRepos INSTANCE;
    private AlbumRepos(){ super(); }

    public static AlbumRepos getInstance(){
        if(INSTANCE == null)
            INSTANCE = new AlbumRepos();
        return INSTANCE;
    }

    public void getAlbumsByUserId(int userId,Callback<List<Album>> retrofitCallback){
        Call<List<Album>> call = service.getAlbumsByUserId(userId);
        call.enqueue(retrofitCallback);
    }
}
