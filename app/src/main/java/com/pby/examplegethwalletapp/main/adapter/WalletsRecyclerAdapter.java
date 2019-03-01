package com.pby.examplegethwalletapp.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pby.examplegethwalletapp.R;

public class WalletsRecyclerAdapter extends RecyclerView.Adapter<WalletsRecyclerAdapter.WalletsViewHolder> {

    private String[] mAccountArray;

    public WalletsRecyclerAdapter(String[] accountArray){
        mAccountArray = accountArray;
    }

    @NonNull
    @Override
    public WalletsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_wallet, viewGroup, false);
        WalletsViewHolder walletsViewHolder = new WalletsViewHolder(v);

        return walletsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletsViewHolder walletsViewHolder, int position) {
        walletsViewHolder.mTxtAddress.setText(mAccountArray[position]);
    }

    @Override
    public int getItemCount() {
        return mAccountArray.length;
    }

    public static class WalletsViewHolder extends RecyclerView.ViewHolder{
        private TextView mTxtAddress;

        public WalletsViewHolder(View itemView){
            super(itemView);
            mTxtAddress = (TextView) itemView.findViewById(R.id.txt_wallet_card);
        }
    }

}
