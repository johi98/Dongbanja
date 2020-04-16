package com.example.dongbanja.profile;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dongbanja.R;

public class ProfileReviseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_revise);  // layout xml 과 자바파일을 연결


        Button c = (Button)findViewById(R.id.savebutton);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText)findViewById(R.id.editname)).getText().toString();
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        ProfileActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });

} // end onCreate()
} // end MyTwo