package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends MainActivity{
    //named 2 because this is the second instance of a sign up button
    Button signupBtn;
    EditText email;
    EditText username;
    EditText password;
    ParseUser user = new ParseUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        signupBtn = findViewById(R.id.signupBtn);
        email=findViewById(R.id.emailInput);
        username=findViewById(R.id.usernameInput);
        password=findViewById(R.id.passwordInput);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setEmail(email.getText().toString());
                user.put("handle",",my_handle");
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // Hooray! Let them use the app now.
                            Log.e("Sign Up",email.getText().toString()+username.getText().toString()+password.getText().toString() );
                            Log.e("Sign Up","You made an account!");
                            SignUp.this.done(user,e);

                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            Log.e("Sign Up",email.getText().toString()+username.getText().toString()+password.getText().toString() );
                            Log.e("Sign Up","You weren't able to make an account");
                        }
                    }
                });
            }
        });
    }
    public void done(ParseUser user, ParseException e) {
        if (e == null){
            Log.d("LoginActivity", "Login Succesful");

            final Intent intent = new Intent(SignUp.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Log.d("LoginActivity", "Login Failure");
            e.printStackTrace();
        }
    }

}
