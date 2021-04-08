package com.example.ltctapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SealAddFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SealAddFrag extends Fragment {
    EditText CTSecondary1,CTSecondary2,CTSecondary3,MeterA1,MeterA2,MeterB1,MeterB2,MRI,MD,TTB1,TTB2,TTBH,MBFH,CTBH1,CTBH2;

    public static String ctsecondary1,ctsecondary2,ctsecondary3,metera1,metera2,meterb1,meterb2,mri,md,ttb1,ttb2,ttbh,mbfh,ctbh1,ctbh2;
    AddMeterActivity addMeterActivity;
    TextView applyBut,cancelBut;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SealAddFrag() {
        if(ctsecondary1 == null)ctsecondary1 = "";
        if(ctsecondary2 == null)ctsecondary2 = "";
        if(ctsecondary3 == null)ctsecondary3 = "";
        if(metera1 == null)metera1 = "";
        if(metera2 == null)metera2 = "";
        if(meterb1 == null)meterb1 = "";
        if(meterb2 == null)meterb2 = "";
        if(mri == null)mri = "";
        if(ttb1 == null)ttb1 = "";
        if(ttb2 == null)ttb2 = "";
        if(ttbh == null)ttbh = "";
        if(mbfh == null)mbfh = "";
        if(ctbh1 == null)ctbh1 = "";
        if(ctbh2 == null)ctbh2 = "";
        if(md == null)md = "";


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SealAddFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SealAddFrag newInstance(String param1, String param2) {
        SealAddFrag fragment = new SealAddFrag();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_seal_add, container, false);
        CTSecondary1 = view.findViewById(R.id.CTSecondarySeal1);
        CTSecondary2 = view.findViewById(R.id.CTSecondarySeal2);
        CTSecondary3 = view.findViewById(R.id.CTSecondarySeal3);
        MeterA1 = view.findViewById(R.id.MeterA1Seal);
        MeterA2 = view.findViewById(R.id.MeterA2Seal);
        MeterB1 = view.findViewById(R.id.MeterB1Seal);
        MeterB2 = view.findViewById(R.id.MeterB2Seal);
        MD = view.findViewById(R.id.MDSeal);
        MRI = view.findViewById(R.id.MRISeal);
        TTB1 = view.findViewById(R.id.TTB1Seal);
        TTB2 = view.findViewById(R.id.TTB2Seal);
        TTBH = view.findViewById(R.id.TTBHSeal);
        MBFH = view.findViewById(R.id.MBFHSeal);
        CTBH1 = view.findViewById(R.id.CTBH1Seal);
        CTBH2  = view.findViewById(R.id.CTBH2Seal);
        CTSecondary1.setText(ctsecondary1);
        CTSecondary2.setText(ctsecondary2);
        CTSecondary3.setText(ctsecondary3);
        MeterA1.setText(metera1);
        MeterA2.setText(metera2);
        MeterB1.setText(meterb1);
        MeterB2.setText(meterb2);
        MD.setText(md);
        MRI.setText(mri);
        TTB1.setText(ttb1);
        TTB2.setText(ttb2);
        TTBH.setText(ttbh);
        MBFH.setText(mbfh);
        CTBH1.setText(ctbh1);
        CTBH2.setText(ctbh2);
        applyBut  = view.findViewById(R.id.applyBut);
        cancelBut  = view.findViewById(R.id.cancelBut);
        addMeterActivity = (AddMeterActivity)getActivity();

        applyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ctsecondary1 = CTSecondary1.getText().toString();
                        ctsecondary2 = CTSecondary2.getText().toString();
                        ctsecondary3 = CTSecondary3.getText().toString();
                        metera1 = MeterA1.getText().toString();
                        metera2 = MeterA2.getText().toString();
                        meterb1 = MeterB1.getText().toString();
                        meterb2 = MeterB2.getText().toString();
                        mri = MRI.getText().toString();
                        md = MD.getText().toString();
                        ttb1 = TTB1.getText().toString();
                        ttb2 = TTB2.getText().toString();
                        ttbh = TTBH.getText().toString();
                        mbfh = MBFH.getText().toString();
                        ctbh1 = CTBH1.getText().toString();
                        ctbh2 = CTBH2.getText().toString();

                        addMeterActivity.SealNo = ctsecondary1 + "/" + ctsecondary2 + "/" + ctsecondary3 + "/" + metera1 + "/" + metera2 + "/" + meterb1 + "/" + meterb2 + "/" + md + "/" + mri + "/" + ttb1 + "/" + ttb2 + "/" + ttbh + "/" + mbfh + "/" + ctbh1 + "/" + ctbh2;
                        addMeterActivity.closeSealFrag();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
            }

        });

        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ctsecondary1 = "";
                 ctsecondary2 = "";
                 ctsecondary3 = "";
                 metera1 = "";
                 metera2 = "";
                 meterb1 = "";
                 meterb2 = "";
                 mri = "";
                 ttb1 = "";
                 ttb2 = "";
                 ttbh = "";
                 mbfh = "";
                 ctbh1 = "";
                 ctbh2 = "";
                 addMeterActivity.SealNo = "";
                 addMeterActivity.closeSealFrag();
            }
        });


        return  view;
    }

    boolean isValid(String s){
        ctsecondary1 = "";
        ctsecondary2 = "";
        ctsecondary3 = "";
        metera1 = "";
        metera2 = "";
        meterb1 = "";
        meterb2 = "";
        mri = "";
        ttb1 = "";
        ttb2 = "";
        ttbh = "";
        mbfh = "";
        ctbh1 = "";
        ctbh2 = "";
        return !ctsecondary1.contains("/");
    }
}
