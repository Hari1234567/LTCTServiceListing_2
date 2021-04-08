package com.example.ltctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    RecyclerView userListView;
    UserAdapter userAdapter;
    List<User> userList;
    FirebaseDatabase database;
    DatabaseReference userDataRef;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userListView = findViewById(R.id.UserRecyclerView);
        userList = new ArrayList<User>();
        userAdapter = new UserAdapter(userList);
        database = FirebaseDatabase.getInstance();
        userDataRef = database.getReference("/Users");
        userListView.setAdapter(userAdapter);
        progressBar = findViewById(R.id.loadingBar);
        progressBar.setVisibility(View.VISIBLE);
        userListView.setLayoutManager(new LinearLayoutManager(this));
        userDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    userList.clear();
                    progressBar.setVisibility(View.INVISIBLE);
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {

                        if(!userSnapshot.getValue(User.class).userID.equalsIgnoreCase(FirebaseAuth.getInstance().getUid()))
                            userList.add(userSnapshot.getValue(User.class));
                        else{
                            User user = userSnapshot.getValue(User.class);
                            user.userName += "(YOU)";
                            userList.add(0,user);
                        }
                        User user = userSnapshot.getValue(User.class);

                        if(user.userID.equalsIgnoreCase(FirebaseAuth.getInstance().getUid())){

                            if(!user.adminMode){
                                Toast.makeText(getApplicationContext(),"You have been blocked admin access",Toast.LENGTH_SHORT).show();
                                finish();


                            }
                        }
                    }

                    userAdapter.notifyDataSetChanged();

                }catch(Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

}