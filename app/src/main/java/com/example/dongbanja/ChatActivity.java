
package com.example.dongbanja;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;

//액티비티 시작시 오픈된 채팅방을 검색하여
//자신의 아이디가 들어간 채팅방과 채팅방의 하위 객체인 uid2로 되어있는 자신의 채팅방을 확인
//chatActivity 연결

public class ChatActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    private RecyclerView chat_view;
    public  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    private  List<ChatDTO> chaList;


    private EditText chat_edit;
    private Button chat_send;
    private Button img_send;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth;
    private TextView textView;
    String roomKey ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        //광고
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        firebaseAuth = FirebaseAuth.getInstance();// FirebaseAuth 인스턴스 생성
        // 채팅방 키, 유저 이름 저장

        final String userId = firebaseAuth.getUid();
        final String chatUser2="";
        chat_view =  findViewById(R.id.chat_view);
        chat_view.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        chat_view.setLayoutManager(mlayoutManager);
        chaList = new ArrayList<>();
        mAdapter = new ChatAdapter(chaList, ChatActivity.this);
        chat_view.setAdapter(mAdapter);
        chat_view.addItemDecoration(new MyItemDecoration());
        chat_edit = (EditText) findViewById(R.id.chat_edit);
        chat_send = (Button) findViewById(R.id.chat_sent);
        img_send = (Button) findViewById(R.id.img_sent);

        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();




        databaseReference = FirebaseDatabase.getInstance().getReference("chat_room").child("room").child("uid");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s ="";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    s = snapshot.getValue().toString();
                    if(s.contains(userId)){

                        roomKey = snapshot.getKey();



                        // 채팅 방 입장
                        databaseReference = FirebaseDatabase.getInstance().getReference("chat_room").child("room");

                        openChat(roomKey);

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("Id");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s ="";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    s = snapshot.getValue().toString();
                    if(s.contains(userId)){
                        //User 큐에서 매칭된 아이디 삭제
                            s = snapshot.getValue().toString();
                            UserModel userModel = snapshot.getValue(UserModel.class);
                            Log.e("데이터스",userModel.uid);
                        databaseReference = FirebaseDatabase.getInstance().getReference("chat_queue").child("Id");
                                Log.e("나 삭제","1");
                                databaseReference.child(snapshot.getKey()).setValue(null);
                        databaseReference = FirebaseDatabase.getInstance().getReference("chat_room").child("room");

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        databaseReference = FirebaseDatabase.getInstance().getReference("chat_room").child("room");

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_edit.getText().toString().equals(""))
                    return;

                ChatDTO chat = new ChatDTO( chat_edit.getText().toString(),userId); //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child(roomKey).push().setValue(chat); // 데이터를 json파일에 chat의 자식으로 넣는다
                chat_edit.setText(""); //입력창 초기화


            }
        });


        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showExit();


            }
        });



    }

    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.add( chatDTO.getMessage());
    }

  /*  private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO.getUserName() + " : " + chatDTO.getMessage());
    }*/

    private void openChat(String user1) {
        // 리스트 어댑터 생성 및 세팅

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child(user1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatDTO chat = dataSnapshot.getValue(ChatDTO.class);
                ((ChatAdapter) mAdapter).addChat(chat);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //  removeMessage(dataSnapshot, adapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            finish();

        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(20,20,20,20);

        }
    }
    void showExit()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("정말 나가시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("chat_room").child("room").child("uid");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final String userId = firebaseAuth.getUid();
                                String s ="";
                                databaseReference = FirebaseDatabase.getInstance().getReference("chat_room").child("room").child("uid");
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    s = snapshot.getValue().toString();
                                    if(s.contains(userId)){

                                        databaseReference.child(snapshot.getKey()).setValue(null);


                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                        finish();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }


}