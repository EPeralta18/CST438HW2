package com.example.hw21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button button;
    private TextView invalidMessage;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private ArrayList<String> usersNames = new ArrayList<String>();
    private ArrayList<String> passwords = new ArrayList<String>();
    private ArrayList<String> names = new ArrayList<String>();

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.loginUsername);
        password = (EditText) findViewById(R.id.loginPassword);
        button = (Button) findViewById(R.id.loginButton);
        invalidMessage = (TextView) findViewById(R.id.invalidMess);

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getUsers();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser(userName.getText().toString(), password.getText().toString());
            }
        });
    }

    private void validateUser(String name, String pass){
        if(name.equals("Admin")){
            if(pass.equals("123")){
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
            }
        }

        for(int i = 0; i < usersNames.size();i++){
            if(name.equals(usersNames.get(i))){
                if(pass.equals(passwords.get(i))){
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    intent.putExtra("userID", userName.getText().toString());
                    intent.putExtra("name", names.get(i));
                    startActivity(intent);
                }
            }
        }
    }
    private void getUsers(){
        Call<List<Users>> call = jsonPlaceHolderApi.getUsers();

        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if(!response.isSuccessful()){
                    invalidMessage.setText("Code: " + response.code());
                    return;
                }

                List<Users> users = response.body();

                for(Users user : users){
                    usersNames.add(Integer.toString(user.getId()));
                    passwords.add("login" + user.getId());
                    names.add(user.getUsername());
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                invalidMessage.setText("No Response");
            }
        });
    }
}