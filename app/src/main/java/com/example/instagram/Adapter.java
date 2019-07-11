package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseUser;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
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
        PrettyTime time = new PrettyTime();
        //System.out.println(tweet);
        //populate the views according to this data
        final ParseUser user=post.getUser();
        holder.User.setText(user.getUsername());
        holder.Description.setText(post.getDescription());
        holder.CreatedAt.setText(post.RelativeTime());
        Glide.with(context).load(post.getImage().getUrl()).into(holder.Content);

    }
    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // create ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView Content;
        public TextView User;
        public TextView Description;
        public TextView CreatedAt;
        public Button detailsBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            Content = (ImageView) itemView.findViewById(R.id.Content);
            User = (TextView) itemView.findViewById(R.id.User);
            Description = (TextView) itemView.findViewById(R.id.descriptionInput);
            CreatedAt = (TextView) itemView.findViewById(R.id.CreatedAt);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Post post = mPosts.get(position);
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("User",post);
            context.startActivity(intent);
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