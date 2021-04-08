package com.example.ltctapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SealDetailsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SealDetailsFrag extends Fragment {
    EditText CTSecondary1,CTSecondary2,CTSecondary3,MeterA1,MeterA2,MeterB1,MeterB2,MRI,MD,TTB1,TTB2,TTBH,MBFH,CTBH1,CTBH2;
    DetailsActivity detailsActivity;
    TextView applyBut, cancelBut;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SealDetailsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SealDetailsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SealDetailsFrag newInstance(String param1, String param2) {
        SealDetailsFrag fragment = new SealDetailsFrag();
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
        View view =  inflater.inflate(R.layout.fragment_seal_add, container, false);
        detailsActivity = (DetailsActivity)getActivity();
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
        applyBut = view.findViewById(R.id.applyBut);
        cancelBut = view.findViewById(R.id.cancelBut);
        if(detailsActivity.SealNo.equalsIgnoreCase("NO DATA AVAILABLE")){
            detailsActivity.SealNo = "";
        }
        String[] sealNos = detailsActivity.SealNo.split("\\/",-1);
        String[] finalSealNos = new String[15];
        for(int i = 0;i<15;i++){
            try{
                finalSealNos[i] = sealNos[i];
            }catch(IndexOutOfBoundsException n){
                finalSealNos[i] = "";
            }
        }

        EditText[] editTexts = {CTSecondary1,CTSecondary2,CTSecondary3,MeterA1,MeterA2,MeterB1,MeterB2,MD,MRI,TTB1,TTB2,TTBH,MBFH,CTBH1,CTBH2};

        for(int i = 0;i<15;i++) {
         editTexts[i].setText(finalSealNos[i]);
         editTexts[i].setEnabled(detailsActivity.editMode);
        }
        applyBut.setVisibility(detailsActivity.editMode?View.VISIBLE:View.INVISIBLE);
        cancelBut.setVisibility(detailsActivity.editMode?View.VISIBLE:View.INVISIBLE);

        applyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ctsecondary1 = CTSecondary1.getText().toString();
                        String ctsecondary2 = CTSecondary2.getText().toString();
                        String ctsecondary3 = CTSecondary3.getText().toString();
                        String metera1 = MeterA1.getText().toString();
                        String metera2 = MeterA2.getText().toString();
                        String meterb1 = MeterB1.getText().toString();
                        String meterb2 = MeterB2.getText().toString();
                        String mri = MRI.getText().toString();
                        String md = MD.getText().toString();
                        String ttb1 = TTB1.getText().toString();
                        String ttb2 = TTB2.getText().toString();
                        String ttbh = TTBH.getText().toString();
                        String mbfh = MBFH.getText().toString();
                        String ctbh1 = CTBH1.getText().toString();
                        String ctbh2 = CTBH2.getText().toString();
                        detailsActivity.SealNo = ctsecondary1 + "/" + ctsecondary2 + "/" + ctsecondary3 + "/" + metera1 + "/" + metera2 + "/" + meterb1 + "/" + meterb2 + "/" + md + "/" + mri + "/" + ttb1 + "/" + ttb2 + "/" + ttbh + "/" + mbfh + "/" + ctbh1 + "/" + ctbh2;
                        detailsActivity.printToast("Success");
                        detailsActivity.closeSealFrag();
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

                detailsActivity.closeSealFrag();
            }
        });
        return view;
    }
}