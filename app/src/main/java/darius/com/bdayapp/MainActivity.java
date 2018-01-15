package darius.com.bdayapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.AuthUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import darius.com.bdayapp.pojos.Datum;
import darius.com.bdayapp.pojos.UserFeed;

public class MainActivity extends AppCompatActivity{

    private static final int RC_SIGN_IN=123;
    private TextView greetingTextView;
    private Button logoutButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        context=getApplicationContext();

        progressBar = findViewById(R.id.progress_bar);
        logoutButton = findViewById(R.id.logout_button);

        progressBar.setVisibility(View.VISIBLE);
        logoutButton.setVisibility(View.GONE);

        if (auth.getCurrentUser() == null) {

            Log.d("Developer!!! ","User can log in now!!!");

            AuthUI.IdpConfig facebookIdp = new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER)
                    .setPermissions(Arrays.asList("user_friends", "user_posts"))
                    .build();
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(Arrays.asList(
                            facebookIdp))
                    .build(), RC_SIGN_IN);


        } else {

            Log.d("Developer!!!", "User is already logged in!!!");

            progressBar.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
    }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(context)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Developer!!!", "User Logged Out");
                                finish();
                            }
                        });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Log.d("Developer!!!", "Auth successful");

                progressBar.setVisibility(View.GONE);
                logoutButton.setVisibility(View.VISIBLE);

                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/feed",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {

                                JSONObject jsonObject = response.getJSONObject();
                                try {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    for (int i = 0; i<jsonArray.length(); i++) {
                                        JSONObject jsonPost = jsonArray.getJSONObject(i);
                                        if (jsonPost.toString().contains("message")) {
                                            String message = jsonPost.getString("message");
                                            String id = jsonPost.getString("id");

                                            if (message.contains("La multi ani") || message.contains("Happy birthday")) {
                                                Bundle params = new Bundle();
                                                params.putString("message", "This is a test comment");

                                                new GraphRequest(
                                                        AccessToken.getCurrentAccessToken(),
                                                        "/"+id+"/comments",
                                                        params,
                                                        HttpMethod.POST,
                                                        new GraphRequest.Callback() {
                                                            public void onCompleted(GraphResponse response) {
                                                                Log.d("Developer!!!", "Comment successful, you bastard!!!");
                                                            }
                                                        }
                                                ).executeAsync();
                                            }
                                        }


                                    }


                                } catch (JSONException e) {
                                    Log.d ("Developer!!!", "Json exception");
                                }

                                Log.d("Developer!!!", response.toString());

                            }
                        }
                ).executeAsync();

            }
             else {
                Log.d("Developer!!!", "Auth error");
            }
        }
    }
}
