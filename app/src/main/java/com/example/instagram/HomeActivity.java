package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private EditText descriptionInput;
    private ImageButton createButton;
    private ParseUser user;
    private String CreatedAt;
    private String description;
    private ParseFile image;
    private ImageButton logoutBtn;
    private ImageView imageView;
    private ImageView ivPreview;
    private RecyclerView rvPosts;
    Adapter adapter;
    ArrayList<Post> posts;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user=ParseUser.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        if(ParseUser.getCurrentUser()==null){
            Log.e("Is there a user?","No");
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{Log.e("Is there a user?","yes");}

        swipeContainer = findViewById(R.id.swipeContainer);
        descriptionInput = findViewById(R.id.descriptionInput);
        createButton = findViewById(R.id.createBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        rvPosts = findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        posts = new ArrayList<>();
        adapter = new Adapter(posts);
        rvPosts.setAdapter(adapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                loadTopPosts();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright);
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Specify the object id
        query.getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback<Post>() {
            public void done(Post item, ParseException e) {
                if (e == null) {
                    // Access data using the `get` methods for the object
                    user=item.getUser();
                    description=item.getDescription();
                    image=item.getImage();
                    Toast.makeText(HomeActivity.this, description, Toast.LENGTH_SHORT).show();
                } else {
                    // something went wrong
                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TakingPicture.class);
                intent.putExtra("User",ParseUser.getCurrentUser());
                startActivity(intent);
                finish();

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("Logout","Logging out of account.");
                ParseUser.logOut();
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loadTopPosts();
    }

    private void loadTopPosts() {
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getRecent().withUser();
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                adapter.clear();
                if (e == null) {
                    //brute force method to get top 20 posts
                    if(objects.size()>20)
                    {
                        for (int i = objects.size()-20; i < objects.size(); i++) {
                            posts.add(0,objects.get(i));
                            rvPosts.scrollToPosition(0);
                            Log.d("HomeActivity", "Post[" + i + "] = "
                                    + objects.get(i).getDescription() + "\nusername = "
                                    + objects.get(i).getUser().getUsername());
                        }
                    }
                    else
                    {
                        for (int i = 0; i < objects.size(); i++) {
                            posts.add(0,objects.get(i));
                            rvPosts.scrollToPosition(0);
                            Log.d("HomeActivity", "Post[" + i + "] = "
                                    + objects.get(i).getDescription() + "\nusername = "
                                    + objects.get(i).getUser().getUsername());
                        }
                    }
                    swipeContainer.setRefreshing(false);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
