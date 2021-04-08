package com.example.ltctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    EditText AccNo,CTCoilRatio,CTMake,ConsumerName,Division,Load,MF,MeterMake,MeterNo,PoNo,Section,ServiceDate,Tariff,_Class;
    Button SealNoBut;
    public String SealNo = "";
    int position;
    List<MeterData> dataList;
    Button editBut;
    boolean entried = false;
    boolean editMode = false;
    MeterData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setTitle("Service Details("+MainActivity.division+")");
        AccNo = findViewById(R.id.AccNoText);
        CTCoilRatio = findViewById(R.id.CTCoilRatio);
        CTMake = findViewById(R.id.CTMake);
        ConsumerName = findViewById(R.id.consumerNameText);
        Division = findViewById(R.id.division);
        Load = findViewById(R.id.Load);
        MF = findViewById(R.id.MF);
        MeterMake = findViewById(R.id.MeterMake);
        MeterNo = findViewById(R.id.MeterNo);
        PoNo = findViewById(R.id.PoNo);
        Section = findViewById(R.id.Section);
        ServiceDate = findViewById(R.id.ServiceDate);
        Tariff = findViewById(R.id.Tariff);
        _Class = findViewById(R.id.Class);
        SealNoBut = findViewById(R.id.SealNo);

        SealNoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SealDetailsFrag sealDetailsFrag = new SealDetailsFrag();
                fragmentTransaction.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);

                fragmentTransaction.replace(R.id.SealDetailsFrag,sealDetailsFrag,"SEALSFRAG").commit();

                getSupportActionBar().hide();
            }
        });

        final Drawable editTextBG = AccNo.getBackground();
        final EditText[] textViews = new EditText[]{AccNo,CTCoilRatio,CTMake,ConsumerName,Division,Load,MF,MeterMake,MeterNo,PoNo,Section,ServiceDate,Tariff,_Class};
        for(int i =0;i<textViews.length;i++){
            textViews[i].setBackground(null);
            textViews[i].setEnabled(false);
        }
        position = getIntent().getIntExtra("POSITION", 0);
        editBut = findViewById(R.id.editBut);
        dataList = new ArrayList<MeterData>();
        editBut.setClickable(false);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("/"+MainActivity.division+"/");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    dataList.clear();
                    for(DataSnapshot productSnapshot:dataSnapshot.getChildren()){
                        dataList.add(productSnapshot.getValue(MeterData.class));
                    }
                    if(!entried) {
                        MeterData product = dataSnapshot.child(position+"").getValue(MeterData.class);

                        AccNo.setText(modify(product.AccNo));
                        CTCoilRatio.setText(modify(product.CTCoilRatio));
                        CTMake.setText(modify(product.CTMake));
                        ConsumerName.setText(modify(product.ConsumerName));
                        Division.setText(modify(product.Division));
                        Load.setText(modify(product.Load));
                        MF.setText(modify(product.MF));
                        MeterMake.setText(modify(product.MeterMake));
                        MeterNo.setText(modify(product.MeterNo));
                        PoNo.setText(modify(product.PoNo));
                        Section.setText(modify(product.Section));
                        ServiceDate.setText(modify(product.ServiceDate));
                        Tariff.setText(modify(product.Tariff));
                        _Class.setText(modify(product.__Class));
                        SealNo = modify(product.SealNumber);

                        editBut.setClickable(true);
                        entried = true;
                        data = product;
                    }
                    else{
                        boolean exists = false;

                        for(int i = 0;i< dataList.size();i++){
                            if(data.AccNo.equalsIgnoreCase(dataList.get(i).AccNo)){
                                position = i;
                                exists = true;
                                break;
                            }
                        }
                        MeterData product = dataSnapshot.child(position+"").getValue(MeterData.class);
                        if(!exists){

                            finish();
                        }
                    }
                }catch (NullPointerException n){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.adminAcc) {
                    if (!editMode) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                        builder.setMessage("Are you sure?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editBut.setText("Apply");
                                for (int i = 0; i < textViews.length; i++) {
                                    if (textViews[i].getText().toString().equals("NO DATA AVAILABLE")) {
                                        textViews[i].setText("");
                                    }
                                    textViews[i].setEnabled(true);
                                    textViews[i].setBackground(editTextBG);
                                }
                                editMode = true;
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                        builder.setMessage("Are you sure?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        MeterData meterData = new MeterData(AccNo.getText().toString(), CTCoilRatio.getText().toString(), CTMake.getText().toString(), ConsumerName.getText().toString(), Division.getText().toString(), Load.getText().toString(), MF.getText().toString(), MeterMake.getText().toString(), MeterNo.getText().toString(), PoNo.getText().toString(), Section.getText().toString(), ServiceDate.getText().toString(), (position + 1) + "", Tariff.getText().toString(), _Class.getText().toString(),SealNo);
                                        ref.child(position + "").setValue(meterData);
                                        printToast("Successful");
                                        finish();
                                        DetailsActivity.this.finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }


                } else {
                    Toast.makeText(getApplicationContext(), "You do not have admin access", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
    String modify(String data){

        if(data==null||data.trim().equalsIgnoreCase("null")||data.isEmpty()){
            data = "NO DATA AVAILABLE";
        }
        return data;
    }

    public void printToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public  void onBackPressed(){
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag("SEALSFRAG");
        if(myFragment!=null){
           /* SealAddFrag.ctsecondary1 = "";
            SealAddFrag.ctsecondary2 = "";
            SealAddFrag.ctsecondary3 = "";
            SealAddFrag.metera1 = "";
            SealAddFrag.metera2 = "";
            SealAddFrag.meterb1 = "";
            SealAddFrag.meterb2 = "";
            SealAddFrag.mri = "";
            SealAddFrag.ttb1 = "";
            SealAddFrag.ttb2 = "";
            SealAddFrag.ttbh = "";
            SealAddFrag.mbfh = "";
            SealAddFrag.ctbh1 = "";
            SealAddFrag.ctbh2 = "";*/
        }
        closeSealFrag();
    }

    public void closeSealFrag(){
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag("SEALSFRAG");
        if(myFragment==null) {
            super.onBackPressed();

        }
        else{
            getSupportActionBar().show();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);
            fragmentTransaction.remove(myFragment).commit();





        }
    }
}