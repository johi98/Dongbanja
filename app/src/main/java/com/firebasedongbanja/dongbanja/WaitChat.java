package com.firebasedongbanja.dongbanja;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitChat extends AppCompatActivity implements View.OnClickListener{


    private Button user_next;
    private Button user_wait;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private  FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_caht);
        user_next = (Button) findViewById(R.id.user_next);
        user_wait = (Button) findViewById(R.id.user_wait);
        firebaseAuth = FirebaseAuth.getInstance();

        user_wait.setOnClickListener(this);
        user_next.setOnClickListener(this);

    }
        @Override
        public void onClick(View v) {

        if (v == user_wait) {//대기열에 추가
            final String userId = firebaseAuth.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("Id");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean thereId = false;
                      //  int count = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                          //  Log.e("snaps:",snapshot.getValue().toString());
                           // Log.e("snaps:","{uid="+userId+"}");
                            String s = snapshot.getValue().toString();
                            Log.e("s",s);
                            if(s.equals("{uid="+userId+"}")){
                              thereId = true;
                                Log.e("Id",userId);

                                break;
                            }
                         //   count++;
                        }
                        if (!thereId){
                            Log.e("d","d"+thereId);
                            UserModel userModel = new UserModel();
                            userModel.uid = userId;
                           databaseReference.push().setValue(userModel);

                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });


        }


        if(v== user_next) {
            //User_queue에서 아이디를 읽고 그것이 자신의 아이디가 아니라면
            //유저와 상대 유저 아이디 값을 가진 채팅방을 생성
            //아이디 삭제



            final String userId = firebaseAuth.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("Id");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                 //   int count = 0;
                    String result = "";
                    String s ="";
                    boolean thereMyId = false;
                    boolean thereRoom = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        s = snapshot.getValue().toString();
                        if(s.equals("{uid="+userId+"}")){

                            thereMyId = true;

                        }
                    }
                    //User_Queue에 자신의 아이디가 있어야만 채팅방 생성
                    if(thereMyId){

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        s = snapshot.getValue().toString();
                        //      count++;
                        if (!s.equals("{uid=" + userId + "}")) {
                            databaseReference = FirebaseDatabase.getInstance().getReference("chat_room").child("room").child("uid");
                            result = s.substring(5, s.length() - 1);
                            ChatModel chatModel = new ChatModel();
                            chatModel.uid1 = userId;
                            chatModel.uid2 = result;
                            databaseReference.push().setValue(chatModel);

                            thereRoom =true;
                            break;
                        }
                      }
                    }


                    if(thereRoom){
                    //User 큐에서 매칭된 아이디 삭제
                    databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("Id");

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        s = snapshot.getValue().toString();
                        if (s.equals("{uid=" + userId + "}")) {

                            databaseReference.child(snapshot.getKey()).setValue(null);
                        } else if (s.equals("{uid=" + result + "}")) {

                            databaseReference.child(snapshot.getKey()).setValue(null);
                        }
                    }

                    thereRoom = false;
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            Intent intent = new Intent(WaitChat.this, ChatActivity.class);
            startActivity(intent);
        }


        }


    }

//매칭 시작 버큰을 클릭
//queue에 사용자를 집어넣음
//다른 q 에있는 사용자와 연결
//
