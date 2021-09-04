package com.example.hw21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePage extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private int ID;
//    private Intent intent = getIntent();
//    private String userID = intent.getStringExtra("userID");
//    private String name = intent.getStringExtra("name");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        ID = Integer.parseInt((String) extras.get("userID"));
        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        textViewResult.append("Welcome " + (String) extras.get("name") + "\n");
        textViewResult.append("Your ID: " + (String) extras.get("userID") + "\n\n");
        getPosts();
    }

    private void getPosts(){
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                for(Post post : posts){
                    if(post.getUserId() == ID){
                        String content = "";
                        content += "Post #" + post.getId() + " " + post.getText() + "\n\n";
                        textViewResult.append(content);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}