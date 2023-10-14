package cseb.tech.smarthublms.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

        String imageUrl = post.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.postImage);
        } else {
            holder.postImage.setVisibility(View.GONE); // Hide the ImageView if there's no image
        }

        // Time stamp
        Calendar today = Calendar.getInstance();
        Calendar uploadTime = Calendar.getInstance();
        uploadTime.setTime(post.getTimestamp());

        SimpleDateFormat sdf;
        if (today.get(Calendar.YEAR) == uploadTime.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == uploadTime.get(Calendar.DAY_OF_YEAR)) {
            // If the timestamp is from today, format it as "HH:mm"
            sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            holder.tvTimeStamp.setText(sdf.format(uploadTime.getTime()));
        } else {
            // If the timestamp is from another day, format it as "E, HH:mm"
            sdf = new SimpleDateFormat("E, HH:mm", Locale.getDefault());
            holder.tvTimeStamp.setText(sdf.format(uploadTime.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView content, tvPostedBy, tvTimeStamp;
        ImageView postImage;

        PostViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.post_content);
            tvPostedBy = itemView.findViewById(R.id.tvPostedBy);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            postImage = itemView.findViewById(R.id.post_image);
        }
    }
}
