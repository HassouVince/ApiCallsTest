package fr.systemathic.vlabstestjava.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import fr.systemathic.vlabstestjava.Models.Album;
import fr.systemathic.vlabstestjava.Models.Photo;
import fr.systemathic.vlabstestjava.Models.Post;
import fr.systemathic.vlabstestjava.Models.User;
import fr.systemathic.vlabstestjava.R;
import fr.systemathic.vlabstestjava.UI.Fragments.PhotosFragment;
import fr.systemathic.vlabstestjava.UI.Fragments.PostDetailsFragment;
import fr.systemathic.vlabstestjava.UI.Fragments.UserDetailsFragment;
import fr.systemathic.vlabstestjava.UI.Fragments.UsersFragment;
import fr.systemathic.vlabstestjava.Utils.Utils;

public class MainActivity extends AppCompatActivity implements UsersFragment.FragmentCallback,
        UserDetailsFragment.FragmentCallback, PhotosFragment.FragmentCallback {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        fragmentManager = getSupportFragmentManager();
        if(!checkConnection())
            return;
        setFragment(UsersFragment.newInstance());
    }

    public void setFragment(Fragment fragment) {
        if(!checkConnection())
            return;
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment,
                            fragment instanceof PostDetailsFragment ? "postfragment" :  /// TAG
                            fragment instanceof UsersFragment ? "usersfragment"     ///
                                    : null)                                            ///
                    .addToBackStack(null).commit();
        }
        else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        showNoConnexionMessage(connected);
        return connected;
    }

    private void showNoConnexionMessage(boolean isConnected) {
        if (!isConnected) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            final AlertDialog alertDialog;
            alertDialogBuilder.setMessage("Veuillez verifier votre connection a internet");
            alertDialogBuilder.setPositiveButton(getString(android.R.string.yes),
                    (dialog, which) -> MainActivity.super.recreate());
            alertDialogBuilder.setNegativeButton(getString(android.R.string.no), (dialog, which) -> MainActivity.this.finish());
            alertDialogBuilder.setCancelable(false);
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void onUserClick(User user) {
        UserDetailsFragment fragment = (UserDetailsFragment) UserDetailsFragment.newInstance();
        fragment.setUser(user);
        setFragment(fragment);
    }

    @Override
    public void onAlbumClick(Album album) {
        PhotosFragment fragment = (PhotosFragment) PhotosFragment.newInstance();
        fragment.setAlbum(album);
        setFragment(fragment);
    }

    @Override
    public void onPostClick(Post post) {
        PostDetailsFragment fragment = (PostDetailsFragment) PostDetailsFragment.newInstance();
        fragment.setPost(post);
        setFragment(fragment);
    }

    @Override
    public void onPhotoClick(Photo photo) {
        ImageView imgFullScreen = findViewById(R.id.imgFullScreen);
        imgFullScreen.setVisibility(View.VISIBLE);
        findViewById(R.id.container).setVisibility(View.GONE);
        Utils.displayImageWithGlide(this,imgFullScreen,photo.getUrl());
    }

    @Override
    public void onBackPressed() {
        UsersFragment ufragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("usersfragment");
        if(ufragment != null && ufragment.isVisible()){
            finish();
            return;
        }

        PostDetailsFragment fragment = (PostDetailsFragment) getSupportFragmentManager().findFragmentByTag("postfragment");
        if (fragment != null && fragment.isVisible() && fragment.getLayoutWriteComment().getVisibility() == View.VISIBLE) {
            fragment.getLayoutWriteComment().setVisibility(View.GONE);
        }else if(findViewById(R.id.imgFullScreen).getVisibility() == View.VISIBLE){
            findViewById(R.id.imgFullScreen).setVisibility(View.GONE);
            findViewById(R.id.container).setVisibility(View.VISIBLE);
        }else
            super.onBackPressed();
    }

}