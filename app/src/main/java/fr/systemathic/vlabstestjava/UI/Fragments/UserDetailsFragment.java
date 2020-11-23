package fr.systemathic.vlabstestjava.UI.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.systemathic.vlabstestjava.Adapters.AlbumsAdapter;
import fr.systemathic.vlabstestjava.Adapters.PostsAdapter;
import fr.systemathic.vlabstestjava.Models.Album;
import fr.systemathic.vlabstestjava.Models.Post;
import fr.systemathic.vlabstestjava.Models.User;
import fr.systemathic.vlabstestjava.R;
import fr.systemathic.vlabstestjava.Repository.AlbumRepos;
import fr.systemathic.vlabstestjava.Repository.PostRepos;
import fr.systemathic.vlabstestjava.Utils.ItemClickSupport;
import fr.systemathic.vlabstestjava.Utils.Utils;
import retrofit2.Call;
import retrofit2.Response;

public class UserDetailsFragment extends BaseFragment {

    public interface FragmentCallback{
        void onAlbumClick(Album album);
        void onPostClick(Post post);
    }

    private AlbumsAdapter albumsAdapter;
    private PostsAdapter postsAdapter;
    private RecyclerView rvAlbums, rvPosts;
    private TextView tvAlbums, tvPosts, tvUserName, tvName, tvEmail, tvPhone,
            tvAddress, tvWebsite, tvCompany, tvCompanyPhrase, tvCompanyBs;

    private User user;

    public static BaseFragment newInstance() {
        return new UserDetailsFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_user_details;
    }

    @Override
    protected void initViews(View view, @Nullable Bundle data) {
        rvAlbums = view.findViewById(R.id.rvAlbums);
        rvPosts = view.findViewById(R.id.rvPosts);
        tvAlbums = view.findViewById(R.id.tvAlbums);
        tvPosts = view.findViewById(R.id.tvPosts);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvCompany = view.findViewById(R.id.tvCompanyName);
        tvCompanyBs = view.findViewById(R.id.tvCompanyBs);
        tvCompanyPhrase = view.findViewById(R.id.tvCompanyPhrase);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvName = view.findViewById(R.id.tvName);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvWebsite = view.findViewById(R.id.tvWebSite);

        configureItemClick();
    }

    @Override
    protected void setViews(View view) {
        if(user == null) {
            Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
            return;
        }

        setUserData();
        getAlbums();
    }

    private void configureItemClick(){
        ItemClickSupport.addTo(rvAlbums,getFragmentLayout())
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ((FragmentCallback) getActivity()).onAlbumClick(albumsAdapter.getAlbums().get(position));
                    }
                });

        ItemClickSupport.addTo(rvPosts,getFragmentLayout())
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ((FragmentCallback) getActivity()).onPostClick(postsAdapter.getPosts().get(position));
                    }
                });
    }


    private void getAlbums() {
        dialog.show();
        AlbumRepos.getInstance().getAlbumsByUserId(user.getId(), new retrofit2.Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.code() == 200) {
                    setAlbumsData(response.body());
                    getPosts();
                }else {
                    dialog.dismiss();
                    Utils.showFailureRequestMessage(getContext(), response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                dialog.dismiss();
                Utils.showFailureRequestMessage(getContext(),t.toString());
            }
        });
    }

    private void getPosts() {
        if(!dialog.isShowing())
            dialog.show();

        PostRepos.getInstance().getPostsByUserId(user.getId(), new retrofit2.Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                dialog.dismiss();
                if(response.code() == 200)
                    setPostsData(response.body());
                else
                    Utils.showFailureRequestMessage(getContext(),response.message());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                dialog.dismiss();
                Utils.showFailureRequestMessage(getContext(),t.toString());
            }
        });
    }

    private void setUserData(){
        tvUserName.setText(user.getUsername());
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());
        tvWebsite.setText(user.getWebsite());
        tvCompanyPhrase.setText("\"" + user.getCompany().getCatchPhrase() + "\"");
        tvCompany.setText(user.getCompany().getName());
        tvCompanyBs.setText(user.getCompany().getBs());

        User.Address ad = user.getAddress();
        String address = ad.getStreet() + " " + ad.getCity() + " " + ad.getSuite() + " " + ad.getZipcode();
        tvAddress.setText(address);
    }

    private void setAlbumsData(List<Album> albums){
        tvAlbums.setText("Albums (" + albums.size() + ")");
        rvAlbums.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        albumsAdapter = new AlbumsAdapter(albums);
        rvAlbums.setAdapter(albumsAdapter);
        rvAlbums.setVisibility(View.VISIBLE);
    }

    private void setPostsData(List<Post> posts){
        tvPosts.setText("Posts (" + posts.size() + ")");
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        postsAdapter = new PostsAdapter(posts);
        rvPosts.setAdapter(postsAdapter);
        rvPosts.setVisibility(View.VISIBLE);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
