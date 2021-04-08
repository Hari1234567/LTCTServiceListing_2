package com.example.ltctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddMeterActivity extends AppCompatActivity {
    EditText AccNo,CTCoilRatio,CTMake,ConsumerName,Division,Load,MF,MeterMake,MeterNo,PoNo,Section,ServiceDate,Tariff,_Class;
    Button SealNoBut;
    public String SealNo="";
    List<MeterData> meterDataList;
    Button addButton;
    long count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meter);
        getSupportActionBar().setTitle("New Service Form("+MainActivity.division+")");
        addButton =  findViewById(R.id.addBut);
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

        meterDataList = new ArrayList<MeterData>();
        count = getIntent().getLongExtra("COUNT",-1);
        SealNoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SealAddFrag sealAddFrag = new SealAddFrag();
                fragmentTransaction.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);

                fragmentTransaction.replace(R.id.SealAddFrag,sealAddFrag,"SEALSFRAG").commit();

                getSupportActionBar().hide();
            }
        });
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/"+MainActivity.division);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = dataSnapshot.getChildrenCount();
                meterDataList.clear();
                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()){
                    MeterData product = productSnapshot.getValue(MeterData.class);
                    meterDataList.add(product);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = !ConsumerName.getText().toString().trim().isEmpty();
                if(isValid) {
                    MeterData meterData =  new MeterData(AccNo.getText().toString(),CTCoilRatio.getText().toString(),CTMake.getText().toString(),ConsumerName.getText().toString(),Division.getText().toString(),Load.getText().toString(),MF.getText().toString(),MeterMake.getText().toString(),MeterNo.getText().toString(),PoNo.getText().toString(),Section.getText().toString(),ServiceDate.getText().toString(),(Integer.parseInt(meterDataList.get(meterDataList.size() - 1).Sno) + 1) + "",Tariff.getText().toString(),_Class.getText().toString(),SealNo);
                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = mDatabase.getReference("/"+MainActivity.division);

                    ref.child((Integer.parseInt(meterDataList.get(meterDataList.size() - 2).Sno)) + 1+ "").setValue(meterData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(),"Consumer Name should not be Empty",Toast.LENGTH_SHORT).show();
                }


            }
        });
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