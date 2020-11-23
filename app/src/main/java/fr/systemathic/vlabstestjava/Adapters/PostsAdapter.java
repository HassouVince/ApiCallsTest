package fr.systemathic.vlabstestjava.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.Post;
import fr.systemathic.vlabstestjava.R;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private List<Post> posts;
    public PostsAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_posts, viewGroup, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder viewHolder, int i) {
        viewHolder.display(posts.get(i));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class PostsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle, tvContent;

        public PostsViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleItemPosts);
            tvContent = itemView.findViewById(R.id.tvContentItemPosts);
        }

        public void display(final Post post){
            tvTitle.setText(post.getTitle());
            tvContent.setText(post.getBody());
        }
    }

    public List<Post> getPosts() {
        return posts;
    }
}
