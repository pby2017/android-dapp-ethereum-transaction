package com.pby.examplegethwalletapp.main.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pby.examplegethwalletapp.R;
import com.pby.examplegethwalletapp.main.model.UserWallet;

import java.util.List;

public class WalletsRecyclerAdapter extends RecyclerView.Adapter<WalletsRecyclerAdapter.WalletsViewHolder> {

    private List<UserWallet> mUserWalletList;

    public WalletsRecyclerAdapter(List<UserWallet> userWalletList){
        mUserWalletList = userWalletList;
    }

    @NonNull
    @Override
    public WalletsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_wallet, viewGroup, false);
        WalletsViewHolder walletsViewHolder = new WalletsViewHolder(v);

        final Context mParentContext = viewGroup.getContext();
        final TextView mTxtAddress = (TextView) v.findViewById(R.id.txt_address_card);
        v.findViewById(R.id.layout_card).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    ClipboardManager clipboardManager = (ClipboardManager) mParentContext.getSystemService(mParentContext.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("address", mTxtAddress.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(mParentContext,"ID가 복사되었습니다.",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        return walletsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletsViewHolder walletsViewHolder, int position) {
        walletsViewHolder.mTxtAddress.setText(mUserWalletList.get(position).getAddress());
        walletsViewHolder.mTxtEther.setText(mUserWalletList.get(position).getEther().toString() + " eth");
    }

    @Override
    public int getItemCount() {
        return mUserWalletList.size();
    }

    public static class WalletsViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mLayoutCard;
        private TextView mTxtAddress;
        private TextView mTxtEther;

        public WalletsViewHolder(View itemView){
            super(itemView);
            mLayoutCard = (LinearLayout) itemView.findViewById(R.id.layout_card);
            mTxtAddress = (TextView) itemView.findViewById(R.id.txt_address_card);
            mTxtEther = (TextView) itemView.findViewById(R.id.txt_ether_card);
        }
    }

}
