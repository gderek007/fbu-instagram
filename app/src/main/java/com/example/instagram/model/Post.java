package com.example.instagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

@ParseClassName("Post")

public class Post extends ParseObject {

    private static final String KEY_DESCRIPTION= "description";
    private static final String KEY_IMAGE= "image";
    private static final String KEY_USER= "user";
    private static final String KEY_CREATED= "createdAt";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescriptions(String description){
        put(KEY_DESCRIPTION,description);
    }
    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile image){
        put(KEY_IMAGE,image);
    }
    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
//    public Date getcreatedAt(){
//        return getParseUser(KEY_USER).getCreatedAt();
//    }
    public String RelativeTime(){
        Date date = getParseUser(KEY_USER).getCreatedAt();
        PrettyTime time = new PrettyTime();
        return time.format(date).toString();

    }
    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }
    public static class Query extends ParseQuery<Post> {

        public Query() {
            super(Post.class);
        }
        public Query getTop(){
            setLimit(20);
            return this;
        }
        public Query withUser(){
            include("user");
            return this;
        }

        public Query getRecent(){
            getQuery(KEY_CREATED);


            return this;
        }
    }
}

