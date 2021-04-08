package com.example.ltctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailIDText;
    EditText pwdText;
    Button signinBut;
    FirebaseAuth firebaseAuth;
    TextView signUpText;
    TextView forgotPasswordText;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailIDText = findViewById(R.id.emailEditText);
        pwdText = findViewById(R.id.passwordText);
        firebaseAuth = FirebaseAuth.getInstance();
        signinBut = findViewById(R.id.LoginBut);
        signUpText = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        forgotPasswordText = findViewById(R.id.passwordResetText);
        getSupportActionBar().hide();

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ForgetPasswordFrag forgetPasswordFrag = new ForgetPasswordFrag();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                    fragmentTransaction.add(R.id.forgotPasswordFragment,forgetPasswordFrag,"FORGETPASSFRAG").commit();
                    ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.subParent);
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View child = layout.getChildAt(i);
                        child.setEnabled(false);
                    }

                }catch (Exception e){
                    printToast(e.getMessage());
                };
            }
        });


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);

                startActivity(i);

            }
        });



        if(firebaseAuth.getCurrentUser()!=null){
            if(firebaseAuth.getCurrentUser().isEmailVerified()) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);

                startActivity(i);
                finish();
            }else{
                firebaseAuth.signOut();
            }
        }
        signinBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    progressBar.setVisibility(View.VISIBLE);
                    signinBut.setClickable(false);
                    firebaseAuth.signInWithEmailAndPassword(emailIDText.getText().toString(), pwdText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);

                                    startActivity(i);
                                    finish();
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signinBut.setClickable(true);
                                    printToast("Email Not verified yet");
                                    firebaseAuth.signOut();
                                }
                            } else {
                                signinBut.setClickable(true);
                                progressBar.setVisibility(View.INVISIBLE);
                                printToast(task.getException().getMessage());
                            }
                        }
                    });
                }catch(Exception e){
                    signinBut.setClickable(true);
                    progressBar.setVisibility(View.INVISIBLE);
                    printToast(e.getMessage());
                }
            }

        });
    }
    public void printToast(String msg){
        ConstraintLayout constraintLayout = findViewById(R.id.parent);

        Snackbar.make(constraintLayout, msg, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag("FORGETPASSFRAG");
        if(myFragment==null)
            super.onBackPressed();
        else{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
            fragmentTransaction.remove(myFragment).commit();
            ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.subParent);
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setEnabled(true);
            }
        }

    }
    public void exitFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.remove(getSupportFragmentManager().findFragmentByTag("FORGETPASSFRAG")).commit();
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.subParent);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(true);
        }
    }


}