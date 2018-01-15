package darius.com.bdayapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by dariu on 1/14/2018.
 */

public class PostLoginActivity extends AppCompatActivity {

    private Button logOutButton;
    private FirebaseAuth auth;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_login_activity);
        context = getApplicationContext();
        auth = FirebaseAuth.getInstance();

        logOutButton = findViewById(R.id.log_out_button);
        logOutButton.setText("post login");
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(context)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("AUTH", "User Logged Out");
                                finish();
                            }
                        });
            }
        });

    }
}
