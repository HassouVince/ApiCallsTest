package fr.systemathic.vlabstestjava.UI.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.systemathic.vlabstestjava.Adapters.CommentsAdapter;
import fr.systemathic.vlabstestjava.Models.Comment;
import fr.systemathic.vlabstestjava.Models.CommentResponse;
import fr.systemathic.vlabstestjava.Models.Post;
import fr.systemathic.vlabstestjava.R;
import fr.systemathic.vlabstestjava.Repository.CommentRepos;
import fr.systemathic.vlabstestjava.Utils.Utils;
import retrofit2.Call;
import retrofit2.Response;

public class PostDetailsFragment extends BaseFragment {

    private RecyclerView rvComments;
    private CommentsAdapter adapter;
    private TextView tvTitle, tvContent, tvComments;
    private LinearLayout layoutWriteComment;
    private Button btnWrite, btnSend;
    private EditText edName, edBody, edEmail;

    private Post post;

    public static BaseFragment newInstance() {
        return new PostDetailsFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_post_details;
    }

    @Override
    protected void initViews(View view, @Nullable Bundle data) {
        rvComments = view.findViewById(R.id.rvComments);
        tvTitle = view.findViewById(R.id.tvTitlePost);
        tvContent = view.findViewById(R.id.tvContentPost);
        tvComments = view.findViewById(R.id.tvComments);
        layoutWriteComment = view.findViewById(R.id.layoutWriteComment);
        btnSend = view.findViewById(R.id.btnSendComment);
        btnWrite = view.findViewById(R.id.btnWriteComment);
        edBody = view.findViewById(R.id.edBodyComment);
        edEmail = view.findViewById(R.id.edEmailComment);
        edName = view.findViewById(R.id.edNameComment);

        btnSend.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
    }

    @Override
    protected void setViews(View view) {
        if(post == null){
            Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
            return;
        }

        tvTitle.setText(post.getTitle());
        tvContent.setText(post.getBody());
        getComments();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSendComment:
                sendComment();
                break;
            case R.id.btnWriteComment:
                showWriteLayout();
                break;
        }
    }

    private void getComments() {
        dialog.show();
        CommentRepos.getInstance().getCommentsByPostId(post.getId(), new retrofit2.Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                dialog.dismiss();
                if(response.code() == 200)
                    setData(response.body());
                else
                    Utils.showFailureRequestMessage(getContext(),response.message());
            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                dialog.dismiss();
                Utils.showFailureRequestMessage(getContext(),t.toString());
            }
        });
    }

    private void setData(List<Comment> comments){
        dialog.dismiss();
        tvComments.setText("Comments (" + comments.size() + ")");
        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentsAdapter(comments);
        rvComments.setAdapter(adapter);
        rvComments.setVisibility(View.VISIBLE);
    }

    private void showWriteLayout(){
        edName.setText("");
        edEmail.setText("");
        edBody.setText("");
        layoutWriteComment.setVisibility(View.VISIBLE);
    }

    private void sendComment(){
        if(!isEntriesAllowed())
            return;
        dialog.show();
        String name = edName.getText().toString();
        String body = edBody.getText().toString();
        String email = edEmail.getText().toString();
        final Comment comment = new Comment(post.getId(),name,body,email);
        CommentRepos.getInstance().postComment(comment, new retrofit2.Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                dialog.dismiss();
                if(response.code() == 201){
                    addNewComment(comment,response.body());
                }else{
                    Utils.showFailureRequestMessage(getContext(),response.message());
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                dialog.dismiss();
                Utils.showFailureRequestMessage(getContext(),t.toString());
            }
        });
    }

    private void addNewComment(Comment comment, CommentResponse response){
        Toast.makeText(getContext(), "Votre commentaire a bien été ajouté ! Id du commentaire : " +
                response.getId(), Toast.LENGTH_SHORT).show();
        ((CommentsAdapter) rvComments.getAdapter()).getComments().add(comment);
        rvComments.getAdapter().notifyDataSetChanged();
        layoutWriteComment.setVisibility(View.GONE);
    }

    private boolean isEntriesAllowed(){
        if(edBody.getText().toString().isEmpty()){
            edBody.setError("Veuillez entrer un commentaire");
            return false;
        }
        if(edName.getText().toString().isEmpty()){
            edName.setError("Veuillez entrer un Nom/Titre");
            return false;
        }
        if(edEmail.getText().toString().isEmpty() || !Utils.isValidEmail(edEmail.getText().toString())){
            edEmail.setError("Veuillez entrer une adresse mail valide");
            return false;
        }

        return true;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LinearLayout getLayoutWriteComment() {
        return layoutWriteComment;
    }
}

