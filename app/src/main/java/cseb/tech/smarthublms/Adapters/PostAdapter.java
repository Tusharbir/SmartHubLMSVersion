package cseb.tech.smarthublms.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cseb.tech.smarthublms.Post;
import cseb.tech.smarthublms.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.content.setText(post.getContent());

        String postedBy = post.getPostedBy();
        Log.d("PostAdapter", "Posted by: " + postedBy);  // Debug log
        holder.tvPostedBy.setText(post.getPostedBy());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView content, tvPostedBy;

        PostViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.post_content);
            tvPostedBy = itemView.findViewById(R.id.tvPostedBy);
        }
    }
}
