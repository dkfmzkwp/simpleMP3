package com.example.mp3playerproject_kss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SQLiteDatabase sqLiteDatabase;
    private MyDBHelper myDBHelper;
    private Button btnLogin;
    private EditText etId, etPassword;
    String id = "kss";
    String pw = "7545";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBHelper(getApplicationContext(),"musicDB");

        btnLogin=findViewById(R.id.btnLogin);
        etId=findViewById(R.id.etId);
        etPassword=findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnLogin :
                if (etId.getText().toString().equals(id)) {
                    if (etPassword.getText().toString().equals(pw)) {
                        Intent intent = new Intent(getApplicationContext(), MusicPlaying.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
                etId.setText("");
                etPassword.setText("");
                break;
        }

        // Intent intent = new Intent(getApplicationContext(),MusicPlaying.class);
        // startActivity(intent);
        }
    }

