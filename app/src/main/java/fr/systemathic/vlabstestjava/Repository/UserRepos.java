package fr.systemathic.vlabstestjava.Repository;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.User;
import retrofit2.Call;
import retrofit2.Callback;

public class UserRepos extends BaseRepos{

    public static UserRepos INSTANCE;
    private UserRepos(){super();}

    public static UserRepos getInstance(){
        if(INSTANCE == null)
            INSTANCE = new UserRepos();
        return INSTANCE;
    }

    public void getAllUsers(Callback<List<User>> retrofitCallback){
        Call<List<User>> call = service.getAllUsers();
        call.enqueue(retrofitCallback);
    }
}
