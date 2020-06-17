package com.firebasedongbanja.dongbanja;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;

public class UserInfo extends AppCompatActivity {

    public String nickname;
    public String gender;
    public String uid;
    private DatabaseReference mDatabase;


    public FirebaseAuth firebaseAuth;
    public DatabaseReference mPostReference;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    RadioButton chooseman;
    RadioButton choosewoman;

    TextView userCheck;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();// FirebaseAuth 인스턴스 생성

        userCheck = (TextView) findViewById(R.id.check);
        RadioGroup choose;
        choose = (RadioGroup) findViewById(R.id.choose);
        chooseman = (RadioButton) findViewById(R.id.chooseman);
        choosewoman = (RadioButton) findViewById(R.id.choosewoman);

        choose.setOnCheckedChangeListener(m);

        Button c = (Button)findViewById(R.id.revise);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                //입력받아오기
                nickname = ((EditText)findViewById(R.id.addnickname)).getText().toString();




            /*Toast toast = Toast.makeText(getApplicationContext(),
                         name11, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();*/

                UserInfoCheck();
            }
        });


    } // end onCreate()

    //라디오버튼 관련
    RadioGroup.OnCheckedChangeListener m = new RadioGroup.OnCheckedChangeListener(){
        private RadioGroup group;
        private int checkedId;

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            this.group = group;
            this.checkedId = checkedId;
            Log.v("출력", checkedId + " ");

            if (group.getId() == R.id.choose) {
                switch (checkedId) {
                    case R.id.chooseman:
                        gender = "man";
                        break;

                    case R.id.choosewoman:
                        gender = "woman";
                        break;
                };
            }
        }
    };

    private void writeNewUser(String userId, String gender, String uid) {
        UserInfoModel user = new UserInfoModel(gender, uid);

        mDatabase.child("UserInfo").child(userId).setValue(user);
    }

    private void UserInfoCheck()
    {
        if(nickname.length() <= 0 )
        {
            userCheck.setText("이름을 입력하십시오");
        }
        else
        {
            final String uid = firebaseAuth.getUid();
            writeNewUser(nickname, gender, uid);


            Intent intent = new Intent(
                    getApplicationContext(), // 현재 화면의 제어권자
                    SubActivity.class); // 다음 넘어갈 클래스 지정
            startActivity(intent); // 다음 화면으로 넘어간다
            finish();

            Toast toast = Toast.makeText(getApplicationContext(),
                    "회원정보 입력에 성공하였습니다.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        }
    }




} // end MyTwo