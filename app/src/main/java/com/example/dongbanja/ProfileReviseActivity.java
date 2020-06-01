package com.example.dongbanja;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dongbanja.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.InputStream;

public class ProfileReviseActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0;
    public static ImageView imageView;

    public static String nickname1;
    public static String name1;
    public static String phonenumber1;
    public static String education1;
    public static String length1;
    public static String money1;
    public static String job1;
    public static String religion1;
    public static String salary1;
    public static String family1;
    public static String drunksmoke1;
    public static String living1;
    public static TextView profile_check;
    public String man, women;
    public String email1;

    public FirebaseAuth firebaseAuth;
    public DatabaseReference mPostReference;

    /*private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();*/



    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_revise);

    mDatabase = FirebaseDatabase.getInstance().getReference();

    profile_check = (TextView) findViewById(R.id.profile_check);

    //이미지 불러오기
        imageView = findViewById(R.id.imageview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        Button c = (Button)findViewById(R.id.revise_button);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                Spinner phone1 = (Spinner)findViewById(R.id.phonefirst);
                Spinner edu = (Spinner)findViewById(R.id.editeducation);
                Spinner edit = (Spinner)findViewById(R.id.editing);
                Spinner drunk = (Spinner)findViewById(R.id.editdurnk);
                Spinner smoke = (Spinner)findViewById(R.id.editsmoke);
                Spinner money = (Spinner)findViewById(R.id.money);
                Spinner living = (Spinner)findViewById(R.id.editliving);
                Spinner religion = (Spinner)findViewById(R.id.editreligion);
                Spinner salary = (Spinner)findViewById(R.id.editsalary);

                //입력받아오기
                nickname1 = ((EditText)findViewById(R.id.editnickname)).getText().toString();

                name1 = ((EditText)findViewById(R.id.editname)).getText().toString();

                String first = phone1.getSelectedItem().toString();
                final String phone2 = ((EditText)findViewById(R.id.phonesecond)).getText().toString();
                final String phone3 = ((EditText)findViewById(R.id.phonethird)).getText().toString();
                phonenumber1 = first+"-"+phone2+"-"+phone3;

                String edu1 = edu.getSelectedItem().toString();
                String edit1 = edit.getSelectedItem().toString();
                education1 = edu1+" , "+edit1;

                String length = ((EditText)findViewById(R.id.length)).getText().toString();
                length1 = length +" cm";

                job1 = ((EditText)findViewById(R.id.editjob)).getText().toString();

                money1 = money.getSelectedItem().toString();

                man = ((EditText)findViewById(R.id.man)).getText().toString();
                women = ((EditText)findViewById(R.id.women)).getText().toString();
                family1 = man+" 남 "+women+" 여 ";

                String drunk11 = drunk.getSelectedItem().toString();
                String smoke11 = smoke.getSelectedItem().toString();
                drunksmoke1 = drunk11+" , "+smoke11;

                living1 = living.getSelectedItem().toString();

                salary1 = salary.getSelectedItem().toString();

                religion1 = religion.getSelectedItem().toString();



            /*Toast toast = Toast.makeText(getApplicationContext(),
                         name11, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();*/


            profileCheck();
            }
        });
    } // end onCreate()

    private void profileCheck()
    {
        if(nickname1.length() <= 6 || nickname1.length() >= 12)
        {
            profile_check.setVisibility(View.VISIBLE);
            profile_check.setText("닉네임을 6~12자 이내로 입력하십시오");
        }
        else if(name1.length() <= 0 )
        {
            profile_check.setVisibility(View.VISIBLE);
            profile_check.setText("이름을 입력하십시오");
        }
        else if(phonenumber1.length() <= 12 )
        {
            profile_check.setVisibility(View.VISIBLE);
            profile_check.setText("전화번호를 재대로 입력하십시오");
        }
        else if(length1.length() <= 3 )
        {
            profile_check.setVisibility(View.VISIBLE);
            profile_check.setText("키를 입력하십시오");
        }
        else if(job1.length() <= 0 )
        {
            profile_check.setVisibility(View.VISIBLE);
            profile_check.setText("직업을 입력하십시오");
        }
        else if(man.length() <= 0 || women.length() <= 0)
        {
            profile_check.setVisibility(View.VISIBLE);
            profile_check.setText("형제관계을 입력하십시오");
        }
        else
        {
            Intent intent = new Intent(
                    getApplicationContext(), // 현재 화면의 제어권자
                    ProfileActivity.class); // 다음 넘어갈 클래스 지정
            startActivity(intent); // 다음 화면으로 넘어간다
            finish();


            Toast toast = Toast.makeText(getApplicationContext(),
                    "회원정보 수정에 성공하였습니다.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();

            writeNewUser(nickname1, name1, education1, phonenumber1, length1, job1, money1, family1, drunksmoke1, living1, salary1, religion1);
        }

    }

    private void writeNewUser(String userId, String name
,String education,String phonenumber,String length,String job,String money,String family,
                              String drunksmoke,String living,String salary, String religion)
    {
        UserProfile userProfile = new UserProfile(name
, education, phonenumber, length, job, money, family, drunksmoke, living, salary, religion);
        mDatabase.child("users").child(userId).setValue(userProfile);
    }


    //이미지 불러오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    imageView.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
} // end MyTwo