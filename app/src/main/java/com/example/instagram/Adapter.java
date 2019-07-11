package com.example.instagram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    public ArrayList<String> banned;
    private List<Post> mPosts;
    Context context;
    // pass in the tweets array in the constructor
    public Adapter(List<Post> posts){
        mPosts = posts;
    }
    // for each row, inflate the layout and cache references into View hohjgiflgtrljdvkerhdkcnirjvcvfebnrlder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.post,parent,false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }
    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        // get the data according to position
        Post post = mPosts.get(position);
        //System.out.println(tweet);
        //populate the views according to this data
        final ParseUser user=post.getUser();
        holder.User.setText(user.getUsername());
        holder.Description.setText(post.getDescription());
        holder.CreatedAt.setText(post.getcreatedAt());
        //holder.created.setText(getRelativeTimeAgo(tweet.createdAt));
        Glide.with(context).load(post.getImage().getUrl()).into(holder.Content);
    }
    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // creat ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView Content;
        public TextView User;
        public TextView Description;
        public TextView CreatedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            // perform findViewById lookups
            Content = (ImageView) itemView.findViewById(R.id.Content);
            User = (TextView) itemView.findViewById(R.id.User);
            Description = (TextView) itemView.findViewById(R.id.description_et);
            CreatedAt = (TextView) itemView.findViewById(R.id.CreatedAt);
        }
    }
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }
}