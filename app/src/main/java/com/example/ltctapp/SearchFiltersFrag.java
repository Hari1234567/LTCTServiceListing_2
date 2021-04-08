package com.example.ltctapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFiltersFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFiltersFrag extends Fragment {

    public static String nameSearch ,AccNoSearch,CTMakeSearch,MeterNoSearch,MeterMakeSearch,SubDivSearch,SectionSearch,CTRatioSearch,ClassSearch,TariffSearch,SealNoSearch,LoadSearch,MFSearch,PoNoSearch,ServiceDateSearch;
    EditText AccNo,CTCoilRatio,CTMake,ConsumerName,Division,Load,MF,MeterMake,MeterNo,PoNo,Section,ServiceDate,Tariff,_Class,SealNo;
    TextView applyText,cancelText;
    TextView relevantResults;
    MainActivity mainActivity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFiltersFrag() {
         if(nameSearch == null) nameSearch = "";
         if(AccNoSearch ==  null)AccNoSearch = "";
         if(CTMakeSearch == null)CTMakeSearch = "";
         if(MeterNoSearch == null)MeterNoSearch = "";
         if(MeterMakeSearch == null)MeterMakeSearch = "";
         if(SubDivSearch == null)SubDivSearch = "";
         if(SectionSearch == null)SectionSearch = "";
         if(CTRatioSearch == null)CTRatioSearch = "";
         if(ClassSearch == null)ClassSearch = "";
         if(TariffSearch == null)TariffSearch = "";
         if(SealNoSearch == null)SealNoSearch = "";
         if(LoadSearch == null)LoadSearch = "";
         if(MFSearch == null)MFSearch = "";
         if(PoNoSearch == null)PoNoSearch = "";
         if(ServiceDateSearch == null)ServiceDateSearch = "";
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFiltersFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFiltersFrag newInstance(String param1, String param2) {
        SearchFiltersFrag fragment = new SearchFiltersFrag();
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
        View view = inflater.inflate(R.layout.fragment_search_filters, container, false);
        relevantResults = view.findViewById(R.id.title);
        mainActivity = (MainActivity)getActivity();
        AccNo = view.findViewById(R.id.AccNoText);
        CTCoilRatio = view.findViewById(R.id.CTCoilRatio);
        CTMake = view.findViewById(R.id.CTMake);
        ConsumerName = view.findViewById(R.id.consumerNameText);
        Division = view.findViewById(R.id.division);
        Load = view.findViewById(R.id.Load);
        MF = view.findViewById(R.id.MF);
        MeterMake = view.findViewById(R.id.MeterMake);
        MeterNo = view.findViewById(R.id.MeterNo);
        PoNo = view.findViewById(R.id.PoNo);
        Section = view.findViewById(R.id.Section);
        ServiceDate = view.findViewById(R.id.ServiceDate);
        Tariff = view.findViewById(R.id.Tariff);
        _Class = view.findViewById(R.id.Class);
        SealNo = view.findViewById(R.id.SealNo);
        applyText = view.findViewById(R.id.applyBut);
        cancelText = view.findViewById(R.id.cancelBut);




        ConsumerName.setText(nameSearch);
        CTMake.setText(CTMakeSearch);
        AccNo.setText(AccNoSearch);
        MeterNo.setText(MeterNoSearch);
        MeterMake.setText(MeterMakeSearch);
        Division.setText(SubDivSearch);
        Section.setText(SectionSearch);
        CTCoilRatio.setText(CTRatioSearch);
        PoNo.setText(PoNoSearch);
        Tariff.setText(TariffSearch);
        SealNo.setText(SealNoSearch);
        _Class.setText(ClassSearch);
        ServiceDate.setText(ServiceDateSearch);
        Load.setText(LoadSearch);
        MF.setText(MFSearch);

        applyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                mainActivity.closeSearchFrag();
            }
        });
        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                nameSearch = "";
                AccNoSearch = "";
                CTMakeSearch = "";
                MeterNoSearch = "";
                MeterMakeSearch = "";
                SubDivSearch = "";
                SectionSearch = "";
                CTRatioSearch = "";
                ClassSearch = "";
                TariffSearch = "";
                SealNoSearch = "";
                LoadSearch = "";
                MFSearch = "";
                PoNoSearch = "";
                ServiceDateSearch = "";
                mainActivity.search();
                mainActivity.closeSearchFrag();
            }
        });
        relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
        ConsumerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               nameSearch =charSequence.toString();

               mainActivity.search();
               relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        CTMake.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   CTMakeSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        AccNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                AccNoSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        MeterNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 MeterNoSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        MeterMake.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 MeterMakeSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Division.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 SubDivSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Section.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  SectionSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        CTCoilRatio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CTRatioSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        PoNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 PoNoSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Tariff.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    TariffSearch = charSequence.toString();
                     mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        SealNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  SealNoSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        _Class.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ClassSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ServiceDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ServiceDateSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Load.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 LoadSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        MF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 MFSearch = charSequence.toString();
                mainActivity.search();
                relevantResults.setText("Relevant Results"+"("+mainActivity.meterDataList.size()+")");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        return view;
    }
}