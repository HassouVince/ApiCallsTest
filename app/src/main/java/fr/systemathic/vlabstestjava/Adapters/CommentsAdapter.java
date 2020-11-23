package fr.systemathic.vlabstestjava.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.Comment;
import fr.systemathic.vlabstestjava.R;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private List<Comment> comments;
    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_comment, viewGroup, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder viewHolder, int i) {
        viewHolder.display(comments.get(i));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle, tvContent, tvMail;

        public CommentsViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleItemComment);
            tvContent = itemView.findViewById(R.id.tvContentItemComment);
            tvMail = itemView.findViewById(R.id.tvEmailItemComment);
        }

        public void display(final Comment comment){
            tvTitle.setText(comment.getName());
            tvContent.setText(comment.getBody());
            tvMail.setText(comment.getEmail());
        }
    }

    public List<Comment> getComments() {
        return comments;
    }
}
