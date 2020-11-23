package fr.systemathic.vlabstestjava.Repository;

import fr.systemathic.vlabstestjava.Api.ApiInterface;
import fr.systemathic.vlabstestjava.Api.RetrofitClient;

public class BaseRepos {
    protected final ApiInterface service;
    protected BaseRepos(){
        service = RetrofitClient.getClient().create(ApiInterface.class);
    }
}
