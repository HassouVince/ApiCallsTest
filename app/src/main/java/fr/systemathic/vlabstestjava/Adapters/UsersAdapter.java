package fr.systemathic.vlabstestjava.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.systemathic.vlabstestjava.Models.User;
import fr.systemathic.vlabstestjava.R;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private List<User> users;
    public UsersAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_users, viewGroup, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder viewHolder, int i) {
        viewHolder.display(users.get(i));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class UsersViewHolder extends RecyclerView.ViewHolder{

        private TextView tv, tvName, tvUserName;

        public UsersViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvItemUsers);
            tvName = itemView.findViewById(R.id.tvNameItemUsers);
            tvUserName = itemView.findViewById(R.id.tvUserNameItemUsers);
        }

        public void display(final User user){
            tv.setText(user.getUsername().substring(0,1));
            tvName.setText(user.getName());
            tvUserName.setText(user.getUsername());
        }
    }

    public List<User> getUsers() {
        return users;
    }
}
