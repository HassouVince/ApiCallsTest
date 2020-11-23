package fr.systemathic.vlabstestjava.Repository;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.Album;
import fr.systemathic.vlabstestjava.Models.Comment;
import fr.systemathic.vlabstestjava.Models.CommentResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class CommentRepos extends BaseRepos{

    public static CommentRepos INSTANCE;
    private CommentRepos(){ super(); }

    public static CommentRepos getInstance(){
        if(INSTANCE == null)
            INSTANCE = new CommentRepos();
        return INSTANCE;
    }

    public void getCommentsByPostId(int postId,Callback<List<Comment>> retrofitCallback){
        Call<List<Comment>> call = service.getCommentsByPostId(postId);
        call.enqueue(retrofitCallback);
    }

    public void postComment(Comment comment,Callback<CommentResponse> retrofitCallback){
        Call<CommentResponse> call = service.postComment(comment);
        call.enqueue(retrofitCallback);
    }

}
