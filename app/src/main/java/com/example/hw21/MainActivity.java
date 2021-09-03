package com.example.hw21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button button;
    private TextView invalidMessage;

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
    }
}