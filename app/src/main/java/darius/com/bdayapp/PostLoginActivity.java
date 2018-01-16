package darius.com.bdayapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dariu on 1/14/2018.
 */

public class PostLoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Context context;
    private HashMap<String, Integer> friends;
    private int max;
    private String bestFriendId = "1";
    private TextView resultTextView;
    private ProgressBar postLoginProgressBar;


    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getBestFriendId() {
        return bestFriendId;
    }

    public void setBestFriendId(String bestFriendId) {
        this.bestFriendId = bestFriendId;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_login_activity);

        resultTextView = findViewById(R.id.result_text_view);
        postLoginProgressBar = findViewById(R.id.post_login_progress_bat);

        context = getApplicationContext();
        auth = FirebaseAuth.getInstance();

        friends = new HashMap<String, Integer>();

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        JSONObject jsonObject = response.getJSONObject();
                        try {
                            final JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonPost = jsonArray.getJSONObject(i);
                                String id = jsonPost.getString("id");
                                new GraphRequest(
                                        AccessToken.getCurrentAccessToken(),
                                        "/" + id + "/reactions",
                                        null,
                                        HttpMethod.GET,
                                        new GraphRequest.Callback() {
                                            public void onCompleted(GraphResponse response) {
                                                JSONObject post = response.getJSONObject();
                                                try {
                                                    JSONArray likes = post.getJSONArray("data");

                                                    for (int j = 0; j < likes.length(); j++) {
                                                        JSONObject friend = likes.getJSONObject(j);
                                                        String friendId = friend.getString("id");

                                                        if (friends.containsKey(friendId)) {
                                                            friends.put(friendId, friends.get(friendId) + 1);
                                                            if (friends.get(friendId) > getMax()) {

                                                                setMax(friends.get(friendId));
                                                                setBestFriendId(friendId);

                                                                GraphRequest request = GraphRequest.newGraphPathRequest(
                                                                        AccessToken.getCurrentAccessToken(),
                                                                        "/" + getBestFriendId(),
                                                                        new GraphRequest.Callback() {
                                                                            @Override
                                                                            public void onCompleted(GraphResponse response) {
                                                                                JSONObject name = response.getJSONObject();
                                                                                try {
                                                                                    postLoginProgressBar.setVisibility(View.GONE);
                                                                                    resultTextView.setVisibility(View.VISIBLE);
                                                                                    resultTextView.setText(name.getString("name"));
                                                                                } catch (JSONException e) {
                                                                                    Log.d("Developer!!!", "JSONException");
                                                                                }

                                                                            }
                                                                        });

                                                                request.executeAsync();


                                                                Log.d("Developer!!!", "there you go" + getMax() + " " + getBestFriendId());


                                                            }
                                                        } else {
                                                            friends.put(friendId, 1);
                                                        }
                                                    }

                                                } catch (JSONException e) {
                                                    Log.d("Developer!!!", "Json exception");
                                                }
                                            }
                                        }
                                ).executeAsync();
                            }
                        } catch (JSONException e) {
                            Log.d("Developer!!!", "Json exception");
                        }
                    }

                }
        ).executeAsync();
    }

}
