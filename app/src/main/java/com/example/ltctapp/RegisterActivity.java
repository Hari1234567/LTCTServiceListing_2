package com.example.ltctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText userNameText, emailText, pwdText;
    Button registerBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userNameText = findViewById(R.id.userNameEditText);
        emailText = findViewById(R.id.emailEditText);
        pwdText = findViewById(R.id.passwordText);
        getSupportActionBar().hide();
        registerBut = findViewById(R.id.registerBut);
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(emailText.getText().toString(),pwdText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final FirebaseUser user = auth.getCurrentUser();
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(userNameText.getText().toString()).build();

                            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                        try {
                                            DatabaseReference databaseReference = firebaseDatabase.getReference("/Users");
                                            User userData = new User(user.getDisplayName(), user.getEmail(), user.getUid(), false, false);
                                            databaseReference.child(user.getUid()).setValue(userData);
                                        }catch (Exception e){
                                            printToast(e.getMessage());
                                        }
                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {

                                                    printToast("Verification mail sent to "+user.getEmail());
                                                    finish();
                                                }else{
                                                    printToast(task.toString());
                                                }
                                            }
                                        });
                                    }else{
                                        printToast(task.getException().getMessage());
                                    }
                                }
                            });

                        }else{
                            printToast(task.getException().getMessage());
                        }
                    }
                });

            }
        });
    }
    public void printToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}