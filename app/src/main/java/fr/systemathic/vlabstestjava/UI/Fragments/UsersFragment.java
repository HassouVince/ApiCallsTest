package fr.systemathic.vlabstestjava.UI.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.systemathic.vlabstestjava.Adapters.UsersAdapter;
import fr.systemathic.vlabstestjava.Models.User;
import fr.systemathic.vlabstestjava.R;
import fr.systemathic.vlabstestjava.Repository.UserRepos;
import fr.systemathic.vlabstestjava.Utils.ItemClickSupport;
import fr.systemathic.vlabstestjava.Utils.Utils;
import retrofit2.Call;
import retrofit2.Response;

public class UsersFragment extends BaseFragment {

    public interface FragmentCallback{
        void onUserClick(User user);
    }

    private RecyclerView rvUsers;
    private UsersAdapter adapter;

    public static BaseFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_users;
    }

    @Override
    protected void initViews(View view, @Nullable Bundle data) {
        rvUsers = view.findViewById(R.id.rvUsers);
        configureItemClick();
    }

    @Override
    protected void setViews(View view) {
        getUsers();
    }

    private void configureItemClick(){
        ItemClickSupport.addTo(rvUsers,getFragmentLayout())
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ((FragmentCallback) getActivity()).onUserClick(adapter.getUsers().get(position));
                    }
                });
    }

    private void getUsers() {
        dialog.show();
        UserRepos.getInstance().getAllUsers(new retrofit2.Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                dialog.dismiss();
                if(response.code() == 200)
                    setData(response.body());
                else
                    Utils.showFailureRequestMessage(getContext(),response.message());
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                dialog.dismiss();
                Utils.showFailureRequestMessage(getContext(),t.toString());
            }
        });
    }

    private void setData(List<User> users){
        dialog.dismiss();
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UsersAdapter(users);
        rvUsers.setAdapter(adapter);
        rvUsers.setVisibility(View.VISIBLE);
    }
}
