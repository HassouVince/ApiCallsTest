package fr.systemathic.vlabstestjava.Api;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.Album;
import fr.systemathic.vlabstestjava.Models.Comment;
import fr.systemathic.vlabstestjava.Models.CommentResponse;
import fr.systemathic.vlabstestjava.Models.Photo;
import fr.systemathic.vlabstestjava.Models.Post;
import fr.systemathic.vlabstestjava.Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    /*
      GET METHODS
     */

    //// USERS

    @GET("/users")
    Call<List<User>> getAllUsers();

    @GET("/users/{userid}")
    Call<User> getUserById(@Path("userid") int userId);

    /// POSTS

    @GET("/posts")
    Call<List<Post>> getAllPosts();

    @GET("/posts/{postid}")
    Call<Post> getPostById(@Path("postid") int postId);

    @GET("/users/{userid}/posts")
    Call<List<Post>> getPostsByUserId(@Path("userid") int userId);


    ////// ALBUMS

    @GET("/albums")
    Call<List<Album>> getAllAlbums();

    @GET("/albums/{albumid}")
    Call<Album> getAlbumById(@Path("albumid") int albumId);

    @GET("/users/{userid}/albums")
    Call<List<Album>> getAlbumsByUserId(@Path("userid") int userId);

    /// PHOTOS

    @GET("/photos/{photoid}")
    Call<Photo> getPhotoById(@Path("photoid") int photoId);

    @GET("albums/{albumid}/photos")
    Call<List<Photo>> getPhotosByAlbumId(@Path("albumid") int albumId);

    // COMMENTS
    @GET("posts/{postid}/comments")
    Call<List<Comment>> getCommentsByPostId(@Path("postid") int postId);

    /*
      POST METHODS
     */

    @Headers({"Accept:application/json", "Content-Type:application/json;"})
    @POST("/comments")
    Call<CommentResponse> postComment(@Body Comment body);
}
