package com.example.ltctapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgetPasswordFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgetPasswordFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText text;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForgetPasswordFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetPasswordFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgetPasswordFrag newInstance(String param1, String param2) {
        ForgetPasswordFrag fragment = new ForgetPasswordFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        text = view.findViewById(R.id.emailEditText);
        final Button verifyBut = view.findViewById(R.id.resetMailBut);
        verifyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    verifyBut.setClickable(false);
                    firebaseAuth.sendPasswordResetEmail(text.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            verifyBut.setClickable(true);
                            if (task.isSuccessful()) {
                                printToast("Password reset mail sent");
                                LoginActivity loginActivity = (LoginActivity)getActivity();
                                loginActivity.exitFragment();
                            } else {
                                printToast(task.getException().getMessage());
                            }
                        }
                    });
                }catch(Exception e){
                    printToast(e.getMessage());
                }
            }
        });
        return view;
    }
    public void printToast(String msg){
        ConstraintLayout constraintLayout = getActivity().findViewById(R.id.parent);

        Snackbar.make(constraintLayout, msg, Snackbar.LENGTH_LONG).show();

    }
}