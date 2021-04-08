package com.example.ltctapp;
import android.content.ClipData;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Dictionary;
import java.util.List;

public class MeterAdapter extends RecyclerView.Adapter<MeterAdapter.CustomViewHolder> {
    List<MeterData> meterDataList;
    List<Integer> meterIndices;


    public MeterAdapter(List<MeterData> meterDataList,List<Integer> meterIndices){
        this.meterDataList = meterDataList;
        this.meterIndices = meterIndices;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meter_panel, parent, false);

        CustomViewHolder customViewHolder = new CustomViewHolder(view);

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        holder.meterName.setText(meterDataList.get((position)).ConsumerName);
        holder.globalSerialNo = meterDataList.get(position).Sno;

        holder.sNoText.setText(""+(position+1));
        holder.detailsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                 intent.putExtra("POSITION", meterIndices.get(position));
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return meterDataList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public String globalSerialNo;
        public TextView meterName;
        public TextView sNoText;
        public Button detailsBut;
        public View holderView;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            holderView = itemView;
            meterName = (TextView)itemView.findViewById(R.id.meterNameText);
            sNoText = (TextView)itemView.findViewById(R.id.snoText);
            detailsBut = (Button)itemView.findViewById(R.id.detailsBut);
        }
    }

}
