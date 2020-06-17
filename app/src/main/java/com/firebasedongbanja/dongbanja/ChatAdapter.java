package com.firebasedongbanja.dongbanja;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

/**
 * Created by KPlo on 2018. 10. 28..
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatDTO> mDataset;
    private String myNickName;
    private FirebaseAuth firebaseAuth;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView TextView_msg;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);

            TextView_msg = v.findViewById(R.id.singleMessage);
            rootView = v;

        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<ChatDTO> myDataset, Context context) {
        //{"1","2"}
        this.mDataset = myDataset;
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public int getItemViewType(int position) {
        ChatDTO chat = mDataset.get(position);

        final String userId = firebaseAuth.getUid();



        if(chat.getUid().equals(userId)) {

            return 0;

        }
        else {
            return 1 ;
        }

    }
    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        LinearLayout v = null;

        if (viewType == 0) {
            v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_chat_singlemessage2, parent, false);
        }
        if (viewType == 1){
            v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_chat_singlemessage, parent, false);
        }

        MyViewHolder vh = new MyViewHolder(v);


        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ChatDTO chat = mDataset.get(position);

        final String userId = firebaseAuth.getUid();


        holder.TextView_msg.setText(chat.getMessage());
        if(chat.getUid().equals(userId)) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.RIGHT;
            holder.TextView_msg.setLayoutParams(params);

        }
        else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.LEFT;
            holder.TextView_msg.setLayoutParams(params);
        }

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        //삼항 연산자
        return mDataset == null ? 0 :  mDataset.size();
    }

    public ChatDTO getChat(int position) {
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(ChatDTO chat) {
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1); //갱신
    }

}