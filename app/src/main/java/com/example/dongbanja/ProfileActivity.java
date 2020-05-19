package com.example.dongbanja;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dongbanja.R;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);  // layout xml 과 자바파일을 연결

        /*String name = ProfileReviseActivity.name;
        Toast toast = Toast.makeText(getApplicationContext(),
                name, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

         */
        ProfileReviseActivity profilereviseactivity = new ProfileReviseActivity();
        TextView name = (TextView) findViewById(R.id.name);
        TextView phonenumber = (TextView) findViewById(R.id.phonenumber);
        TextView education = (TextView) findViewById(R.id.education);
        TextView length = (TextView) findViewById(R.id.length);
        TextView money = (TextView) findViewById(R.id.money);
        TextView job = (TextView) findViewById(R.id.job);
        TextView religion = (TextView) findViewById(R.id.religion);
        TextView salary = (TextView) findViewById(R.id.salary);
        TextView family = (TextView) findViewById(R.id.family);
        TextView drunksmoke = (TextView) findViewById(R.id.editdurnk);
        TextView living = (TextView) findViewById(R.id.living);
        ImageView imageView = (ImageView) findViewById(R.id.imageview);

        //텍스트 , 이미지 입력

            name.setText(profilereviseactivity.name1);

            final String phoneNumber1 = profilereviseactivity.phonenumber1;
            System.out.println(phoneNumber1);

            phonenumber.setText(profilereviseactivity.phonenumber1);





            education.setText(profilereviseactivity.education1);
            length.setText(profilereviseactivity.length1);
            money.setText(profilereviseactivity.money1);
            job.setText(profilereviseactivity.job1);
            religion.setText(profilereviseactivity.religion1);
            salary.setText(profilereviseactivity.salary1);
            family.setText(profilereviseactivity.family1);
            drunksmoke.setText(profilereviseactivity.drunksmoke1);

            imageView = profilereviseactivity.imageView;
            //imageView.setImageBitmap();



            Button c = (Button) findViewById(R.id.revise_button);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(
                            getApplicationContext(), // 현재 화면의 제어권자
                            ProfileReviseActivity.class); // 다음 넘어갈 클래스 지정
                    startActivity(intent); // 다음 화면으로 넘어간다
                    finish();
                }
            });
        } // end onCreate()
    } // end MyTwo