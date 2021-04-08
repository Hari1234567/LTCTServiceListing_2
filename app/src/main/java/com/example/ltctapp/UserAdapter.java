package com.example.ltctapp;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {
    List<User> userList;


    public UserAdapter(List<User> userList){
        this.userList = userList;
    }
    @NonNull
    @Override
    public UserAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_element, parent, false);

        UserAdapter.CustomViewHolder customViewHolder = new UserAdapter.CustomViewHolder(view);

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UserAdapter.CustomViewHolder holder, final int position) {
        holder.userName.setText(userList.get(position).userName);
        holder.emailIDText.setText(userList.get(position).emailID);
        holder.accessToggle.setChecked(userList.get(position).readAccess);
        holder.adminToggle.setChecked(userList.get(position).adminMode);
        if(position == 0){
            holder.adminToggle.setEnabled(false);
            holder.accessToggle.setEnabled(false);
        }
        holder.adminToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                holder.adminToggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(buttonView.getContext());
                        String message = isChecked?" allow ":" block ";
                        builder.setMessage("Are you sure to "+ message + "Admin Access "+userList.get(position).userName+"?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/Users");
                                userList.get(position).adminMode = isChecked;
                                databaseReference.child(userList.get(position).userID).setValue(userList.get(position)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(buttonView.getContext(),"Success",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.adminToggle.setChecked(!isChecked);
                                dialog.cancel();
                            }
                        }).create().show();
                    }
                });
            }
        });

        holder.accessToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                //holder.adminToggle.setEnabled(isChecked);
                holder.accessToggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(buttonView.getContext());
                        String message = isChecked?" allow ":" block ";
                        builder.setMessage("Are you sure to "+ message + "Read Access "+userList.get(position).userName+"?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/Users");
                                userList.get(position).readAccess = isChecked;
                                userList.get(position).adminMode = isChecked && holder.adminToggle.isChecked();
                                holder.adminToggle.setChecked(isChecked && holder.adminToggle.isChecked());
                                databaseReference.child(userList.get(position).userID).setValue(userList.get(position)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(buttonView.getContext(),"Success",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.adminToggle.setChecked(!isChecked);
                                dialog.cancel();
                            }
                        }).create().show();
                    }
                });
            }
        });





    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView userName,emailIDText;
        public Switch accessToggle,adminToggle;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameText);
            accessToggle = itemView.findViewById(R.id.accessToggle);
            emailIDText = itemView.findViewById(R.id.emailText);
            adminToggle = itemView.findViewById(R.id.adminToggle);

        }
    }



}
