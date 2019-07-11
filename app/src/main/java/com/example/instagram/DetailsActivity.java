package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
public class DetailsActivity extends AppCompatActivity {
     ImageView Content;
     TextView description_et;
     TextView User;
     TextView CreatedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);
        Intent intent = getIntent();
        Post post = (intent.getParcelableExtra("User"));
        Content = findViewById(R.id.Content);
        description_et = findViewById(R.id.description_et);
        User = findViewById(R.id.User);
        CreatedAt = findViewById(R.id.CreatedAt);
        User.setText(post.getUser().getUsername());
        CreatedAt.setText(post.getcreatedAt().toString());
        description_et.setText(post.getDescription());
        Glide.with(this).load(post.getImage().getUrl()).into(Content);
    }
}
