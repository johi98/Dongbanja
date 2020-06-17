package com.example.dongbanja;
// 아이디 삭제 구현
//방이 생성되어 있으면 Chat 액티비티로 이동 구현
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;


import java.util.HashMap;
import java.util.Map;

public class SubActivity extends AppCompatActivity implements View.OnClickListener{


    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mPostReference;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button test;
    private TextView textivewDelete;

    private InterstitialAd mInterstitialAd;

    private Button button_goChat;
    String name;
    ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    Handler handler = new Handler();
    boolean backKeyClick = false;
    boolean handlerOn = false;
    boolean thereRoom = false;
    boolean startChat = false;
    String userGender = "";
    String userId ="";
    String result = "";
    boolean thereHumanResult = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
                                          @Override
                                          public void onAdClosed() {
                                              // Load the next interstitial.
                                              mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                              Intent intent = new Intent(SubActivity.this, ChatActivity.class);
                                              startActivity(intent);
    }
            @Override
            public void onAdLeftApplication() {
                // 여따가 큐삭제코드
            }

        });


        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        textivewDelete = (TextView) findViewById(R.id.textviewDelete);
        button_goChat = (Button)findViewById(R.id.buttonGoChat);
        test = (Button)findViewById(R.id.women_queue);
        progressDialog = new ProgressDialog(this);
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getUid();
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //textViewUserEmail의 내용을 변경해 준다.
        textViewUserEmail.setText("반갑습니다.\n"+ user.getEmail()+"으로 로그인 하였습니다.");

        //logout button event
        buttonLogout.setOnClickListener(this);
        textivewDelete.setOnClickListener(this);
        button_goChat.setOnClickListener(this);
        test.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            final String userId = firebaseAuth.getUid();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean thereId = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String s = snapshot.getValue().toString();
                    UserInfoModel userInfoModel = snapshot.getValue(UserInfoModel.class);
                    if (s.contains(userId)) {

                        userGender = userInfoModel.gender;
                        Log.e("das",userGender);
                    }
                }//폴문끝

            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }

        });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == test) {// woman에 큐 생성

            Intent intent = new Intent(SubActivity.this, ChatActivity.class);
            startActivity(intent);

            databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                            databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("woman");
                                UserModel userModel = new UserModel();
                                userModel.uid = userId;
                                databaseReference.push().setValue(userModel);
                }
                @Override
                public void onCancelled(DatabaseError databaseError){
                }
            });
        }

        //회원탈퇴를 클릭하면 회원정보를 삭제한다. 삭제전에 컨펌창을 하나 띄워야 겠다.
        if(view == textivewDelete) {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(SubActivity.this);
            alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SubActivity.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), SingUpActivity.class));
                                        }
                                    });
                        }
                    }
            );
            alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(SubActivity.this, "취소", Toast.LENGTH_LONG).show();
                }
            });
            alert_confirm.show();
        }


        if(view==button_goChat) {
            progressDialog.setMessage("매칭중입니다. 잠시 기다려 주세요...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                        if (progressDialog.isShowing()) {
                            backKeyClick = true;
                            progressDialog.dismiss();
                            Log.e("backon", String.valueOf(backKeyClick));
                        }
                        return true;
                    }
                    return false;
                }
            });
            backKeyClick = false;
           if (!startChat){
                setChatQueue();
            }//큐에 아이디를 집어 넣음
            chatMatching();//다른아이디가 있을경우 아이디를 찾음

        }


    }


    public void setChatQueue(){


        Log.e("큐넣기전",userGender);
       if (userGender.equals("man")) {

            Log.e("das","남자큐");
            databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("man");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean thereId = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String s = snapshot.getValue().toString();
                            if (s.contains(userId)) {
                                thereId = true;
                            }

                    }
                    if (!thereId) {
                        UserModel userModel = new UserModel();
                        userModel.uid = userId;
                        databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("man");
                        Log.e("das","남자큐 넣음");
                        databaseReference.push().setValue(userModel);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }

        if (userGender.equals("woman")) {


            databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("woman");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean thereId = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String s = snapshot.getValue().toString();
                        if (s.contains(userId)) {
                            thereId = true;
                        }

                    }
                    if (!thereId) {
                        UserModel userModel = new UserModel();
                        userModel.uid = userId;
                        databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("woman");
                        databaseReference.push().setValue(userModel);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }

        }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void chatMatching(){
        final String userId = firebaseAuth.getUid();
        startChat = false;


        databaseReference = FirebaseDatabase.getInstance().getReference("chat_room").child("room").child("uid");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s ="";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    s = snapshot.getValue().toString();
                    if(s.contains(userId)){
                        handler.removeMessages(0);
                        progressDialog.dismiss();
                        thereRoom = true;
                        if(!startChat) {
                            startChat = true;
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();

                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }

                        }


                    }
                    Log.e("das","잘돌아감1");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        databaseReference = firebaseDatabase.getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = "";


                    if (userGender.equals("man")) {

                        thereHuman("woman");
                    }

                    if (userGender.equals("woman")) {

                        thereHuman("man");
                    }

                if (!thereRoom&&thereHumanResult) {
                    //다른사람의 아이디와 나의 아이디가 있어야 채팅방 생성
                    //User_Queue에 자신의 아이디가 있어야만 채팅방 생성




                    if (!startChat) {
                        startChat = true;
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();

                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        handler.removeMessages(0);
                        progressDialog.dismiss();

                        Intent intent = new Intent(SubActivity.this, ChatActivity.class);

                        startActivity(intent);
                    }



                }



             if(!backKeyClick&&!thereRoom) {
                    handlerOn = true;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (backKeyClick){
                                handler.removeMessages(0);
                              //  handler.removeCallbacksAndMessages(null);
                             //   handler =null;
                            }
                                chatMatching();
                            Log.e("핸들러 돌아가는중","팽도는헨들러");
                        }
                    }, 1000 );
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }


        public void thereHuman(final String gender){


            databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child(gender);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String s = "";
                    final String gender2 = gender;
                    databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child(gender2);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        s = snapshot.getValue().toString();

                        if (!s.equals(userId)) {
                            thereHumanResult = true;
                        }
                    }
                    if (thereHumanResult && !thereRoom) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            UserModel userModel = snapshot.getValue(UserModel.class);
                            databaseReference = FirebaseDatabase.getInstance().getReference("chat_room").child("room").child("uid");
                            result = userModel.uid;
                            ChatModel chatModel = new ChatModel();
                            chatModel.uid1 = userId;
                            chatModel.uid2 = result;
                            databaseReference.push().setValue(chatModel);


                            break;

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }



            });


        }






}



