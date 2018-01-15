package darius.com.bdayapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private TextView greetingTextView;
    private Button postLoginButton;
    private Button logoutButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        context = getApplicationContext();

        progressBar = findViewById(R.id.progress_bar);
        logoutButton = findViewById(R.id.logout_button);
        postLoginButton = findViewById(R.id.post_login_button);


        progressBar.setVisibility(View.VISIBLE);
        postLoginButton.setVisibility(View.GONE);
        logoutButton.setVisibility(View.GONE);

        if (auth.getCurrentUser() == null) {

            Log.d("Developer!!! ", "User can log in now!!!");

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
            postLoginButton.setVisibility(View.VISIBLE);
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

        postLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostLoginActivity.class);
                startActivity(intent);
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
                postLoginButton.setVisibility(View.VISIBLE);

            } else {
                Log.d("Developer!!!", "Auth error");
            }
        }
    }
}
