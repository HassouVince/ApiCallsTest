package fr.systemathic.vlabstestjava.Repository;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.Album;
import fr.systemathic.vlabstestjava.Models.Post;
import retrofit2.Call;
import retrofit2.Callback;

public class PostRepos extends BaseRepos{

    public static PostRepos INSTANCE;
    private PostRepos(){ super(); }

    public static PostRepos getInstance(){
        if(INSTANCE == null)
            INSTANCE = new PostRepos();
        return INSTANCE;
    }

    public void getPostsByUserId(int userId,Callback<List<Post>> retrofitCallback){
        Call<List<Post>> call = service.getPostsByUserId(userId);
        call.enqueue(retrofitCallback);
    }

}
